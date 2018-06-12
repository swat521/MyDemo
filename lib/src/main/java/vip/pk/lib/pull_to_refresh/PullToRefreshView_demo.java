package vip.pk.lib.pull_to_refresh;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import vip.pk.lib.R;
import vip.pk.lib.base.BaseActivity;
import vip.pk.lib.net_img.NetImgImageLoader;
import vip.pk.pklib.utils.T;

public class PullToRefreshView_demo extends BaseActivity implements PullToRefreshView.OnFooterRefreshListener, PullToRefreshView.OnHeaderRefreshListener {

	// 下拉上划
	PullToRefreshView mPullToRefreshView;
	// listview相关
	private ListView my_list;
	private ArrayList<Map<String, Object>> listdata;
	private CustomListAdapter adapter;
	Map<String, Object> item;


	@Override
	public void initView() {
		setContentView(R.layout.pull_to_refresh_demo);

		mPullToRefreshView = (PullToRefreshView) findViewById(R.id.main_pull_refresh_view);
		mPullToRefreshView.setOnHeaderRefreshListener(this);
		mPullToRefreshView.setOnFooterRefreshListener(this);

		init_list();
	}

	private void init_list() {
		// TODO Auto-generated method stub

		my_list = (ListView) findViewById(R.id.list);
		listdata = new ArrayList<Map<String, Object>>();
		
		item = new HashMap<String, Object>();
		item.put("name", "ListView分组和字母导航Demo_pin_letter_listview");

		listdata.add(item);

		item = new HashMap<String, Object>();
		item.put("name", "扫描二维码和条维码CaptureActivity");
		listdata.add(item);

		for (int i = 0; i < 20; i++) {

			item = new HashMap<String, Object>();
			item.put("name", "测试图片网络异步加载.....");
			listdata.add(item);

		}

		
		
		adapter = new CustomListAdapter(this, listdata);
		my_list.setAdapter(adapter);
		my_list. setEmptyView (findViewById (R.id.empty_view));  
		my_list.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				// TODO Auto-generated method stub

			}
		});
		
	}



	@Override
	public void onHeaderRefresh(PullToRefreshView view) {
		// TODO Auto-generated method stub
		mPullToRefreshView.postDelayed(new Runnable() {

			@Override
			public void run() {
				// 获取系统时间
				Calendar c = Calendar.getInstance();
				SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				String formattedDate = df.format(c.getTime());

				mPullToRefreshView.onHeaderRefreshComplete("最后刷新时间 :" + formattedDate);
			}
		}, 1000);
	}

	@Override
	public void onFooterRefresh(PullToRefreshView view) {
		// TODO Auto-generated method stub
		mPullToRefreshView.postDelayed(new Runnable() {

			@Override
			public void run() {
				mPullToRefreshView.onFooterRefreshComplete();
			}
		}, 1000);
	}

	
	public class CustomListAdapter extends BaseAdapter {
		private LayoutInflater mInflater;
		private Context mContext = null;
		List<Map<String, Object>> gridview_listdata = null;

		public NetImgImageLoader imageLoader;

		public CustomListAdapter(Context context, List<Map<String, Object>> lstImageItem) {
			this.mContext = context;
			this.gridview_listdata = lstImageItem;

			mInflater = LayoutInflater.from(mContext);

			imageLoader = new NetImgImageLoader(mContext);

		}

		public Object getItem(int position) {
			return position;
		}

		public long getItemId(int position) {
			// TODO Auto-generated method stub
			return position;
		}

		public int getCount() {
			// TODO Auto-generated method stub
			return gridview_listdata.size();
		}

		public View getView(int position, View convertView, android.view.ViewGroup parent) {
			if (convertView == null) {
				// 和item_custom.xml脚本关联
				convertView = mInflater.inflate(R.layout.pull_to_refresh_demo_list_item, null);
			}
			
			TextView tv_text = (TextView) convertView.findViewById(R.id.tv_list_title);

			// 加载图片
			// imageLoader.DisplayImage((String) listdata.get(position).get("img"), (Activity) mContext, iv_img);
			T.showLong(PullToRefreshView_demo.this, (CharSequence) listdata.get(position).get("name"));
			tv_text.setText((CharSequence) listdata.get(position).get("name"));
			
			return convertView;
		}
		// 网络图片下载后显示效果


	}

	
	
	
	
	
	


}