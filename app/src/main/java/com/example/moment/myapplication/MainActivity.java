package com.example.moment.myapplication;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.RotateAnimation;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.example.moment.myapplication.adapter.WeatherAdapter;
import com.example.moment.myapplication.module.Weather;
import com.example.moment.myapplication.until.CityUtil;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.ArrayList;

public class MainActivity extends Activity {
    private TextView cityName , time , temperature , weatherDesc , wind , titleCity,all_city;
    private ImageView dayWeatherPic , nightWeatherPic,back_bnt;
    private GridView weatherGrid;
    private ImageButton set_but , updater_but;
    private String currentCity = "长沙";
    private WeatherAdapter adapter;
    private ArrayList<Weather>weathers=new ArrayList();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        myView();
        Log.v("TAG","hah");
        mydate();

    }
    // 初始化控件
    public void myView(){
        cityName=(TextView)findViewById(R.id.city_name);
        time=(TextView)findViewById(R.id.time);
        titleCity=(TextView)findViewById(R.id.text_1);
        temperature=(TextView)findViewById(R.id.temperature);
        wind=(TextView) findViewById(R.id.wind);
        all_city=(TextView)findViewById(R.id.all_city);
        dayWeatherPic=(ImageView)findViewById(R.id.day_weather);
        nightWeatherPic=(ImageView)findViewById(R.id.night_weather);
        set_but=(ImageButton)findViewById(R.id.set_but);
        updater_but=(ImageButton)findViewById(R.id.updater_but);
        updater_but.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Animation am=new RotateAnimation(0,720,RotateAnimation.RELATIVE_TO_SELF,0.5f,RotateAnimation.RELATIVE_TO_SELF,0.5f);
                am.setDuration(1000);//持续时间，旋转动画
                updater_but.startAnimation(am);
                mydate();
            }
        });
        titleCity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder=new AlertDialog.Builder(MainActivity.this);//对话框
                builder.setTitle("请输入城市");
                final EditText txt = new EditText(MainActivity.this);
                builder.setView(txt);
                builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        currentCity = txt.getText().toString();//当前城市
                        mydate();


                    }
                });
                builder.show();
            }
        });


        set_but.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final View view = LayoutInflater.from(MainActivity.this).inflate(R.layout.citywindow_layout, null);
                final PopupWindow pw = new PopupWindow(view, 210, WindowManager.LayoutParams.WRAP_CONTENT);//窗口机制,设置属性
                pw.setBackgroundDrawable(new BitmapDrawable());//设置窗口消失
                pw.setFocusable(true);//设置聚焦
                pw.setOutsideTouchable(true);
                //设置PopupWindow 对话框

                pw.showAsDropDown(set_but, 10, 0);


                view.findViewById(R.id.near_city).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        pw.dismiss();//隐藏对话框
                        Intent it = new Intent(MainActivity.this, NearActivity.class);
                        startActivity(it);


                    }
                });
                view.findViewById(R.id.all_city).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent=new Intent(MainActivity.this,ListViewCity.class);
                        startActivity(intent);
                    }
                });
            }
        });

        weatherGrid=(GridView)findViewById(R.id.weather_grid);
        adapter=new WeatherAdapter(weathers,this);
        weatherGrid.setAdapter(adapter);
    }
    String result;
    Boolean flag=true;//网络请求数据



    public void mydate() {
        new AsyncTask() {
            @Override
            protected Object doInBackground(Object[] params) {

                try {
                    URL url = new URL("http://api.map.baidu.com/telematics/v3/weather?location="+ URLEncoder.encode(currentCity,"utf-8")+"&output=json&ak=7hPzeLgr3NWgPwlpc7BvGUXG4ZnK8Smo");
                    HttpURLConnection con = (HttpURLConnection) url.openConnection();
                    con.setRequestMethod("GET");
                    con.setReadTimeout(6000);/*AsynTask类在继承时要传入3个泛型。第一个泛型对应execute（）
                                              向doInBackground（）的传递类型。
                                               第二个泛型对应doInBackground()的返回类型和传递给onPostExecute()的类型。
                                                第三个泛型对应publishProgress()向progressUpdate（）传递的类型。
                                              传递的数据都是对应类型的数组，数组都是可变长的哦。可以根据具体情况使用。*/

                        if (con.getResponseCode()==200){
                        InputStream in = con.getInputStream();
                        byte[] bytes = new byte[1024*512];
                        ByteArrayOutputStream baos = new ByteArrayOutputStream();
                        int len = 0;
                        while ((len = in.read(bytes)) > 0){
                            baos.write(bytes , 0 , len);
                        }
                        result = new String(baos.toByteArray());
                        Log.v("TAG","result");

                        baos.close();
                        in.close();
                        con.disconnect();

                        JSONObject obj=new JSONObject(result);
                        String stu=obj.getString("stu");
                        if(stu.equals("success")){
                            flag=true;
                            JSONArray results = obj.getJSONArray("results");
                            JSONObject job = results.getJSONObject(0);
                            Gson gson=new Gson();
                            String weatherdata=job.getString("weatherdata");
                            //将具备json形式的字符串转换成相对应的数据对象或者集合
                            weathers.clear();//清除旧数据
                            ArrayList<Weather> data = gson.fromJson(weatherdata, new TypeToken<ArrayList<Weather>>() {
                            }.getType());
                            weathers.addAll(data);


                        }else{
                            flag = false;
                        }
                    }
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                }catch (IOException e){
                    e.printStackTrace();
                }catch (JSONException e){
                    e.printStackTrace();
                }
                return null;
            }

            @Override
            protected void onPostExecute(Object o) {
                super.onPostExecute(o);
                if(! flag){
                    Toast.makeText(MainActivity.this,"请输入正确城市",Toast.LENGTH_SHORT).show();
                }else {
                    CityUtil util=new CityUtil(MainActivity.this);
                    util.SavaCity(currentCity);
                    if (weathers !=null&& weathers .size()>0){
                        cityName.setText(currentCity);
                        titleCity.setText(currentCity);
                        Weather wt=weathers.get(0);
                        String weatherstr=wt.getWeather();
                        String windStr = wt.getWind();
                        String temStr = wt.getTemperature();
                        String dateStr = wt.getDate();
                        String dayPicStr = wt.getDayPictureUrl();
                        String nightPicStr = wt.getNightPictureUrl();
                        time.setText(dateStr);
                        temperature.setText(temStr);
                        weatherDesc.setText(weatherstr);
                        wind.setText(windStr);
                        Picasso.with(MainActivity.this).load(dayPicStr).into(dayWeatherPic);
                        Picasso.with(MainActivity.this).load(nightPicStr).into(nightWeatherPic);
                        adapter.notifyDataSetChanged();
                    }
                }
            }
        }.execute();


    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == RESULT_OK){
            if(requestCode == 1000){
                currentCity = data.getStringExtra("city");
                mydate();
            }
        }
    }

}
