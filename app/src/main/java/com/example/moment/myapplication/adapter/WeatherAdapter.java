package com.example.moment.myapplication.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.moment.myapplication.R;
import com.example.moment.myapplication.module.Weather;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by Moment on 2016/7/19.
 */
public class WeatherAdapter extends BaseAdapter {
    private ArrayList<Weather> weathers;
    private Context context;
    public WeatherAdapter(ArrayList<Weather>weathers,Context context){
        this.weathers=weathers;
        this.context=context;//适配器初始化
    }
    public int getCount() {
        System.out.println("长度"+weathers.size());
        return weathers.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolper vh=null;
        if (convertView==null){
            convertView=LayoutInflater.from(context).inflate(R.layout.weather_item,null);
            //将convertView里面的每个控件都交由ViewHolder的成员来保存
            vh=new ViewHolper();
            vh.weatherItem=(TextView)convertView.findViewById(R.id.weather_item);
            vh.windItem=(TextView)convertView.findViewById(R.id.wind_item);
            vh.temperatureItem=(TextView)convertView.findViewById(R.id.temperature_item);
            vh.dataItem=(TextView)convertView.findViewById(R.id.date_item);
            vh.dayPicItem=(ImageView)convertView.findViewById(R.id.day_item);
            vh.nightPicItem=(ImageView)convertView.findViewById(R.id.night_item);
            convertView.setTag(vh);
        }else {

        }
        Weather w=weathers.get(position);
        String weatherStr = w.getWeather();
        String windStr = w.getWind();
        String temStr = w.getTemperature();
        String dateStr = w.getDate().substring(0,2);
        String dayPicStr = w.getDayPictureUrl();
        String nightPicStr = w.getNightPictureUrl();
        vh.weatherItem.setText(weatherStr);
        vh.windItem.setText(windStr);
        vh.temperatureItem.setText(temStr);
        vh.dataItem.setText(dateStr);
        Picasso.with(context).load(dayPicStr).into(vh.dayPicItem);
        Picasso.with(context).load(nightPicStr).into(vh.nightPicItem);
        return convertView;
    }
    static class ViewHolper{
        TextView weatherItem;
        TextView windItem;
        TextView temperatureItem;
        TextView dataItem;
        ImageView dayPicItem;
        ImageView nightPicItem;
    }
}
