package vip.pk.lib.base;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.os.Looper;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Toast;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import vip.pk.lib.utils.FileTxt;

public class CrashHandler implements Thread.UncaughtExceptionHandler {
    private Thread.UncaughtExceptionHandler mDefaultHandler;
    // CrashHandler实例
    private static CrashHandler INSTANCE = new CrashHandler();
    // 程序的Context对象
    private Context mContext;

    private CrashHandler() {
    }

    public static CrashHandler getInstance() {
        return INSTANCE;
    }

    /**
     * 初始化
     *
     * @param context
     */
    public void init(Context context) {
        mContext = context;
        // 获取系统默认的UncaughtException处理
        mDefaultHandler = Thread.getDefaultUncaughtExceptionHandler();
        // 设置该CrashHandler为程序的默认处理
        Thread.setDefaultUncaughtExceptionHandler(this);
    }

    /**
     * 异常捕获
     */
    @Override
    public void uncaughtException(Thread thread, Throwable ex) {
        if (!handleException(ex) && mDefaultHandler != null) {
            // 如果用户没有处理则让系统默认的异常处理器来处
            mDefaultHandler.uncaughtException(thread, ex);
        } else {
            // 跳转到崩溃提示Activity
            Intent intent = new Intent(mContext, CrashDialog.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            mContext.startActivity(intent);
            System.exit(0);// 关闭已奔溃的app进程
        }
    }

    /**
     * 自定义错误捕获
     *
     * @param ex
     * @return true:如果处理了该异常信息;否则返回false.
     */
    private boolean handleException(Throwable ex) {
        if (ex == null) {
            return false;
        }

        // 收集错误信息
        getCrashInfo(ex);

        return true;
    }

    /**
     * 收集错误信息
     *
     * @param ex
     */
    private void getCrashInfo(Throwable ex) {
        Writer writer = new StringWriter();
        PrintWriter printWriter = new PrintWriter(writer);
        ex.printStackTrace(printWriter);
        Throwable cause = ex.getCause();
        while (cause != null) {
            cause.printStackTrace(printWriter);
            cause = cause.getCause();
        }
        printWriter.close();
        String errorMessage = writer.toString();
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            String mFilePath = Environment.getExternalStorageDirectory() + "/" + "x024_error.log";
            FileTxt.WirteTxt(mFilePath, FileTxt.ReadTxt(mFilePath) + '\n' + errorMessage);
        } else {
            Log.i("pk", "哦豁，说好的SD呢...");
        }

    }

}
