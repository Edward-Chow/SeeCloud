package com.example.seecloud.database;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by monkeysmac on 2017/4/9.
 */
/*
由于现阶段难tingyun.com没有api接口，不能实时获取监测数据，只能采用本地给出的方式模拟算法
 */
public class InitCloudServer {
    private List<CloudServer> clouds = new ArrayList<>();

    private CloudServer jinshan = new CloudServer("jinshan", 1);

    private CloudServer qingyun = new CloudServer("qingyun", 2);

    private CloudServer tengxun = new CloudServer("tengxun", 3);

    private CloudServer UnitedStack = new CloudServer("UnitedStack", 4);

    private CloudServer UCloud = new CloudServer("UCloud", 5);

    private CloudServer huawei = new CloudServer("huawei", 6);

    private CloudServer baidu = new CloudServer("baidu", 7);

    private CloudServer microsoft = new CloudServer("microsoft", 8);

    private CloudServer aws_china = new CloudServer("aws_china", 9);

    private CloudServer ali = new CloudServer("ali", 10);

    private CloudServer yidong = new CloudServer("yidong", 11);

    private CloudServer liantong = new CloudServer("liantong", 12);

    private CloudServer tianyi = new CloudServer("tianyi", 13);

    public InitCloudServer() {
        jinshan.initAttr(0.062, 0.214, 1.791, 11.972, 350.0, 0.286, 0.253, 0.9827, 3597.0, 5);
        qingyun.initAttr(0.069, 0.274, 1.779, 11.786, 288.2, 0.438, 0.245, 0.9942, 3240, 6);
        tengxun.initAttr(0.073, 0.205, 1.374, 11.147, 196.9, 0.162, 0.607, 0.9967, 3480, 2);
        UnitedStack.initAttr(0.092, 0.288, 1.845, 12.219, 272.5, 0.413, 0.316, 0.9965, 3314, 10);
        UCloud.initAttr(0.063, 0.231, 1.487, 11.463, 228.4, 0.282, 0.083, 0.9962, 3087, 4);
        huawei.initAttr(0.062, 0.19, 2.913, 13.776, 320.6, 0.207, 0.232, 0.9820, 3670, 9);
        baidu.initAttr(0.075, 0.218, 1.7, 11.887, 356.6, 0.246, 0.101, 0.9814, 3367, 8);
        microsoft.initAttr(0.073, 0.358, 1.68, 11.474, 442.4, 0.762, 2.140, 0.995, 3214, 7);
        aws_china.initAttr(0.064, 0.19, 1.588, 11.311, 175.3, 0.125, 0.030, 0.9968, 2998, 3);
        ali.initAttr(0.062, 0.211, 1.49, 11.376, 201.5, 0.199, 0.121, 0.9968, 3350, 1);
        yidong.initAttr(0.084, 0.538, 2.931, 13.69, 635.8, 0.666, 1.514, 0.8052, 3180, 13);
        liantong.initAttr(0.219, 0.403, 2.628, 13.74, 579.2, 0.794, 4.856, 0.8757, 858, 12);
        tianyi.initAttr(0.082, 0.287, 2.24, 12.606, 528.2, 0.554, 0.115, 0.9695, 3200, 11);
        CloudServer cloudServer[] = {
                jinshan, qingyun, tengxun, UnitedStack, UCloud, huawei, baidu, microsoft, aws_china, ali, yidong, liantong, tianyi
        };
        for (CloudServer cloud:cloudServer
             ) {
            clouds.add(cloud);
        }
    }

    public List<CloudServer> getClouds() {
        return clouds;
    }

}
