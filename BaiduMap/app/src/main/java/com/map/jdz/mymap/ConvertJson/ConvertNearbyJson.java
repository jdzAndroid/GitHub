package com.map.jdz.mymap.ConvertJson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import com.map.jdz.mymap.Modle.NearbyModel;

/**
 * Created by John on 2015/7/16.
 */
public class ConvertNearbyJson {


    public List<NearbyModel> Convert_Json(String json) throws JSONException {
        List<NearbyModel> list=new ArrayList<NearbyModel>();
        JSONObject object=new JSONObject(json);
        JSONArray jsonArray=object.getJSONArray("result");
          for (int i=0;i<jsonArray.length();i++)
          {
              JSONObject    jsonObject=  jsonArray.getJSONObject(i);
              NearbyModel model=new NearbyModel();
              model.setName(jsonObject.getString("name"));
              model.setNavigation(jsonObject.getString("navigation"));
              model.setAddress(jsonObject.getString("address"));
              model.setLat(jsonObject.getString("latitude"));
              model.setLot(jsonObject.getString("longitude"));
              model.setStars(jsonObject.getString("stars"));
              model.setAvg_price(jsonObject.getString("avg_price"));
              model.setImg(jsonObject.getString("photos"));
              model.setTags(jsonObject.getString("tags"));
              model.setAll_remarks(jsonObject.getString("all_remarks"));
              model.setVery_good_remarks(jsonObject.getString("very_good_remarks"));
              model.setGood_remarks(jsonObject.getString("good_remarks"));
              model.setCommon_remarks(jsonObject.getString("common_remarks"));
              model.setBad_remarks(jsonObject.getString("bad_remarks"));
              model.setVery_bad_remarks(jsonObject.getString("very_bad_remarks"));
              model.setNearby_shops(jsonObject.getString("nearby_shops"));
              model.setRecommended_products(jsonObject.getString("recommended_products"));
              model.setRecommended_dishes(jsonObject.getString("recommended_dishes"));
              model.setArea(jsonObject.getString("area"));
              model.setCity(jsonObject.getString("city"));
              model.setPhone(jsonObject.getString("phone"));
              model.setProduct_rating(jsonObject.getString("product_rating"));
              model.setEnvironment_rating(jsonObject.getString("environment_rating"));
              model.setService_rating(jsonObject.getString("service_rating"));
              model.setNearby_shops(jsonObject.getString("nearby_shops"));
              list.add(model);
          }
        return list;
    }
}
