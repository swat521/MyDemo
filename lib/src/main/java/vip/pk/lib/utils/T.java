package vip.pk.pklib.utils;

import android.content.Context;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.RotateAnimation;
import android.view.animation.ScaleAnimation;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

public class T {

    private static Toast toast;

    static AnimationSet startAnim;
    static AnimationSet endAnim;

    static boolean show_sys_toast = true;

    /**
     * 短时间显示Toast
     *
     * @param context
     * @param message
     */
    public static void showShort(Context context, CharSequence message) {

        if (null == toast) {
            toast = Toast.makeText(context, message, Toast.LENGTH_SHORT);
            // toast.setGravity(Gravity.CENTER, 0, 0);
        } else {
            toast.setText(message);
        }
        toast.show();


    }

    /**
     * 短时间显示Toast
     *
     * @param context
     * @param message
     */
    public static void showShort(Context context, int message) {

        if (null == toast) {
            toast = Toast.makeText(context, message, Toast.LENGTH_SHORT);
            // toast.setGravity(Gravity.CENTER, 0, 0);
        } else {
            toast.setText(message);
        }
        toast.show();

    }

    /**
     * 长时间显示Toast
     *
     * @param context
     * @param message
     */
    public static void showLong(Context context, CharSequence message) {

        if (null == toast) {
            toast = Toast.makeText(context, message, Toast.LENGTH_LONG);
            // toast.setGravity(Gravity.CENTER, 0, 0);
        } else {
            toast.setText(message);
        }
        toast.show();

    }

    /**
     * 长时间显示Toast
     *
     * @param context
     * @param message
     */
    public static void showLong(Context context, int message) {

        if (null == toast) {
            toast = Toast.makeText(context, message, Toast.LENGTH_LONG);
            // toast.setGravity(Gravity.CENTER, 0, 0);
        } else {
            toast.setText(message);
        }
        toast.show();

    }

    /**
     * 自定义显示Toast时间
     *
     * @param context
     * @param message
     * @param duration
     */
    public static void show(Context context, CharSequence message, int duration) {

        if (null == toast) {
            toast = Toast.makeText(context, message, duration);
            // toast.setGravity(Gravity.CENTER, 0, 0);
        } else {
            toast.setText(message);
        }
        toast.show();

    }

    /**
     * 自定义显示Toast时间
     *
     * @param context
     * @param message
     * @param duration
     */
    public static void show(Context context, int message, int duration) {

        if (null == toast) {
            toast = Toast.makeText(context, message, duration);
            // toast.setGravity(Gravity.CENTER, 0, 0);
        } else {
            toast.setText(message);
        }
        toast.show();

    }

    /** Hide the toast, if any. */
    public static void hideToast() {
        if (null != toast) {
            toast.cancel();
        }
    }

    public static void show_mytoast(Context context, String message) {
        anim();
        TDrawerToast my_toast = TDrawerToast.getInstance(context);
        my_toast.show(message);
    }

    public static void show_logo_mytoast(Context context,int res_id){

        TDrawerToast my_toast = TDrawerToast.getInstance(context);
        //显示应用Logo
        ImageView iv = new ImageView(context);
        iv.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT));
        iv.setImageResource(res_id);

        my_toast.show(iv);
    }

    public static void show_time_mytoast(Context context,String message,int duration){

        TDrawerToast my_toast = TDrawerToast.getInstance(context);
        //持续5000毫秒
        my_toast.show(message, duration);
    }






    public static void show_anim_mytoast(Context context, String message) {
        anim();
        TDrawerToast my_toast = TDrawerToast.getInstance(context);
        my_toast.show(message, null, startAnim, endAnim);
    }

    public static void anim() {
        // 入场动画
        // 旋转
        RotateAnimation rAnim = new RotateAnimation(0, 720, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        rAnim.setDuration(500);
        rAnim.setFillAfter(true);
        // 缩放
        ScaleAnimation sAnim = new ScaleAnimation(0, 1, 0, 1, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        sAnim.setDuration(500);
        sAnim.setFillAfter(true);
        // 透明度
        AlphaAnimation aAnim1 = new AlphaAnimation(0, 1);
        aAnim1.setDuration(500);
        aAnim1.setFillAfter(true);
        startAnim = new AnimationSet(false);
        startAnim.addAnimation(rAnim);
        startAnim.addAnimation(aAnim1);
        startAnim.addAnimation(sAnim);

        // 离场动画
        // ScaleAnimation sAnim = new ScaleAnimation(1,0,1,0);
        // sAnim.setDuration(500);
        // sAnim.setFillAfter(true);
        // 移动
        TranslateAnimation animTrans = new TranslateAnimation(Animation.RELATIVE_TO_PARENT, 0f, Animation.RELATIVE_TO_PARENT, 0f, Animation.RELATIVE_TO_SELF, 0f, Animation.RELATIVE_TO_SELF, 1f);
        animTrans.setDuration(500);
        animTrans.setFillAfter(true);
        // 透明度
        AlphaAnimation aAnim2 = new AlphaAnimation(1, 0);
        aAnim2.setDuration(500);
        aAnim2.setFillAfter(true);
        endAnim = new AnimationSet(false);
        endAnim.addAnimation(animTrans);
        endAnim.addAnimation(aAnim2);

    }
}
