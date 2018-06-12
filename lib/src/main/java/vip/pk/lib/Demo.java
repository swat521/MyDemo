package vip.pk.lib;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import vip.pk.lib.about_us.AboutUs;
import vip.pk.lib.base.BaseActivity;
import vip.pk.lib.button.DjsButtonDemo;
import vip.pk.lib.chart.ChartDemo;
import vip.pk.lib.clear_edit_text_view.EditTextDemo;
import vip.pk.lib.clipboard.ClipboardMonitor;
import vip.pk.lib.count_down_view.CountDownViewDemo;
import vip.pk.lib.curtain_view.CurtainView_demo;
import vip.pk.lib.data_picker.DataPickerDemo;
import vip.pk.lib.db.Db;
import vip.pk.lib.hdp_view.HdpDemo;
import vip.pk.lib.map.LbsDemo;
import vip.pk.lib.permission.PermissionDemo;
import vip.pk.lib.progress_bar.ProgressBarDemo;
import vip.pk.lib.pull_to_refresh.PullToRefreshView_demo;
import vip.pk.lib.sport_view.SportViewDemo;
import vip.pk.lib.tab_bar.TabBarDemo;
import vip.pk.lib.tab_bottom.TabBottomDemo;
import vip.pk.lib.user.UserLogin;
import vip.pk.lib.user.UserReg;
import vip.pk.lib.user.UserResetPwd;
import vip.pk.lib.webview.MyWebView;
import vip.pk.lib.yzm_huadong.YzmHuadongDemo;

public class Demo extends BaseActivity {
    private ListView lv_list;
    private ArrayList<Map<String, Object>> list_data;
    private Map<String, Object> map;
    private MyAdapter adapter;
    Bundle b;

    @Override
    public void initView() {
        setContentView(R.layout.demo);
        initList();
    }

