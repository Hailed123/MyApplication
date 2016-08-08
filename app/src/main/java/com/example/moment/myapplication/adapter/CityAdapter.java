package com.example.moment.myapplication.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.moment.myapplication.R;
import com.example.moment.myapplication.module.CityItem;

import java.util.ArrayList;

/**
 * Created by Moment on 2016/7/21.
 */
public class CityAdapter extends BaseAdapter {
    private Context context;
    private ArrayList<CityItem> citys;
    public CityAdapter(Context context, ArrayList<CityItem> citys){
        this.context=context;
        this.citys=citys;
    }
    @Override
    public int getCount() {
        return citys.size();
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
        ViewHolder holder=null;
        if(convertView==null){
          convertView=LayoutInflater.from(context).inflate(R.layout.city_item,null);
            holder=new ViewHolder();
            holder.cityIndex=(TextView)convertView.findViewById(R.id.city_index);
            holder.cityName=(TextView)convertView.findViewById(R.id.city_name);
            convertView.setTag(holder);
        }else {
            holder=(ViewHolder)convertView.getTag();
        }
        CityItem ci=citys.get(position);
        holder.cityName.setText(ci.cityName);
        String nowIndex=ci.cityIndex;
        String exIndex="";
        if(position >0){
            exIndex=citys.get(position-1).cityIndex;
        }
        if(nowIndex.equals(exIndex)){
            holder.cityIndex.setText(ci.cityIndex);
        }else {
            holder.cityIndex.setText(ci.cityIndex);
            holder.cityIndex.setVisibility(View.VISIBLE);
        }
        return convertView;
    }
    static class ViewHolder{
        TextView cityIndex;
        TextView cityName;
    }
}
