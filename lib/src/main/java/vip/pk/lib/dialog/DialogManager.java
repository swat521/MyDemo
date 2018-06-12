package vip.pk.lib.dialog;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface.OnClickListener;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.TextView;

import vip.pk.lib.R;

public class DialogManager {

	private static Dialog dialog;

	
	public static void closeDialog(){
		dialog.dismiss();
	}
	
	
	/*
	 * 自定义视图对话框
	 */
	public static void DiyViewDialog(Context context, String msg, String btn_ok_str, View.OnClickListener ls_ok, String btn_cannel_str, View.OnClickListener ls_cancel) {

		dialog = new Dialog(context, R.style.dialog_diy_view);
		// LayoutInflater是用来找layout文件夹下的xml布局文件，并且实例化
		LayoutInflater factory = LayoutInflater.from(context);
		// 把activity_login中的控件定义在View中
		View view = factory.inflate(R.layout.dialog_diy_view, null);
		// 将LoginActivity中的控件显示在对话框中

		TextView tv_title = (TextView) view.findViewById(R.id.tv_title);
		tv_title.setText("温馨提示");
		TextView tv_msg = (TextView) view.findViewById(R.id.tv_msg);
		tv_msg.setText(msg);

		//按钮中间的分割线
		View v_btn_diver = (View) view.findViewById(R.id.v_btn_diver);

		//确定按钮
		TextView tv_btn_ok = (TextView) view.findViewById(R.id.tv_btn_ok);

		if (btn_ok_str.equals("")) {
			v_btn_diver.setVisibility(View.GONE);
			tv_btn_ok.setVisibility(View.GONE);
		} else {
			tv_btn_ok.setVisibility(View.VISIBLE);
			tv_btn_ok.setText(btn_ok_str);
			tv_btn_ok.setOnClickListener(ls_ok);
		}
		TextView tv_btn_cannel = (TextView) view.findViewById(R.id.tv_btn_cannel);
		if (btn_cannel_str.equals("")) {
			v_btn_diver.setVisibility(View.GONE);
			tv_btn_cannel.setVisibility(View.GONE);
		} else {
			tv_btn_ok.setVisibility(View.VISIBLE);
			tv_btn_cannel.setText(btn_cannel_str);
			tv_btn_cannel.setOnClickListener(ls_cancel);
		}
		dialog.setCancelable(false);
		dialog.setContentView(view);
		dialog.setCanceledOnTouchOutside(false);
		Window window = dialog.getWindow();
		window.setGravity(Gravity.CENTER); // 此处可以设置dialog显示的位置
		//window.setWindowAnimations(R.style.dialog_anim_style); // 添加动画

		dialog.show();
	}

	/*
	 * 列表对话框
	 */

	public static void list_dialog(final Context context,String title, final String[] strArr,OnClickListener onClickListener) {

		AlertDialog.Builder builder = new AlertDialog.Builder(context);

		builder.setTitle(title).setIcon(R.mipmap.tip).setItems(strArr, onClickListener).show();

	}

	/**
	 * PopupWindow做的对话框 感谢： http://www.apkbus.com/android-56965-1-1.html http://blog.csdn.net/zhufuing/article/details/17783333 http://www.open-open.com/lib/view/open1379383271818.html
	 * 
	 * @param title
	 * @param v
	 */
	public static void popupWindowDialog(Context context,String title, View v, String showType) {
		/*
		// 装载布局文件
		View view = LayoutInflater.from(context).inflate(R.layout.dialog_alertdialog, null);
		// 创建PopupWindow对象，添加视图，设置宽高，最后一个参数为设置点击屏幕空白处(按返回键)对话框消失。
		// 也可以用.setFocusable(true);.
		final PopupWindow pWindow = new PopupWindow(view, LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT, true);
		pWindow.setBackgroundDrawable(new BitmapDrawable());// 为了让对话框点击空白处消失，必须有这条语句
		pWindow.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);// 出现输入法时，重新布局
		pWindow.setAnimationStyle(R.style.AnimationFromTop);// 设置动画

		TextView titleTv = (TextView) view.findViewById(R.id.tv_alert_dialog);
		titleTv.setText(title);
		Button btn = (Button) view.findViewById(R.id.btn_ok);
		btn.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View arg0) {
				//showToast("按下PopupWindow中的按钮了~");
				pWindow.dismiss();
			}
		});

		if (showType.equals("drop")) {
			// 用下拉方式显示
			pWindow.showAsDropDown(v);
		} else if (showType.equals("other")) {

		} else {
			//下方弹出
			pWindow.showAtLocation(v, Gravity.BOTTOM, 0, 0);
		}
*/
	}

	

}
