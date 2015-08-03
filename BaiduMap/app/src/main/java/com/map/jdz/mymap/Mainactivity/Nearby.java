package com.map.jdz.mymap.Mainactivity;

import android.app.Activity;
import android.graphics.Matrix;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.map.jdz.mymap.Adapter.Nearby_recycleviewAdapter;
import com.map.jdz.mymap.Adapter.RecycleViewItemDecoration;
import com.thinkland.sdk.android.Parameters;
import org.json.JSONException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import com.map.jdz.mymap.ConvertJson.ConvertNearbyJson;
import com.map.jdz.mymap.R;
import com.map.jdz.mymap.Adapter.Nearby_ViewpagerAdapter;
import com.map.jdz.mymap.Connectwork.Nearby_Service_Thread;
import com.map.jdz.mymap.Modle.NearbyModel;

/**
 * Created by John on 2015/7/12.
 */
public class Nearby extends Activity implements View.OnClickListener,SwipeRefreshLayout.OnRefreshListener {
    private ViewPager nearby_viewpager;
    private AutoCompleteTextView searchView_nearby;
    private ImageView dot1,dot2,dot3;
    private RecyclerView nearby_listview;
    private Nearby_ViewpagerAdapter viewpagerAdapter;
    private TextView btn_search_beautiful_fruit,btn_book_hotel,btn_go_where,btn_search_scene,
            btn_search_group,btn_want_bus,btn_see_movie,btn_takeaway;
    private Button btn_busstation,btn_subway,btn_fillingstation,btn_bank,btn_supermarket,
            btn_toilet,btn_internet_cafe,btn_ktv,btn_bath,btn_all,btn_beautiful_fruit,
            btn_entertainment,btn_hotel;
    private ImageButton btn_goback;
    private List<ImageView> list;
    private int[] imagesid={R.drawable.viewpagerone,R.drawable.viewpagertwo,R.drawable.viewpagerone};
    private int oldposition=0;
    private  int currentposition;
    private List<View> dot;
    private ScheduledExecutorService scheduledExecutorService;
    private Handler handler;
    private double latlng,lonlng;
    private int pageindex=1;
    private Nearby_Service_Thread nearby_service_thread;
    private Parameters parameters;
    private int handle_get_nearby_thread=1;
    private List<NearbyModel> list_nearby=new ArrayList<NearbyModel>(),list_nearbytotal=new ArrayList<NearbyModel>();
    private ConvertNearbyJson convertNearbyJson=new ConvertNearbyJson();
    private String str_NearbyModel;
    private Nearby_recycleviewAdapter recycleviewAdapter;
    private SwipeRefreshLayout refreshLayout;
    private int data_size;
    private RecycleViewItemDecoration recycleViewItemDecoration;
    private LinearLayoutManager linearLayoutManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.listview_nearby_top);
        handler=new Handler()
        {
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                switch (msg.what) {

                    case 0:
                        nearby_viewpager.setCurrentItem(currentposition);

                        break;

                    case  1:
                        str_NearbyModel= (String) msg.obj;
                        try {
                            list_nearby = convertNearbyJson.Convert_Json(str_NearbyModel);
                            if (list_nearby.size() > 0) {
                                list_nearbytotal.addAll(list_nearby);
                                if (recycleviewAdapter == null) {
                                    recycleviewAdapter = new Nearby_recycleviewAdapter(list_nearby, getApplicationContext());
//                                    nearby_listview.addItemDecoration(recycleViewItemDecoration);
                                    nearby_listview.setAdapter(recycleviewAdapter);

                                } else {
                                    recycleviewAdapter.notifyDataSetChanged();
                                }
//                                getitemheight();

                            }else {

//                                refreshLayout.setEnabled(false);
                            }

                        }
                        catch (JSONException e) {
                            e.printStackTrace();
                        }

//                        refreshLayout.setRefreshing(false);
                        break;
                }


            }
        };

         /*
      * 初始化view
      * */
        initView();

        /*
        * 设置点击事件
        * */
        setclicklistenter();




        /*
        * 获取intent数据
        *
        * */
        if (getIntent()!=null)
        {
            Bundle bundle=getIntent().getExtras();
            latlng=bundle.getDouble("lat");
            lonlng=bundle.getDouble("lon");
            parameters=new Parameters();
            parameters.add("lng",lonlng);
            parameters.add("lat",latlng);
            parameters.add("page",pageindex);
            parameters.add("dtype","json");
            GetNearbyServiceData(parameters);
        }


    }


    private  void  getitemheight()
    {
        int totalheight=0;
        View itemview;
        int size= recycleviewAdapter.getItemCount();
        for (int i=0;i<size;i++){
            itemview=  nearby_listview.getLayoutManager().getChildAt(i);
            itemview.measure(0,0);
            totalheight=itemview.getMeasuredHeight();
            itemview.getLayoutParams().height=totalheight;
        }
    }


    /*
    * 加载附近商店信息
    * */
    private void GetNearbyServiceData(Parameters parameters)
    {
        nearby_service_thread=new Nearby_Service_Thread(parameters,getApplicationContext(),handler);

    }


    //      点击事件的具体实现
    private void setclicklistenter() {
        btn_ktv.setOnClickListener(this);
        btn_internet_cafe.setOnClickListener(this);
        btn_toilet.setOnClickListener(this);
        btn_supermarket.setOnClickListener(this);
        btn_all.setOnClickListener(this);
        btn_bank.setOnClickListener(this);
        btn_bath.setOnClickListener(this);
        btn_beautiful_fruit.setOnClickListener(this);
        btn_search_beautiful_fruit.setOnClickListener(this);
        btn_book_hotel.setOnClickListener(this);
        btn_busstation.setOnClickListener(this);
        btn_see_movie.setOnClickListener(this);
        btn_entertainment.setOnClickListener(this);
        btn_fillingstation.setOnClickListener(this);
        btn_go_where.setOnClickListener(this);
        btn_goback.setOnClickListener(this);
        btn_hotel.setOnClickListener(this);
        btn_search_group.setOnClickListener(this);
        btn_search_scene.setOnClickListener(this);
        btn_subway.setOnClickListener(this);
        btn_takeaway.setOnClickListener(this);
        btn_want_bus.setOnClickListener(this);
//        refreshLayout.setOnRefreshListener(this);
    }
    //   初始化view的具体实现
    private void initView() {
//        refreshLayout= (SwipeRefreshLayout) findViewById(R.id.swipe_refreshlayout);
        nearby_listview= (RecyclerView) findViewById(R.id.recycle_nearby);
        nearby_listview.setLayoutManager(new LinearLayoutManager(this));
        searchView_nearby= (AutoCompleteTextView) findViewById(R.id.sound_search);
        nearby_viewpager= (ViewPager) findViewById(R.id.viewpager_nearby);
        btn_search_beautiful_fruit= (TextView) findViewById(R.id.btn_search_beautiful_fruit);
        btn_book_hotel= (TextView) findViewById(R.id.btn_menu_book_hotel);
        btn_go_where= (TextView)findViewById(R.id.btn_menu_go_where);
        btn_search_scene= (TextView)findViewById(R.id.btn_search_scene);
        btn_search_group= (TextView)findViewById(R.id.btn_menu_search_group);
        btn_want_bus= (TextView)findViewById(R.id.btn_menu_search_bus);
        btn_see_movie= (TextView)findViewById(R.id.btn_menu_see_movie);
        btn_takeaway= (TextView)findViewById(R.id.btn_menu_search_takeaway);
        btn_busstation= (Button) findViewById(R.id.btn_busstation);
        btn_subway=(Button)findViewById(R.id.btn_subway);
        btn_fillingstation=(Button)findViewById(R.id.btn_fillingstation);
        btn_bank=(Button)findViewById(R.id.btn_bank);
        btn_supermarket=(Button)findViewById(R.id.btn_supermarket);
        btn_toilet=(Button)findViewById(R.id.btn_toilet);
        btn_internet_cafe=(Button)findViewById(R.id.btn_internet_cafe);
        btn_ktv=(Button)findViewById(R.id.btn_ktv);
        btn_goback=(ImageButton)findViewById(R.id.btn_nearby_goback);
        btn_all=(Button)findViewById(R.id.btn_all);
        btn_beautiful_fruit=(Button)findViewById(R.id.btn_beautifu_fruit);
        btn_entertainment=(Button)findViewById(R.id.btn_entertainment);
        btn_hotel=(Button)findViewById(R.id.btn_hotel);
        btn_bath=(Button)findViewById(R.id.btn_bath);
        recycleViewItemDecoration=new RecycleViewItemDecoration(this);
        list=new ArrayList<ImageView>();
        dot=new ArrayList<View>();
        dot.add(findViewById(R.id.dot1));
        dot.add(findViewById(R.id.dot2));
        dot.add(findViewById(R.id.dot3));
        for (int i=0;i<3;i++)
        {
            ImageView iv=new ImageView(this);
          iv.setBackgroundResource(imagesid[i]);
            list.add(iv);
        }
        viewpagerAdapter=new Nearby_ViewpagerAdapter(list);
        nearby_viewpager.setAdapter(viewpagerAdapter);
        nearby_viewpager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                dot.get(position).setBackgroundResource(R.drawable.dot_press);
                dot.get(position).setBackgroundResource(R.drawable.dot_normal);
                oldposition=position;
                currentposition=position;
            }

            @Override
            public void onPageSelected(int position) {

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

    }


    /*
    * view�ĵ���¼��ľ���ʵ��
    *
    * */
    @Override
    public void onClick(View v) {
        switch (v.getId())
        {

            /*
            * 找美食
            * */
            case R.id.btn_search_beautiful_fruit:


                break;

         /*
         * 订酒店
         * */

            case R.id.btn_menu_book_hotel:

                break;

         /*
         * 周末去哪儿
         *
         * */

            case R.id.btn_menu_go_where:

                break;

         /*
         * 搜景点
         *
         * */

            case R.id.btn_search_scene:

                break;


         /*
         * 找团购
         * */

            case R.id.btn_menu_search_group:

                break;

         /*
         * 用车
         * */

            case R.id.btn_menu_search_bus:

                break;
         /*
         * 看电影
         * */

            case R.id.btn_menu_see_movie:

                break;
         /*
         * 叫外卖
         * */

            case R.id.btn_menu_search_takeaway:

                break;

         /*
         * 公交站
         *
         * */

            case R.id.btn_busstation:

                break;


         /*
         * 地铁
         *
         * */

            case R.id.btn_subway:

                break;



         /*
         * 加油站
         *
         * */
            case R.id.btn_fillingstation:

                break;

         /*
         * 银行
         *
         * */

            case R.id.btn_bank:

                break;


         /*
         * 超市
         *
         * */

            case R.id.btn_supermarket:

                break;

         /*
         * 厕所
         *
         * */

            case R.id.btn_toilet:

                break;


         /*
         *网吧
         *
         * */

            case R.id.btn_internet_cafe:

                break;


         /*
         * KTV
         *
         * */
            case R.id.btn_ktv:

                break;


         /*
         * 返回上一级
         *
         * */

            case R.id.btn_back:

                break;

         /*
         *洗浴
         *
         * */

            case R.id.btn_bath:

                break;



         /*
         * 全部
         *
         * */

            case R.id.btn_all:

                break;



         /*
         *美食
         *
         * */
            case R.id.btn_beautifu_fruit:

                break;



         /*
         *休闲
         *
         * */

            case R.id.btn_entertainment:

                break;


         /*
         * 酒店
         *
         * */

            case R.id.btn_hotel:

                break;


        }


    }

    @Override
    protected void onStart() {
        super.onStart();
        scheduledExecutorService= Executors.newSingleThreadScheduledExecutor();
        scheduledExecutorService.scheduleWithFixedDelay(new myViewpagerTask(),2,2, TimeUnit.SECONDS);
    }


    /*
    * swiperefreshlayout刷新监听事件
    * */

    @Override
    public void onRefresh() {
        pageindex=pageindex+1;
        parameters.remove("page");
        parameters.add("page", pageindex);
        GetNearbyServiceData(parameters);
    }


    private  class  myViewpagerTask implements Runnable
    {


        @Override
        public void run() {
            currentposition=currentposition+1;
            if (currentposition>=dot.size())
            {
                currentposition=0;

            }
            Message msg=new Message();
            msg.arg1=currentposition;
            msg.what=0;
            handler.sendMessage(msg);
        }
    }
}
