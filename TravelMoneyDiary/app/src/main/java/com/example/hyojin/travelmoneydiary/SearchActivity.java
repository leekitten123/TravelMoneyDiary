package com.example.hyojin.travelmoneydiary;


import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.formatter.PercentFormatter;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;

public class SearchActivity  extends AppCompatActivity {

    DBManager dbManager = new DBManager(this, "expense.db", null, 1);
    DBAdapter adapter;

    ArrayList<UsageList> ul = new ArrayList<>();

    Button ButtonSearch;
    ListView ExpenseListView;

    private PieChart expense_Chart ;
    float[] yData = { 5, 10, 15, 30, 40 };
    String[] xData = { "Sony", "Huawei", "LG", "Apple", "Samsung" };



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        ButtonSearch = (Button) findViewById(R.id.button_Search);

        ExpenseListView = (ListView) findViewById(R.id.expenseListView);

        expense_Chart = (PieChart) findViewById(R.id.expense_Chart);
        // configure pie chart


        ButtonSearch.setOnClickListener(new View.OnClickListener() {
            public void onClick (View v) {
                ul.clear();
                dbManager.getResult(ul);
                adapter = new DBAdapter(SearchActivity.this, ul, R.layout.expense_row);
                ExpenseListView.setAdapter(adapter);

                for (int i = 0 ; i <ul.size() ; i++) {
                    xData[i] = ul.get(i).content;
                    yData[i] = ul.get(i).price;
                }
                expense_Chart.setUsePercentValues(true); // ???
                expense_Chart.setDescription("Expense Chart"); // 우측 하단의 제목
                // enable rotation of the chart by touch
                expense_Chart.setRotationAngle(0);
                expense_Chart.setRotationEnabled(true); // 터치해서 돌리기 가능

                // add data
                addData();

                Toast.makeText(SearchActivity.this, "조회 완료", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void addData() {

        ArrayList<Entry> yVals1 = new ArrayList<Entry>();
        //yVals에 ydata 추가
        for (int i = 0; i < yData.length; i++)
            yVals1.add(new Entry(yData[i], i));


        ArrayList<String> xVals = new ArrayList<String>();
        //xVals에 xdata 추가
        for (int i = 0; i < xData.length; i++)
            xVals.add(xData[i]);

        // create pie data set
        PieDataSet dataSet = new PieDataSet(yVals1, "kk"); // 우측 최하단에 스트링 출력
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

        expense_Chart.setData(data); // Pie data값 추가

        // undo all highlights
        expense_Chart.highlightValues(null);

        // update pie chart
        expense_Chart.invalidate();
    }
}
