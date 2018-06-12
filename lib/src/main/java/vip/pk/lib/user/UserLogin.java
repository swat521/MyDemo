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

import java.util.HashMap;
import java.util.Map;

import vip.pk.lib.R;
import vip.pk.lib.base.BaseActivity;
import vip.pk.lib.json_bean.JsonResult;
import vip.pk.lib.loading.LoadingDialog;
import vip.pk.lib.loading.LoadingDialogExecute;
import vip.pk.pklib.utils.HttpUtil;
import vip.pk.pklib.utils.SharePreferenceUtil;
import vip.pk.pklib.utils.T;

import static vip.pk.lib.weather.Weather.data_json;

public class UserLogin extends BaseActivity {

	private ToggleButton tb_show_hidden;
	private EditText et_login_input_user;
	private EditText et_login_input_pwd;
	private String login_input_user;
	private String login_input_pwd;
	private Bundle b;
	private String loginUrl;
	private String regUrl;
	private String sendSmsUrl;
	private String resetPwdUrl;

	@Override
	public void initView() {
		setContentView(R.layout.user_login);

		b = getIntent().getExtras();

		if(b == null){
			T.showLong(curContent,"参数错误");
			finish();
		}else{
			loginUrl = b.getString("login_url");
			regUrl = b.getString("reg_url");
			sendSmsUrl = b.getString("send_sms_url");
			resetPwdUrl = b.getString("reset_pwd_url");

			init_user_login();
		}
	}

	private void init_user_login() {
		// TODO Auto-generated method stub


		et_login_input_user = (EditText) findViewById(R.id.et_login_input_user);
		et_login_input_pwd = (EditText) findViewById(R.id.et_login_input_pwd);
		tb_show_hidden = (ToggleButton) findViewById(R.id.tb_show_hidden);
		// 显示隐藏密码
		tb_show_hidden.setOnCheckedChangeListener(new OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(CompoundButton arg0, boolean arg1) {
				// TODO Auto-generated method stub

				if (tb_show_hidden.isChecked()) {
					// 文本正常显示
					et_login_input_pwd.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
					Editable etable = et_login_input_pwd.getText();
					Selection.setSelection(etable, etable.length());
				} else {
					// 文本以密码形式显示
					et_login_input_pwd.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
					Editable etable = et_login_input_pwd.getText();
					Selection.setSelection(etable, etable.length());
				}

			}
		});
	}

	public void btn_reg_user(View v) {
		Intent i = new Intent(curContent, UserReg.class);
		Bundle b = new Bundle();
		b.putString("reg_url",regUrl);
		b.putString("send_sms_url",sendSmsUrl);
		i.putExtras(b);
		startActivity(i);
	}

	public void btn_get_user_pwd(View v) {
		Intent i = new Intent(curContent, UserResetPwd.class);
		Bundle b = new Bundle();
		b.putString("reset_pwd_url",resetPwdUrl);
		b.putString("send_sms_url",sendSmsUrl);
		i.putExtras(b);
		startActivity(i);
	}

	public void btn_login(View v) {
		
		login_input_user = et_login_input_user.getText().toString();
		login_input_pwd = et_login_input_pwd.getText().toString();
		
		
		LoadingDialog loadingDialog = new LoadingDialog(curContent, new LoadingDialogExecute() {

			@Override
			public boolean execute() {

				// 在这里执行耗时的操作，如连接网络拉取数据
				try {

					
					String timestamp = SharePreferenceUtil.getStringValue(curContent, "timestamp", "");
					
					Map<String, String> url_Params;
					url_Params = new HashMap<String, String>();
					url_Params.put("user_name", login_input_user);
					url_Params.put("user_pwd",login_input_pwd);

			
					
					data_json = HttpUtil.doPost(loginUrl, url_Params);

					Bundle bb = new Bundle();
					bb.putString("data_json", data_json);

					setData(bb);
				} catch (Exception e) {
					Log.e("debug",Log.getStackTraceString(e)); 
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
					SharePreferenceUtil.putStringValue(curContent, "token", (String)jsonResult.getData());
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
