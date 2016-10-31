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
        setContentView(R.layout.activity_expense);      //activity_expense

        edittext1 = (EditText)findViewById(R.id.editText1);
        edittext2 = (EditText)findViewById(R.id.editText2);
        edittext3 = (EditText)findViewById(R.id.editText3);

        cost = (TextView)findViewById(R.id.cost);
        date = (TextView)findViewById(R.id.date);
        content = (TextView)findViewById(R.id.content);

        btn1 = (Button)findViewById(R.id.button1);
        btn2 = (Button)findViewById(R.id.button2);
        btn3 = (Button)findViewById(R.id.button3);

        expense_db = new SQLDB(this);       //SQLDB 객체를 생성

        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {               //expense_db 객체에 getWritableDatabase()메소드를 호출
                sql = expense_db.getWritableDatabase(); //SQLiteDatabase 클래스에 반환. 개체 생성됨을 의미
                expense_db.onUpgrade(sql, 1, 2);        //expense_db 객체에 onUpgrade() 메소드 호출, 테이블 초기화
                sql.close();                            //sql 객체를 닫아 다른곳 에서 이용 가능 하도록.
            }
        });

        btn2.setOnClickListener(new View.OnClickListener() {    //작동 안 될 확률 농후. 다시 손 봐야 될 듯.
            @Override
            public void onClick(View v) {
                sql = expense_db.getReadableDatabase();                 //읽을 수 있는 expense_db 객체 생성
                Cursor cursor;                                          //Cursor 클래스 선언
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
                sql = expense_db.getWritableDatabase();             //쓸 수 있는 SQLiteDatabase 객체 생성
                sql.execSQL("INSERT INTO member VALUES("            //sql 객체의 execSQL() 메소드를 통해 데이터를 추가
                        + edittext1.getText().toString() + ",'"
                        + edittext2.getText().toString() + "','"
                        + edittext3.getText().toString() + "');");
                sql.close();
                Toast.makeText(getApplicationContext(), "정보가 저장되었습니다.", Toast.LENGTH_LONG).show();  //토스트 이용하여 밑에 알림 띄우기.
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    public abstract class SQLDB extends SQLiteOpenHelper{
        public SQLDB(Context context) {
            super(context, "human", null, 1);
        }
        @Override
        public void onCreate(SQLiteDataabase expense_db) {
            db.execSQL("create table " + "(cost interger primary key, date interger primary key, address char(30))");
        }
        @Override
        public void onUpgrade(SQLiteDatabase expense_db, int oldVersion, int newVersion) {
            expense_db.execSQL("DROP TABLE IF EXISTS member");
            onCreate(expense_db);
        }

        @Override
        public boolean onOptionsItemSelected(MenuItem item) {
            // Handle action bar item clicks here. The action bar will
            // automatically handle clicks on the Home/Up button, so long
            // as you specify a parent activity in AndroidManifest.xml.
            int id = item.getItemId();

            //noinspection SimplifiableIfStatement
            if (id == R.id.action_settings) {
                return true;
            }

            return super.onOptionsItemSelected(item);


        }
    }
}








