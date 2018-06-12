package vip.pk.pklib.utils;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import android.app.ActivityManager;
import android.content.Context;
import android.util.Log;

public class L {

	private static Boolean MYLOG_SWITCH = true; // 日志文件总开关
	private static Boolean MYLOG_WRITE_TO_FILE = true;// 日志写入文件开关
	private static char MYLOG_TYPE = 'v';// 输入日志类型，w代表只输出告警信息等，v代表输出所有信息
	private static String MYLOG_PATH_SDCARD_DIR = "/sdcard/";// 日志文件在sdcard中的路径
	private static int SDCARD_LOG_FILE_SAVE_DAYS = 0;// sd卡中日志文件的最多保存天数
	private static String MYLOGFILEName = "Log_pkpk8.txt";// 本类输出的日志文件名称
	private static SimpleDateFormat myLogSdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");// 日志的输出格式
	private static SimpleDateFormat logfile = new SimpleDateFormat("yyyy-MM-dd");// 日志文件格式
	static String tag = "pk";

	public static void w(Object msg) { // 警告信息
		if (MYLOG_SWITCH) {

			Log.e(tag, "======>" + msg);

			if (MYLOG_WRITE_TO_FILE) {
				writeLogtoFile(msg+"");
			}
		}
	}

	public static void e(Exception e) {

		if (MYLOG_SWITCH) {

			Log.e("debug",Log.getStackTraceString(e)); 
			e.printStackTrace();
			StringWriter sw = new StringWriter();
			e.printStackTrace(new PrintWriter(sw));
			Log.e(tag, "======>" + "错误原因：" + sw.toString());

			if (MYLOG_WRITE_TO_FILE) {

				writeLogtoFile("错误原因：" + sw.toString());

			}

		}

	}

	public static void w(Context c,Object msg) {
		if (MYLOG_SWITCH) {

			Log.e(tag, "===<"+getRunningActivityName(c)+">===>" + msg);
			T.showLong(c,"===<"+getRunningActivityName(c)+">===>" + msg);
			if (MYLOG_WRITE_TO_FILE) {
				writeLogtoFile(msg+"");
			}
		}
	}

	public static void e(Context c,Exception e) {

		if (MYLOG_SWITCH) {

			e.printStackTrace();
			StringWriter sw = new StringWriter();
			e.printStackTrace(new PrintWriter(sw));
			Log.e(tag, "===<"+getRunningActivityName(c)+">===>" + "错误原因：" + sw.toString());
			T.showLong(c,"===<"+getRunningActivityName(c)+">===>" + "错误原因：" + sw.toString());
			if (MYLOG_WRITE_TO_FILE) {

				writeLogtoFile("错误原因：" + sw.toString());

			}

		}

	}
	
	
	private static String getRunningActivityName(Context c) {
		ActivityManager activityManager = (ActivityManager) c.getSystemService(Context.ACTIVITY_SERVICE);
		String runningActivity = activityManager.getRunningTasks(1).get(0).topActivity.getClassName();
		return runningActivity;
	}



	/**
	 * 打开日志文件并写入日志
	 * 
	 * @return
	 * **/
	private static void writeLogtoFile(String text) {// 新建或打开日志文件
		Date nowtime = new Date();
		String needWriteFiel = logfile.format(nowtime);
		String needWriteMessage = myLogSdf.format(nowtime) + "    " + "    " + text;
		File file = new File(MYLOG_PATH_SDCARD_DIR, needWriteFiel + MYLOGFILEName);
		try {
			FileWriter filerWriter = new FileWriter(file, true);// 后面这个参数代表是不是要接上文件中原来的数据，不进行覆盖
			BufferedWriter bufWriter = new BufferedWriter(filerWriter);
			bufWriter.write(needWriteMessage);
			bufWriter.newLine();
			bufWriter.close();
			filerWriter.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * 删除制定的日志文件
	 * */
	public static void delFile() {// 删除日志文件
		String needDelFiel = logfile.format(getDateBefore());
		File file = new File(MYLOG_PATH_SDCARD_DIR, needDelFiel + MYLOGFILEName);
		if (file.exists()) {
			file.delete();
		}
	}

	/**
	 * 得到现在时间前的几天日期，用来得到需要删除的日志文件名
	 * */
	private static Date getDateBefore() {
		Date nowtime = new Date();
		Calendar now = Calendar.getInstance();
		now.setTime(nowtime);
		now.set(Calendar.DATE, now.get(Calendar.DATE) - SDCARD_LOG_FILE_SAVE_DAYS);
		return now.getTime();
	}

}
