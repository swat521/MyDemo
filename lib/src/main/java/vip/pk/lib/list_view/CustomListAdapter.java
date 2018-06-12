package vip.pk.lib.list_view;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;
import java.util.Map;

import vip.pk.lib.R;
import vip.pk.lib.net_img.NetImgImageLoader;

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
        ViewHolder viewHolder = null;
        if (convertView == null) {
            viewHolder = new ViewHolder();
            // 和item_custom.xml脚本关联
            convertView = mInflater.inflate(R.layout.pull_to_refresh_demo_list_item, null);

            viewHolder.mTextView = (TextView) convertView.findViewById(R.id.tv_list_title);
            convertView.setTag(viewHolder);
        }else{
            viewHolder = (ViewHolder) convertView.getTag();
        }


        // 加载图片
        // imageLoader.DisplayImage((String) listdata.get(position).get("img"), (Activity) mContext, iv_img);
        viewHolder.mTextView.setText((CharSequence) gridview_listdata.get(position).get("name"));


        return convertView;
    }


    class ViewHolder{
        TextView mTextView;
    }
}
