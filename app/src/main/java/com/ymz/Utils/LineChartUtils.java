package com.ymz.Utils;

import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.Color;
import android.graphics.drawable.Drawable;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.LimitLine;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.google.gson.Gson;
import com.ymz.Entity.CompositeIndexBean;
import com.ymz.Entity.IncomeBean;
import com.ymz.Entity.UserBean;
import com.ymz.Model.Statistics.StatisticsFragment;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

/**
 * Administrator  ：zhouyuru
 * 2020/10/15
 * Describe ：
 */
public class LineChartUtils {

    private static XAxis xAxis;                //X轴
    private static YAxis leftYAxis, rightYaxis;            //左侧Y轴
    private static Legend legend;              //图例
    private static String x1[] = {"周一", "周二", "周三", "周四", "周五", "周六", "周日"};
    private static String Y1[] = {"0", "10", "20", "30", "40", "50", "60"};
    private static LineChart lineCharts;

    public LineChartUtils(LineChart lineChart) {
        this.lineCharts = lineChart;
        leftYAxis = lineChart.getAxisLeft();
        rightYaxis = lineChart.getAxisRight();
        xAxis = lineChart.getXAxis();
        initChart(lineChart);
    }

    /**
     * 初始化图表
     */
    public static void initChart(LineChart lineChart) {
        /***图表设置***/
        //是否展示网格线
        lineChart.setDrawGridBackground(false);
        lineChart.setBackgroundColor(Color.WHITE);
        //是否显示边界
        lineChart.setDrawBorders(false);
        //是否可以拖动
//        lineChart.setDragEnabled(false);
        lineChart.setDoubleTapToZoomEnabled(false);
        //是否有触摸事件
//        lineChart.setTouchEnabled(true);
//        //设置XY轴动画效果
//        lineChart.animateY(2500);
//        lineChart.animateX(1500);
//        去掉右下角的标签文字
        Description description = new Description();
//        description.setText("需要展示的内容");
        description.setEnabled(false);
        lineChart.setDescription(description);

        /***XY轴的设置***/
        xAxis = lineChart.getXAxis();
//        左侧y轴
        leftYAxis = lineChart.getAxisLeft();
        //  右侧y轴
        rightYaxis = lineChart.getAxisRight();
        //X轴设置显示位置在底部
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setAxisMinimum(0f);
        xAxis.setGranularity(1f);
        //保证Y轴从0开始，不然会上移一点
        leftYAxis.setAxisMinimum(0f);
        rightYaxis.setAxisMinimum(0f);
//      那是 X Y轴自己的网格线，禁掉即可
        xAxis.setDrawGridLines(false);
        leftYAxis.setDrawGridLines(false);
//        目标效果图没有右侧Y轴，所以去掉右侧Y轴
        rightYaxis.setEnabled(false);
        rightYaxis.setDrawGridLines(false);
        xAxis.setValueFormatter(new IAxisValueFormatter() {
            @Override
            public String getFormattedValue(float value, AxisBase axis) {
                int pos = (int) value;
                String name = x1[pos];
                return name;
            }
        });
        xAxis.setLabelCount(7, false);
        leftYAxis.setValueFormatter(new IAxisValueFormatter() {
            @Override
            public String getFormattedValue(float value, AxisBase axis) {
//                int pos1 = (int) value;
//                String name1 = Y1[pos1];
//                return name1;
                return ((int) (value) + "");

            }
        });
        leftYAxis.setLabelCount(8, false);
        //是否显示边界
        lineChart.setDrawBorders(false);
        lineChart.setDrawGridBackground(false);
        /***折线图例 标签 设置***/
        legend = lineChart.getLegend();
        //设置显示类型，LINE CIRCLE SQUARE EMPTY 等等 多种方式，查看LegendForm 即可
        legend.setForm(Legend.LegendForm.LINE);
        legend.setTextSize(14f);
        //显示位置 左下方
        legend.setVerticalAlignment(Legend.LegendVerticalAlignment.TOP);
        legend.setHorizontalAlignment(Legend.LegendHorizontalAlignment.LEFT);
        legend.setOrientation(Legend.LegendOrientation.HORIZONTAL);
        //是否绘制在图表里面
        legend.setDrawInside(false);
//        //是否显示
//        legend.setEnabled(false);
    }

