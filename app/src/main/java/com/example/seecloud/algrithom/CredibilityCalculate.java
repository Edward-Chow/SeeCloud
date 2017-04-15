package com.example.seecloud.algrithom;

import android.util.Log;

import com.example.seecloud.database.CloudServer;
import com.example.seecloud.database.InitCloudServer;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

/**
 * Created by monkeysmac on 2017/4/12.
 */
/*
       connectingTime, firstPackageTime, firstScreenTime, totalDownloadTime, applicationServerResponseTime,cpuUsage, databaseCallingTime, apdex, serverMemoryTakeUp
理想解：    min             min                 min             min                     min                      min             min         max           min
负理想解：  max             max                  max             max                     max                     max             max         min             max
 */

public class CredibilityCalculate {

    //二维数组，横坐标cloud纵坐标属性
    private double[][] X;
    //规范决策矩阵
    private double[][] Z;
    //客观权重
    private double[] W;
    //主观权重
    private double[] W_;
    //客观属性占比权重
    private double Mo;
    //主观属性占比权重
    private double Ms;
    //正理想解数组 属性个数
    private double[] Apositive;
    //负理想解数组
    private double[] Anegative;
    //到正理想解的距离数组 服务商个数
    private double[] Dpositive;
    //到负理想解的距离数组
    private double[] Dnegative;
    //接近程度 服务商个数
    private double[] C;
    //列平方和开方
    private double[] sqrtSubColumnSquare;
    //云服务商个数
    private static final int CLoud_Server_Numbers = 13;
    //客观属性个数
    private static final int Objective_Attribute_Numbers = 9;
    //主观属性个数
    private static final int Subjective_Attribute_Numbers = 9;
    //主观可信度 云服务提供商个数
    private double[][] subjectiveCre = new double[CLoud_Server_Numbers][3];
    //云滴数字特征Ex En He
    private double[][] digital = new double[9][3];

    private String[] clouds = new String[]{
            "金山云", "青云", "腾讯云", "UnitedStack", "UCloud", "华为云", "百度云", "微软云", "AWS-China", "阿里云",
            "移动云", "联通沃云", "天翼云"
    };
    //测试用哦主观信任度数组
    private double[] tempSub = {4.5, 3.7, 4.3, 3.5, 4.7, 4.9, 3.2, 3.9, 4.0, 3.3, 4.4, 3.0, 3.6};

    private List<CloudServer> cloudServerList = new ArrayList<>();

    public CredibilityCalculate(int[] weights) {
        Mo = weights[weights.length-2];
        Ms = weights[weights.length-1];
        double os = Mo + Ms;
        Mo = Mo / os;
        Ms = Ms / os;
        //按照用户设置值宠幸分配权重
        double sum = 0.0;
        double temp = 0.0;
        for (int i = 0; i < Objective_Attribute_Numbers; i++) {
            sum += weights[i];
            Log.e("weight", Double.toString(weights[i]));
        }
        //暂定主客观权重 主观2成 客观8成
        temp = Mo / sum;
        double sumw = 0.0;
        W = new double[Objective_Attribute_Numbers];
        for (int i = 0; i < Objective_Attribute_Numbers; i++) {
            W[i] = temp * weights[i];
            //Log.e("Wi", Double.toString(W[i]));
        }

        //重分配主观权重
        sum = 0.0;
        for (int i = Objective_Attribute_Numbers; i < Objective_Attribute_Numbers + Subjective_Attribute_Numbers; i++) {
            sum += weights[i];
        }
        W_ = new double[Subjective_Attribute_Numbers];
        for (int i = Objective_Attribute_Numbers; i < Objective_Attribute_Numbers + Subjective_Attribute_Numbers; i++) {
            W_[i-9] = weights[i] / sum;
        }

        //计算主观信任度，初始化主观可信度数组
        for (int k = 0; k < CLoud_Server_Numbers; k++) {
            for (int i = 0; i < Subjective_Attribute_Numbers; i++) {
                digital[i] = generateDrop();
            }
            subjectiveCre[k] = calculateCloudModel(digital);
            //Log.e("subjective", subjectiveCre[k][0]+"");
        }

        //初始化X数组 ！！！记得写出主观信任度算法后列坐标+1
        X = new double[CLoud_Server_Numbers][Objective_Attribute_Numbers+1];
        InitCloudServer initCloudServer = new InitCloudServer();
        for (int i = 0; i < CLoud_Server_Numbers; i++) {
            for (int j = 0; j < Objective_Attribute_Numbers+1; j++) {
                X[i][j] = initCloudServer.getClouds().get(i).getAttributes().get(j).getNumber();
                if (j == Objective_Attribute_Numbers) {
                    X[i][j] = subjectiveCre[i][0];
                }
                //Log.e("Xij", X[i][j]+"");
            }
        }

        sqrtSubColumnSquare = new double[Objective_Attribute_Numbers+1];
        sqrtSubColumnSquare = getColumnSquareSumSqrt(X);
        for (int i = 0; i < Objective_Attribute_Numbers+1; i++) {
            //Log.e("sqrt", sqrtSubColumnSquare[i]+"");
        }

        //归一化，生成规范决策矩阵,!!!!列记得在有主观信任度之后+1
        Z = new double[CLoud_Server_Numbers][Objective_Attribute_Numbers+1];
        for (int i = 0; i < CLoud_Server_Numbers; i++) {
            for (int j = 0; j < Objective_Attribute_Numbers+1; j++) {
                if (j == Objective_Attribute_Numbers) {
                    Z[i][j] = X[i][j] / sqrtSubColumnSquare[j] * 0.2;
                } else {
                    Z[i][j] = X[i][j] / sqrtSubColumnSquare[j] * W[j];
                }
            }
        }

        //确定最优方案和最劣方案
        double Xj[] = new double[Z.length];
        Apositive = new double[Objective_Attribute_Numbers+1];
        Anegative = new double[Objective_Attribute_Numbers+1];
        for (int i = 0; i < Z[0].length; i++) {
            for (int j = 0; j < Z.length; j++) {
                Xj[j] = Z[j][i];
            }
            if (i == 7) {
                Apositive[i] = getColumnMax(Xj);
                Anegative[i] = getColumnMin(Xj);
            } else {
                Apositive[i] = getColumnMin(Xj);
                Anegative[i] = getColumnMax(Xj);
            }
            //Log.e("Apositive", Apositive[i]+"");
            //Log.e("Anegative", Anegative[i]+"");
        }

        //计算与最优方案以及最劣方案的距离
        double sump = 0.0;
        double sumn = 0.0;
        Dpositive = new double[CLoud_Server_Numbers];
        Dnegative = new double[CLoud_Server_Numbers];
        for (int i = 0; i < X.length; i++) {
            for (int j = 0; j < X[0].length; j++) {
                sump += Math.pow(Apositive[j]-X[i][j], 2);
                sumn += Math.pow(Anegative[j]-X[i][j], 2);
            }
            Dpositive[i] = Math.sqrt(sump);
            Dnegative[i] = Math.sqrt(sumn);
            sump = 0.0;
            sumn = 0.0;
        }

        //各方案与理想解的接近程度
        C = new double[CLoud_Server_Numbers];
        for (int i = 0; i < CLoud_Server_Numbers; i++) {
            C[i] = Dnegative[i] / (Dnegative[i] + Dpositive[i]);
            Log.e("Ci", Double.toString(C[i]));
        }
        //对服务商按照Ci的大小进行排序
        for (int i = 0; i < C.length-1; i++) {
            for (int j = i+1; j < C.length; j++) {
                if (C[i] < C[j]) {
                    String string = new String();
                    string = clouds[i];
                    clouds[i] = clouds[j];
                    clouds[j] = string;
                }
            }
        }
        for (int i = 0; i < CLoud_Server_Numbers; i++) {
            cloudServerList.add(new CloudServer(clouds[i], i));
        }
    }

