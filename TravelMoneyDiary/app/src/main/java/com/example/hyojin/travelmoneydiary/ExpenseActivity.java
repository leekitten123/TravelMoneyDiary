package com.example.hyojin.travelmoneydiary;

import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class ExpenseActivity extends AppCompatActivity {

    DBManagerHandler Handler;

    Button ButtonExpense, ButtonIncome, ButtonSave;
    EditText EditTextDate, EditTextContent, EditTextPrice;
    TextView TextViewPrice;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_expense);

        Handler = new DBManagerHandler(getApplicationContext());

        ButtonExpense = (Button) findViewById(R.id.button_Expense);
        ButtonIncome = (Button) findViewById(R.id.button_Income);
        ButtonSave = (Button) findViewById(R.id.button_Save);

        EditTextDate = (EditText) findViewById(R.id.editText_Date);
        EditTextContent = (EditText) findViewById(R.id.editText_Content);
        EditTextPrice = (EditText) findViewById(R.id.editText_Price);

        TextViewPrice = (TextView) findViewById(R.id.textView_Price);

        ButtonSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Handler.insert (Integer.parseInt(EditTextDate.getText().toString()), EditTextContent.getText().toString(), Integer.parseInt(EditTextPrice.getText().toString()));
                Handler.close();
                Toast.makeText(getApplicationContext(), "정상 입력 되었습니다.", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void onClick_Income (View v) {
        Intent intent_Income = new Intent (getApplicationContext(), IncomeActivity.class);
        startActivity (intent_Income);
        finish();
    }
}








