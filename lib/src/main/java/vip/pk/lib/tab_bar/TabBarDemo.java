package vip.pk.lib.tab_bar;


import android.widget.Toast;

import vip.pk.lib.R;
import vip.pk.lib.base.BaseActivity;


public class TabBarDemo extends BaseActivity {

    private TabTopLayout mTabTopLayout;

    @Override
    public void initView(){

        setContentView(R.layout.tab_bar_demo);
        mTabTopLayout = (TabTopLayout)findViewById(R.id.home_toptab);
        mTabTopLayout.setData(new String[]{"全部", "在线", "离线"},100,100,20,20);
        mTabTopLayout.setTabsDisplay(0);
        mTabTopLayout.setOnTopTabSelectedListener(new TabTopLayout.OnTopTabSelectListener() {
            @Override
            public void onTopTabSelected(int index) {

                Toast.makeText(curContent,index+"",Toast.LENGTH_LONG).show();

            }
        });


    }


}
