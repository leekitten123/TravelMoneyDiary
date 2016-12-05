package com.example.hyojin.travelmoneydiary;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.Toast;

import com.github.mikephil.charting.charts.BarChart;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Calendar;

public class MainActivity extends AppCompatActivity {

    int iYear;  //달력 표시할 년도
    int iMonth; //달력 표시할 월
    int iDate;  //달력 표시할 일
    private static String Address;
    private static URL url;
    private static BufferedReader br;
    private static HttpURLConnection conn;
    private static String protocol = "GET";
    static int numCountry = 4 ; // 어느나라인가? 0 = 미국, 1 = 일본, 2 = 유로, 3 = 중국, 4 = 한국
    private long lastTimeBackPressed;
    static float[] rate_1 = new float[12] ; // 환율 받아서 저장_1
    static float[] rate_2 = new float[12] ; // 환율 받아서 저장_2

    DBManager dbManager_expense = new DBManager(this, "expense.db", null, 1);
    DBManager dbManager_income = new DBManager(this, "income.db", null, 1);

    TextView TodayIncome, TodayExpense;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Calendar today;                                        //오늘의 정보를 담을 Calendar객체
        today = Calendar.getInstance();                        //today에 객채의 정보 받아옴
        iYear = today.get(Calendar.YEAR);                      //오늘의 년도를 받아서 iYear에 저장
        iMonth = today.get(Calendar.MONTH) + 1;                //오늘의 달을 받아서 iMonth 저장 달은 0~11이므로 1을더함
        iDate = today.get(Calendar.DAY_OF_MONTH);              //오늘의 일을 받아서 iDate에 저장
        TextView caltv = (TextView) findViewById(R.id.calendartv);   //xml에서 첫화면의 calendar부분의 객체를 받아옴
        caltv.setText(iYear + "년 " + iMonth + "월 " + iDate + "일");            //위 부분에 현재 년월일을 적음
        Button countrybtn = (Button)findViewById(R.id.chooseCountrybtn);
        TextView eafter = (TextView) findViewById(R.id.exchangeafter);        //환율변동 후에 값을 적어놓음
        registerForContextMenu(countrybtn);
        iMonth-=1;
        countrybtn.setBackgroundResource(R.drawable.krw);

        //하나은행사이트에서 환율정보를 받아오는 부분
        new Thread() {
            public void run() {                      //어느버전 이상으로는 쓰레드를 사용해야지 웹에서 데이터를 받아올수 있어서 쓰레드를 사용한다
                try {
                    webdata();
                } catch (Exception e) {            //네트워크가 연결되어있지않거나 어떠한 사유로 웹에서 데이터를 받아올수없으면 종료한다
                    e.printStackTrace();

                }
            }
        }.start();      //쓰레드를시작한다

        TodayIncome = (TextView) findViewById(R.id.todayIncome);
        TodayExpense = (TextView) findViewById(R.id.todayExpense);
        int todayIncome = dbManager_income.todayTotal(iYear*10000 + (iMonth+1)*100 + iDate);
        int todayExpense = dbManager_expense.todayTotal(iYear*10000 + (iMonth+1)*100 + iDate);

        TodayIncome.setText(Integer.toString(todayIncome) + "원");
        TodayExpense.setText(Integer.toString(todayExpense) + "원");

