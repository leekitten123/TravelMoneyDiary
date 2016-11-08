package com.example.hyojin.travelmoneydiary;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.github.mikephil.charting.charts.PieChart;

import java.util.ArrayList;

public class SearchActivity  extends AppCompatActivity {

    DBManager dbManager_expense = new DBManager(this, "expense.db", null, 1);
    DBManager dbManager_income = new DBManager(this, "income.db", null, 1);
    DBAdapter adapter_expense, adapter_income;

    ArrayList<UsageList> ul_expense = new ArrayList<>();
    ArrayList<UsageList> ul_income = new ArrayList<>();

    Button ButtonSearch;
    ListView ExpenseListView, IncomeListView;

    EditText StartDay, EndDay;

    String[] xData ;
    float[] yData ;

    PieChart expense_Chart ;
    MyPieChart expense_MyPieChart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        ButtonSearch = (Button) findViewById(R.id.button_Search);
        ExpenseListView = (ListView) findViewById(R.id.expenseListView);
        IncomeListView = (ListView) findViewById(R.id.incomeListView);

        StartDay = (EditText) findViewById(R.id.editText_StartDay);
        EndDay = (EditText) findViewById(R.id.editText_EndDay);

        expense_Chart = (PieChart) findViewById(R.id.expense_Chart);
        expense_MyPieChart = new MyPieChart(expense_Chart) ;

        ButtonSearch.setOnClickListener(new View.OnClickListener() {
            public void onClick (View v) {
                if (Integer.parseInt(StartDay.getText().toString()) > Integer.parseInt(EndDay.getText().toString())) {
                    Toast.makeText(SearchActivity.this, "잘못된 입력이 있습니다.", Toast.LENGTH_SHORT).show();
                } else {
                    ul_expense.clear();
                    dbManager_expense.getResult(ul_expense, Integer.parseInt(StartDay.getText().toString()), Integer.parseInt(EndDay.getText().toString()));
                    adapter_expense = new DBAdapter(SearchActivity.this, ul_expense, R.layout.expense_row);
                    ExpenseListView.setAdapter(adapter_expense);

                    ul_income.clear();
                    dbManager_income.getResult(ul_income, Integer.parseInt(StartDay.getText().toString()), Integer.parseInt(EndDay.getText().toString()));
                    adapter_income = new DBAdapter(SearchActivity.this, ul_income, R.layout.income_row);
                    IncomeListView.setAdapter(adapter_income);

                    Toast.makeText(SearchActivity.this, "조회 완료", Toast.LENGTH_SHORT).show();
                }

                getXYData() ;
                expense_MyPieChart.setChartName("Expense Chart");
                expense_MyPieChart.setXYData(xData, yData);
                expense_MyPieChart.addData();
            }
        });
    }

    void getXYData() {

        xData = new String[ul_expense.size()] ;
        yData = new float[ul_expense.size()] ;

        for (int i = 0 ; i <ul_expense.size() ; i++) {
            xData[i] = ul_expense.get(i).content;
            yData[i] = ul_expense.get(i).price;
        }

    }
}
