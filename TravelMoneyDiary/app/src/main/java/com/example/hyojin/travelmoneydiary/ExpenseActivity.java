package com.example.hyojin.travelmoneydiary;

import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class ExpenseActivity extends AppCompatActivity {

    final DBManager dbManager = new DBManager(this, "expense.db", null, 1);

    Button ButtonExpense, ButtonIncome, ButtonSave;
    EditText EditTextDate, EditTextContent, EditTextPrice;
    TextView TextViewPrice;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_expense);

        ButtonExpense = (Button) findViewById(R.id.button_Expense);
        ButtonIncome = (Button) findViewById(R.id.button_Income);
        ButtonSave = (Button) findViewById(R.id.button_Save);

        EditTextDate = (EditText) findViewById(R.id.editText_Date);
        EditTextContent = (EditText) findViewById(R.id.editText_Content);
        EditTextPrice = (EditText) findViewById(R.id.editText_Price);

        TextViewPrice = (TextView) findViewById(R.id.textView_Price);

        ButtonSave.setOnClickListener(new View.OnClickListener() {
            public void onClick (View v) {
                dbManager.insert(Integer.parseInt(EditTextDate.getText().toString()), EditTextContent.getText().toString(), Integer.parseInt(EditTextPrice.getText().toString()));
                Log.i("저장", "성공");
                Toast.makeText(ExpenseActivity.this, "정상 입력 되었습니다.", Toast.LENGTH_SHORT).show();

                clear();
            }
        });
    }

    public void onClick_Income (View v) {
        Intent intent_Income = new Intent (getApplicationContext(), IncomeActivity.class);
        startActivity (intent_Income);
        finish();
    }

    void clear() {
        EditTextDate.setText("");
        EditTextContent.setText("");
        EditTextPrice.setText("");
    }
}








