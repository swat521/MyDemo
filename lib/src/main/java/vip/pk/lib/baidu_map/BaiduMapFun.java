package com.project.iwork.map.baidu_map;

import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.MapStatus;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.model.LatLng;
import com.project.iwork.map.utils.GeoHasher;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BaiduMapFun {
    /**
     * 比较选出集合中最大经纬度
     */
    public static Map<String, Object> getMax(List<LatLng> latlonList) {

        List<Double> latList = new ArrayList<Double>();

        List<Double> lonList = new ArrayList<Double>();

        for (int i = 0; i < latlonList.size(); i++) {
            double latitude = latlonList.get(i).latitude;
            double longitude = latlonList.get(i).longitude;
            latList.add(latitude);
            lonList.add(longitude);
        }

        Map<String,Object> latlonMap = new HashMap<String, Object>();
        latlonMap.put("max_lat",Collections.max(latList));
        latlonMap.put("min_lat",Collections.min(latList));
        latlonMap.put("max_lon",Collections.max(lonList));
        latlonMap.put("min_lon", Collections.min(lonList));

        return latlonMap;
    }

    /**
     * 计算两个Marker之间的距离
     */
    public static Double calculateDistance(Map<String, Object> latlonMap) {

        Double distance = GeoHasher.GetDistance((Double)latlonMap.get("max_lat"), (Double)latlonMap.get("max_lon"),(Double) latlonMap.get("min_lat"), (Double)latlonMap.get("min_lon"));

        return distance;
    }
    /**
     *根据距离判断地图级别
     */
    public static void getLevel(BaiduMap mBaiduMap,Double distance) {
         float level;
        int zoom[] = {10, 20, 50, 100, 200, 500, 1000, 2000, 5000, 1000, 2000, 25000, 50000, 100000, 200000, 500000, 1000000, 2000000};
        //Log.i("info", "maxLatitude==" + maxLatitude + ";minLatitude==" + minLatitude + ";maxLongitude==" + maxLongitude + ";minLongitude==" + minLongitude);
        //Log.i("info", "distance==" + distance);
        for (int i = 0; i < zoom.length; i++) {
            int zoomNow = zoom[i];
            if (zoomNow - distance * 1000 > 0) {
                level = 18-i +6 ;
                //设置地图显示级别为计算所得level
                mBaiduMap.setMapStatus(MapStatusUpdateFactory.newMapStatus(new MapStatus.Builder().zoom(level).build()));
                break;
            }
        }
    }

    /**
     * 计算中心点经纬度，将其设为启动时地图中心
     */
    public static void setCenter(BaiduMap mBaiduMap,Map<String, Object> latlonMap) {
        LatLng center = new LatLng(((Double)latlonMap.get("max_lat") + (Double)latlonMap.get("min_lat")) / 2, ((Double)latlonMap.get("max_lon") + (Double)latlonMap.get("min_lon")) / 2);
        //Log.i("info", "center==" + center);
        mBaiduMap.animateMapStatus(MapStatusUpdateFactory.newLatLng(center), 500);
    }
}
