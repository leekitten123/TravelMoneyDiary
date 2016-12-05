package com.example.hyojin.travelmoneydiary;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;
import java.util.Calendar;

public class MyBarChart {

    BarChart barChart ;
    String[] dataName = setCalendarDay() ;

    MyBarChart (BarChart barChart) {
        this.barChart = barChart ;
    }

    public void add(ArrayList<UsageList> ul) {
        ArrayList<BarEntry> barEntries = new ArrayList<>();
        ArrayList<String> stringLabels = new ArrayList<>();

        for (int i = 0 ; i < 5 ; i++) {
            stringLabels.add(dataName[i]) ;
        }

        int[] price = new int [5] ;

        for (int i = 0 ; i < ul.size() ; i++) {
            for (int j = 0 ; j < 5 ; j++) {
                if (ul.get(i).date == Integer.parseInt(dataName[j])) {
                    price[j] += ul.get(i).price ;
                }
            }
        }

        for (int i = 0 ; i < 5 ; i++) {
            barEntries.add(new BarEntry(price[i], i));
        }

        BarDataSet barDataSet = new BarDataSet(barEntries, "날자 별 지출") ;
        barDataSet.setColors(ColorTemplate.JOYFUL_COLORS);

        BarData barData = new BarData(stringLabels, barDataSet);
        barChart.setData(barData);
        barChart.animateY(3000);
    }

    public String setCalendar (Calendar calendar, int i) {
        calendar.add(calendar.DATE, i);

        String year = String.valueOf(calendar.get(Calendar.YEAR)) ;
        String month = String.valueOf(calendar.get(Calendar.MONTH) + 1) ;
        String date = String.valueOf(calendar.get(Calendar.DAY_OF_MONTH)) ;

        if (month.length() < 2) {
            month = "0" + month ;
        }

        if (date.length() < 2) {
            date = "0" + date ;
        }

        return (year + month + date) ;
    }

    public String[] setCalendarDay () {

        String[] strings = new String[5] ;

        Calendar today = Calendar.getInstance();

        strings[0] = setCalendar(today, 0) ;
        strings[1] = setCalendar(today, -1);
        strings[2] = setCalendar(today, -1);
        strings[3] = setCalendar(today, -1);
        strings[4] = setCalendar(today, -1);

        return strings ;
    }
}
