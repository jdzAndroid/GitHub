package com.map.jdz.mymap.Adapter;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * Created by Administrator on 2015/8/2.
 */
public class MyLinearlyout_Manager extends LinearLayoutManager {
    public MyLinearlyout_Manager(Context context) {
        super(context);
    }

    @Override
    public void onMeasure(RecyclerView.Recycler recycler, RecyclerView.State state, int widthSpec, int heightSpec) {
        super.onMeasure(recycler, state, widthSpec, heightSpec);
//        View view=recycler.getViewForPosition(0);
//        if (view!=null){
//            measureChild(view,widthSpec,heightSpec);
//            int measureWidth= View.MeasureSpec.getSize(widthSpec);
//            int measureHeight=view.getMeasuredHeight();
//            setMeasuredDimension(measureWidth,measureHeight);
//        }
        setMeasuredDimension(100,300);
    }
}
