package vip.pk.lib.sport_view;

import android.os.Bundle;

import vip.pk.lib.R;
import vip.pk.lib.base.BaseActivity;


public class SportViewDemo extends BaseActivity {

    private SportView sportView;

    @Override
    public void initView() {
        setContentView(R.layout.sport_view);
        try {
            init_sport_view();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void init_sport_view() throws Exception {

        sportView = (SportView)findViewById(R.id.sport);
        sportView.setStartAngle(-235);//设置起始位置（角度）
        sportView.setEndAngle(55);//设置结束位置（角度）
        sportView.setAvg(4000);//设置平均值
        sportView.setProgress(3808);//设置步数

    }
}
