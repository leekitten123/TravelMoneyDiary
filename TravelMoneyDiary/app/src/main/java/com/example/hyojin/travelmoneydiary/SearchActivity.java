package com.example.hyojin.travelmoneydiary;


import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class SearchActivity  extends AppCompatActivity {

    DBManager dbManager = new DBManager(this, "expense.db", null, 1);
    DBAdapter adapter;

    ArrayList<UsageList> ul = new ArrayList<>();

    Button ButtonSearch;
    ListView ExpenseListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        ButtonSearch = (Button) findViewById(R.id.button_Search);

        ExpenseListView = (ListView) findViewById(R.id.expenseListView);

        ButtonSearch.setOnClickListener(new View.OnClickListener() {
            public void onClick (View v) {
                ul.clear();
                dbManager.getResult(ul);
                adapter = new DBAdapter(SearchActivity.this, ul, R.layout.expense_row);
                ExpenseListView.setAdapter(adapter);
                Toast.makeText(SearchActivity.this, "조회 완료", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
