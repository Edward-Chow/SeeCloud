package com.example.seecloud;

import android.content.Intent;
import android.graphics.Color;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import lecho.lib.hellocharts.listener.ComboLineColumnChartOnValueSelectListener;
import lecho.lib.hellocharts.model.Axis;
import lecho.lib.hellocharts.model.AxisValue;
import lecho.lib.hellocharts.model.Column;
import lecho.lib.hellocharts.model.ColumnChartData;
import lecho.lib.hellocharts.model.ComboLineColumnChartData;
import lecho.lib.hellocharts.model.Line;
import lecho.lib.hellocharts.model.LineChartData;
import lecho.lib.hellocharts.model.PointValue;
import lecho.lib.hellocharts.model.SubcolumnValue;
import lecho.lib.hellocharts.util.ChartUtils;
import lecho.lib.hellocharts.view.ComboLineColumnChartView;

public class ChartActivity extends AppCompatActivity {

    private static double intents[] = new double[13];
    private static String name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chart);

        Intent intent = getIntent();
        intents = intent.getDoubleArrayExtra("index");
        name = intent.getStringExtra("name");

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction().add(R.id.chart_container, new ChartFragment()).commit();
        }
    }

    public static class ChartFragment extends Fragment {
        private ComboLineColumnChartView chart;
        private ComboLineColumnChartData data;
        private List<AxisValue> axisValues = new ArrayList<AxisValue>();

        private int numberOfLines = 1;
        private int maxNumberOfLines = 4;
        private int numberOfPoints = 13;

        float[][] numbersTab = new float[maxNumberOfLines][numberOfPoints];

        private boolean hasAxes = true;
        private boolean hasAxesNames = true;
        private boolean hasPoints = true;
        private boolean hasLines = true;
        private boolean isCubic = false;
        private boolean hasLabels = false;

        //用于接收参数

        private final static String[] clouds = new String[]{
                "金山云", "青云", "腾讯云", "UnitedStack", "UCloud", "华为云", "百度云", "微软云", "AWS-China", "阿里云",
                "移动云", "联通沃云", "天翼云"
        };

        public ChartFragment() {
        }

        @Nullable
        @Override
        public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
            View view = inflater.inflate(R.layout.fragment_chart, container, false);

            chart = (ComboLineColumnChartView) view.findViewById(R.id.chart);

            generateValues();
            generateData();

            return view;
        }

        private void generateValues() {
            for (int i = 0; i < maxNumberOfLines; i++) {
                for (int j = 0; j < clouds.length; j++) {
                    numbersTab[i][j] = (float)intents[j];
                }
            }
        }

        private void generateData() {
            // Chart looks the best when line data and column data have similar maximum viewports.
            data = new ComboLineColumnChartData(generateColumnData(), generateLineData());

            if (hasAxes) {
                Axis axisX = new Axis();
                Axis axisY = new Axis().setHasLines(true);
                axisX.setValues(axisValues);
                if (hasAxesNames) {
                    axisX.setName("云服务商");
                    //从上个activity获取
                    axisY.setName(name);
                }
                data.setAxisXBottom(axisX);
                data.setAxisYLeft(axisY);
            } else {
                data.setAxisXBottom(null);
                data.setAxisYLeft(null);
            }

            chart.setComboLineColumnChartData(data);
        }

        private LineChartData generateLineData() {

            List<Line> lines = new ArrayList<Line>();
            /*for (int i = 0; i < numberOfLines; i++) {

                List<PointValue> values = new ArrayList<PointValue>();
                for (int j = 0; j < numberOfPoints; j++) {
                    //给出折线对应cloud点的值
                    values.add(new PointValue(j, (float)intents[j]));
                }*/
            List<PointValue> pointValueList = new ArrayList<>();
            for (int i = 0; i < numberOfPoints; ++i) {
                pointValueList.add(new PointValue(i, (float)intents[i]));
                axisValues.add(new AxisValue(i).setLabel(clouds[i]));
            }

                Line line = new Line(pointValueList);
                line.setColor(Color.RED);
                line.setCubic(isCubic);
                line.setHasLabels(hasLabels);
                line.setHasLabelsOnlyForSelected(true);
                line.setHasLines(hasLines);
                line.setHasPoints(hasPoints);
                lines.add(line);


            LineChartData lineChartData = new LineChartData(lines);

            return lineChartData;

        }

        private ColumnChartData generateColumnData() {
            int numSubcolumns = 1;
            int numColumns = clouds.length;
            // Column can have many subcolumns, here by default I use 1 subcolumn in each of 8 columns.
            List<Column> columns = new ArrayList<Column>();

            List<SubcolumnValue> values;
            for (int i = 0; i < numColumns; i++) {

                values = new ArrayList<SubcolumnValue>();
                for (int j = 0; j < numSubcolumns; j++) {
                    //给出列值
                    values.add(new SubcolumnValue((float) intents[i], ChartUtils.pickColor()).setLabel(clouds[i]));
                }
                //点击柱状显示数量
                axisValues.add(new AxisValue(i).setLabel(clouds[i]));
                columns.add(new Column(values).setHasLabelsOnlyForSelected(true));
            }

            ColumnChartData columnChartData = new ColumnChartData(columns);
            columnChartData.setValueLabelBackgroundColor(Color.BLACK);
            columnChartData.setAxisXBottom(new Axis(axisValues).setHasLines(true));
            //columnChartData.setAxisYLeft(new Axis().setHasLines(true).setMaxLabelChars(4));

            return columnChartData;
        }

        private class ValueTouchListener implements ComboLineColumnChartOnValueSelectListener {
            @Override
            public void onValueDeselected() {
                // TODO Auto-generated method stub

            }

            @Override
            public void onColumnValueSelected(int columnIndex, int subcolumnIndex, SubcolumnValue value) {
                Toast.makeText(getActivity(), "Selected column: " + value, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onPointValueSelected(int lineIndex, int pointIndex, PointValue value) {
                Toast.makeText(getActivity(), "Selected line point: " + value, Toast.LENGTH_SHORT).show();
            }

        }
    }


}

