package vip.pk.mydemo;


import android.os.Bundle;

import vip.pk.lib.Demo;
import vip.pk.lib.base.BaseActivity;

public class MainActivity extends BaseActivity {


    @Override
    public void initView() {
        openActivity(Demo.class);
    }
}
