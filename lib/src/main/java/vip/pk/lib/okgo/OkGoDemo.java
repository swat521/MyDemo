package vip.pk.lib.okgo;

import com.lzy.okgo.OkGo;

import vip.pk.lib.R;
import vip.pk.lib.base.BaseActivity;
public class OkGoDemo extends BaseActivity {

    @Override
    public void initView() {
        setContentView(R.layout.okgo_demo);

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //Activity销毁时，取消网络请求
        OkGo.getInstance().cancelTag(this);
    }
}
