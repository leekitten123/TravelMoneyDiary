package com.example.hyojin.travelmoneydiary;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBManager extends SQLiteOpenHelper {

    // DBManager 객체로 관리할 Database 이름과 버전 정보를 받음
    public DBManager (Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super (context, name, factory, version);
    }

    // Database 를 생성할 때 호출되는 메서드
    @Override
    public void onCreate (SQLiteDatabase db) {
        // 새로운 Table 생성
        db.execSQL("CREATE TABLE database(_id INTEGER PRIMARY KEY AUTOINCREMENT, date INTEGER , content TEXT , price INTEGER);");
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

    public String getResult () {
        SQLiteDatabase db = getReadableDatabase();
        String result = "";

        Cursor cursor = db.rawQuery("SELECT * FROM database", null);
        while (cursor.moveToNext()) {
            result += cursor.getInt(0) + cursor.getString(1) + cursor.getInt(2);
        }

        return result;
    }
}
