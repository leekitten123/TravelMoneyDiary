package com.example.administrator.expense;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class IncomeActivity extends AppCompatActivity {

    Button ButtonExpense, ButtonIncome, ButtonSave;
    EditText EditTextDate, EditTextContent, EditTextPrice;
    TextView TextViewPrice;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_income);

        ButtonExpense = (Button) findViewById(R.id.button_Expense);
        ButtonIncome = (Button) findViewById(R.id.button_Income);
        ButtonSave = (Button) findViewById(R.id.button_Save);

        EditTextDate = (EditText) findViewById(R.id.editText_Date);
        EditTextContent = (EditText) findViewById(R.id.editText_Content);
        EditTextPrice = (EditText) findViewById(R.id.editText_Price);

        TextViewPrice = (TextView) findViewById(R.id.textView_Price);
    }

    public void onClick_Expense (View v) {
        Intent intent_Expense = new Intent (getApplicationContext(), ExpenseActivity.class);
        startActivity (intent_Expense);
        finish();
    }
}
