package vip.pk.lib.tab_bottom;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import vip.pk.lib.base.BaseFragment;

public class FragmentDemo extends BaseFragment {

    private View v;

    /**
     * 静态工厂方法需要一个String型的值来初更新fragment中的内容，
     * 然后返回新的fragment到调用者
     */
    public static FragmentDemo getInstance(String text) {
        FragmentDemo fragment = new FragmentDemo();
        Bundle args = new Bundle();
        args.putString("text", text);
        fragment.setArguments(args);
        return fragment;
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

       // v = inflater.inflate(R.layout.asdfsadf, null);

        return v;
    }


        @Override
    protected void lazyLoad() {

    }
}
