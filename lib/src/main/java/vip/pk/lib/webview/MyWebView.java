package vip.pk.lib.webview;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.provider.MediaStore;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.webkit.CookieManager;
import android.webkit.CookieSyncManager;
import android.webkit.DownloadListener;
import android.webkit.JsResult;
import android.webkit.ValueCallback;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import java.io.File;

import vip.pk.lib.R;
import vip.pk.lib.base.BaseActivity;
import vip.pk.pklib.utils.T;


public class MyWebView extends BaseActivity {
	private WebView wv_myweb;
	private Bundle b;
	private String ad_url;

	private ValueCallback<Uri> mUploadMessage;
	private final static int FILECHOOSER_RESULTCODE = 1;
	private String mCameraFilePath = null;
	private String title;


	@SuppressLint("JavascriptInterface")
	@Override
	public void initView() {
		setContentView(R.layout.webview);

		b = getIntent().getExtras();

		if (b != null) {

			//处理网址
			ad_url = (String) b.get("url");

			if (ad_url != null) {

				// 如果没有http://就加上
				String newStr = ad_url.substring(0, 7);

				if (!newStr.equals("http://")) {
					ad_url = "http://" + ad_url;
				}
			}
			//处理标题
			title = (String) b.get("title");

			if (title != null) {
				//set_title(R.id.tv_title,title);
			}
		} else {
			//set_title(R.id.tv_title,"测试页面");
			ad_url = "https://www.baidu.com";
		}

		//show_back_btn(R.id.iv_btn_back);

		wv_myweb = (WebView) findViewById(R.id.wv_myweb);

		// 设置WebView属性，能够执行Javascript脚本
		wv_myweb.getSettings().setJavaScriptEnabled(true);

		wv_myweb.addJavascriptInterface(new DemoJavaScriptInterface(), "demo");
		T.showLong(MyWebView.this, ad_url);
		wv_myweb.loadUrl(ad_url);

		wv_myweb.setWebViewClient(new WebViewClient() {
			@SuppressLint("MissingPermission")
			public boolean shouldOverrideUrlLoading(WebView view, String url) {

				// 如果不需要其他对点击链接事件的处理返回true，否则返回false
				T.showLong(MyWebView.this, url);

				if (url.indexOf("tel:") < 0) {// 页面上有数字会导致连接电话

					view.loadUrl(url);

				} else {

					Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse(url));
					startActivity(intent);
				}

				return true;
			}

			@Override
			public void onPageStarted(WebView view, String url, Bitmap favicon)

			{

				super.onPageStarted(view, url, favicon);

				/*
				 * loadingView.setVisibility(View.VISIBLE); webView.setVisibility(View.INVISIBLE); rocketAnimation.start();
				 */

			}

			@Override
			public void onPageFinished(WebView view, String url)

			{

				super.onPageFinished(view, url);

				/*
				 * loadingView.setVisibility(View.GONE); rocketAnimation.stop(); mHandler.sendEmptyMessageDelayed(View.VISIBLE, 300);
				 */

			}

			@Override
			public void onReceivedError(WebView view, int errorCode, String description, String failingUrl)

			{
				super.onReceivedError(view, errorCode, description, failingUrl);

				//mErrorView.setVisibility(View.VISIBLE);

				/*
				 * loadingView.setVisibility(View.GONE);
				 *
				 * rocketAnimation.stop();
				 *
				 * webView.loadUrl("file:///android_asset/error.html");
				 */
			}

		});

		wv_myweb.setWebChromeClient(new WebChromeClient() {
			// 关键代码，以下函数是没有API文档的，所以在Eclipse中会报错，如果添加了@Override关键字在这里的话。

			@Override
			public boolean onJsAlert(WebView view, String url, String message, JsResult result) {
				result.confirm();
				return true;
			}

			// For Android 3.0+
			public void openFileChooser(ValueCallback<Uri> uploadMsg) {
				if (uploadMsg == null) {
					return;
				}

				mUploadMessage = uploadMsg;
				Intent i = new Intent(Intent.ACTION_GET_CONTENT);
				i.addCategory(Intent.CATEGORY_OPENABLE);
				i.setType("image/*");
				MyWebView.this.startActivityForResult(Intent.createChooser(i, "File Chooser"), FILECHOOSER_RESULTCODE);

			}

			// For Android 3.0+
			public void openFileChooser(ValueCallback uploadMsg, String acceptType) {
				if (uploadMsg == null) {
					return;
				}

				mUploadMessage = uploadMsg;
				Intent i = new Intent(Intent.ACTION_GET_CONTENT);
				i.addCategory(Intent.CATEGORY_OPENABLE);
				i.setType("*/*");
				MyWebView.this.startActivityForResult(Intent.createChooser(i, "File Browser"), FILECHOOSER_RESULTCODE);
			}

			// For Android 4.1
			public void openFileChooser(ValueCallback<Uri> uploadMsg, String acceptType, String capture) {
				if (uploadMsg == null) {
					return;
				}

				mUploadMessage = uploadMsg;
				Intent i = new Intent(Intent.ACTION_GET_CONTENT);
				i.addCategory(Intent.CATEGORY_OPENABLE);
				i.setType("image/*");
				MyWebView.this.startActivityForResult(Intent.createChooser(i, "File Chooser"), FILECHOOSER_RESULTCODE);

			}

			private Intent createDefaultOpenableIntent() {
				// Create and return a chooser with the default OPENABLE
				// actions including the camera, camcorder and sound
				// recorder where available.
				Intent i = new Intent(Intent.ACTION_GET_CONTENT);
				i.addCategory(Intent.CATEGORY_OPENABLE);
				i.setType("*/*");

				Intent chooser = createChooserIntent(createCameraIntent(), createCamcorderIntent(), createSoundRecorderIntent());
				chooser.putExtra(Intent.EXTRA_INTENT, i);
				return chooser;
			}

			private Intent createChooserIntent(Intent... intents) {
				Intent chooser = new Intent(Intent.ACTION_CHOOSER);
				chooser.putExtra(Intent.EXTRA_INITIAL_INTENTS, intents);
				chooser.putExtra(Intent.EXTRA_TITLE, "File Chooser");
				return chooser;
			}

			private Intent createCameraIntent() {
				Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

				File externalDataDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM);
				System.out.println("externalDataDir:" + externalDataDir);
				File cameraDataDir = new File(externalDataDir.getAbsolutePath() + File.separator + "browser-photo");
				cameraDataDir.mkdirs();
				mCameraFilePath = cameraDataDir.getAbsolutePath() + File.separator + System.currentTimeMillis() + ".jpg";
				System.out.println("mcamerafilepath:" + mCameraFilePath);
				cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(new File(mCameraFilePath)));

				return cameraIntent;
			}

