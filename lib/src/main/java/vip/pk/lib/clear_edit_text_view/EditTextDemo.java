package vip.pk.lib.clear_edit_text_view;

import android.content.Context;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import vip.pk.lib.R;
import vip.pk.lib.base.BaseActivity;

public class EditTextDemo extends BaseActivity {
    private ClearEditText et_search;
    private ListView user_list;

    private String[] data;
    private ArrayAdapter<String> mAdapter;

    private List<User> mDatas = new ArrayList<>();
    private UserAdapter mUserAdapter;

    @Override
    public void initView() {

        setContentView(R.layout.edit_text_demo);
        initData();

        et_search = (ClearEditText) findViewById(R.id.et_search);
        user_list = (ListView) findViewById(R.id.user_list);

        initListView();
        intiEditView();
    }



    private void initData() {
        data = new String[]{"张无忌", "周芷若", "赵敏", "东方不败", "令狐冲", "上官婉儿",
                "纳兰容若", "张李丹妮", "任盈盈"};

        User user1 = new User(R.mipmap.logo, "张无忌");
        mDatas.add(user1);
        User user2 = new User(R.mipmap.logo, "周芷若");
        mDatas.add(user2);
        User user3 = new User(R.mipmap.logo, "赵敏");
        mDatas.add(user3);
        User user4 = new User(R.mipmap.logo, "东方不败");
        mDatas.add(user4);
        User user5 = new User(R.mipmap.logo, "令狐冲");
        mDatas.add(user5);
        User user6 = new User(R.mipmap.logo, "上官婉儿");
        mDatas.add(user6);
        User user7 = new User(R.mipmap.logo, "纳兰容若");
        mDatas.add(user7);
        User user8 = new User(R.mipmap.logo, "张李丹妮");
        mDatas.add(user8);
        User user9 = new User(R.mipmap.logo, "任盈盈");
        mDatas.add(user9);
        User user10 = new User(R.mipmap.logo, "绝无神");
        mDatas.add(user10);
    }



