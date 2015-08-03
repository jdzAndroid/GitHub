package com.map.jdz.mymap.Adapter;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * Created by Administrator on 2015/7/26.
 */
public class RecycleViewItemDecoration extends RecyclerView.ItemDecoration {
    private Drawable drawable;
    private int[] ATTRS=new int[]{android.R.attr.listDivider};
    public RecycleViewItemDecoration(Context context) {
        super();
        TypedArray typedArray=context.obtainStyledAttributes(ATTRS);
        drawable=typedArray.getDrawable(0);

    }


    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        super.getItemOffsets(outRect, view, parent, state);
                    outRect.set(0,0,0,drawable.getIntrinsicHeight());
    }

    @Override
    public void onDraw(Canvas c, RecyclerView parent, RecyclerView.State state) {
        super.onDraw(c, parent, state);
        int childcount=parent.getChildCount();
        int top=0,bottoom=0,left=0,right=0;
        for (int i=0;i<childcount;i++){
            View child=parent.getChildAt(i);
            top=parent.getTop()+child.getHeight();
            bottoom=top+ drawable.getIntrinsicHeight();
            left=child.getLeft();
            right=left+child.getWidth();
            drawable.setBounds(left,top,right,bottoom);
            drawable.draw(c);
        }

    }
}
