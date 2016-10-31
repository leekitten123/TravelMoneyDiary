package com.example.hyojin.travelmoneydiary;

/*
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
                Toast.makeText(getApplicationContext(), "정상 입력 되었습니 다.", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void onClick_Income (View v) {
        Intent intent_Income = new Intent (getApplicationContext(), IncomeActivity.class);
        startActivity (intent_Income);
        finish();
    }
}

*/


import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


public class MainActivity extends AppCompatActivity {

    SQLDB expense_db;       //myDB-> SQLDB  my->expense_db
    EditText edittext1, edittext2, edittext3;
    TextView cost, date, content;   //금액, 날짜, 내용
    Button btn1, btn2, btn3;
    SQLiteDatabase sql;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_expense);      //activity_expense? activity_main?

        edittext1 = (EditText) findViewById(R.id.editText1);
        edittext2 = (EditText) findViewById(R.id.editText2);
        edittext3 = (EditText) findViewById(R.id.editText3);

        cost = (TextView) findViewById(R.id.cost);
        date = (TextView) findViewById(R.id.date);
        content = (TextView) findViewById(R.id.content);

        btn1 = (Button) findViewById(R.id.button1);
        btn2 = (Button) findViewById(R.id.button2);
        btn3 = (Button) findViewById(R.id.button3);

        expense_db = new SQLDB(this);

        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sql = expense_db.getWritableDatabase();
                expense_db.onUpgrade(sql, 1, 2);
                sql.close();
            }
        });

        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sql = expense_db.getReadableDatabase();
                Cursor cursor;
                cursor = sql.rawQuery("SELECT * FROM MEMBER;", null);
                String cost2 = "금액" + "\r\n";
                String date2 = "날짜" + "\r\n";
                String content2 = "내용" + "\r\n";

                while (cursor.moveToNext()) {
                    cost2 += cursor.getString(0) + "\r\n";
                    date2 += cursor.getString(1) + "\r\n";
                    content2 += cursor.getString(2) + "\r\n";
                }
                cost.setText(cost2);
                date.setText(date2);
                content.setText(content2);
                cursor.close();
                sql.close();
            }
        });

        btn3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sql = expense_db.getWritableDatabase();
                sql.execSQL("INSERT INTO member VALUES(" + edittext1.getText().toString() + ",'"
                        + edittext2.getText().toString() + "','"
                        + edittext3.getText().toString() + "');");
                sql.close();
                Toast.makeText(getApplicationContext(), "정보가 저장되었습니다.", Toast.LENGTH_LONG).show();
            }
        });


    }
}








