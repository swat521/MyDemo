package vip.pk.lib.map;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import vip.pk.lib.R;
import vip.pk.lib.base.BaseActivity;
import vip.pk.pklib.utils.T;

public class LbsDemo extends BaseActivity implements LbsCallBack{


    @Override
    public void initView() {
        setContentView(R.layout.lbs_demo);
    }


    public void get_lbs(View v){


        Lbs.GetLBS(LbsDemo.this,LbsDemo.this);

    }

    @Override
    public void UpdateCityName(String cityName) {
        T.showLong(LbsDemo.this,"定位完成");
        ((TextView)findViewById(R.id.tv_city)).setText(cityName);

    }
}
