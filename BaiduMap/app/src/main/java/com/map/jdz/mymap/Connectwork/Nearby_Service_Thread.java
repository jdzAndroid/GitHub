package com.map.jdz.mymap.Connectwork;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.widget.Toast;

import com.thinkland.sdk.android.DataCallBack;
import com.thinkland.sdk.android.JuheData;
import com.thinkland.sdk.android.Parameters;

/**
 * Created by John on 2015/7/15.
 */
public class Nearby_Service_Thread {
   private Parameters parameters;
    private Context context;
    private Handler handler;
    private int handle_get_nearby_thread=1;
    public Nearby_Service_Thread (Parameters parameters,Context context,Handler handler) {
     this.parameters=parameters;
        this.context=context;
        this.handler=handler;
        new GetNearbyData().run();
    }


    public  class GetNearbyData implements Runnable
    {


        @Override
        public void run() {
            JuheData.executeWithAPI(context, 45, "http://apis.juhe.cn/catering/query",
                    "GET", parameters, new DataCallBack() {
                        Message msg=new Message();
                        @Override
                        public void onSuccess(int i, String s)
                        {
                           msg.what=handle_get_nearby_thread;
                            msg.obj=s;
                            handler.sendMessage(msg);
                            System.out.print(s);
                        }

                        @Override
                        public void onFinish() {

                        }

                        @Override
                        public void onFailure(int i, String s, Throwable throwable) {
                            Toast.makeText(context,throwable.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    } );
        }
    }


}
