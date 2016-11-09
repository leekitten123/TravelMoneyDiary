package com.example.hyojin.travelmoneydiary;

import android.graphics.Color;

import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.formatter.PercentFormatter;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;

public class MyPieChart {
    PieChart PieChart ;

    float[] yData ;
    String[] xData ;

    MyPieChart(PieChart PieChart) {
        this.PieChart = PieChart ;

        PieChart.setUsePercentValues(true);

        PieChart.setRotationAngle(0) ;
        PieChart.setRotationEnabled(true) ; // enable rotation of the chart by touch
    }

    void addData() {
        ArrayList<Entry> yVals1 = new ArrayList<Entry>();
        for (int i = 0; i < yData.length; i++)
            yVals1.add(new Entry(yData[i], i));

        ArrayList<String> xVals = new ArrayList<String>();
        for (int i = 0; i < xData.length; i++)
            xVals.add(xData[i]);

        // create pie data set
        PieDataSet dataSet = new PieDataSet(yVals1, "List"); // 우측 최하단에 스트링 출력
        dataSet.setSliceSpace(3); // 숫자가 크면 원 그래프에서 데이터간의 간격 증가
        dataSet.setSelectionShift(5); // 숫자가 크면 반지름 감소

        // add many colors
        ArrayList<Integer> colors = new ArrayList<Integer>();
        for (int c : ColorTemplate.VORDIPLOM_COLORS)
            colors.add(c);
        for (int c : ColorTemplate.JOYFUL_COLORS)
            colors.add(c);
        for (int c : ColorTemplate.COLORFUL_COLORS)
            colors.add(c);
        for (int c : ColorTemplate.LIBERTY_COLORS)
            colors.add(c);
        for (int c : ColorTemplate.PASTEL_COLORS)
            colors.add(c);
        colors.add(ColorTemplate.getHoloBlue());
        dataSet.setColors(colors);

        // instantiate pie data object now
        PieData data = new PieData(xVals, dataSet); // x를 Pie dataset에 넣어준다.

        data.setValueFormatter(new PercentFormatter());
        data.setValueTextSize(11f); // 원그래프에서 값 텍스트 크기
        data.setValueTextColor(Color.GRAY); // 그래프 안의 값 텍스트의 색

        PieChart.setData(data); // Pie data값 추가

        // undo all highlights
        PieChart.highlightValues(null);

        // update pie chart
        PieChart.invalidate();
    }

    void setXYData(String[] xData, float[] yData) {
        this.yData = yData ;
        this.xData = xData ;
    }

    void setChartName(String string) {
        PieChart.setDescription(string) ; //차트 우측 하단의 제목 정하기
    }
}
