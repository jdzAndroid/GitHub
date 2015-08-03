package com.map.jdz.mymap.Mainactivity;

import android.app.Activity;
import android.os.Bundle;
import android.preference.PreferenceFragment;
import android.view.Window;

import com.map.jdz.mymap.R;

/**
 * Created by John on 2015/7/17.
 */
public class Setting_activity extends Activity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.my_Setting);
       requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
      getFragmentManager().beginTransaction().replace(android.R.id.content,new Prefresfrgmt()).commit();
    }


    public static class Prefresfrgmt extends PreferenceFragment {

        @Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            addPreferencesFromResource(R.xml.preference);
        }
    }
}
