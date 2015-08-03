package com.map.jdz.mymap.Adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.map.jdz.mymap.R;
import com.map.jdz.mymap.Modle.NearbyModel;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.assist.ImageLoadingListener;
import com.nostra13.universalimageloader.core.assist.ImageSize;

import java.util.List;

/**
 * Created by John on 2015/7/12.
 */


public class Nearby_recycleviewAdapter extends RecyclerView.Adapter<Nearby_recycleviewAdapter.ViewHolder> {
    private List<NearbyModel> list;
    private Context context;
    private ImageLoader imageLoader;
    private DisplayImageOptions options;
    private ImageSize imgsize;
    private ViewHolder holder;
    private ImageLoaderConfiguration imageLoaderConfiguration;
    private AnimationSet animationSet;
    public Nearby_recycleviewAdapter(List<NearbyModel> list, Context context) {
        this.list=list;
        this.context=context;
        imgsize=new ImageSize(100,100);
        imageLoader=ImageLoader.getInstance();
        animationSet=new AnimationSet(true);
        options=new DisplayImageOptions.Builder()
                .showImageForEmptyUri(R.drawable.img_loading)
                .showImageOnFail(R.drawable.img_loading_fail)
                .showStubImage(R.drawable.img_loading)
                .cacheOnDisc()
                .cacheInMemory()
                .bitmapConfig(Bitmap.Config.RGB_565)
                .build();
         imageLoaderConfiguration.createDefault(context);
    }
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view=LayoutInflater.from(context).inflate(R.layout.recycleview_nearby_item,parent,false);

        ViewHolder holder=new ViewHolder(view);
        holder.product_img= (ImageView)view.findViewById(R.id.nearby_product_img);
        holder.product_logo= (ImageView)view.findViewById(R.id.nearby_product_logo);
        holder.product_name= (TextView)view.findViewById(R.id.nearby_product_name);
        holder.product_diatance= (TextView)view.findViewById(R.id.product_distance);
        holder.product_detail= (TextView)view.findViewById(R.id.nearby_product_detail);
        holder.product_address= (TextView)view.findViewById(R.id.nearby_product_address);
        holder.product_ratingbar= (RatingBar)view.findViewById(R.id.nearby_product_ratebar);
        holder.product_price= (TextView)view.findViewById(R.id.product_price);
        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        holder.product_name.setText(list.get(position).getName());
        if (list.get(position).getStars()==""){
            holder.product_ratingbar.setRating(0);
        }else {
            holder.product_ratingbar.setRating(Float.parseFloat(list.get(position).getStars()));
        }
        holder.product_address.setText(list.get(position).getAddress());
        String tags=list.get(position).getTags();
        if (tags.contains(",")){
            tags=tags.substring(0,tags.indexOf(","));
        }
        holder.product_detail.setText(tags);
        holder.product_diatance.setText(list.get(position).getStars() + "米");
        holder.product_price.setText("均价:" + list.get(position).getAvg_price());
        imageLoader.displayImage(list.get(position).getImg(), holder.product_img, options);
        imageLoader.loadImage(list.get(position).getImg(),imgsize, options,new setImg(holder.product_img));
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemCount() {
        return list.size();
    }


    public  class setImg implements ImageLoadingListener {

       private ImageView img;

        public setImg(ImageView img) {
            this.img = img;
        }


        @Override
        public void onLoadingStarted(String s, View view) {
           img.setBackgroundResource(R.drawable.img_loading);
          img.setAnimation(new RotateAnimation(0, 360, Animation.RELATIVE_TO_SELF, Animation.RELATIVE_TO_SELF));
            img.animate();
        }

        @Override
        public void onLoadingFailed(String s, View view, FailReason failReason) {
            img.setBackgroundResource(R.drawable.img_loading_fail);
        }

        @Override
        public void onLoadingComplete(String s, View view, Bitmap bitmap) {
         img.setImageBitmap(bitmap);
        }

        @Override
        public void onLoadingCancelled(String s, View view) {

        }
    }







    public   class  ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener
    {
      public ImageView product_img,product_logo;
        public TextView product_name,product_diatance,product_detail,product_address
              ,product_price;
        public RatingBar product_ratingbar;
        public ViewHolder(View itemView) {
            super(itemView);

            itemView.setOnClickListener(this);

        }



        @Override
        public void onClick(View v) {
            Toast.makeText(context,itemView.getId()+"",Toast.LENGTH_SHORT).show();
        }
    }


}
