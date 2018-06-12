package vip.pk.lib.splash;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.DownloadManager;
import android.app.DownloadManager.Request;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.graphics.Bitmap;
import android.media.Image;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import vip.pk.lib.R;
import vip.pk.lib.base.BaseActivity;
import vip.pk.lib.loading.LoadingDialog;
import vip.pk.lib.loading.LoadingDialogExecute;
import vip.pk.pklib.utils.HttpUtil;
import vip.pk.pklib.utils.NetUtil;
import vip.pk.pklib.utils.SharePreferenceUtil;
import vip.pk.pklib.utils.SignUntil;
import vip.pk.pklib.utils.T;


public abstract class MySplash extends BaseActivity {

	private String local_ver_name;
	private int local_ver_code;
	private int server_ver_code;
	
	private Bundle b;

	int stay_time = 0;
	int stay_time_least = 5;
	boolean to_activity = false;
	private Handler handler = new Handler();

	private VerBean verBean;
	private String splashUrl;
	private Class<?> nextActivity;
	private String data_json;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		//setContentView(vip.pk.pklib.R.layout.splash);
		InitSplash();
	}
	public abstract void InitSplash();
	public void setSplashUrl(String splashUrl){
		this.splashUrl = splashUrl;
	}
	public void setNextActivity(Class<?> nextActivity){
		this.nextActivity = nextActivity;
	}

	public void setWebUrl(String webUrl){
		((TextView)findViewById(R.id.tv_web_url)).setText(webUrl);
	}
	public void setSplashBg(Bitmap bitMap){
		((ImageView)findViewById(R.id.splash_bg)).setImageBitmap(bitMap);
	}

	public void init_splash() {
		// TODO Auto-generated method stub

		net_type();
		get_local_ver();
		get_server_ver();
		
		handler.postDelayed(runnable, 1000);
	}

	/*
	 * 判断是否更新app
	 */
	private void is_update() {
		// TODO Auto-generated method stub
		
		
		if(server_ver_code == 0){
			to_activity = true;
			goto_main();
		}else{
			if(server_ver_code>local_ver_code){
				showUpdateDialog(verBean.getVerName(),verBean.getVerDesc(),verBean.getVerDownUrl());
			}else{
				to_activity = true;
				goto_main();
			}
		}
		
		
	}

	// 跳转到主界面
	private void goto_main() {
		if (stay_time > stay_time_least && to_activity) {
			if (handler != null) {
				handler.removeCallbacks(runnable);
			}

			openActivity(nextActivity);
			finish();

		}else{
			handler.postDelayed(runnable, 1000);
		}
	}

	//在splash停留至少3秒
	Runnable runnable = new Runnable() {
		@Override
		public void run() {
			// TODO Auto-generated method stub

			// 要做的事情
			if (stay_time > stay_time_least) {
				goto_main();
			} else {
				stay_time++;
				handler.postDelayed(this, 1000);
			}
		}
	};

	public void net_type() {

		int net_status = NetUtil.getNetworkState(curContent);

		switch (net_status) {
		case 0:
			T.showLong(curContent, "没有检测到网络");
			break;

		case 1:
			T.showLong(curContent, "手机正在通过WIFI上网");
			break;
		case 2:
			T.showLong(curContent, "手机正在通过流量上网");
			break;
		default:
			break;
		}
	}

	public void get_server_ver() {

		LoadingDialog loadingDialog = new LoadingDialog(curContent, new LoadingDialogExecute() {

			

			@Override
			public boolean execute() {

				// 在这里执行耗时的操作，如连接网络拉取数据
				try {

					//Thread.sleep(5000);
					// ……
					
					String timestamp = SharePreferenceUtil.getStringValue(curContent, "timestamp", "");
					
					Map<String, String> url_Params;
					url_Params = new HashMap<String, String>();
					url_Params.put("timestamp", timestamp);
					url_Params.put("cur_time",""+(System.currentTimeMillis()/1000));
					url_Params.put("sign", SignUntil.getSign(url_Params));

					data_json = HttpUtil.doPost(splashUrl, url_Params);

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

				data_json = msg_bundle.getString("data_json");

					try {

						JSONObject jsonObject = new JSONObject(data_json);

						String code = jsonObject.getString("Code");
						String msg_str = jsonObject.getString("Msg");
						String data = jsonObject.getString("Data");

						Gson gson = new Gson();
						verBean = gson.fromJson(data, VerBean.class);

						server_ver_code = verBean.getVerCode();
						
					} catch (JSONException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
								T.showLong(curContent, "解析数据出错,数据:"+data_json);
								server_ver_code = 0;
								
					}
					is_update();
			}

			@Override
			public void executeFailure() {
				// 异步执行失败或出错时的处理
				// ……
				server_ver_code = 0;
				T.showLong(curContent, getErrorInfo());
				is_update();
			}

		});
		loadingDialog.setIsShowDialog(false);
		loadingDialog.start();
	}

	public void get_local_ver() {

		PackageManager manager = MySplash.this.getPackageManager();
		try {
			PackageInfo info = manager.getPackageInfo(curContent.getPackageName(), 0);
			local_ver_name = info.versionName; // 版本名
			local_ver_code = info.versionCode; // 版本号

		} catch (NameNotFoundException e) {
			// TODO Auto-generated catch blockd
			//e.printStackTrace();
			local_ver_name = "获取版本名称错误";
			local_ver_code = 0;

		}
		
		((TextView)findViewById(R.id.tv_local_ver)).setText(local_ver_name);

	}

	// 显示是否更新对话框
	private void showUpdateDialog(final String app_name, String app_desc, final String down_url) {
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setTitle("检测到新版本");
		builder.setMessage(app_desc);
		builder.setPositiveButton("下载", new DialogInterface.OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int which) {
				// TODO Auto-generated method stub
				
				start_download(verBean.getVerName(),verBean.getVerDownUrl(),verBean.getVerDownType());
				
				Toast.makeText(MySplash.this, "通知栏下载中...", Toast.LENGTH_LONG).show();
				
				to_activity = true;
				goto_main();
			}
		}).setNegativeButton("取消", new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				// TODO Auto-generated method stub
				to_activity = true;
				goto_main();
			}
		});
		builder.show();
	}

	private void start_download(String app_name, String down_url,String down_type) {
		if(down_type.equals("service")){
			Intent i = new Intent(MySplash.this, SplashUpdateService.class);
			i.putExtra("app_name", app_name);
			i.putExtra("down_url", down_url);
			startService(i);
		}else{
			system_down(down_url);
		}
		
	}

	@SuppressLint("NewApi")
	private void system_down(String down_url) {
		// TODO Auto-generated method stub
		DownloadManager downloadManager = (DownloadManager) getSystemService(DOWNLOAD_SERVICE);  
        
	    Uri uri = Uri.parse(down_url);  
	    Request request = new Request(uri);  
	  
	    //设置允许使用的网络类型，这里是移动网络和wifi都可以    
	    request.setAllowedNetworkTypes(Request.NETWORK_MOBILE| Request.NETWORK_WIFI);
	  
	    //禁止发出通知，既后台下载，如果要使用这一句必须声明一个权限：android.permission.DOWNLOAD_WITHOUT_NOTIFICATION    
	    //request.setShowRunningNotification(false);    
	  
	    //不显示下载界面    
	    request.setVisibleInDownloadsUi(false);  
	        /*设置下载后文件存放的位置,如果sdcard不可用，那么设置这个将报错，因此最好不设置如果sdcard可用，下载后的文件        在/mnt/sdcard/Android/data/packageName/files目录下面，如果sdcard不可用,设置了下面这个将报错，不设置，下载后的文件在/cache这个  目录下面*/  
	    //request.setDestinationInExternalFilesDir(this, null, "tar.apk");  
	    long id = downloadManager.enqueue(request);  
	    //TODO 把id保存好，在接收者里面要用，最好保存在Preferences里面  
	    /*
	    public class CompleteReceiver extends BroadcastReceiver {  
  
    private DownloadManager downloadManager;  
  
    @Override  
    public void onReceive(Context context, Intent intent) {  
          
        String action = intent.getAction();  
        if(action.equals(DownloadManager.ACTION_DOWNLOAD_COMPLETE)) {  
            Toast.makeText(context, "下载完成了....", Toast.LENGTH_LONG).show();  
              
            long id = intent.getLongExtra(DownloadManager.EXTRA_DOWNLOAD_ID, 0);                                                                                      //TODO 判断这个id与之前的id是否相等，如果相等说明是之前的那个要下载的文件  
            Query query = new Query();  
            query.setFilterById(id);  
            downloadManager = (DownloadManager) context.getSystemService(Context.DOWNLOAD_SERVICE);  
            Cursor cursor = downloadManager.query(query);  
              
            int columnCount = cursor.getColumnCount();  
            String path = null;                                                                                                                                       //TODO 这里把所有的列都打印一下，有什么需求，就怎么处理,文件的本地路径就是path  
            while(cursor.moveToNext()) {  
                for (int j = 0; j < columnCount; j++) {  
                    String columnName = cursor.getColumnName(j);  
                    String string = cursor.getString(j);  
                    if(columnName.equals("local_uri")) {  
                        path = string;  
                    }  
                    if(string != null) {  
                        System.out.println(columnName+": "+ string);  
                    }else {  
                        System.out.println(columnName+": null");  
                    }  
                }  
            }  
            cursor.close();  
        //如果sdcard不可用时下载下来的文件，那么这里将是一个内容提供者的路径，这里打印出来，有什么需求就怎么样处理                                                   if(path.startsWith("content:")) {  
                               cursor = context.getContentResolver().query(Uri.parse(path), null, null, null, null);  
                               columnCount = cursor.getColumnCount();  
                               while(cursor.moveToNext()) {  
                                    for (int j = 0; j < columnCount; j++) {  
                                                String columnName = cursor.getColumnName(j);  
                                                String string = cursor.getString(j);  
                                                if(string != null) {  
                                                     System.out.println(columnName+": "+ string);  
                        }else {  
                            System.out.println(columnName+": null");  
                        }  
                    }  
                }  
                cursor.close();  
            }  
              
        }else if(action.equals(DownloadManager.ACTION_NOTIFICATION_CLICKED)) {  
            Toast.makeText(context, "点击<span style="font-family: 宋体; ">通知</span><span style="font-size: 10.5pt; text-indent: 21pt; font-family: 宋体; ">了....", Toast.LENGTH_LONG).show();</span>  
        }  
    }  
}  
	    
	    
	    <receiver android:name=".CompleteReceiver">  
            <intent-filter>  
                <action android:name="android.intent.action.DOWNLOAD_COMPLETE"/>  
                <action android:name="android.intent.action.DOWNLOAD_NOTIFICATION_CLICKED"/>  
            </intent-filter>  
        </receiver>  
	    
	    
	    */
	}
	
}
