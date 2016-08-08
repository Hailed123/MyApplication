package com.example.moment.myapplication;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ListView;

import java.util.ArrayList;

/**
 * Created by Moment on 2016/7/19.
 */
public class NearActivity extends Activity {
    private ListView nearCityList;
    private ArrayList<String> citys;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.nearcity_layout);
        nearCityList=(ListView)findViewById(R.id.near_city_list);

    }
}
