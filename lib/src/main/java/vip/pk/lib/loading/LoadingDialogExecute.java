package vip.pk.lib.loading;

import android.os.Bundle;
import android.util.Log;
















/**
 * 进度对话框代理方法类，需要由用户实现execute、executeSuccess、executeFailure三个方法。
 * execute方法由子线程异步执行，不允许更新主线程UI；executeSuccess、executeFailure两方法由主线程执行，可以更新主线程UI
 
  使用方法
  
  
  
  		LoadingDialog loadingDialog = new LoadingDialog(cur_content, new LoadingDialogExecute() {

			@Override
			public boolean execute() {

				// 在这里执行耗时的操作，如连接网络拉取数据
				try {

					Thread.sleep(5000);
					// ……
					
					String timestamp = SharePreferenceUtil.getStringValue(cur_content, "timestamp", "");
					
					Map<String, String> url_Params;
					url_Params = new HashMap<String, String>();
					url_Params.put("timestamp", timestamp);
					url_Params.put("cur_time",""+(System.currentTimeMillis()/1000));
					url_Params.put("sign", SignUntil.getSign(url_Params));

					data_json = HttpUtil.doPost(MyUrl.web_url_user + "send_sms", url_Params);

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

					try {

						JSONObject jsonObject = new JSONObject(data_json);

						String code = jsonObject.getString("code");
						
						String timestamp = jsonObject.getString("timestamp");

						SharePreferenceUtil.putStringValue(cur_content, "timestamp", timestamp);
						
						
						
						
						
						String msg_str = jsonObject.getString("msg");
						String goods_list_info = jsonObject.getString("data");

						JSONArray goods_list_arr = new JSONArray(goods_list_info);

						for (int j = 0; j < goods_list_arr.length(); j++) {
							JSONObject goods_list_item = goods_list_arr.getJSONObject(j);
						}

					} catch (JSONException e) {
								// TODO Auto-generated catch block
								Log.e("debug",Log.getStackTraceString(e)); 
								T.showLong(cur_content, "解析数据出错,数据:"+data_json);
					}

			}

			@Override
			public void executeFailure() {
				// 异步执行失败或出错时的处理
				// ……
				T.showLong(cur_content, getErrorInfo());

			}

		});
		// loadingDialog.setIsShowDialog(false);
		loadingDialog.start();
  
  
  

  
  
  /////////////////////////////////////////////////////
  
  		LoadingDialog loadingDialog = new LoadingDialog(cur_content, new LoadingDialogExecute() {
			
			
			@Override
			public boolean execute() {

				// 在这里执行耗时的操作，如连接网络拉取数据
				try {
					
					Thread.sleep(5000);
					// ……

					Map<String, String> url_Params;
					url_Params = new HashMap<String, String>();
					url_Params.put("send_to", "asdfasdf");
					
					 data_json = HttpUtil.doPost(MyUrl.web_url_user+"send_sms", url_Params);
					
					
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
				
			}

			@Override
			public void executeFailure() {
				// 异步执行失败或出错时的处理
				// ……
				T.showLong(User_reg.this, getErrorInfo());
				
			}

			
		});
		//loadingDialog.setIsShowDialog(false);
		loadingDialog.start();
  
 
  
  
  
  
 * @see LoadingDialog
 * @author 优化设计
 * @version 0.2
 */
public abstract class LoadingDialogExecute {
	private String errorInfo = "发生错误";
	private Bundle data_msg;
	/**
	 * 获取最后一次错误信息描述
	 * 
	 * @return
	 */
	public String getErrorInfo() {
		return errorInfo;
	}

	/**
	 * 设置最后一次错误信息描述
	 * 
	 * @param errorInfo
	 */
	public void setErrorInfo(String errorInfo) {
		this.errorInfo = errorInfo;
	}
	
	/*
	 * 设置数据
	 */
	
	public void setData(Bundle data_msg){
		this.data_msg = data_msg;
	}
	
	/*
	 * 获取数据 
	 */
	public Bundle getData(){
		return data_msg;
	}

	/**
	 * 异步执行方法，不允许用户在此方法中更新主线程UI，需要用户自行对异常捕获处理
	 * 
	 * @return
	 */
	public abstract boolean execute();

	/**
	 * 异步方法执行成功时执行的方法，可在此方法中更新主线程UI
	 */
	public abstract void executeSuccess(Bundle msg_bundle);

	/**
	 * 异步方法执行失败时执行的方法，可在此方法中更新主线程UI
	 */
	public abstract void executeFailure();
}
