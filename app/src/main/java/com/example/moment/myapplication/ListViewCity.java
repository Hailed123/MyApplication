package com.example.moment.myapplication;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.example.moment.myapplication.adapter.CityAdapter;
import com.example.moment.myapplication.module.CityItem;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Moment on 2016/7/22.
 */
public class ListViewCity extends Activity {
    String[] indexs = {"A", "B", "C", "D", "E", "F", "G", "H", "J", "K", "L", "M", "N", "P", "Q", "R", "S", "T", "W", "X", "Y", "Z"};
    ArrayList<CityItem> citys = new ArrayList<>();

    ListView allCityList;
    LinearLayout cityIndexLl;
    int itemHeight;
    LinearLayout.LayoutParams params;
    TextView showIndex;
    //key:存放字母     value：存放该字母在城市集合中出现的第一个位置
    Map<String , Integer> selector = new HashMap<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_city);

        getData();
        allCityList = (ListView) findViewById(R.id.all_city_list);
        CityAdapter ca = new CityAdapter(this , citys);
        allCityList.setAdapter(ca);

        cityIndexLl = (LinearLayout) findViewById(R.id.city_index_ll);
        //在onCreate方法中，获取任何控件的高度，拿到的都是0
        showIndex = (TextView) findViewById(R.id.show_index);
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {//Activity一加载完毕，就触发
        super.onWindowFocusChanged(hasFocus);
        int length = cityIndexLl.getHeight();
        Log.e("TAG", "右边的高度是:" + length);
        itemHeight = length / indexs.length;
        params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT , itemHeight);
        setRightView();
    }

    private void setRightView() {

        for(int i = 0 ; i < indexs.length ; i++){
            TextView txt = new TextView(this);
            //宽高
            txt.setLayoutParams(params);
            txt.setText(indexs[i]);
            cityIndexLl.addView(txt);
        }
        cityIndexLl.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {

                //知道触摸的是哪个区域
                float y = event.getY();
                //获取触摸点在数组中所处的索引
                int i = (int) (y / itemHeight);
                if(i < indexs.length) {
                    //通过索引获取对应的字母
                    String key = indexs[i];
                    //获取这个字母在城市集合中出现的第一个位置
                    int position = selector.get(key);

                    //设置ListView显示项的开始位置
                    allCityList.setSelectionFromTop(position, 0);//使用setSelectionFromTop来定位ListView
                    showIndex.setText(key);

                    switch (event.getAction()) {
                        case MotionEvent.ACTION_DOWN://单点按下
                            cityIndexLl.setBackgroundColor(Color.parseColor("#cccccc"));
                            showIndex.setVisibility(View.VISIBLE);
                            break;

                        case MotionEvent.ACTION_UP:
                            cityIndexLl.setBackgroundColor(Color.parseColor("#00ffffff"));
                            showIndex.setVisibility(View.GONE);//单点松开
                            break;
                    }
                }
                return true;
            }
        });

    }

    //获取所有城市
    //通过对应的字母，找到在R.Array类中对应字母属性的值
    public void getData() {
        //获取资源数组
        //反射第一步：获取你要操作的类的类对象
        try {
            Class cls = R.array.class;
            for (int i = 0; i < indexs.length; i++) {
                //反射
                //2：根据字符串名称获取对应的属性
                Field f = cls.getField(indexs[i]);
                //3.获取属性对应的属性值
                //此参数代表用来调用这个方法的对象，它必须是个R.array对象
                int id = f.getInt(null);
                String[] ary = getResources().getStringArray(id); //getResources()获取存在系统的资源
                String str = "";
                for(int j = 0 ; j < ary.length ; j++){
                    str += ary[j] + ",";
                    CityItem ci = new CityItem();
                    ci.cityName = ary[j];
                    ci.cityIndex = indexs[i];
                    citys.add(ci);
                }
                System.out.println(indexs[i] + "   " + str);
            }

            for(int i = 0 ; i < indexs.length ; i++){
                for(int j = citys.size() - 1 ; j >= 0 ; j--){
                    if(indexs[i].equals(citys.get(j).cityIndex)){
                        selector.put(indexs[i] , j);
                    }
                }
            }



        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
