package com.map.jdz.mymap.Adapter;

import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import java.util.List;

/**
 * Created by John on 2015/7/12.
 */
public class Nearby_ViewpagerAdapter extends PagerAdapter {
    private List<ImageView> list;

    public  Nearby_ViewpagerAdapter( List<ImageView> list)
    {
        this.list=list;

    }

    @Override

    public int getCount() {
        return list.size();
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {

        container.removeView(list.get(position));

    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        container.addView(list.get(position));

        return list.get(position);
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view==object;
    }
}
