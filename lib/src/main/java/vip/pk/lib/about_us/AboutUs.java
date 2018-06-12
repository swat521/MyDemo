package vip.pk.lib.about_us;

import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import vip.pk.lib.R;
import vip.pk.lib.base.BaseActivity;
import vip.pk.lib.dialog.DialogManager;

public class AboutUs extends BaseActivity {
	
	private DialogManager dm;
	
	String[] pay_list = new String[] { "支付宝支付" };
	protected String pay_info;

	@Override
	public void initView() {

		setContentView(R.layout.about_us);
	}

	public void juan_zeng(final View v) {

		

		DialogManager.list_dialog(v.getContext(),"捐赠方式", pay_list, new OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int which) {

				pay_info = pay_list[which];

				if (pay_info.equals("支付宝支付")) {
					toAlipayScan();
				} else if (pay_info.equals("微信支付")) {
					toWeChatScan();
				} else if (pay_info.equals("paypal")) {

				} else {

					Intent i = getPackageManager().getLaunchIntentForPackage("com.tencent.mobileqq");
					i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
					i.putExtra("swat521", true);
					startActivity(i);
				}
				dialog.dismiss();
			}
		});
		/*
				
		*/
	}
	private void toAlipayScan() {
		try {
			//利用Intent打开支付宝
			//支付宝跳过开启动画打开扫码和付款码的url scheme分别是alipayqr://platformapi/startapp?saId=10000007和
			//alipayqr://platformapi/startapp?saId=20000056

			String weixin_pay_info = "&qrcode=" + "https://qr.alipay.com/aellar2jzw4u4upsdf?_s=web-other";

			Uri uri = Uri.parse("alipayqr://platformapi/startapp?saId=10000007" + weixin_pay_info);
			Intent intent = new Intent(Intent.ACTION_VIEW, uri);
			startActivity(intent);
		} catch (Exception e) {
			//若无法正常跳转，在此进行错误处理
			Toast.makeText(curContent, "无法跳转到支付宝，请检查您是否安装了支付宝！", Toast.LENGTH_SHORT).show();
		}

		/*
		try {
			//Uri qr_uri = BaseTools.get_drawable_uri(cur_content, R.drawable.alipay_qr);
		
			Intent i = new Intent();//https://qr.alipay.com/apak0kwgrghltsbt6b?_s=web-other
			i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
			i.setAction("android.intent.action.VIEW");
			i.setData(Uri.parse("alipayqr://platformapi/startapp?saId=10000007&clientVersion=3.7.0.0718&qrcode=" + "https://qr.alipay.com/aellar2jzw4u4upsdf?_s=web-other"));
		
			startActivity(i);
		} catch (Exception e) {
		    //若无法正常跳转，在此进行错误处理
		    T.showLong(cur_content, "无法跳转到支付宝，请检查您是否安装了支付宝！");
		}
		*/
	}

	private void toWeChatScan() {

		try {
			//利用Intent打开微信
			Uri uri = Uri.parse("weixin://dl/scan");
			Intent intent = new Intent(Intent.ACTION_VIEW, uri);
			startActivity(intent);
		} catch (Exception e) {
			//若无法正常跳转，在此进行错误处理
			Toast.makeText(curContent, "无法跳转到微信，请检查您是否安装了微信！", Toast.LENGTH_SHORT).show();
		}
		/*
		try {
			
			Intent i = new Intent();
			//i.setAction(Intent.ACTION_VIEW);
			i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
			i.setAction(Intent.ACTION_MAIN);
			i.addCategory(Intent.CATEGORY_LAUNCHER);
			i.setClassName("com.tencent.mm", "com.tencent.mm.ui.LauncherUI");
		    i.setData(Uri.parse("weixin://dl/scan"));
		    //weixin://dl/businessWebview/link/?appid=%s&url=%s
		    startActivity(i);
			
		} catch (Exception e) {
		    //若无法正常跳转，在此进行错误处理
		    Toast.makeText(About_us.this, "无法跳转到微信，请检查您是否安装了微信！", Toast.LENGTH_SHORT).show();
		}
		*/
	}

}
