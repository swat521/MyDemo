package vip.pk.lib.clipboard;

import android.os.Bundle;
import android.view.Window;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;


import java.util.HashMap;
import java.util.Map;

import vip.pk.lib.R;
import vip.pk.lib.base.BaseActivity;
import vip.pk.lib.bean.ResultBean;
import vip.pk.lib.loading.LoadingDialog;
import vip.pk.lib.loading.LoadingDialogExecute;
import vip.pk.pklib.utils.HttpUtil;
import vip.pk.pklib.utils.SharePreferenceUtil;
import vip.pk.pklib.utils.T;

public class ResultActivity extends BaseActivity {


    private static final String TAG = "ResultActivity";
    private static int count = 0;
    private static final int TRANSLATE_RESULT = 1;
    private static String result = "";
    private static final String NO_RESULT = "无法找到结果！";
    private String clip;
    private int state;
    private static final int STATE_ORIGIN = 1;
    private static final int STATE_LOWERCASE = 2;


    private TextView translateResultTextView;
    private String data_json;


    @Override
    public void initView() {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.clipboard_result);




        translateResultTextView = (TextView) findViewById(R.id.translate_result);
        //剪贴板
        clip = getIntent().getStringExtra(ClipboardService.clipboardText);

        //db
        //String sql  = "CREATE TABLE IF NOT EXISTS video (id INTEGER PRIMARY KEY AUTOINCREMENT,video_url TEXT)";
        //DBHelper db = DBHelper.getInstance(ResultActivity.this, "pk.db", 1, QueryData.getInitDbSql(sql));


        //getData(WebUrl.baseApi+"/article/add_article");

    }

    @Override
    protected void onDestroy() {
        showToast(TAG + "onDestroy() called.");
        super.onDestroy();
    }

    public void getData(final String httpGetUri ){

        LoadingDialog loadingDialog = new LoadingDialog(curContent, new LoadingDialogExecute() {


            @Override
            public boolean execute() {

                // 在这里执行耗时的操作，如连接网络拉取数据
                try {

                    Thread.sleep(2000);
                    // ……

                    Map<String, String> url_Params;
                    url_Params = new HashMap<String, String>();
                    url_Params.put("video_other_url", clip);
                    url_Params.put("token", SharePreferenceUtil.getStringValue(ResultActivity.this,"token",""));

                    data_json = HttpUtil.doPost(httpGetUri, url_Params);


                    Bundle bb = new Bundle();
                    bb.putString("data_json", data_json);

                    setData(bb);
                } catch (Exception e) {
                    e.printStackTrace();
                    setErrorInfo("出现错误\n" + e.getMessage());
                    return false;
                }
                return true;
            }


            @Override
            public void executeSuccess(Bundle msg_bundle) {
                // 更新UI操作，如填充ListView

                data_json  = msg_bundle.getString("data_json");
                Gson gson = new Gson();
                ResultBean resultBean = gson.fromJson(data_json,ResultBean.class);




                    if (resultBean.getCode() ==0) {
                        translateResultTextView.setText(resultBean.getMsg());
                    } else {
                        translateResultTextView.setText(resultBean.getMsg());
                    }

            }

            @Override
            public void executeFailure() {
                // 异步执行失败或出错时的处理
                // ……
                T.showLong(ResultActivity.this, getErrorInfo());

            }


        });
        //loadingDialog.setIsShowDialog(false);
        loadingDialog.start();
    }



    void showToast(String info) {
        Toast.makeText(getApplicationContext(), info, Toast.LENGTH_SHORT).show();
    }


}
