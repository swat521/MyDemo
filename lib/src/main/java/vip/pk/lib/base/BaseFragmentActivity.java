package vip.pk.lib.base;

import android.app.ActivityManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.KeyEvent;
import android.view.WindowManager;
import android.widget.Toast;

import java.util.List;

import vip.pk.lib.Demo;

public abstract class BaseFragmentActivity extends FragmentActivity{
    private MyApp myApp;
    private Context curContent;
    private long firstPressTime;
    private boolean backToExit = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        init();
        initView();
    }

    private void init() {
        AppManager.getAppManager().addActivity(this);
        myApp = (MyApp) getApplicationContext();
        curContent = BaseFragmentActivity.this;

        // 禁止键盘自动弹出
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

    }

    /**
     * 初始化布局
     */
    public abstract void initView();

    public void openActivity(Class<?> cls) {
        Intent i = new Intent(curContent, cls);
        startActivity(i);
    }

    public void openActivity(Class<?> cls, Bundle b) {
        Intent i = new Intent(curContent, cls);
        i.putExtras(b);
        startActivity(i);
    }

    public void openActivity(Class<?> cls, Bundle b,boolean isFinish) {
        Intent i = new Intent(curContent, cls);
        i.putExtras(b);
        startActivity(i);
        if(isFinish){
            finish();
        }
    }


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (backToExit) {
            if (keyCode == KeyEvent.KEYCODE_BACK) {
                if (System.currentTimeMillis() - firstPressTime < 2000) {
                    // finish();
                    // 退出程序
                    exitApp();
                } else {
                    firstPressTime = System.currentTimeMillis();
                    Toast.makeText(curContent, "再按一次退出", Toast.LENGTH_LONG).show();
                }
                return true;
            }


            if(keyCode == KeyEvent.KEYCODE_MENU) {
                Intent i = new Intent(curContent, Demo.class);
                startActivity(i);
            }
        }

        return super.onKeyDown(keyCode, event);
    }

    public void exitApp() {
        // 退出程序
        AppManager.getAppManager().AppExit(BaseFragmentActivity.this);
    }

    //根据包名和类名启动应用程序
    public void startAppByPackageName2ClassName(String packageName, String className) {
        Intent intent = new Intent(Intent.ACTION_MAIN);
        intent.addCategory(Intent.CATEGORY_LAUNCHER);
        ComponentName cn = new ComponentName(packageName, className);
        intent.setComponent(cn);
        startActivity(intent);
    }
    //根据包名应用程序

    /**
     *
     * @param packagename
     */
    public void startAppByPackageName(String packagename) {

        // 通过包名获取此APP详细信息，包括Activities、services、versioncode、name等等
        PackageInfo packageinfo = null;
        try {
            packageinfo = getPackageManager().getPackageInfo(packagename, 0);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        if (packageinfo == null) {
            return;
        }

        // 创建一个类别为CATEGORY_LAUNCHER的该包名的Intent
        Intent resolveIntent = new Intent(Intent.ACTION_MAIN, null);
        resolveIntent.addCategory(Intent.CATEGORY_LAUNCHER);
        resolveIntent.setPackage(packageinfo.packageName);

        // 通过getPackageManager()的queryIntentActivities方法遍历
        List<ResolveInfo> resolveinfoList = getPackageManager().queryIntentActivities(resolveIntent, 0);

        ResolveInfo resolveinfo = resolveinfoList.iterator().next();
        if (resolveinfo != null) {
            // packagename = 参数packname
            String packageName = resolveinfo.activityInfo.packageName;
            // 这个就是我们要找的该APP的LAUNCHER的Activity[组织形式：packagename.mainActivityname]
            String className = resolveinfo.activityInfo.name;
            // LAUNCHER Intent
            Intent intent = new Intent(Intent.ACTION_MAIN);
            intent.addCategory(Intent.CATEGORY_LAUNCHER);

            // 设置ComponentName参数1:packagename参数2:MainActivity路径
            ComponentName cn = new ComponentName(packageName, className);

            intent.setComponent(cn);
            startActivity(intent);
        }
    }
    /**
     * 返回app运行状态
     * 1:程序在前台运行
     * 2:程序在后台运行
     * 3:程序未启动
     * 注意：需要配置权限<uses-permission android:name="android.permission.GET_TASKS" />
     */
    public int getAppSatus(Context context, String pageName) {

        ActivityManager am = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningTaskInfo> list = am.getRunningTasks(20);

        //判断程序是否在栈顶
        if (list.get(0).topActivity.getPackageName().equals(pageName)) {
            return 1;
        } else {
            //判断程序是否在栈里
            for (ActivityManager.RunningTaskInfo info : list) {
                if (info.topActivity.getPackageName().equals(pageName)) {
                    return 2;
                }
            }
            return 3;//栈里找不到，返回3
        }
    }

}