    /**
     * 曲线初始化设置 一个LineDataSet 代表一条曲线
     *
     * @param lineDataSet 线条
     * @param color       线条颜色
     * @param mode
     */
    public static void initLineDataSet(LineDataSet lineDataSet, int color, LineDataSet.Mode mode) {
        lineDataSet.setColor(color);
        lineDataSet.setCircleColor(color);
        lineDataSet.setLineWidth(1f);
        lineDataSet.setCircleRadius(3f);

//新加的
        lineDataSet.setDrawCircles(false);
        lineDataSet.setDrawValues(false);
        //设置曲线值的圆点是实心还是空心
        lineDataSet.setDrawCircleHole(false);
        lineDataSet.setValueTextSize(10f);
        //设置折线图填充
        lineDataSet.setDrawFilled(true);
        lineDataSet.setFormLineWidth(1f);
        lineDataSet.setFormSize(15.f);
        if (mode == null) {
            //设置曲线展示为圆滑曲线（如果不设置则默认折线）
            lineDataSet.setMode(LineDataSet.Mode.CUBIC_BEZIER);
        } else {
            lineDataSet.setMode(mode);
        }
    }

    /**
     * 展示曲线
     *
     * @param dataList 数据集合
     * @param name     曲线名称
     * @param color    曲线颜色
     */
    public static void showLineChart(List<IncomeBean> dataList, String name, int color) {
        List<Entry> entries = new ArrayList<>();
        for (int i = 0; i < dataList.size(); i++) {
            IncomeBean data = dataList.get(i);
            Entry entry = new Entry(i, (float) data.getValue());
            entries.add(entry);
        }
        // 每一个LineDataSet代表一条线
        LineDataSet lineDataSet = new LineDataSet(entries, name);
        initLineDataSet(lineDataSet, color, LineDataSet.Mode.LINEAR);
//        不显示点，在 initLineDataSet方法中添加
        lineDataSet.setDrawCircles(false);
//        不显示值
        lineDataSet.setDrawValues(false);
        LineData lineData = new LineData(lineDataSet);
        lineCharts.setData(lineData);
    }

    /**
     * 添加曲线
     */
    public static void addLine(List<CompositeIndexBean> dataList, String name, int color) {
        List<Entry> entries = new ArrayList<>();
        for (int i = 0; i < dataList.size(); i++) {
            CompositeIndexBean data = dataList.get(i);
            Entry entry = new Entry(i, (float) data.getRate());
            entries.add(entry);
        }
        // 每一个LineDataSet代表一条线
        LineDataSet lineDataSet = new LineDataSet(entries, name);
        initLineDataSet(lineDataSet, color, LineDataSet.Mode.LINEAR);
        //        不显示点，在 initLineDataSet方法中添加
        lineDataSet.setDrawCircles(false);
//        不显示值
        lineDataSet.setDrawValues(false);
        lineCharts.getLineData().addDataSet(lineDataSet);
        lineCharts.invalidate();
    }

    /**
     * 添加曲线
     */
    public static void addUserLine(List<UserBean> dataList, String name, int color) {
        List<Entry> entries = new ArrayList<>();
        for (int i = 0; i < dataList.size(); i++) {
            UserBean data = dataList.get(i);
            Entry entry = new Entry(i, (float) data.getRate());
            entries.add(entry);
        }
        // 每一个LineDataSet代表一条线
        LineDataSet lineDataSet = new LineDataSet(entries, name);
        initLineDataSet(lineDataSet, color, LineDataSet.Mode.LINEAR);
        //        不显示点，在 initLineDataSet方法中添加
        lineDataSet.setDrawCircles(false);
//        不显示值
        lineDataSet.setDrawValues(false);
        lineCharts.getLineData().addDataSet(lineDataSet);
        lineCharts.invalidate();
    }

    /**
     * 设置线条填充背景颜色
     *
     * @param drawable
     */
    public static void setChartFillDrawable(Drawable drawable) {
        if (lineCharts.getData() != null && lineCharts.getData().getDataSetCount() > 0) {
            LineDataSet lineDataSet = (LineDataSet) lineCharts.getData().getDataSetByIndex(0);
            //避免在 initLineDataSet()方法中 设置了 lineDataSet.setDrawFilled(false); 而无法实现效果
            lineDataSet.setDrawFilled(true);
            lineDataSet.setFillDrawable(drawable);
            lineCharts.invalidate();
        }
    }

//    /**
//     * 设置 可以显示X Y 轴自定义值的 MarkerView
//     */
//    public void setMarkerView(Context context) {
//        LineChartMarkView mv = new LineChartMarkView(context, xAxis.getValueFormatter());
//        mv.setChartView(lineChart);
//        lineChart.setMarker(mv);
//        lineChart.invalidate();
//    }
}
