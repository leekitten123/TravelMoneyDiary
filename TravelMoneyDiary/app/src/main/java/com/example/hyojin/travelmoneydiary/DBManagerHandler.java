package com.example.hyojin.travelmoneydiary;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class DBManagerHandler {
    private DBManager mDBManager;
    private SQLiteDatabase db;

    public DBManagerHandler (Context context) {
        this.mDBManager = new DBManager(context);
    }

    //Database 닫기
    public void close() {
        db.close();
    }

    //Database 에 정보저장
    public void insert (int Date, String Content, int Price) {
        db = mDBManager.getWritableDatabase();
        ContentValues val = new ContentValues();
        val.put ("Date", Date);
        val.put ("Content", Content);
        val.put ("Price", Price);
        db.insert("Expense", null, val);
    }

    //Database 에서 정보 가져오기
    public Cursor select () {
        db = mDBManager.getReadableDatabase();
        Cursor cursor = db.query("Expense", null, null, null, null, null, null);

        return cursor;
    }
}
