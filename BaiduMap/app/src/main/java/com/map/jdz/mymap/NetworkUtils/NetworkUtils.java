package com.map.jdz.mymap.NetworkUtils;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Map;

/**
 * Created by Administrator on 2015/7/19.
 */
public class NetworkUtils {

   private String userAgent = "Mozilla/5.0 (Windows NT 6.1) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/29.0.1547.66 Safari/537.36";
   private String url;
    private Map<String,String> mapParams;
    public NetworkUtils(String url,Map<String,String> mapParams) throws Exception {
       this.url=url;
        this.mapParams=mapParams;
        getData(url,mapParams);
    }


    public String getData(String url,Map<String,String> mapParams) throws Exception {
        /*
        * 实例化一个HttpURLConnection对象
        * */
        HttpURLConnection conn=null;

          /*
        * 获取一个StringBuffer实例
        * */
        StringBuffer sb=null;

          /*
        * 如果有参数值不为空
        * */


        if (mapParams!=null){
            sb=new StringBuffer();
            conn.setDoInput(true);
            conn.setConnectTimeout(5000);
            conn.setDefaultUseCaches(true);
            conn.setRequestProperty("User-agent", userAgent);
            conn.setRequestMethod("GET");
            sb=sb.append(url).append("?");
            for (Map.Entry s:mapParams.entrySet()){
              sb=sb.append(s.getKey()).append("=").append(s.getValue()).append("&");
            }
            sb=sb.deleteCharAt(sb.length()-1);
        }
        /*
        * 实例化一个URL对象,并传入url；
        * */
        URL u=new URL(sb.toString());
        /*
        * 打开链接
        * */
       conn= (HttpURLConnection) u.openConnection();
        conn.connect();
        sb=new StringBuffer();
        int code=conn.getResponseCode();
        BufferedReader reader=null;

        if (code==200){

            InputStream in=conn.getInputStream();
           reader=new BufferedReader(new InputStreamReader(in));
            String str=null;
            while ((str=reader.readLine())!=null){
                sb.append(str).append(System.getProperty("line.separator"));

            }
            reader.close();
            if (sb.toString().length()==0){
                return null;

            }
            return  sb.toString().substring(0,sb.toString().length()- System.getProperty("line.separator").length());

        }
        if (conn!=null){

            conn.disconnect();
        }
        return null;
    }

}
