package com.example.hangyou;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


public class DataBaseHelper extends SQLiteOpenHelper {
    static String name="HANGYOU.db";
    static int dbVersion=1;
    public DataBaseHelper(Context context) {
        super(context, name, null, dbVersion);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String sql="create table if not exists HANGYOU(id integer primary key autoincrement,schedule varchar(100),begintime varchar(100),ddl varchar(100),isIm integer,isUr integer)";
        sqLiteDatabase.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
