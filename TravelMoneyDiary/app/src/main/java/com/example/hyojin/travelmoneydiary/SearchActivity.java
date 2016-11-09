package com.example.hyojin.travelmoneydiary;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.github.mikephil.charting.charts.PieChart;

import java.util.ArrayList;
import java.util.Calendar;

public class SearchActivity  extends AppCompatActivity {

    DBManager dbManager_expense = new DBManager(this, "expense.db", null, 1);
    DBManager dbManager_income = new DBManager(this, "income.db", null, 1);
    DBAdapter adapter_expense, adapter_income;

    ArrayList<UsageList> ul_expense = new ArrayList<>();
    ArrayList<UsageList> ul_income = new ArrayList<>();

    Button ButtonSearch;
    ListView ExpenseListView, IncomeListView;

    TextView StartDay, EndDay;

    String[] xData_Expense ;
    float[] yData_Expense ;

    String[] xData_Income ;
    float[] yData_Income ;

    PieChart expense_Chart ;
    MyPieChart expense_MyPieChart;

    PieChart income_Chart ;
    MyPieChart income_MyPieChart ;

    int iYears;  //달력 표시할 년도
    int iMonths; //달력 표시할 월
    int iDates;  //달력 표시할 일
    int iYeare;
    int iMonthe;
    int iDatee;
    int cmonths;
    int cmonthe;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        ButtonSearch = (Button) findViewById(R.id.button_Search);
        ExpenseListView = (ListView) findViewById(R.id.expenseListView);
        IncomeListView = (ListView) findViewById(R.id.incomeListView);

        StartDay = (TextView) findViewById(R.id.editText_StartDay);
        EndDay = (TextView) findViewById(R.id.editText_EndDay);

        expense_Chart = (PieChart) findViewById(R.id.expense_Chart);
        expense_MyPieChart = new MyPieChart(expense_Chart) ;

        income_Chart = (PieChart) findViewById(R.id.income_Chart) ;
        income_MyPieChart = new MyPieChart(income_Chart) ;

        Calendar today;                                        //오늘의 정보를 담을 Calendar객체
        today = Calendar.getInstance();                        //today에 객채의 정보 받아옴
        iYears = today.get(Calendar.YEAR);                      //오늘의 년도를 받아서 iYear에 저장
        iMonths = today.get(Calendar.MONTH) + 1;                //오늘의 달을 받아서 iMonth 저장 달은 0~11이므로 1을더함
        iDates = today.get(Calendar.DAY_OF_MONTH);              //오늘의 일을 받아서 iDate에 저장
        iYeare = today.get(Calendar.YEAR);                      //오늘의 년도를 받아서 iYear에 저장
        iMonthe = today.get(Calendar.MONTH) + 1;                //오늘의 달을 받아서 iMonth 저장 달은 0~11이므로 1을더함
        iDatee = today.get(Calendar.DAY_OF_MONTH);              //오늘의 일을 받아서 iDate에 저장
        cmonths=iMonths-1;
        cmonthe=iMonthe-1;

        TextView tvstart = (TextView) findViewById(R.id.editText_StartDay);   //xml에서 첫화면의 calendar부분의 객체를 받아옴
        TextView tvend = (TextView) findViewById(R.id.editText_EndDay);   //xml에서 첫화면의 calendar부분의 객체를 받아옴
        tvstart.setText(iYears + "년 " + iMonths + "월 " + iDates + "일");
        tvend.setText(iYeare + "년 " + iMonthe + "월 " + iDatee + "일");

        ButtonSearch.setOnClickListener(new View.OnClickListener() {
            public void onClick (View v) {
                final int dateints = iYears*10000+(iMonths)*100+iDates;
                final int dateinte = iYeare*10000+(iMonthe)*100+iDatee;
                if (dateints > dateinte) {
                    Toast.makeText(SearchActivity.this, "잘못된 입력이 있습니다.", Toast.LENGTH_SHORT).show();
                } else {
                    ul_expense.clear();

                    dbManager_expense.getResult(ul_expense,dateints,dateinte);
                    adapter_expense = new DBAdapter(SearchActivity.this, ul_expense, R.layout.expense_row);
                    ExpenseListView.setAdapter(adapter_expense);

                    ul_income.clear();

                    dbManager_income.getResult(ul_income, dateints,dateinte);
                    adapter_income = new DBAdapter(SearchActivity.this, ul_income, R.layout.income_row);
                    IncomeListView.setAdapter(adapter_income);

                    Toast.makeText(SearchActivity.this, "조회 완료", Toast.LENGTH_SHORT).show();
                }

                xData_Expense = getXData(ul_expense);
                yData_Expense = getYData(ul_expense) ;
                expense_MyPieChart.setChartName("Expense Chart");
                expense_MyPieChart.setXYData(xData_Expense, yData_Expense);
                expense_MyPieChart.addData();

                xData_Income = getXData(ul_income);
                yData_Income = getYData(ul_income) ;
                income_MyPieChart.setChartName("Income Chart");
                income_MyPieChart.setXYData(xData_Income, yData_Income);
                income_MyPieChart.addData();
            }
        });
    }

    String[] getXData(ArrayList<UsageList> ul) {
        String[] xData = new String[ul.size()];

        for (int i = 0; i < ul.size(); i++) {
            xData[i] = ul.get(i).content;
        }

        return xData ;
    }

    float[] getYData(ArrayList<UsageList> ul) {
        float[] yData = new float[ul.size()];

        for (int i = 0; i < ul.size(); i++) {
            yData[i] = ul.get(i).price;
        }

        return yData ;
    }

    public void onclickstart(View view) {
        DatePickerDialog.OnDateSetListener dateSetListener = new DatePickerDialog.OnDateSetListener() { //datepicker


            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                // TODO Auto-generated method stub
                monthOfYear+=1;
                TextView caltv = (TextView) findViewById(R.id.editText_StartDay);           // calendartv객체를 받아옴
                caltv.setText(year + "년 " + monthOfYear + "월 " + dayOfMonth + "일");           //선택한 년원일으로 caltv에 날짜를 적음

                iYears = year;                 //이부분을 하지 않으면 클릭하여서 날짜를 바꾸면 그게 DatePickerDialog에 반영되지 않음
                iMonths = monthOfYear;
                iDates = dayOfMonth;
                cmonths=monthOfYear-1;


            }
        };
        new DatePickerDialog(this, dateSetListener, iYears, cmonths, iDates).show();      //dateoicker를 보여줌

    }
    public void onclickend(View view) {
        DatePickerDialog.OnDateSetListener dateSetListener = new DatePickerDialog.OnDateSetListener() { //datepicker


            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                // TODO Auto-generated method stub
                monthOfYear+=1;
                TextView caltv = (TextView) findViewById(R.id.editText_EndDay);           // calendartv객체를 받아옴
                caltv.setText(year + "년 " + monthOfYear + "월 " + dayOfMonth + "일");           //선택한 년원일으로 caltv에 날짜를 적음

                iYeare = year;                 //이부분을 하지 않으면 클릭하여서 날짜를 바꾸면 그게 DatePickerDialog에 반영되지 않음
                iMonthe = monthOfYear;
                iDatee = dayOfMonth;
                cmonthe=monthOfYear-1;
            }
        };
        new DatePickerDialog(this, dateSetListener, iYeare, cmonthe, iDatee).show();      //dateoicker를 보여줌
    }

}