    //计算一个云服务商的云模型参数
    public double[] calculateCloudModel(double[][] attr) {
        double ex = 0.0, en, he;
        double temp = 0.0;
        for (int i = 0; i < Subjective_Attribute_Numbers; i++) {
            temp += Math.pow(W_[i], 2);
        }
        for (int i = 0; i < Subjective_Attribute_Numbers; i++) {
            ex += W_[i] * attr[i][0];
        }
        double sum1 = 0.0, sum2 = 0.0;
        for (int i = 0; i < Subjective_Attribute_Numbers; i++) {
            sum1 += attr[i][1] * Math.pow(W_[i], 2);
            sum2 += attr[i][2] * Math.pow(W_[i], 2);
        }
        en = sum1 / temp;
        he = sum2 / temp;
        double[] result = new double[]{ex, en, he};
        return result;
    }

    //为一个属性随机生成100个云滴来假设为用户的评级，并计算期望，熵和超熵
    public double[] generateDrop() {
        double ex, en, he;
        double[] xi = new double[100];
        Random random = new Random();
        DecimalFormat df = new DecimalFormat("#.00");
        for (int i = 0; i < xi.length; i++) {
            xi[i] = Double.parseDouble(df.format(5 * random.nextDouble()));
        }
        double sum = 0.0;
        for (int i = 0; i < xi.length; i++) {
            sum += xi[i];
        }
        ex = sum / xi.length;
        sum = 0;
        for (int i = 0; i < xi.length; i++) {
            sum += Math.abs(xi[i] - ex);
        }
        en = Math.sqrt(Math.PI / 2) * sum / xi.length;
        sum = 0;
        for (int i = 0; i < xi.length; i++) {
            sum += Math.pow(xi[i]-ex, 2);
        }
        he = Math.sqrt(sum/(xi.length-1)-Math.pow(ex, 2));
        double[] result = new double[]{ex, en, he};
        return result;
    }
    //计算一列的平方和的1/2次方
    public double[] getColumnSquareSumSqrt(double[][] X) {
        double[] result = new double[X[0].length];
        double sum = 0;
        //对每一列中的每行的数据进行循环
        for (int j = 0; j < X[0].length; j++) {
            for (int i = 0; i < X.length; i++) {
                sum += Math.pow(X[i][j], 2);
            }
            result[j] = Math.sqrt(sum);
            sum = 0.0;
        }
        return result;
    }

    public double getColumnMax(double Xj[]) {
        Arrays.sort(Xj);
        return Xj[CLoud_Server_Numbers-1];
    }

    public double getColumnMin(double Xj[]) {
        Arrays.sort(Xj);
        return Xj[0];
    }

    public List<CloudServer> getCloudServerList() {
        return cloudServerList;
    }
}
