package com.example.hyojin.travelmoneydiary;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.ContextMenu;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Calendar;

public class IncomeActivity extends AppCompatActivity {

    final DBManager dbManager = new DBManager(this, "income.db", null, 1);

    int iYear;
    int iMonth;
    int iDate;
    Button ButtonExpense, ButtonIncome, ButtonSave;
    EditText EditTextDate, EditTextContent, EditTextPrice;
    TextView TextViewPrice;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_income);
        Calendar today;
        today = Calendar.getInstance();
        iYear = today.get(Calendar.YEAR);
        iMonth = today.get(Calendar.MONTH) + 1;
        iDate = today.get(Calendar.DAY_OF_MONTH);
        TextView caltv = (TextView) findViewById(R.id.incomedate);
        caltv.setText(iYear + "년 " + iMonth + "월 " + iDate + "일");
        iMonth-=1;
        Button contrybtn = (Button)findViewById(R.id.incomeCountry);
        registerForContextMenu(contrybtn);

        ButtonExpense = (Button) findViewById(R.id.button_Expense);
        ButtonIncome = (Button) findViewById(R.id.button_Income);
        ButtonSave = (Button) findViewById(R.id.button_Save);

        TextView imcomedate = (TextView) findViewById(R.id.incomedate);

        String temp = imcomedate.getText().toString();

        final int dateint = iYear*10000+(iMonth+1)*100+iDate;

        EditTextContent = (EditText) findViewById(R.id.editText_Content);
        EditTextPrice = (EditText) findViewById(R.id.editText_Price);

        TextViewPrice = (TextView) findViewById(R.id.textView_Price);

        ButtonSave.setOnClickListener(new View.OnClickListener() {
            public void onClick (View v) {
                if (EditTextContent.getText().toString().equals("") || EditTextPrice.getText().toString().equals("")) {
                    Toast.makeText(IncomeActivity.this, "잘못된 입력이 있습니다.", Toast.LENGTH_SHORT).show();
                    clear();
                } else {
                    dbManager.insert(dateint, EditTextContent.getText().toString(), Integer.parseInt(EditTextPrice.getText().toString()));
                    Log.i("저장", "성공");
                    Toast.makeText(IncomeActivity.this, "정상 입력 되었습니다.", Toast.LENGTH_SHORT).show();
                    clear();
                }
            }
        });
    }

    public void onClick_Expense (View v) {
        Intent intent_Expense = new Intent (getApplicationContext(), ExpenseActivity.class);
        startActivity (intent_Expense);
        finish();
    }

    void clear() {

        EditTextContent.setText("");
        EditTextPrice.setText("");
    }

    public void clickincomedate(View v){
        DatePickerDialog.OnDateSetListener dateSetListener = new DatePickerDialog.OnDateSetListener() { //datepicker


            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                // TODO Auto-generated method stub
                monthOfYear+=1;
                TextView caltv = (TextView) findViewById(R.id.incomedate);           // calendartv객체를 받아옴
                caltv.setText(year + "년 " + monthOfYear + "월 " + dayOfMonth + "일");           //선택한 년원일으로 caltv에 날짜를 적음

                iYear = year;                 //이부분을 하지 않으면 클릭하여서 날짜를 바꾸면 그게 DatePickerDialog에 반영되지 않음
                iMonth = monthOfYear-1;
                iDate = dayOfMonth;
            }
        };

        new DatePickerDialog(this, dateSetListener, iYear, iMonth, iDate).show();      //dateoicker를 보여줌
    }
    public void onCreateContextMenu (ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo){
        super.onCreateContextMenu(menu, v, menuInfo);
        // 컨텍스트 메뉴가 최초로 한번만 호출되는 콜백 메서드

        menu.setHeaderTitle("Choose Country");
        menu.add(0, 1, 100, "달러");
        menu.add(0, 2, 100, "엔화");
        menu.add(0, 3, 100, "유로");
        menu.add(0, 4, 100, "위안");
        menu.add(0, 5, 100, "한화");
    }


}
