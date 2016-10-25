package com.example.hyojin.acticity_main;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.SpannableString;
import android.text.style.UnderlineSpan;
import android.view.View;
import android.widget.DatePicker;
import android.widget.TextView;

import java.util.Calendar;


public class MainActivity extends AppCompatActivity {

    int iYear;  //달력 표시할 년도
    int iMonth; //달력 표시할 월
    int iDate;  //달력 표시할 일


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Calendar today;                                        //오늘의 정보를 담을 Calendar객체
        today = Calendar.getInstance();                        //today에 객채의 정보 받아옴
        iYear = today.get(Calendar.YEAR);                      //오늘의 년도를 받아서 iYear에 저장
        iMonth = today.get(Calendar.MONTH) + 1;                //오늘의 달을 받아서 iMonth 저장 달은 0~11이므로 1을더함
        iDate = today.get(Calendar.DAY_OF_MONTH);              //오늘의 일을 받아서 iDate에 저장
        TextView caltv = (TextView)findViewById(R.id.calendartv);   //xml에서 첫화면의 calendar부분의 객체를 받아옴
        caltv.setText(iYear+"년 "+iMonth+"월 "+iDate+"일");            //위 부분에 현재 년월일을 적음
        TextView eafter = (TextView)findViewById(R.id.exchangeafter);        //환율변동 후에 값을 적어놓음


        SpannableString content = new SpannableString("                        ");//밑줄긋기위해 일단 빈칸으로채움 나중에 실제환율변동된값을 적을예정
        content.setSpan(new UnderlineSpan(),0,content.length(),0);          //밑줄생성
        eafter.setText(content);            //밑줄그음



    }


    public void clickcalendar(View view) {
        DatePickerDialog.OnDateSetListener dateSetListener = new DatePickerDialog.OnDateSetListener() { //datepicker


            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                // TODO Auto-generated method stub

                TextView caltv = (TextView)findViewById(R.id.calendartv);           // calendartv객체를 받아옴
                caltv.setText(year+"년 "+monthOfYear+"월 "+dayOfMonth+"일");           //선택한 년원일으로 caltv에 날짜를 적음

            }
        };
        new DatePickerDialog(MainActivity.this, dateSetListener, iYear, iMonth, iDate).show();      //dateoicker를 보여줌


    }
}

