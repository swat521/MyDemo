package vip.pk.lib.tab_bottom;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import vip.pk.lib.R;
import vip.pk.lib.base.BaseFragmentActivity;
public class TabBottomDemo extends BaseFragmentActivity {
    private TabView tabView;



    @Override
    public void initView() {
        setContentView(R.layout.tab_bottom_demo);

        tabView= (TabView) findViewById(R.id.tabView);
        //start add data
        List<TabViewChild> tabViewChildList=new ArrayList<>();
        TabViewChild tabViewChild01=new TabViewChild(R.mipmap.ex_menu_guiji_s,R.mipmap.ex_menu_guiji_n,"首页",  FragmentDemo.getInstance("首页"));
        TabViewChild tabViewChild02=new TabViewChild(R.mipmap.ex_menu_jiankong_s,R.mipmap.ex_menu_jiankong_n,"分类",  FragmentDemo.getInstance("分类"));
        TabViewChild tabViewChild03=new TabViewChild(R.mipmap.ex_menu_fenzu_s,R.mipmap.ex_menu_fenzu_n,"资讯",  FragmentDemo.getInstance("资讯"));
        TabViewChild tabViewChild04=new TabViewChild(R.mipmap.ex_menu_tongji_s,R.mipmap.ex_menu_tongji_n,"购物车",FragmentDemo.getInstance("购物车"));
        TabViewChild tabViewChild05=new TabViewChild(R.mipmap.ex_menu_more_s,R.mipmap.ex_menu_more_n,"我的",  FragmentDemo.getInstance("我的"));
        tabViewChildList.add(tabViewChild01);
        tabViewChildList.add(tabViewChild02);
        tabViewChildList.add(tabViewChild03);
        tabViewChildList.add(tabViewChild04);
        tabViewChildList.add(tabViewChild05);
        //end add data
        tabView.setTabViewDefaultPosition(2);
        tabView.setTabViewChild(tabViewChildList,getSupportFragmentManager());
        tabView.setOnTabChildClickListener(new TabView.OnTabChildClickListener() {
            @Override
            public void onTabChildClick(int  position, ImageView currentImageIcon, TextView currentTextView) {
                Toast.makeText(getApplicationContext(),"position:"+position,Toast.LENGTH_SHORT).show();
            }
        });
    }
}
