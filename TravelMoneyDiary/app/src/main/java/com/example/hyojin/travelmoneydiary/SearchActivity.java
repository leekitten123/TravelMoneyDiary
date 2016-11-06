package com.example.hyojin.travelmoneydiary;


import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class SearchActivity  extends AppCompatActivity {

    DBManager dbManager_expense = new DBManager(this, "expense.db", null, 1);
    DBManager dbManager_income = new DBManager(this, "income.db", null, 1);
    DBAdapter adapter_expense, adapter_income;

    ArrayList<UsageList> ul_expense = new ArrayList<>();
    ArrayList<UsageList> ul_income = new ArrayList<>();

    Button ButtonSearch;
    ListView ExpenseListView, IncomeListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        ButtonSearch = (Button) findViewById(R.id.button_Search);

        ExpenseListView = (ListView) findViewById(R.id.expenseListView);
        IncomeListView = (ListView) findViewById(R.id.incomeListView);

        ButtonSearch.setOnClickListener(new View.OnClickListener() {
            public void onClick (View v) {
                dbManager_expense.getResult(ul_expense);
                adapter_expense = new DBAdapter(SearchActivity.this, ul_expense, R.layout.expense_row);
                ExpenseListView.setAdapter(adapter_expense);

                dbManager_income.getResult(ul_income);
                adapter_income = new DBAdapter(SearchActivity.this, ul_income, R.layout.income_row);
                IncomeListView.setAdapter(adapter_income);

                Toast.makeText(SearchActivity.this, "조회 완료", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
