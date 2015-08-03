package com.map.jdz.mymap.InitMapkey;

import android.app.Application;
import android.content.Context;
import android.widget.Toast;

import com.baidu.lbsapi.BMapManager;
import com.baidu.lbsapi.MKGeneralListener;
import com.baidu.mapapi.SDKInitializer;
import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.cache.memory.impl.LruMemoryCache;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.download.BaseImageDownloader;
import com.thinkland.sdk.android.JuheSDKInitializer;

/**
 * Created by John on 2015/7/14.
 */
public class InitMapKey extends Application {
    private static  InitMapKey initMapKey=null;
    public BMapManager bMapManager=null;
    public ImageLoaderConfiguration configuration;
    public ImageLoader imageLoader;
    @Override
    public void onCreate() {
        super.onCreate();
       initMapKey=this;
        SDKInitializer.initialize(getApplicationContext());
        JuheSDKInitializer.initialize(getApplicationContext());
         configuration=new ImageLoaderConfiguration.Builder(getApplicationContext())
                  .threadPoolSize(3)
                 .threadPriority(Thread.NORM_PRIORITY-2)
                 .memoryCache(new LruMemoryCache(8*1024*1024))
                 .memoryCacheSize(8*1024*1024)
                 .discCacheSize(80*1024*1024)
                 .discCacheFileNameGenerator(new Md5FileNameGenerator())
                 .discCacheFileCount(100)
                 .imageDownloader(new BaseImageDownloader(getApplicationContext(),5*1000,30*1000))
                 .build();
        ImageLoader.getInstance().init(configuration);
        initEngineManager(this);

    }

    public void initEngineManager(Context context) {
        if (bMapManager==null)
        {
            bMapManager=new BMapManager(context);

        }
        if (!bMapManager.init(new MYGeneralListener()))
        {
            Toast.makeText(initMapKey.getInstance().getApplicationContext(),
                    "BMapManager  初始化错误!", Toast.LENGTH_LONG).show();

        }


    }
    public static InitMapKey getInstance(){

        return initMapKey;
    }
    public static  class MYGeneralListener implements MKGeneralListener
    {



        public void onGetPermissionState(int i) {
            if (i!=0){
                Toast.makeText(initMapKey.getInstance(),"API KEY认证失败!", Toast.LENGTH_SHORT).show();

            }
            if (i==0)
            {
                Toast.makeText(initMapKey.getInstance(),"API KEY认证成功!",Toast.LENGTH_SHORT).show();
            }
        }


    }



}
