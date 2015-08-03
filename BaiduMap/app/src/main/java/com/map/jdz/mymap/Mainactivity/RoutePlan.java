package com.map.jdz.mymap.Mainactivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.map.jdz.mymap.R;

/**
 * Created by John on 2015/6/26.
 */
public class RoutePlan extends Activity implements View.OnClickListener {
    private Button btn_start_search;
    private ImageButton btn_busline,btn_transiteline,btn_walkline,btn_back;
    private RecyclerView recyclerView;
    private EditText et_startaddress,et_endaddress;
    private LinearLayout ly_gohome,ly_gocomplay;
    private Bundle bundle;
    private String start_address,end_address,city;
    private String flag="bus";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.routeplan);
        initView();
    }

    private void initView() {
        btn_back=(ImageButton)findViewById(R.id.btn_back);
        btn_busline=(ImageButton)findViewById(R.id.btn_busline);
        btn_transiteline=(ImageButton)findViewById(R.id.btn_transiteline);
        btn_walkline=(ImageButton)findViewById(R.id.btn_walkline);
        et_endaddress=(EditText)findViewById(R.id.tv_endaddress);
        et_startaddress=(EditText)findViewById(R.id.tv_startaddress);
        ly_gocomplay=(LinearLayout)findViewById(R.id.gocomplay_setting);
        ly_gohome=(LinearLayout)findViewById(R.id.gohome_setting);
        recyclerView = (RecyclerView)findViewById(R.id.routeplan_recycleview);
        btn_start_search=(Button)findViewById(R.id.btn_start_search);
        et_startaddress.setText(start_address);
        et_endaddress.setText(end_address);
        btn_back.setOnClickListener(this);
        btn_walkline.setOnClickListener(this);
        btn_transiteline.setOnClickListener(this);
        btn_busline.setOnClickListener(this);
        btn_start_search.setOnClickListener(this);
        et_startaddress.setText(start_address);
        et_endaddress.setText(end_address);
        }

    @Override
    public void onClick(View v) {

        switch (v.getId())
        {
            case R.id.btn_busline:
                flag="bus";
                break;

            case  R.id.btn_transiteline:
                flag="driving";

                break;

            case  R.id.btn_walkline:
                flag="walk";

                break;
            case R.id.btn_start_search:
                start_address=et_startaddress.getText().toString();
                end_address=et_endaddress.getText().toString();
                if (start_address==""||end_address==""){
                    Toast.makeText(this,"请输入完整的其实地址!",Toast.LENGTH_SHORT);
                    if (start_address==""){
                        et_startaddress.requestFocus();
                    }else {
                        et_endaddress.requestFocus();
                    }
                } else {

                    Intent intent=new Intent();
                    intent.setClass(this, MapMainActivity.class);
                    Bundle b=new Bundle();
                    b.putString("start_address",start_address);
                    b.putString("end_address",end_address);
                    b.putString("flag",flag);
                    b.putString("city",city);
                     intent.putExtras(b);
                    startActivity(intent);
                }



                break;
        }




    }
}
