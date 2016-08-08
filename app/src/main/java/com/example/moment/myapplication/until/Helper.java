package com.example.moment.myapplication.until;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Moment on 2016/7/19.
 */
public class Helper extends SQLiteOpenHelper {
    public Helper(Context context){
        super(context,"weather.db",null,1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql = "create table city_tb (" +
                "_id integer primary key autoincrement, " +
                "city_name varchar(20))";
        db.execSQL(sql);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
