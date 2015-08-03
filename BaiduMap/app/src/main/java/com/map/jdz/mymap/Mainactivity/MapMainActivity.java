package com.map.jdz.mymap.Mainactivity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Point;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Environment;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import com.baidu.lbsapi.BMapManager;
import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.MapStatus;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.MyLocationData;
import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.overlayutil.BusLineOverlay;
import com.baidu.mapapi.overlayutil.DrivingRouteOverlay;
import com.baidu.mapapi.overlayutil.PoiOverlay;
import com.baidu.mapapi.overlayutil.TransitRouteOverlay;
import com.baidu.mapapi.overlayutil.WalkingRouteOverlay;
import com.baidu.mapapi.search.core.CityInfo;
import com.baidu.mapapi.search.core.PoiInfo;
import com.baidu.mapapi.search.core.RouteLine;
import com.baidu.mapapi.search.core.SearchResult;
import com.baidu.mapapi.search.poi.OnGetPoiSearchResultListener;
import com.baidu.mapapi.search.poi.PoiCitySearchOption;
import com.baidu.mapapi.search.poi.PoiDetailResult;
import com.baidu.mapapi.search.poi.PoiDetailSearchOption;
import com.baidu.mapapi.search.poi.PoiNearbySearchOption;
import com.baidu.mapapi.search.poi.PoiResult;
import com.baidu.mapapi.search.poi.PoiSearch;
import com.baidu.mapapi.search.route.DrivingRouteLine;
import com.baidu.mapapi.search.route.DrivingRoutePlanOption;
import com.baidu.mapapi.search.route.DrivingRouteResult;
import com.baidu.mapapi.search.route.OnGetRoutePlanResultListener;
import com.baidu.mapapi.search.route.PlanNode;
import com.baidu.mapapi.search.route.RoutePlanSearch;
import com.baidu.mapapi.search.route.TransitRouteLine;
import com.baidu.mapapi.search.route.TransitRoutePlanOption;
import com.baidu.mapapi.search.route.TransitRouteResult;
import com.baidu.mapapi.search.route.WalkingRouteLine;
import com.baidu.mapapi.search.route.WalkingRoutePlanOption;
import com.baidu.mapapi.search.route.WalkingRouteResult;
import com.baidu.mapapi.search.sug.OnGetSuggestionResultListener;
import com.baidu.mapapi.search.sug.SuggestionResult;
import com.baidu.mapapi.search.sug.SuggestionSearch;
import com.baidu.mapapi.utils.OpenClientUtil;
import com.baidu.mapapi.utils.route.BaiduMapRoutePlan;
import com.baidu.mapapi.utils.route.RouteParaOption;
import com.baidu.navisdk.adapter.BaiduNaviManager;
import com.baidu.nplatform.comapi.basestruct.GeoPoint;
import com.baidu.nplatform.comapi.map.MapController;
import com.baidu.nplatform.comapi.map.Overlay;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import com.map.jdz.mymap.R;
import com.map.jdz.mymap.InitMapkey.InitMapKey;


