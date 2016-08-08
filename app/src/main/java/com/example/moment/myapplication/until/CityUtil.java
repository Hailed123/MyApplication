package com.example.moment.myapplication.until;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

/**
 * Created by Moment on 2016/7/19.实现最近城市
 */
public class CityUtil  {
    SQLiteDatabase db;
    public CityUtil(Context context){
        Helper helper=new Helper(context);
        db=helper.getReadableDatabase();
    }
    public  void SavaCity(String cityName){
        if (hasCity(cityName)){
        deleteCity(cityName);//先删除原有城市
        }
        String sql="insert into city_tb (city_name) values(?)";
        db.execSQL(sql,new String[]{cityName});
    }
    //获取所有最近城市，按_id降序排列
    public ArrayList<String>getAllCity(){
        ArrayList<String> citys = new ArrayList<>();
        String sql = "select * from city_tb order by _id desc";
        Cursor c = db.rawQuery(sql , null);
        while (c.moveToNext()){
            citys.add(c.getString(1));
        }
        return citys;
    }
    // 判断城市是否存在
    public boolean hasCity(String cityName){
        boolean flag = false;

        String sql = "select * from city_tb where city_name = ?";
        Cursor c = db.rawQuery(sql , new String[]{cityName});
        flag = c.moveToFirst();    //true/false

        return flag;
    }
    /**
     * 删除已存在的城市
     * 作用1：长按城市名时，要删除城市  作用2：在添加最近城市时，先删除已经存在的
     */
    public void deleteCity(String cityName){
        String sql = "delete from city_tb where city_name = ?";
        db.execSQL(sql , new String[]{cityName});
    }

}
