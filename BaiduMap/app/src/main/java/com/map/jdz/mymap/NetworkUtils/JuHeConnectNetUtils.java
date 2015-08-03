package com.map.jdz.mymap.NetworkUtils;

import android.content.Context;

import com.thinkland.sdk.android.DataCallBack;
import com.thinkland.sdk.android.JuheData;
import com.thinkland.sdk.android.Parameters;

/**
 * Created by Administrator on 2015/7/19.
 */
public class JuHeConnectNetUtils {
    private String url;
    private Parameters parameters;
    private String flag;
    private Context context;
    private int id;
    private String data=null;
    public JuHeConnectNetUtils(String flag, String url, Parameters parameters,Context context,int id) {
        this.flag = flag;
        this.url = url;
        this.parameters = parameters;
        this.id=id;
    }

    public String getJuHeData()
      {

        JuheData.executeWithAPI(context,id,url,JuheData.GET,parameters,new DataCallBack()
        {


            @Override
            public void onSuccess(int i, String s) {
                System.out.print(s);
             data=s;
            }

            @Override
            public void onFinish() {

            }

            @Override
            public void onFailure(int i, String s, Throwable throwable) {

            }
        });
          return data;
      }


}