    private void initList() {

        lv_list = (ListView) findViewById(R.id.demo_list);
        list_data = new ArrayList<Map<String, Object>>();

        map = new HashMap<String, Object>();
        map.put("title", "测试crash收集");
        map.put("fun", "crashTest");
        map.put("class_name", Demo.class);
        list_data.add(map);

        map = new HashMap<String, Object>();
        map.put("title", "运动展示view");
        map.put("class_name", SportViewDemo.class);
        list_data.add(map);

        map = new HashMap<String, Object>();
        map.put("title", "登录");
        map.put("class_name", UserLogin.class);
        b = new Bundle();
        b.putString("login_url", "http://www.baidu.com");
        map.put("b", b);
        list_data.add(map);

        map = new HashMap<String, Object>();
        map.put("title", "注册,");
        map.put("class_name", UserReg.class);
        b = new Bundle();
        b.putString("reg_url", "http://www.baidu.com");
        b.putString("send_sms_url", "http://www.baidu.com");
        map.put("b", b);
        list_data.add(map);

        map = new HashMap<String, Object>();
        map.put("title", "忘记密码");
        map.put("class_name", UserResetPwd.class);
        b = new Bundle();
        b.putString("reset_pwd_url", "http://www.baidu.com");
        b.putString("send_sms_url", "http://www.baidu.com");
        map.put("b", b);
        list_data.add(map);

        map = new HashMap<String, Object>();
        map.put("title", "关于我们");
        map.put("class_name", AboutUs.class);
        list_data.add(map);

        map = new HashMap<String, Object>();
        map.put("title", "高德定位");
        map.put("class_name", LbsDemo.class);
        list_data.add(map);

        map = new HashMap<String, Object>();
        map.put("title", "权限申请");
        map.put("class_name", PermissionDemo.class);
        list_data.add(map);

        map = new HashMap<String, Object>();
        map.put("title", "WebView");
        map.put("class_name", MyWebView.class);
        list_data.add(map);

        map = new HashMap<String, Object>();
        map.put("title", "下拉刷新,上拉加载更多");
        map.put("class_name", PullToRefreshView_demo.class);
        list_data.add(map);

        map = new HashMap<String, Object>();
        map.put("title", "数据库操作");
        map.put("class_name", Db.class);
        list_data.add(map);

        map = new HashMap<String, Object>();
        map.put("title", "滑动验证码");
        map.put("class_name", YzmHuadongDemo.class);
        list_data.add(map);

        map = new HashMap<String, Object>();
        map.put("title", "倒计时按钮");
        map.put("class_name", DjsButtonDemo.class);
        list_data.add(map);

        map = new HashMap<String, Object>();
        map.put("title", "图表示例");
        map.put("class_name", ChartDemo.class);
        list_data.add(map);
/*
        map = new HashMap<String, Object>();
        map.put("title", "日历示例");
        map.put("class_name", CalendarViewDemo.class);
        list_data.add(map);
*/
        map = new HashMap<String, Object>();
        map.put("title", "剪贴板监控并翻译");
        map.put("class_name", ClipboardMonitor.class);
        list_data.add(map);

        map = new HashMap<String, Object>();
        map.put("title", "tab_bar 示例");
        map.put("class_name", TabBarDemo.class);
        list_data.add(map);

        map = new HashMap<String, Object>();
        map.put("title", "tab_bottom示例2");
        map.put("class_name", TabBottomDemo.class);
        list_data.add(map);

        map = new HashMap<String, Object>();
        map.put("title", "搜索示例,带清除按钮,搜索列表");
        map.put("class_name", EditTextDemo.class);
        list_data.add(map);


        map = new HashMap<String, Object>();
        map.put("title", "窗帘效果");
        map.put("class_name", CurtainView_demo.class);
        list_data.add(map);

        map = new HashMap<String, Object>();
        map.put("title", "自定义progress_bar");
        map.put("class_name", ProgressBarDemo.class);
        list_data.add(map);

        map = new HashMap<String, Object>();
        map.put("title", "日期选择控件");
        map.put("class_name", DataPickerDemo.class);
        list_data.add(map);

        map = new HashMap<String, Object>();
        map.put("title", "倒计时view");
        map.put("class_name", CountDownViewDemo.class);
        list_data.add(map);

        map = new HashMap<String, Object>();
        map.put("title", "幻灯片demo");
        map.put("class_name", HdpDemo.class);
        list_data.add(map);


        adapter = new MyAdapter(Demo.this, list_data);
        lv_list.setAdapter(adapter);

        lv_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // TODO Auto-generated method stub

                //T.showShort(Demo.this, list_data.get(position).get("title").toString());

                if (list_data.get(position).containsKey("fun")) {
                    String fun = (String) list_data.get(position).get("fun");
                    if( fun.equals("crashTest")){
                        String aa = null;
                        aa.length();
                    }






                } else {

                    Intent intent = new Intent();
                    intent.setClass(Demo.this, (Class<? extends android.app.Activity>) list_data.get(position).get("class_name"));
                    Bundle b = (Bundle) list_data.get(position).get("b");
                    if (b != null) {
                        intent.putExtras(b);
                    }

                    startActivity(intent);
                }


            }
        });
    }


    public class MyAdapter extends BaseAdapter {

        private LayoutInflater mInflater;
        List<Map<String, Object>> dataList = null;

        public MyAdapter(Context context, List<Map<String, Object>> inListData) {
            this.mInflater = LayoutInflater.from(context);
            this.dataList = inListData;
        }

        @Override
        public int getCount() {
            // TODO Auto-generated method stub
            return list_data.size();
        }

        @Override
        public Object getItem(int position) {
            // TODO Auto-generated method stub
            return position;
        }

        @Override
        public long getItemId(int position) {
            // TODO Auto-generated method stub
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder viewHolder = null;
            if (convertView == null) {
                viewHolder = new ViewHolder();
                convertView = mInflater.inflate(android.R.layout.activity_list_item, null);
                viewHolder.mTextView = (TextView) convertView.findViewById(android.R.id.text1);
                convertView.setTag(viewHolder);
            } else {
                viewHolder = (ViewHolder) convertView.getTag();
            }

            viewHolder.mTextView.setText((String) dataList.get(position).get("title"));


            return convertView;
        }

        class ViewHolder {
            TextView mTextView;
        }
    }
}