        ArrayList<UsageList> ulList = new ArrayList<>() ;
        MyBarChart myBarChart = new MyBarChart((BarChart) findViewById(R.id.barChart));
        dbManager_expense.getResult(ulList, Integer.parseInt(myBarChart.dataName[4]), Integer.parseInt(myBarChart.dataName[0]));
        myBarChart.add(ulList);
    }

    public void webdata() throws Exception {            //하나은행에서 환율정보를 받아오는 메서드
        Address = "http://fx.kebhana.com/fxportal/jsp/RS/DEPLOY_EXRATE/fxrate_all.html";        //자료를 받아올 주소
        url = new URL(Address);
        conn = (HttpURLConnection) url.openConnection();        //연결해줌
        conn.setRequestMethod(protocol);        //언어타입과 문자타입지정
        br = new BufferedReader(new InputStreamReader(conn.getInputStream(), "MS949")); //MS949는 한글표현

        int i = 0, j = 0 ;

        String line ;
        String tempLine ;

        while ((line = br.readLine()) != null) {//웹에서 소스보기를 통하여 원하는 부분의 데이터를 받아옴
            if (line.startsWith("<td class='buy'>")) { // 환율 받기 1
                tempLine = line.replace("<td class='buy'>", "").replace("</td>", "");
                rate_1[i] = Float.parseFloat(tempLine) ;
                i++ ;
            }

            if (line.startsWith("<td class='sell'>")) { // 환율 받기 2
                tempLine = line.replace("<td class='sell'>", "").replace("</td>", "") ;
                rate_2[j] = Float.parseFloat(tempLine) ;
                j++ ;
            }
        }
    }

    public void clickcalendar(View view) {
        DatePickerDialog.OnDateSetListener dateSetListener = new DatePickerDialog.OnDateSetListener() { //datepicker


            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                // TODO Auto-generated method stub
                monthOfYear+=1;
                TextView caltv = (TextView) findViewById(R.id.calendartv);           // calendartv객체를 받아옴
                caltv.setText(year + "년 " + monthOfYear + "월 " + dayOfMonth + "일");           //선택한 년원일으로 caltv에 날짜를 적음

                iYear = year;                 //이부분을 하지 않으면 클릭하여서 날짜를 바꾸면 그게 DatePickerDialog에 반영되지 않음
                iMonth = monthOfYear-1;
                iDate = dayOfMonth;

                int todayIncome = dbManager_income.todayTotal(iYear*10000 + (iMonth+1)*100 + iDate);
                int todayExpense = dbManager_expense.todayTotal(iYear*10000 + (iMonth+1)*100 + iDate);

                TodayIncome.setText(Integer.toString(todayIncome) + "원");
                TodayExpense.setText(Integer.toString(todayExpense) + "원");
            }
        };

        new DatePickerDialog(MainActivity.this, dateSetListener, iYear, iMonth, iDate).show();      //dateoicker를 보여줌
    }

    public void onClick_Page (View view) {  // 페이지 넘기는 온 클릭
        switch(view.getId()) {
            case R.id.BtnPageToWrite:
                Intent intentToWrite = new Intent(getApplicationContext(), ExpenseActivity.class) ;
                startActivity(intentToWrite);
                finish();
                break;

            case R.id.BtnPageToSearch :
                Intent intentToSearch = new Intent(getApplicationContext(), SearchActivity.class) ;
                startActivity(intentToSearch);
                finish();
                break;
        }
    }

    public void onClick_Refresh (View v) {
        TextView exchangebefore = (TextView) findViewById(R.id.exchangebefore) ;
        TextView excahngeafter = (TextView) findViewById(R.id.exchangeafter) ;

        String[] currencyUnit = {"Dollar", "Yen", "Euro", "Yuan", "Won" } ;
        if(numCountry==1){
            exchangebefore.setText("100" + currencyUnit[numCountry]);
        }
        else {
            exchangebefore.setText("1 " + currencyUnit[numCountry]);
        }
        excahngeafter.setText(String.valueOf(setMoneytoKorea(numCountry, 1)) + " Won");
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

    public boolean onContextItemSelected (MenuItem item){
        Button countrybtn = (Button)findViewById(R.id.chooseCountrybtn);
        // 롱클릭했을 때 나오는 context Menu 의 항목을 선택(클릭) 했을 때 호출

        switch (item.getItemId()) {
            case 1:// 달러
                countrybtn.setBackgroundResource(R.drawable.dollor);
                numCountry = 0 ;
                return true;

            case 2:// 엔화
                countrybtn.setBackgroundResource(R.drawable.jpy);
                numCountry = 1 ;
                return true;

            case 3:// 유로
                countrybtn.setBackgroundResource(R.drawable.eur);
                numCountry = 2 ;
                return true;

            case 4:// 위안
                countrybtn.setBackgroundResource(R.drawable.cny);
                numCountry = 3 ;
                return true;

            case 5: // 한국돈
                countrybtn.setBackgroundResource(R.drawable.krw);
                numCountry = 4 ;
                return true;
        }

        return super.onContextItemSelected(item);
    }

    public void countryclick(View v){

    }

    public float setMoneytoKorea (int numCounrty, int money) {
        // 환율로 한화로 얼만지 계산
        switch (numCountry) {
            case 0:
                return (money * rate_1[4]) ;
            case 1:
                return (money * rate_1[5]) ;
            case 2:
                return (money * rate_1[6]) ;
            case 3:
                return (money * rate_1[7]) ;
            case 4:
                return (money) ;
        }

        return 0 ;
    }

    public void onBackPressed(){
        if(System.currentTimeMillis()-lastTimeBackPressed<1500){
            finish();
            return;
        }
        Toast.makeText(MainActivity.this,"'뒤로' 버튼을 한번 더 누르면 종료됩니다.",Toast.LENGTH_SHORT).show();
        lastTimeBackPressed=System.currentTimeMillis();
    }

}

