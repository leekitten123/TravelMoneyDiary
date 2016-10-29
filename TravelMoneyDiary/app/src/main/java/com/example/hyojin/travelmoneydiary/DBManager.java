package com.example.hyojin.travelmoneydiary;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBManager extends SQLiteOpenHelper {

    private final static String TABLE_NAME = "Expense";
    public static final String DATABASE_NAME = "Expense.db";
    public static final int DATABASE_VERSION = 1;
    String quary ;

    public DBManager (Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);

        quary = "CREATE TABLE " + TABLE_NAME + "(_id INTEGER PRIMARY KEY AUTOINCREMENT " + "," + "Date INTEGER" + "," + "Content TEXT" + "Price INTEGER " + ");"; // Quary 생성
    }

    //테이블 생성
    @Override
    public void onCreate (SQLiteDatabase db) {
        db.execSQL(quary);
    }

    //업그레이드
    @Override
    public void onUpgrade (SQLiteDatabase db, int oldVersion, int newVersion) {
    }
}
