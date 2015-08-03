package com.map.jdz.mymap.Adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import java.util.List;

import com.map.jdz.mymap.R;
import com.map.jdz.mymap.Modle.NearbyModel;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.ImageSize;

/**
 * Created by John on 2015/7/12.
 */


public class Nearby_ListcleviewAdapter extends BaseAdapter {
    private List<NearbyModel> list;
    private Context context;
    private ImageLoader imageLoader;
    private DisplayImageOptions options;
    private ImageSize imgsize;
    private ViewHolder holder;
    private ImageLoaderConfiguration imageLoaderConfiguration;
    public Nearby_ListcleviewAdapter(List<NearbyModel> list, Context context) {
        this.list=list;
        this.context=context;
        imgsize=new ImageSize(100,100);
        imageLoader=ImageLoader.getInstance();
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
    public int getCount() {
        return list.size();
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

//         if (convertView==null){
             holder=new ViewHolder();
             View view=LayoutInflater.from(context).inflate(R.layout.recycleview_nearby_item,null);
             holder.product_img= (ImageView)view.findViewById(R.id.nearby_product_img);
             holder.product_logo= (ImageView)view.findViewById(R.id.nearby_product_logo);
             holder.product_name= (TextView)view.findViewById(R.id.nearby_product_name);
             holder.product_diatance= (TextView)view.findViewById(R.id.product_distance);
             holder.product_detail= (TextView)view.findViewById(R.id.nearby_product_detail);
             holder.product_address= (TextView)view.findViewById(R.id.nearby_product_address);
             holder.product_ratingbar= (RatingBar)view.findViewById(R.id.nearby_product_ratebar);
             holder.product_price= (TextView)view.findViewById(R.id.product_price);
//             convertView.setTag(holder);
//         }else {
//
//             holder= (ViewHolder) convertView.getTag();
//         }

        holder.product_name.setText(list.get(position).getName());
        if (list.get(position).getStars()==""){

            holder.product_ratingbar.setRating(0);
        }else {
            holder.product_ratingbar.setRating(Float.parseFloat(list.get(position).getStars()));

        }

        holder.product_address.setText(list.get(position).getAddress());
        holder.product_detail.setText(list.get(position).getTags());
        holder.product_diatance.setText(list.get(position).getStars() + "米");
        holder.product_price.setText("均价:" + list.get(position).getAvg_price());
        imageLoader.displayImage(list.get(position).getImg(),holder.product_img,options);
        return view;
    }
    private   class  ViewHolder
    {
      private ImageView product_img,product_logo;
      private TextView product_name,product_diatance,product_detail,product_address
              ,product_price;
        private RatingBar product_ratingbar;
    }


}
