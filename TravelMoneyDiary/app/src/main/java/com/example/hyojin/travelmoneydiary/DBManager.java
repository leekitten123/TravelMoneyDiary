package com.example.hyojin.travelmoneydiary;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.Toast;

import java.util.ArrayList;

public class DBManager extends SQLiteOpenHelper {

    // DBManager 객체로 관리할 Database 이름과 버전 정보를 받음
    public DBManager (Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super (context, name, factory, version);
    }

    // Database 를 생성할 때 호출되는 메서드
    @Override
    public void onCreate (SQLiteDatabase db) {
        // 새로운 Table 생성
        db.execSQL("CREATE TABLE database (_id INTEGER PRIMARY KEY AUTOINCREMENT, date INTEGER , content TEXT , price INTEGER);");
    }

    // Database 업그레이드를 위해 버전이 변경될 때 호출되는 함수
    @Override
    public void onUpgrade (SQLiteDatabase db, int oldVersion, int newVersion) {
    }

    public void insert (int date, String content, int price) {
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL("INSERT INTO database VALUES(NULL, " + date + ", '" + content + "', " + price + ");");
        db.close();
    }

    public void getResult (ArrayList<UsageList> ul, int startDay, int endDay) {
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM database", null);

        while (cursor.moveToNext()) {
            int date = cursor.getInt(cursor.getColumnIndex("date"));
            String content = cursor.getString(cursor.getColumnIndex("content"));
            int price = cursor.getInt(cursor.getColumnIndex("price"));

            Log.i ("SQL ", "select : " + "(date:" + date + ")(content:" + content + ")(price:" + price + ")");

            UsageList usageList = new UsageList();

            if (date >= startDay && date <= endDay) {
                usageList.date = date;
                usageList.content = content;
                usageList.price = price;

                ul.add(usageList);
            }
        }
    }

    public int todayTotal (int todayDate) {
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM database", null);

        int todayTotal = 0;

        while (cursor.moveToNext()){
            int date = cursor.getInt(cursor.getColumnIndex("date"));
            int price = cursor.getInt(cursor.getColumnIndex("price"));

            if (date == todayDate) {
                todayTotal = todayTotal + price;
            }
        }

        return todayTotal;
    }
}
