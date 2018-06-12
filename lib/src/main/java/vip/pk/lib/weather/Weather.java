package vip.pk.lib.weather;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;

import com.google.gson.Gson;

import vip.pk.lib.loading.LoadingDialog;
import vip.pk.lib.loading.LoadingDialogExecute;
import vip.pk.pklib.utils.HttpUtil;
import vip.pk.pklib.utils.T;

public class Weather {
    public static String data_json;
    public static WeatherBean weatherBean =null;
    public static String weatherUrl = "https://v.juhe.cn/weather/index?cityname=######&dtype=&format=1&key=d5ddd399a8bb6f8e598d99959a5efb7b";
    public static void GetWeatherInfo(final Context c,String cityName,final WeatherCallBack callBack){

        weatherUrl = weatherUrl.replace("######",cityName);
        LoadingDialog loadingDialog = new LoadingDialog(c, new LoadingDialogExecute() {

            @Override
            public boolean execute() {

                // 在这里执行耗时的操作，如连接网络拉取数据
                try {

                    data_json = HttpUtil.doGet( weatherUrl);

                    Bundle bb = new Bundle();
                    bb.putString("data_json", data_json);

                    setData(bb);
                } catch (Exception e) {
                    Log.e("debug",Log.getStackTraceString(e));
                    setErrorInfo("出现错误\n" + e.getMessage());
                    return false;
                }
                return true;
            }

            @Override
            public void executeSuccess(Bundle msg_bundle) {
                // 更新UI操作，如填充ListView

                data_json = msg_bundle.getString("data_json");

                Gson gson = new Gson();

                weatherBean = gson.fromJson(data_json,WeatherBean.class);

                callBack.UpdateDateShow(weatherBean);

            }

            @Override
            public void executeFailure() {
                // 异步执行失败或出错时的处理
                // ……
                T.showLong(c, getErrorInfo());
            }

        });
        loadingDialog.setIsShowDialog(false);
        loadingDialog.start();

    }



}
