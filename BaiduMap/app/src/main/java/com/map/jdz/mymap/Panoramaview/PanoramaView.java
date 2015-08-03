package com.map.jdz.mymap.Panoramaview;

import android.app.Activity;
import android.os.Bundle;

import com.baidu.lbsapi.BMapManager;
import com.baidu.lbsapi.panoramaview.PanoramaViewListener;

import com.map.jdz.mymap.InitMapkey.InitMapKey;

/**
 * Created by John on 2015/7/14.
 */
public class PanoramaView extends Activity implements PanoramaViewListener {
  private InitMapKey initMapKey;
  private BMapManager bMapManager;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        initMapKey=(InitMapKey)this.getApplication();
//        if (bMapManager==null)
//        {
//            bMapManager=new BMapManager(initMapKey);
//            bMapManager.init(new InitMapKey.MYGeneralListener(this));
//        }
    }

    @Override
    public void onLoadPanoramaBegin() {

    }

    @Override
    public void onLoadPanoramaEnd(String s) {

    }

    @Override
    public void onLoadPanoramaError(String s) {

    }
}