			private Intent createCamcorderIntent() {
				return new Intent(MediaStore.ACTION_VIDEO_CAPTURE);
			}

			private Intent createSoundRecorderIntent() {
				return new Intent(MediaStore.Audio.Media.RECORD_SOUND_ACTION);
			}

		});

		// 实现webview 的下载
		wv_myweb.setDownloadListener(new DownloadListener() {

			@Override
			public void onDownloadStart(String url, String userAgent, String contentDisposition, String mimetype, long contentLength) {
				// TODO Auto-generated method stub
				Uri uri = Uri.parse(url);
				Intent intent = new Intent(Intent.ACTION_VIEW, uri);
				startActivity(intent);
			}
		});
	}


	private Handler mHandler = new Handler();

	/*
	<html>
	<script language="javascript">
	    /* This function is invoked by the activity * /
	    function wave() {
	        alert("1");
	        document.getElementById("droid").src="android_waving.png";
	        alert("2");
	    }
	</script>
	<body>
	    <!-- Calls into the javascript interface for the activity -->
	    <a onClick="window.demo.clickOnAndroid()"><div style="width:80px;
	        margin:0px auto;
	        padding:10px;
	        text-align:center;
	        border:2px solid #202020;" >
	            <img id="droid" src="android_normal.png"/><br>
	            Click me!
	    </div></a>
	</body>
	</html>
	 * 
	 */

	final class DemoJavaScriptInterface {

		DemoJavaScriptInterface() {
		}

		/**
		 * This is not called on the UI thread. Post a runnable to invoke loadUrl on the UI thread.
		 */
		public void clickOnAndroid() {
			mHandler.post(new Runnable() {
				public void run() {
					wv_myweb.loadUrl("javascript:wave()");
				}
			});

		}
	}

	private View mErrorView;
	private boolean mIsErrorPage;

	/**
	 * 显示自定义错误提示页面，用一个View覆盖在WebView
	 */
	protected void showErrorPage() {
		LinearLayout webParentView = (LinearLayout) wv_myweb.getParent();

		initErrorPage();
		while (webParentView.getChildCount() > 1) {
			webParentView.removeViewAt(0);
		}
		LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
		webParentView.addView(mErrorView, 0, lp);
		mIsErrorPage = true;
	}

	protected void hideErrorPage() {
		LinearLayout webParentView = (LinearLayout) wv_myweb.getParent();

		mIsErrorPage = false;
		while (webParentView.getChildCount() > 1) {
			webParentView.removeViewAt(0);
		}
	}

	@SuppressLint("WrongViewCast")
	protected void initErrorPage() {
		if (mErrorView == null) {
			mErrorView = View.inflate(this, R.layout.webview_error, null);
			Button button = (Button) mErrorView.findViewById(R.id.online_error_btn_retry);
			button.setOnClickListener(new OnClickListener() {
				public void onClick(View v) {
					wv_myweb.reload();
				}
			});
			mErrorView.setOnClickListener(null);
		}
	}

	/*
	 * cookies清理：
	 */
	public void clear_cookie() {

		CookieSyncManager.createInstance(this);
		CookieSyncManager.getInstance().startSync();
		CookieManager.getInstance().removeSessionCookie();

	}

	/*
	 * 
	 * 
	 */
	public void clear_history() {
		wv_myweb.clearCache(true);
		wv_myweb.clearHistory();
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		// TODO Auto-generated method stub
		if (keyCode == KeyEvent.KEYCODE_MENU) {
			Toast.makeText(MyWebView.this, "正在刷新...", Toast.LENGTH_SHORT).show();
			wv_myweb.reload();
		}
		if (keyCode == KeyEvent.KEYCODE_BACK) {

			if (wv_myweb.canGoBack()) {
				wv_myweb.goBack();
			} else {
				finish();
			}

		}
		return super.onKeyDown(keyCode, event);
	}
}