public class MapMainActivity extends Activity implements BDLocationListener,OnClickListener,
        OnGetPoiSearchResultListener,OnGetSuggestionResultListener,OnGetRoutePlanResultListener,LocationListener
{
    private MapView mapView;
    private BaiduMap bMap;
    private LocationClient locationClient;
    private LocationClientOption locationClientOption;
    private LocationManager locationManager;
    private MapController mapController;
    private GeoPoint geoPoint;
    private MyLocationData.Builder locationData;
    private Overlay overlay;
    private MapStatusUpdate mapStatusUpdate;
    private MapStatus mapStatus;
    private AutoCompleteTextView tv_position;
    private PoiSearch poiSearch;
    private SuggestionSearch suggestionSearch;
    private AutoCompleteTextView autoCompleteTextView=null;
    private ArrayAdapter<String> sugadapter=null;
    private BMapManager bMapManager;
    private int load_index=0;
    private String provider;
    private String city;
    private Button btn_detail,btn_nearby,btn_toaddress,btn_search,
            btn_nearby_show,btn_routelan,btn_navagation,btn_my;
    private  boolean isFirst=true;
    private  int screen_width,screen_height;
    private DisplayMetrics metrics;
    private ImageButton btn_dingwei,btn_traffic,btn_satelite,btn_panorama,btn_goback_mainactivity;
    private LatLng latLng,latlng_nearby,startlat,endlat;
    private Criteria criteria;
    private PoiNearbySearchOption nearbySearchOption;
    private WalkingRouteOverlay walkingRouteOverlay;
    private DrivingRouteOverlay drivingRouteOverlay;
    private TransitRouteOverlay transitRouteOverlay;
    private BusLineOverlay busLineOverlay;
    private WalkingRouteLine walkingRouteLine;
    private DrivingRouteLine drivingRouteLine;
    private TransitRouteLine transitRouteLine;
    private TransitRoutePlanOption transitRoutePlanOption;
    private DrivingRoutePlanOption drivingRoutePlanOption;
    private WalkingRoutePlanOption walkingRoutePlanOption;
    private RoutePlanSearch routePlanSearch;
    private TextView tv_addressname,tv_addressdetail,tv_route_plan,tv_node_previous,tv_node_next
            ,tv_routeplan_busplay,tv_routeplan_drivingplay,tv_routeplan_bycycleplay,tv_routeplan_walkingplay
            ,tv_time,tv_money;
    private Point point;
    private String end_address,start_address;
    private Bundle bundle;
    private RouteLine routeLine;
    private TransitRouteLine.TransitStep transitStep;
    private LinearLayout ly_bottom_navagation,ly_route_plan,ly_detail,ly_select_routeplanplay,ly_navigation
            ,ly_maintop;
    private List<String> suggestion_address,suggestion_address_detail,suggestion_name;
    private SharedPreferences sharedPreferences;
    private InitMapKey initMapKey;
    private Location location;
    private InputMethodManager inputMethodManager;
    private Button btn_startnavigation,btn_wantcar;
    private RouteParaOption routeParaOption;
    private String mSDCardPath;
    public String MapNavigationKEY="BaiDuMap";
    public static final String ROUTE_PLAN_NODE = "routePlanNode";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initMapKey= (InitMapKey) this.getApplication();
        if (initMapKey.bMapManager==null){
            initMapKey.bMapManager=new BMapManager(initMapKey);
        }
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_map_main);

        initView();
        setLocationClient();
        getprovider();
        setMap();
    }
    private void startRoutePlan(){
        if (getIntent()!=null&&getIntent().getExtras()!=null)
        {
            bundle=getIntent().getExtras();
            String flag=bundle.getString("flag");
            if (flag.equals("bus"))
            {
                transitRoutePlanOption.from(PlanNode.withCityNameAndPlaceName(bundle.getString("city")
                        , bundle.getString("start_address")));
                transitRoutePlanOption.to(PlanNode.withCityNameAndPlaceName(bundle.getString("city")
                        , bundle.getString("end_address")));
                transitRoutePlanOption.city(bundle.getString("city"));
                routePlanSearch.transitSearch(transitRoutePlanOption);
            }else if (flag.equals("driving"))
            {
                drivingRoutePlanOption.from(PlanNode.withCityNameAndPlaceName(bundle.getString("city")
                        , bundle.getString("start_address")));
                drivingRoutePlanOption.to(PlanNode.withCityNameAndPlaceName(bundle.getString("city")
                        , bundle.getString("end_address")));
                routePlanSearch.drivingSearch(drivingRoutePlanOption);
            }
            else if (flag.equals("walk"))
            {
                walkingRoutePlanOption.from(PlanNode.withCityNameAndPlaceName(bundle.getString("city")
                        ,bundle.getString("start_address")));
                walkingRoutePlanOption.to(PlanNode.withCityNameAndPlaceName(bundle.getString("city")
                        ,bundle.getString("end_address")));
                routePlanSearch.walkingSearch(walkingRoutePlanOption);
            }

        }
    }

    public void getprovider() {
        locationManager=(LocationManager)getSystemService(LOCATION_SERVICE);
        criteria=new Criteria();
        criteria.setAccuracy(Criteria.ACCURACY_FINE);
        criteria.setAltitudeRequired(true);
        criteria.setBearingRequired(true);
        criteria.setCostAllowed(true);
        criteria.setSpeedRequired(true);
        criteria.setSpeedAccuracy(Criteria.ACCURACY_FINE);
        criteria.setPowerRequirement(Criteria.POWER_LOW);
        criteria.setBearingAccuracy(Criteria.ACCURACY_FINE);
        provider= locationManager.getBestProvider(criteria, true);
//        locationManager.requestLocationUpdates(provider, 1000, 10, this);
    }

    private void setMap() {
        point=new Point();
        point.set(screen_width - 60, screen_height - 160);
        bMap.setMapType(BaiduMap.MAP_TYPE_NORMAL);
        mapView.showZoomControls(true);
        mapView.setZoomControlsPosition(point);
        mapView.setClickable(true);
        bMap.setMaxAndMinZoomLevel(22, 1);
        bMap.setMyLocationEnabled(true);
    }

    private void initView() {
        /*
         获取手机屏幕宽度高度
        * */
        metrics=new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metrics);
        screen_height=metrics.heightPixels;
        screen_width=metrics.widthPixels;
        suggestion_name=new ArrayList<String>();
        suggestion_address=new ArrayList<String>();
        suggestion_address_detail=new ArrayList<String>();
        mapView=(MapView)findViewById(R.id.map_view);
        btn_dingwei=(ImageButton)findViewById(R.id.dingwei);
        tv_position= (AutoCompleteTextView) findViewById(R.id.tv_mudidi);
        tv_addressname=(TextView)findViewById(R.id.tv_address_name);
        tv_addressdetail=(TextView)findViewById(R.id.tv_address_detail);
        btn_search= (Button) findViewById(R.id.btn_start_search);
        btn_detail=(Button)findViewById(R.id.btn_detail);
        btn_nearby=(Button)findViewById(R.id.btn_nearby);
        btn_toaddress=(Button)findViewById(R.id.btn_toaddress);
        btn_traffic=(ImageButton)findViewById(R.id.btn_traffic);
        btn_satelite=(ImageButton)findViewById(R.id.btn_satelite);
        btn_panorama=(ImageButton)findViewById(R.id.btn_panorama);
        btn_nearby_show=(Button)findViewById(R.id.btn_nearby_show);
        btn_routelan=(Button)findViewById(R.id.btn_routelan);
        btn_navagation=(Button)findViewById(R.id.btn_navigation);
        btn_my=(Button)findViewById(R.id.btn_my);
        ly_detail=(LinearLayout)findViewById(R.id.ly_datail);
        ly_bottom_navagation=(LinearLayout)findViewById(R.id.ly_bottom_navagation);
        ly_route_plan=(LinearLayout)findViewById(R.id.ly_route_plan);
        tv_node_next=(TextView)findViewById(R.id.tv_note_previous);
        tv_node_previous=(TextView)findViewById(R.id.tv_note_xia);
        ly_select_routeplanplay= (LinearLayout) findViewById(R.id.main_select_routeplan_play);
        ly_navigation= (LinearLayout) findViewById(R.id.ly_navigation);
        ly_maintop= (LinearLayout) findViewById(R.id.ly_main_top);
        tv_time= (TextView) findViewById(R.id.tv_time);
        tv_money= (TextView) findViewById(R.id.tv_money);
        btn_goback_mainactivity= (ImageButton) findViewById(R.id.btn_goback_mainactivity);
        tv_routeplan_busplay= (TextView) findViewById(R.id.route_play_bus);
        tv_routeplan_drivingplay= (TextView) findViewById(R.id.route_play_driving);
        tv_routeplan_walkingplay= (TextView) findViewById(R.id.route_play_walking);
        tv_routeplan_bycycleplay= (TextView) findViewById(R.id.route_play_bycycle);
        btn_startnavigation= (Button) findViewById(R.id.main_rounteplan_followwithme);
        btn_wantcar= (Button) findViewById(R.id.main_rounteplan_wantcar);
        inputMethodManager= (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
        walkingRoutePlanOption=new WalkingRoutePlanOption();
        drivingRoutePlanOption=new DrivingRoutePlanOption();
        transitRoutePlanOption=new TransitRoutePlanOption();
        btn_goback_mainactivity.setOnClickListener(this);
        tv_routeplan_bycycleplay.setOnClickListener(this);
        tv_routeplan_busplay.setOnClickListener(this);
        tv_routeplan_drivingplay.setOnClickListener(this);
        tv_routeplan_drivingplay.setOnClickListener(this);
        tv_node_previous.setOnClickListener(this);
        btn_wantcar.setOnClickListener(this);
        btn_startnavigation.setOnClickListener(this);
        tv_node_next.setOnClickListener(this);
        bMap=mapView.getMap();
        nearbySearchOption=new PoiNearbySearchOption();
        walkingRouteOverlay=new WalkingRouteOverlay(bMap);
        drivingRouteOverlay=new DrivingRouteOverlay(bMap);
        transitRouteOverlay=new TransitRouteOverlay(bMap);
        btn_navagation.setOnClickListener(this);
        btn_routelan.setOnClickListener(this);
        btn_nearby_show.setOnClickListener(this);
        btn_my.setOnClickListener(this);
        btn_panorama.setOnClickListener(this);
        btn_satelite.setOnClickListener(this);
        btn_traffic.setOnClickListener(this);
        btn_search.setOnClickListener(this);
        btn_detail.setOnClickListener(this);
        btn_toaddress.setOnClickListener(this);
        btn_nearby.setOnClickListener(this);
        btn_dingwei.setOnClickListener(this);
        poiSearch=PoiSearch.newInstance();
        bMapManager=new BMapManager(this.getApplicationContext());
        suggestionSearch= SuggestionSearch.newInstance();
        poiSearch.setOnGetPoiSearchResultListener(this);
        routePlanSearch=RoutePlanSearch.newInstance();
        routePlanSearch.setOnGetRoutePlanResultListener(this);
        suggestionSearch.setOnGetSuggestionResultListener(this);
        mSDCardPath=getSdcardDir();
//       tv_position.setOnQueryTextListener(this);
        sharedPreferences=getSharedPreferences("suggestion",MODE_APPEND);
        String s=sharedPreferences.getString("name","");
        tv_position.setOnClickListener(this);
        if (s.length()>0)
        {
            String[] sl=s.split(",");
            for (int i=0;i<sl.length;i++)
            {
                suggestion_name.add(sl[i]);

            }
        }

    }

    private void setLocationClient() {

        locationClient=new LocationClient(this);
        locationClientOption=new LocationClientOption();
        locationClientOption.setAddrType("all");
        locationClientOption.setCoorType("bd09ll");
        locationClientOption.setScanSpan(1000 * 60);
        locationClientOption.setIsNeedAddress(true);
        locationClientOption.setNeedDeviceDirect(true);
        locationClientOption.setOpenGps(true);
        locationClientOption.setLocationNotify(true);
        locationClient.setLocOption(locationClientOption);
        locationClient.registerLocationListener(this);
        locationClient.start();
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (locationClient==null||!locationClient.isStarted()) {
            locationClient.start();
        }

        locationClient.requestLocation();
        locationClient.requestNotifyLocation();
        startRoutePlan();

    }

    @Override
    protected void onPause() {
        super.onPause();
        mapView.onPause();
    }



    @Override
    protected void onDestroy() {
        super.onDestroy();
        mapView.onDestroy();
    }

    @Override
    protected void onResume() {
        super.onResume();
        mapView.onResume();
    }


    @Override
    protected void onStop() {
        super.onStop();
        locationClient.stop();
    }


    private void setMapCenter(double lat,double lon)
    {

        MyLocationData locationData=new MyLocationData.Builder()
                .latitude(lat)
                .longitude(lon)
                .satellitesNum(8)
                .direction(100).build();
        bMap.setMyLocationData(locationData);
        latLng=new LatLng(lat,lon);
        System.out.print(latLng);
        mapStatusUpdate= MapStatusUpdateFactory.newLatLng(latLng);
        bMap.animateMapStatus(mapStatusUpdate);

    }


    @Override
    public void onReceiveLocation(BDLocation bdLocation) {

        if (isFirst){
            setMapCenter(bdLocation.getLatitude(),bdLocation.getLongitude());
            isFirst=false;
        }

        city= bdLocation.getCity();
        start_address=bdLocation.getAddrStr();
        if (city==null||city.equals(""))
        {
            Toast.makeText(MapMainActivity.this, "获取城市失败,请稍后重试",
                    Toast.LENGTH_SHORT).show();
            this.finish();
        }
    }

    private  void setRoutePlanResult(String flag)
    {
        String dis=getRoutePlanDistance(routeLine.getDistance());
        String t=getRoutePlanTime(routeLine.getDuration());
        tv_time.setText(dis+"  "+flag+":"+t);
        tv_money.setText("打车约需:" + 21 + "元");
        ly_maintop.setVisibility(LinearLayout.GONE);
        ly_detail.setVisibility(LinearLayout.GONE);
        ly_bottom_navagation.setVisibility(LinearLayout.GONE);
        ly_select_routeplanplay.setVisibility(LinearLayout.VISIBLE);
        ly_navigation.setVisibility(LinearLayout.VISIBLE);
    }

    @Override
    public void onClick(View view) {
        switch(view.getId())
        {
            /*
            * 开始查询
            * */
            case R.id.btn_start_search:
                EditText editText=(EditText)findViewById(R.id.tv_mudidi);
                String s=editText.getText().toString();
                if (s.equals(""))
                {
                    Toast.makeText(this,"请输入地址!",Toast.LENGTH_SHORT).show();
                }else {
                    poiSearch.searchInCity(new PoiCitySearchOption().city(city).keyword(s));
                    inputMethodManager.hideSoftInputFromWindow(btn_search.getWindowToken(),0);
                }

                break;
   /*
            * 将现在地址设置为当前地图的位置
            * */
            case  R.id.dingwei:
                locationClient.requestNotifyLocation();
                break;


            /*
            * 交通模式的开启与关闭
            *
            * */

            case  R.id.btn_traffic:
                if (bMap.isTrafficEnabled())
                {
                    bMap.setTrafficEnabled(false);
                }else {
                    bMap.setTrafficEnabled(true);

                }


                break;



            /*
            * 卫星地图的开启与关闭
            * */
            case R.id.btn_satelite:
                if (bMap.getMapType()==BaiduMap.MAP_TYPE_SATELLITE)
                {
                    bMap.setMapType(BaiduMap.MAP_TYPE_NORMAL);
                }else if (bMap.getMapType()==BaiduMap.MAP_TYPE_NORMAL) {
                    bMap.setMapType(BaiduMap.MAP_TYPE_SATELLITE);
                }
                break;


            /*
            * 前景地图的开启与关闭
            * */

            case R.id.btn_panorama:

                break;

            /*
            * overlay点击进入地址详情
            * 详情
            * */

            case R.id.btn_detail :

                break;

            /*
            * 查看所选地址附近的商家店铺
            * */
            case R.id.btn_nearby:
                Intent i=new Intent(this,Nearby.class);
                Bundle b=new Bundle();
                b.putDouble("lat", latlng_nearby.latitude);
                b.putDouble("lon",latlng_nearby.longitude);
                i.putExtras(b);
                startActivity(i);
                break;

            /*
            * 点击跳入路线规划提前页
            * 去这里
            * */
            case R.id.btn_toaddress:
                walkingRoutePlanOption.from(PlanNode.withCityNameAndPlaceName(city, start_address));
                walkingRoutePlanOption.to(PlanNode.withCityNameAndPlaceName(city,end_address));
                routePlanSearch.walkingSearch(walkingRoutePlanOption);
                break;

            /*
            * 点击进入附近activity
            * 附近
            * 14577    14589
            * */
            case R.id.btn_nearby_show:
                Location location2= locationManager.getLastKnownLocation(provider);
                Intent intent=new Intent(MapMainActivity.this,Nearby.class);
                Bundle bundle=new Bundle();
                bundle.putDouble("lat",location2.getLatitude());
                bundle.putDouble("lon",location2.getLongitude());
                intent.putExtras(bundle);
                startActivity(intent);



                break;
            /*
            * 点击进入路线规划详前页
            * 路线
            * */

            case R.id.btn_routelan:
                Bundle b3=new Bundle();
                b3.putString("city", city);
                b3.putString("leibie","routeplan");
                Intent i2=new Intent(this, RoutePlan.class);
                i2.putExtras(b3);
                startActivity(i2);
                break;


            /*
            * 点击进入开始导航前页
            * 导航
            * */
            case R.id.btn_navigation:

                break;

            /*点击进入地图设置页面
            我的
            * */

            case R.id.btn_my:
             Bundle b5=new Bundle();
                b5.putString("city",city);
                Intent i5=new Intent(this,My.class);
                i5.putExtras(b5);
                startActivity(i5);
                break;

            /*
            * 点击查看路线规划的下一步路线指示
            * */
            case  R.id.tv_note_xia:

                break;


            /*
            * 点击查看路线规划的上一步提示
            * */
            case R.id.tv_note_previous:

                break;


            /*
            * searchview建议查询文本监听器(当用户没有输入任何值的时候)
            * */
            case  R.id.tv_mudidi:



                break;

            /*
            * 选择路线规划方式时的返回按钮
            * */
            case  R.id.btn_goback_mainactivity:

                ly_select_routeplanplay.setVisibility(LinearLayout.GONE);
                ly_navigation.setVisibility(LinearLayout.GONE);
                ly_maintop.setVisibility(LinearLayout.VISIBLE);
                ly_bottom_navagation.setVisibility(LinearLayout.VISIBLE);
                break;

             /*
             * 选择公交路线规划
             * */
            case R.id.route_play_bus:

                break;

            /*
            * 选择步行路线规划
            * */
            case  R.id.route_play_walking:


                break;

            /*
            * 选择自驾机动车路线规划
            * */
            case R.id.route_play_driving:


                break;

            /*
            * 选择自行车路线规划
            * */
            case R.id.route_play_bycycle:


                break;
            /*
            * 开始导航
            * */
            case R.id.main_rounteplan_followwithme:
                if (initDirs()){
                    initNavi();
                }


                RouteParaOption routeParaOption=new RouteParaOption()
                        .cityName(city)
                        .startPoint(startlat)
                        .endPoint(endlat);
                try{
                    BaiduMapRoutePlan.openBaiduMapWalkingRoute(routeParaOption,this);
                }catch(Exception e){
                    Log.i("Exception",e.getMessage());
                    showDialog();
                }



                break;

            /*
            * 叫车
            * */
            case  R.id.main_rounteplan_wantcar:

                break;

        }
    }

    private boolean initDirs() {
        mSDCardPath = getSdcardDir();
        if ( mSDCardPath == null ) {
            return false;
        }
        File f = new File(mSDCardPath, MapNavigationKEY);
        if ( !f.exists() ) {
            try {
                f.mkdir();
            } catch (Exception e) {
                e.printStackTrace();
                return false;
            }
        }
        return true;
    }

    private void initNavi() {

        BaiduNaviManager.getInstance().setNativeLibraryPath(mSDCardPath + "/BaiduNaviSDK_SO");
        BaiduNaviManager.getInstance().init(this, mSDCardPath,MapNavigationKEY,
                new BaiduNaviManager.NaviInitListener() {
                    String authinfo="";
                    @Override
                    public void onAuthResult(int status, String msg) {
                        if (0 == status) {
                            authinfo = "key校验成功!";
                        } else {
                            authinfo = "key校验失败, " + msg;
                        }
                        MapMainActivity.this.runOnUiThread(new Runnable() {

                            @Override
                            public void run() {
                                Toast.makeText(MapMainActivity.this, authinfo, Toast.LENGTH_LONG).show();
                            }
                        });

                    }

                    public void initSuccess() {
                        Toast.makeText(MapMainActivity.this, "百度导航引擎初始化成功", Toast.LENGTH_SHORT).show();
                    }

                    public void initStart() {
                        Toast.makeText(MapMainActivity.this, "百度导航引擎初始化开始", Toast.LENGTH_SHORT).show();
                    }

                    public void initFailed() {
                        Toast.makeText(MapMainActivity.this, "百度导航引擎初始化失败", Toast.LENGTH_SHORT).show();
                    }
                }, null /*mTTSCallback*/);
    }


    private String getSdcardDir() {
        if (Environment.getExternalStorageState().equalsIgnoreCase(
                Environment.MEDIA_MOUNTED)) {
            return Environment.getExternalStorageDirectory().toString();
        }
        return null;
    }


    /**
     * 提示未安装百度地图app或app版本过低
     *
     */
    public void showDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("您尚未安装百度地图app或app版本过低，点击确认安装？");
        builder.setTitle("提示");
        builder.setPositiveButton("确认", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                OpenClientUtil.getLatestBaiduMapApp(MapMainActivity.this);
            }
        });

        builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        builder.create().show();

    }

    /*
    * 计算路线规划时间
    * */
    private  String   getRoutePlanTime(int time)
    {
        int h=0,m=0,d=0;
        String s="";
        time=time/60;
        if (time/60>=1)
        {
            h=time/60;
            if (h>=24){
                d=h/24;
                h=h-d*24;
                m=time-d*24*60-h*60;
                s=d+"天"+h+"小时"+m+"分钟";

            }else {
                m=time-h*60;
                s=h+"小时"+m+"分钟";
            }

        }else {
            s=time+"分钟";
        }

        return s;
    }
    /*
    * 计算路线距离
    *
    * */
    private  String getRoutePlanDistance(int distance)
    {
        String s="";
        int m=0;
        if (distance>=1000)
        {
            m=distance%100;
            distance=distance/1000;
            s=distance+"km"+m+"m";
        }else
        {
            s=distance+"m";
        }

        return s;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        if (ly_bottom_navagation.getVisibility()==LinearLayout.VISIBLE)
        {
            finish();
        }
        else{
            ly_bottom_navagation.setVisibility(LinearLayout.VISIBLE);
            ly_detail.setVisibility(LinearLayout.GONE);
        }
    }

    @Override
    public void onGetPoiResult(PoiResult poiResult) {

        if (poiResult==null||poiResult.error== SearchResult.ERRORNO.RESULT_NOT_FOUND)
        {
            Toast.makeText(MapMainActivity.this, "没有找到相关地点!", Toast.LENGTH_SHORT).show();
            return;
        }

        if (poiResult.error==SearchResult.ERRORNO.NO_ERROR)
        {
            Log.i(poiResult.getAllPoi().toString(), "poiResult");
            bMap.clear();
            PoiOverlay poiOverlay=new MyPoiOverlay(bMap);
            bMap.setOnMarkerClickListener(poiOverlay);
            poiOverlay.setData(poiResult);
            poiOverlay.addToMap();
            poiOverlay.zoomToSpan();
            PoiInfo poiInfo=poiResult.getAllPoi().get(0);
            poiSearch.searchPoiDetail(new PoiDetailSearchOption().poiUid(poiInfo.uid));
            return;
        }

        if (poiResult.error==SearchResult.ERRORNO.AMBIGUOUS_KEYWORD)
        {
            String strInfo="详情:";
            for (CityInfo cityInfo : poiResult.getSuggestCityList())
            {
                strInfo+=cityInfo.city;
                strInfo+=",";
            }
            strInfo+=".";
            Toast.makeText(MapMainActivity.this, strInfo, Toast.LENGTH_SHORT).show();
        }

    }

    @Override
    public void onGetPoiDetailResult(PoiDetailResult poiDetailResult) {
        if (poiDetailResult.error!=SearchResult.ERRORNO.NO_ERROR)
        {
            Toast.makeText(MapMainActivity.this, "未找到相关地址", Toast.LENGTH_SHORT).show();

        }else {
            {

                tv_addressname.setText(poiDetailResult.getName());
                tv_addressdetail.setText(poiDetailResult.getAddress());
                ly_detail.setVisibility(LinearLayout.VISIBLE);
                ly_bottom_navagation.setVisibility(LinearLayout.GONE);
                latlng_nearby=new LatLng(poiDetailResult.getLocation().latitude,poiDetailResult.getLocation().longitude);
                end_address=poiDetailResult.getName();
            }
        }
    }

    @Override
    public void onGetSuggestionResult(SuggestionResult suggestionResult) {
        if ((suggestionResult==null)||(suggestionResult.getAllSuggestions()==null))
        {
            return;

        }

        for (SuggestionResult.SuggestionInfo info: suggestionResult.getAllSuggestions())
        {
            if (info.key!=null)
            {

            }

        }
    }

    @Override
    public void onGetWalkingRouteResult(WalkingRouteResult walkingRouteResult) {
        if (walkingRouteResult==null||walkingRouteResult.error!= SearchResult.ERRORNO.NO_ERROR)
        {
            Toast.makeText(this, "抱歉，为找到结果！", Toast.LENGTH_SHORT).show();
            return;
        }
        if (walkingRouteResult.error==SearchResult.ERRORNO.AMBIGUOUS_ROURE_ADDR)
        {
            Toast.makeText(this, "起始点有歧义", Toast.LENGTH_SHORT).show();
            return;

        }

        if (walkingRouteResult.error== SearchResult.ERRORNO.NO_ERROR)
        {
            bMap.clear();
            routeLine=walkingRouteResult.getRouteLines().get(0);
            setRoutePlanResult("步行约:");
            walkingRouteOverlay=new MyWalkRouteLineOverlay(bMap);
            bMap.setOnMarkerClickListener(walkingRouteOverlay);
            walkingRouteOverlay.setData(walkingRouteResult.getRouteLines().get(0));
            walkingRouteOverlay.addToMap();
            walkingRouteOverlay.zoomToSpan();
            startlat=routeLine.getStarting().getLocation();
            endlat=routeLine.getTerminal().getLocation();

        }
    }

    @Override
    public void onGetTransitRouteResult(TransitRouteResult transitRouteResult) {
        if (transitRouteResult==null||transitRouteResult.error!= SearchResult.ERRORNO.NO_ERROR)
        {

            Toast.makeText(this, "抱歉,没有找到结果", Toast.LENGTH_SHORT).show();
            return;
        }
        if (transitRouteResult.error== SearchResult.ERRORNO.AMBIGUOUS_ROURE_ADDR)
        {
            Toast.makeText(this, "起始点有歧义", Toast.LENGTH_SHORT).show();
            return;
        }
        if (transitRouteResult.error== SearchResult.ERRORNO.NO_ERROR)
        {
            bMap.clear();
            Log.i("transitRouteResult",transitRouteResult.getRouteLines().toString());
            routeLine=transitRouteResult.getRouteLines().get(0);
            setRoutePlanResult("驾车约:");
            transitRouteOverlay=new MyTransiteRouteLineOverlay(bMap);
            bMap.setOnMarkerClickListener(transitRouteOverlay);
            transitRouteOverlay.setData(transitRouteResult.getRouteLines().get(0));
            transitRouteOverlay.addToMap();
            transitRouteOverlay.zoomToSpan();
            startlat=routeLine.getStarting().getLocation();
            endlat=routeLine.getTerminal().getLocation();


        }
    }

    @Override
    public void onGetDrivingRouteResult(DrivingRouteResult drivingRouteResult) {
        Toast.makeText(this,"drivingRouteResult",Toast.LENGTH_SHORT);
        if (drivingRouteResult==null||drivingRouteResult.error!= SearchResult.ERRORNO.NO_ERROR)

        {
            Toast.makeText(this, "抱歉未找到结果", Toast.LENGTH_SHORT).show();
            return;
        }
        if (drivingRouteResult.error== SearchResult.ERRORNO.AMBIGUOUS_ROURE_ADDR)
        {
            Toast.makeText(this, "起始点有歧义", Toast.LENGTH_SHORT).show();
            return;
        }

        if (drivingRouteResult.error== SearchResult.ERRORNO.NO_ERROR)
        {
            bMap.clear();
            routeLine=drivingRouteResult.getRouteLines().get(0);
            setRoutePlanResult("自驾约:");
            drivingRouteOverlay=new MyBusRouteLineOverlay(bMap);
            drivingRouteOverlay.setData(drivingRouteResult.getRouteLines().get(0));
            bMap.setOnMarkerClickListener(drivingRouteOverlay);
            drivingRouteOverlay.addToMap();
            drivingRouteOverlay.zoomToSpan();
            startlat=routeLine.getStarting().getLocation();
            endlat=routeLine.getTerminal().getLocation();


        }
    }

    @Override
    public void onLocationChanged(Location location) {
        LatLng latlng=new LatLng(location.getLatitude(),location.getLongitude());
        MyLocationData myLocationData=new MyLocationData.Builder()
                .latitude(location.getLatitude())
                .longitude(location.getLongitude())
                .satellitesNum(5)
                .build();
        mapStatusUpdate =MapStatusUpdateFactory.newLatLng(latlng);
        bMap.setMyLocationData(myLocationData);
        bMap.animateMapStatus(mapStatusUpdate);


    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {

    }



    public  class MyPoiOverlay extends PoiOverlay
    {


        public MyPoiOverlay(BaiduMap baiduMap) {
            super(baiduMap);
        }

        @Override
        public boolean onPoiClick(int i) {
            super.onPoiClick(i);
            PoiInfo poiInfo=getPoiResult().getAllPoi().get(i);
            poiSearch.searchPoiDetail(new PoiDetailSearchOption().poiUid(poiInfo.uid));
            return true;
        }
    }



    private class MyWalkRouteLineOverlay extends WalkingRouteOverlay
    {


        public MyWalkRouteLineOverlay(BaiduMap baiduMap) {
            super(baiduMap);
        }

        @Override
        public BitmapDescriptor getStartMarker() {
            return BitmapDescriptorFactory.fromResource(R.drawable.start2);
        }


        @Override
        public BitmapDescriptor getTerminalMarker() {
            return BitmapDescriptorFactory.fromResource(R.drawable.end2);
        }



    }

    private  class MyBusRouteLineOverlay extends  DrivingRouteOverlay
    {


        public MyBusRouteLineOverlay(BaiduMap baiduMap) {
            super(baiduMap);
        }

        @Override
        public BitmapDescriptor getStartMarker() {
            return BitmapDescriptorFactory.fromResource(R.drawable.start2);
        }

        @Override
        public BitmapDescriptor getTerminalMarker() {
            return BitmapDescriptorFactory.fromResource(R.drawable.end2);
        }
    }




    private class MyTransiteRouteLineOverlay extends  TransitRouteOverlay
    {


        public MyTransiteRouteLineOverlay(BaiduMap baiduMap) {
            super(baiduMap);
        }

        @Override
        public BitmapDescriptor getStartMarker() {
            return BitmapDescriptorFactory.fromResource(R.drawable.start2);
        }


        @Override
        public BitmapDescriptor getTerminalMarker() {
            return BitmapDescriptorFactory.fromResource(R.drawable.end2);
        }
    }
}
