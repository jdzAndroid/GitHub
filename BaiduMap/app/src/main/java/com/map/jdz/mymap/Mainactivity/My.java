package com.map.jdz.mymap.Mainactivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.thinkland.sdk.android.Parameters;

import com.map.jdz.mymap.NetworkUtils.JuHeConnectNetUtils;
import com.map.jdz.mymap.R;

/**
 * Created by Administrator on 2015/7/19.
 */
public class My extends Activity implements View.OnClickListener {
    private LinearLayout ly_my_footline,ly_package_offline,ly_collection,ly_normal_address,
            ly_orders,ly_contribute,ly_money,ly_my_nonstop,ly_distance,ly_radar,ly_activity_area,
            ly_setting,ly_out_mymap;
    private Button btn_login,btn_integral;
    private TextView tv_temprature,tv_city,tv_PM,tv_PM_state;
    private String city;
    private Bundle bundle;
    private myHandle handle;
    private JuHeConnectNetUtils juhenetutils;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.my);

        /*
        * 获取从主页面传过来的城市名
        * */
        if (getIntent()!=null&&getIntent().getExtras()!=null){
            bundle=getIntent().getExtras();
            city=bundle.getString("city");
        }


        initView();
        setOnclick();
//        new threadOne().run();
    }



    private void initView() {
        ly_my_footline= (LinearLayout) findViewById(R.id.btn_my_footmark);
        ly_package_offline= (LinearLayout) findViewById(R.id.btn_my_downloadoffline);
        ly_collection=(LinearLayout)findViewById(R.id.btn_my_collect);
        ly_normal_address= (LinearLayout) findViewById(R.id.btn_my_normaladdress);
        ly_orders=(LinearLayout)findViewById(R.id.btn_my_orders);
        ly_contribute=(LinearLayout)findViewById(R.id.btn_my_contribute);
        ly_money=(LinearLayout)findViewById(R.id.btn_my_money);
        ly_my_nonstop=(LinearLayout)findViewById(R.id.btn_my_nonstop);
        ly_distance=(LinearLayout)findViewById(R.id.btn_my_distance);
        ly_radar=(LinearLayout)findViewById(R.id.btn_my_radar);
        ly_setting=(LinearLayout)findViewById(R.id.btn_my_setting);
        ly_out_mymap=(LinearLayout)findViewById(R.id.btn_my_out_mymap);
        btn_login= (Button) findViewById(R.id.btn_login);
        btn_integral=(Button)findViewById(R.id.btn_integral);
        tv_city=(TextView)findViewById(R.id.tv_my_city);
        tv_PM=(TextView)findViewById(R.id.tv_today_PM);
        tv_PM_state=(TextView)findViewById(R.id.tv_PM_state);
        tv_temprature=(TextView)findViewById(R.id.tv_today_temperature);
        ly_activity_area= (LinearLayout) findViewById(R.id.btn_my_activityarea);
    }
    private void setOnclick() {
        ly_my_footline.setOnClickListener(this);
        ly_out_mymap.setOnClickListener(this);
        ly_setting.setOnClickListener(this);
        ly_radar.setOnClickListener(this);
        ly_activity_area.setOnClickListener(this);
        ly_collection.setOnClickListener(this);
        ly_contribute.setOnClickListener(this);
        ly_distance.setOnClickListener(this);
        ly_money.setOnClickListener(this);
        ly_package_offline.setOnClickListener(this);
        ly_normal_address.setOnClickListener(this);
        ly_orders.setOnClickListener(this);
        ly_my_nonstop.setOnClickListener(this);
        btn_login.setOnClickListener(this);
        btn_integral.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            /*
            * 我的足迹
            * */
            case R.id.btn_my_footmark:


                break;
            /*
            * 离线包下载
            * */
            case R.id.btn_my_downloadoffline:


                break;
            /*
            * 我的收藏
            * */

            case R.id.btn_my_collect:


                break;
            /*
            * 常用地址
            * */

            case R.id.btn_my_normaladdress:


                break;

            /*
            * 订单
            * */
            case R.id.btn_my_orders:


                break;

            /*
            * 贡献值
            * */
            case R.id.btn_my_contribute:


                break;
            /*
            * 财富
            * */

            case R.id.btn_my_money:


                break;

            /*
            * 我的直达号
            * */
            case R.id.btn_my_nonstop:


                break;

            /*
            * 测距
            * */
            case R.id.btn_my_distance:


                break;

            /*
            * 身边雷达
            * */
            case R.id.btn_my_radar:


                break;

            /*
            * 设置
            * */
            case R.id.btn_my_setting:
                  startActivity(new Intent(this,Setting_activity.class));

                break;

            /*
            * 退出我的地图
            * */

            case R.id.btn_my_out_mymap:


                break;
            /*
            * 会员登录
            * */

            case R.id.btn_login:


                break;


                /*
                * 积分商城
                * */
            case R.id.btn_integral:


                break;


        }


    }

    private class  myHandle extends Handler
    {


        public myHandle(Looper looper) {
            super(looper);
        }

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case 1:
                    Log.i("------", msg.obj.toString());

                    break;

                case 0:
                    Log.i("------", msg.obj.toString());

                    break;
            }
        }
    }

    private  class  threadOne extends Thread {


        @Override
        public void run() {
            super.run();
            Looper.prepare();
            Looper loop= Looper.myLooper();
            handle=new myHandle(loop);
            Parameters parameters=new Parameters();
            parameters.add("cityname",city);
            parameters.add("dtype","json");
            parameters.add("key","af69adde787d82eb59f4f0d133c4e888");
            juhenetutils=new JuHeConnectNetUtils("temprature","http://v.juhe.cn/weather/index",
                    parameters,getApplicationContext(),39);
            String s=juhenetutils.getJuHeData();
            System.out.print(s+"-------------");
            handle.sendEmptyMessage(1);
            Looper.loop();
        }
    }
}