    private void intiEditView() {
        et_search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
//                mAdapter.getFilter().filter(s);

                mUserAdapter.getFilter().filter(s);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    private void initListView() {
//        mAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, data);
//        user_list.setAdapter(mAdapter);

        mUserAdapter = new UserAdapter(EditTextDemo.this,mDatas);
        user_list.setAdapter(mUserAdapter);
    }








    public class UserAdapter extends BaseAdapter implements Filterable {

        private Context mContext;

        /**
         * Contains the list of objects that represent the data of this Adapter.
         * Adapter数据源
         */
        private List<User> mDatas;

        private LayoutInflater mInflater;

        //过滤相关
        /**
         * This lock is also used by the filter
         * (see {@link #getFilter()} to make a synchronized copy of
         * the original array of data.
         * 过滤器上的锁可以同步复制原始数据。
         */
        private final Object mLock = new Object();

        // A copy of the original mObjects array, initialized from and then used instead as soon as
        // the mFilter ArrayFilter is used. mObjects will then only contain the filtered values.
        //对象数组的备份，当调用ArrayFilter的时候初始化和使用。此时，对象数组只包含已经过滤的数据。
        private ArrayList<User> mOriginalValues;
        private ArrayFilter mFilter;

        public UserAdapter(Context context, List<User> datas) {
            mContext = context;
            mDatas = datas;
            mInflater = LayoutInflater.from(context);
        }

        @Override
        public int getCount() {
            return mDatas.size() > 0 ? mDatas.size() : 0;
        }

        @Override
        public Object getItem(int position) {
            return mDatas.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder holder;
            if (convertView == null) {
                convertView = mInflater.inflate(R.layout.ecit_text_demo_item, parent, false);
                holder = new ViewHolder();
                //holder.avatar = (CircleImageView) convertView.findViewById(R.id.iv_avatar);
                holder.name = (TextView) convertView.findViewById(R.id.tv_name);
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }
            User user = mDatas.get(position);
            //holder.avatar.setImageResource(user.getAvatarResId());
            holder.name.setText(user.getName());
            return convertView;
        }

        public class ViewHolder {
           // CircleImageView avatar;
            TextView name;
        }

        @Override
        public Filter getFilter() {
            if (mFilter == null) {
                mFilter = new ArrayFilter();
            }
            return mFilter;
        }

        /**
         * 过滤数据的类
         */
        /**
         * <p>An array filter constrains the content of the array adapter with
         * a prefix. Each item that does not start with the supplied prefix
         * is removed from the list.</p>
         * <p/>
         * 一个带有首字母约束的数组过滤器，每一项不是以该首字母开头的都会被移除该list。
         */
        private class ArrayFilter extends Filter {
            //执行刷选
            @Override
            protected FilterResults performFiltering(CharSequence prefix) {
                FilterResults results = new FilterResults();//过滤的结果
                //原始数据备份为空时，上锁，同步复制原始数据
                if (mOriginalValues == null) {
                    synchronized (mLock) {
                        mOriginalValues = new ArrayList<>(mDatas);
                    }
                }
                //当首字母为空时
                if (prefix == null || prefix.length() == 0) {
                    ArrayList<User> list;
                    synchronized (mLock) {//同步复制一个原始备份数据
                        list = new ArrayList<>(mOriginalValues);
                    }
                    results.values = list;
                    results.count = list.size();//此时返回的results就是原始的数据，不进行过滤
                } else {
                    String prefixString = prefix.toString().toLowerCase();//转化为小写

                    ArrayList<User> values;
                    synchronized (mLock) {//同步复制一个原始备份数据
                        values = new ArrayList<>(mOriginalValues);
                    }
                    final int count = values.size();
                    final ArrayList<User> newValues = new ArrayList<>();

                    for (int i = 0; i < count; i++) {
                        final User value = values.get(i);//从List<User>中拿到User对象
//                    final String valueText = value.toString().toLowerCase();
                        final String valueText = value.getName().toString().toLowerCase();//User对象的name属性作为过滤的参数
                        // First match against the whole, non-splitted value
                        if (valueText.startsWith(prefixString) || valueText.indexOf(prefixString.toString()) != -1) {//第一个字符是否匹配
                            newValues.add(value);//将这个item加入到数组对象中
                        } else {//处理首字符是空格
                            final String[] words = valueText.split(" ");
                            final int wordCount = words.length;

                            // Start at index 0, in case valueText starts with space(s)
                            for (int k = 0; k < wordCount; k++) {
                                if (words[k].startsWith(prefixString)) {//一旦找到匹配的就break，跳出for循环
                                    newValues.add(value);
                                    break;
                                }
                            }
                        }
                    }
                    results.values = newValues;//此时的results就是过滤后的List<User>数组
                    results.count = newValues.size();
                }
                return results;
            }

            //刷选结果
            @Override
            protected void publishResults(CharSequence prefix, FilterResults results) {
                //noinspection unchecked
                mDatas = (List<User>) results.values;//此时，Adapter数据源就是过滤后的Results
                if (results.count > 0) {
                    notifyDataSetChanged();//这个相当于从mDatas中删除了一些数据，只是数据的变化，故使用notifyDataSetChanged()
                } else {
                    /**
                     * 数据容器变化 ----> notifyDataSetInValidated

                     容器中的数据变化  ---->  notifyDataSetChanged
                     */
                    notifyDataSetInvalidated();//当results.count<=0时，此时数据源就是重新new出来的，说明原始的数据源已经失效了
                }
            }
        }
    }




    public class User {
        private int avatarResId;
        private String name;

        public User(int avatarResId, String name) {
            this.avatarResId = avatarResId;
            this.name = name;
        }

        public int getAvatarResId() {
            return avatarResId;
        }

        public void setAvatarResId(int avatarResId) {
            this.avatarResId = avatarResId;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }







}
