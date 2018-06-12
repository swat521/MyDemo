package vip.pk.lib.user;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputType;
import android.text.Selection;
import android.util.Log;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.EditText;
import android.widget.ToggleButton;

import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import vip.pk.lib.R;
import vip.pk.lib.base.BaseActivity;
import vip.pk.lib.json_bean.JsonResult;
import vip.pk.lib.loading.LoadingDialog;
import vip.pk.lib.loading.LoadingDialogExecute;
import vip.pk.pklib.utils.HttpUtil;
import vip.pk.pklib.utils.SharePreferenceUtil;
import vip.pk.pklib.utils.SignUntil;
import vip.pk.pklib.utils.T;

public class UserReg extends BaseActivity {

	private EditText et_reg_input_user;
	private String reg_input_user;
	private EditText et_reg_input_pwd;
	private EditText et_reg_input_yzm;
	private EditText et_tjr;
	private String reg_input_yzm;
	private String tjr;
	private String reg_input_pwd;
	private ToggleButton tb_show_hidden;
	private Bundle b;
	private String regUrl;
	private String sendSmsUrl;
	private String data_json;


	@Override
	public void initView() {
		setContentView(R.layout.user_reg);

		b = getIntent().getExtras();

		if(b == null){
			T.showLong(curContent,"参数错误");
			finish();
		}else{
			regUrl = b.getString("reg_url");
			sendSmsUrl = b.getString("send_sms_url");
			init_user_reg();
		}

	}

	private void init_user_reg() {
		// TODO Auto-generated method stub
		//set_title(R.id.tv_title, "用户注册");

		//show_back_btn(R.id.iv_btn_back);

		et_reg_input_user = (EditText) findViewById(R.id.et_reg_input_user);
		et_reg_input_pwd = (EditText)findViewById(R.id.et_reg_input_pwd);
		et_reg_input_yzm = (EditText) findViewById(R.id.et_reg_input_yzm);
		et_tjr = (EditText)findViewById(R.id.et_tjr);
		tb_show_hidden = (ToggleButton) findViewById(R.id.tb_show_hidden);
		
		// 显示隐藏密码
		tb_show_hidden.setOnCheckedChangeListener(new OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(CompoundButton arg0, boolean arg1) {
				// TODO Auto-generated method stub

				if (tb_show_hidden.isChecked()) {
					// 文本正常显示
					et_reg_input_pwd.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
					Editable etable = et_reg_input_pwd.getText();
					Selection.setSelection(etable, etable.length());
				} else {
					// 文本以密码形式显示
					et_reg_input_pwd.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
					Editable etable = et_reg_input_pwd.getText();
					Selection.setSelection(etable, etable.length());
				}

			}
		});
		
	}

	public void btn_send_sms_code(View v) {

		reg_input_user = et_reg_input_user.getText().toString();
		// reg_input_pwd = et_reg_input_user.getText().toString();
		reg_input_yzm = et_reg_input_user.getText().toString();
		// tjr = et_reg_input_user.getText().toString();
		LoadingDialog loadingDialog = new LoadingDialog(curContent, new LoadingDialogExecute() {

			@Override
			public boolean execute() {

				// 在这里执行耗时的操作，如连接网络拉取数据
				try {

					// ……

					String timestamp = SharePreferenceUtil.getStringValue(curContent, "timestamp", "");

					Map<String, String> url_Params;
					url_Params = new HashMap<String, String>();
					url_Params.put("user_name", reg_input_user);
					url_Params.put("cur_time","");
					url_Params.put("sign", SignUntil.getSign(url_Params));

					data_json = HttpUtil.doPost(sendSmsUrl, url_Params);

					Bundle bb = new Bundle();
					bb.putString("data_json", data_json);

					setData(bb);
				} catch (Exception e) {
					Log.e("debug", Log.getStackTraceString(e));
					setErrorInfo("出现错误\n" + e.getMessage());
					return false;
				}
				return true;
			}

			@Override
			public void executeSuccess(Bundle msg_bundle) {
				// 更新UI操作，如填充ListView

				data_json = msg_bundle.getString("data_json");

				Gson gson = new Gson();
				JsonResult jsonResult= gson.fromJson(data_json, JsonResult.class);

				T.showLong(curContent, jsonResult.getMsg());
				if(jsonResult.getCode()==0){
					et_reg_input_yzm.setText(((Double)jsonResult.getData()).intValue()+"");
				}

			}

			@Override
			public void executeFailure() {
				// 异步执行失败或出错时的处理
				// ……
				T.showLong(curContent, getErrorInfo());

			}

		});
		// loadingDialog.setIsShowDialog(false);
		loadingDialog.start();

	}

	public void btn_reg_user(View v) {
		reg_input_user = et_reg_input_user.getText().toString();
		reg_input_pwd = et_reg_input_pwd.getText().toString();
		reg_input_yzm = et_reg_input_yzm.getText().toString();
		tjr = et_tjr.getText().toString();
		LoadingDialog loadingDialog = new LoadingDialog(curContent, new LoadingDialogExecute() {

			@Override
			public boolean execute() {

				// 在这里执行耗时的操作，如连接网络拉取数据
				try {

					// ……

					String timestamp = SharePreferenceUtil.getStringValue(curContent, "timestamp", "");

					Map<String, String> url_Params;
					url_Params = new HashMap<String, String>();
					url_Params.put("user_name", reg_input_user);
					url_Params.put("user_pwd", reg_input_pwd);
					url_Params.put("sms_code", reg_input_yzm);
					url_Params.put("tui_code", tjr);

					data_json = HttpUtil.doPost(regUrl, url_Params);

					Bundle bb = new Bundle();
					bb.putString("data_json", data_json);

					setData(bb);
				} catch (Exception e) {
					Log.e("debug", Log.getStackTraceString(e));
					setErrorInfo("出现错误\n" + e.getMessage());
					return false;
				}
				return true;
			}

			@Override
			public void executeSuccess(Bundle msg_bundle) {
				// 更新UI操作，如填充ListView

				data_json = msg_bundle.getString("data_json");

				Gson gson = new Gson();
				JsonResult jsonResult= gson.fromJson(data_json, JsonResult.class);
				T.showLong(curContent,jsonResult.getMsg());
				if(jsonResult.getCode() == 0){
					SharePreferenceUtil.putStringValue(curContent, "token", "");
					finish();
				}
			}

			@Override
			public void executeFailure() {
				// 异步执行失败或出错时的处理
				// ……
				T.showLong(curContent, getErrorInfo());

			}

		});
		// loadingDialog.setIsShowDialog(false);
		loadingDialog.start();
	}

}
