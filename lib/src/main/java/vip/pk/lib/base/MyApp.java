package vip.pk.lib.base;

import android.app.Application;
import android.content.Context;

import com.facebook.stetho.Stetho;
import com.lzy.okgo.OkGo;

public class MyApp extends Application {
    int cur = 0;
    private static Application mApplication;

    public MyApp() {
        this.mApplication = this;
    }

    /**
     * @return the Context of this application
     */
    public static Context getInstance() {
        return mApplication;
    }

    public static Context getContext() {
        return mApplication;
    }

    @Override
    public void onCreate() {
        // TODO Auto-generated method stub
        super.onCreate();
        initDbDebug();
        //异常
        CrashHandler.getInstance().init(this);
        //网络请求
        initOkGo();
    }

    private void initOkGo(){
        OkGo.getInstance().init(this);
    }


    /**
     * 网页查看本地存储数据库工具,不用root权限
     */
    private void initDbDebug() {
        Stetho.initialize(
                Stetho.newInitializerBuilder(this)
                        .enableDumpapp(Stetho.defaultDumperPluginsProvider(this))
                        .enableWebKitInspector(Stetho.defaultInspectorModulesProvider(this))
                        .build());
    }


    public int getCurNum() {
        return cur;
    }

    public void setCurNum(int cur_num) {
        cur = cur_num;
    }




}
