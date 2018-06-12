package vip.pk.pklib.utils;

import java.io.BufferedReader;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.InetAddress;
import java.net.MalformedURLException;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.URL;
import java.net.URLConnection;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Enumeration;
import java.util.List;
import java.util.UUID;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ActivityManager;
import android.app.ActivityManager.RunningAppProcessInfo;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Environment;
import android.os.StatFs;
import android.telephony.TelephonyManager;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;




@SuppressLint("NewApi")
public class BaseTools {

	public static Uri get_drawable_uri(Context context, int drawable) {
		Resources r = context.getResources();
		Uri uri = Uri.parse(ContentResolver.SCHEME_ANDROID_RESOURCE + "://" + r.getResourcePackageName(drawable) + "/" + r.getResourceTypeName(drawable) + "/" + r.getResourceEntryName(drawable));
		return uri;
	}

	//获得系统运行
	public static void get_prograss(Context context) {
		ActivityManager mActivityManager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);

		//获得系统运行的进程  
		List<RunningAppProcessInfo> appList1 = mActivityManager.getRunningAppProcesses();
		for (RunningAppProcessInfo running : appList1) {
			System.out.println(running.processName);
		}
		System.out.println("================");

		//获得当前正在运行的service  
		List<ActivityManager.RunningServiceInfo> appList2 = mActivityManager.getRunningServices(100);
		for (ActivityManager.RunningServiceInfo running : appList2) {
			System.out.println(running.service.getClassName());
		}

		System.out.println("================");

		//获得当前正在运行的activity  
		List<ActivityManager.RunningTaskInfo> appList3 = mActivityManager.getRunningTasks(1000);
		for (ActivityManager.RunningTaskInfo running : appList3) {
			System.out.println(running.baseActivity.getClassName());
		}
		System.out.println("================");

		//获得最近运行的应用  
		List<ActivityManager.RecentTaskInfo> appList4 = mActivityManager.getRecentTasks(100, 1);
		for (ActivityManager.RecentTaskInfo running : appList4) {
			System.out.println(running.origActivity.getClassName());
		}
	}

	/**
	 * 软件静默安装
	 * 
	 * @param apkAbsolutePath
	 *            apk文件所在路径
	 * @return 安装结果:获取到的result值<br>
	 * 
	 *         如果安装成功的话是“ pkg: /data/local/tmp/Calculator.apk /nSuccess”，<br>
	 *         如果是失败的话，则没有结尾的“Success”。
	 */
	public String silentInstall(String apkAbsolutePath) {
		String[] args = { "pm", "install", "-r", apkAbsolutePath };
		String result = "";
		ProcessBuilder processBuilder = new ProcessBuilder(args);
		Process process = null;
		InputStream errIs = null;
		InputStream inIs = null;
		try {
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			int read = -1;
			process = processBuilder.start();
			errIs = process.getErrorStream();
			while ((read = errIs.read()) != -1) {
				baos.write(read);
			}
			baos.write("/n".getBytes());
			inIs = process.getInputStream();
			while ((read = inIs.read()) != -1) {
				baos.write(read);
			}
			byte[] data = baos.toByteArray();
			result = new String(data);
		} catch (IOException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (errIs != null) {
					errIs.close();
				}
				if (inIs != null) {
					inIs.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
			if (process != null) {
				process.destroy();
			}
		}
		return result;
	}

	/**
	 * 根据手机的分辨率从 dp 的单位 转成为 px(像素)
	 */
	public static int dip2px(Context context, float dpValue) {
		final float scale = context.getResources().getDisplayMetrics().density;
		return (int) (dpValue * scale + 0.5f);
	}

	/**
	 * 根据手机的分辨率从 px(像素) 的单位 转成为 dp
	 */
	public static int px2dip(Context context, float pxValue) {
		final float scale = context.getResources().getDisplayMetrics().density;
		return (int) (pxValue / scale + 0.5f);
	}

	
	/**
     * 获取屏幕高度(px)
     */
    public static int getScreenHeight(Context context) {
        return context.getResources().getDisplayMetrics().heightPixels;
    }
    /**
     * 获取屏幕宽度(px)
     */
    public static int getScreenWidth(Context context) {
        return context.getResources().getDisplayMetrics().widthPixels;
    }

    public static float dp2px(Context context, float dp) {
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, context.getResources().getDisplayMetrics());
    }
	
	/**
	 * 获取当前应用版本号
	 * 
	 * @param context
	 * @return version
	 * @throws Exception
	 */
	public static String getAppVersion(Context context) throws Exception {
		// 获取packagemanager的实例
		PackageManager packageManager = context.getPackageManager();
		// getPackageName()是你当前类的包名，0代表是获取版本信息
		PackageInfo packInfo = packageManager.getPackageInfo(context.getPackageName(), 0);
		String versionName = packInfo.versionName;
		return versionName;
	}

	/**
	 * 获取当前系统SDK版本号
	 */
	public static int getSystemVersion() {
		/*获取当前系统的android版本号*/
		int version = android.os.Build.VERSION.SDK_INT;
		return version;
	}

	public static int getWindowWidth(Context context) {
		// 获取屏幕分辨率
		WindowManager wm = (WindowManager) (context.getSystemService(Context.WINDOW_SERVICE));
		DisplayMetrics dm = new DisplayMetrics();
		wm.getDefaultDisplay().getMetrics(dm);
		int mScreenWidth = dm.widthPixels;
		return mScreenWidth;
	}

	public static int getWindowHeigh(Context context) {
		// 获取屏幕分辨率
		WindowManager wm = (WindowManager) (context.getSystemService(Context.WINDOW_SERVICE));
		DisplayMetrics dm = new DisplayMetrics();
		wm.getDefaultDisplay().getMetrics(dm);
		int mScreenHeigh = dm.heightPixels;
		return mScreenHeigh;
	}
/*
	// 获取手机的IMEI
	public static String getImei(Context context) {
		TelephonyManager tm = (TelephonyManager) (context.getSystemService(Context.TELEPHONY_SERVICE));
		String imei = tm.getDeviceId();
		return imei;
	}

	// 获取手机的IMSI
	public static String getImsi(Context context) {
		TelephonyManager tm = (TelephonyManager) (context.getSystemService(Context.TELEPHONY_SERVICE));
		String imsi = tm.getSubscriberId();
		return imsi;
	}
*/
	// 获取当前时间
	public static String getCurTime(String Data_Format) {
		if (Data_Format.equals("")) {
			Data_Format = "yyyy年MM月dd日 HH:mm:ss ";
		}
		SimpleDateFormat formatter = new SimpleDateFormat(Data_Format);
		Date curDate = new Date(System.currentTimeMillis());// 获取当前时间
		String str = formatter.format(curDate);
		return str;
	}

	// 获取本机容量信息
	public static String phoneCapacity() {
		// 获取本机信息
		File data = Environment.getDataDirectory();
		StatFs statFs = new StatFs(data.getPath());
		int availableBlocks = statFs.getAvailableBlocks();// 可用存储块的数量
		int blockCount = statFs.getBlockCount();// 总存储块的数量

		int size = statFs.getBlockSize();// 每块存储块的大小

		int totalSize = blockCount * size;// 总存储量

		int availableSize = availableBlocks * size;// 可用容量

		String phoneCapacity = Integer.toString(availableSize / 1024 / 1024) + "MB/" + Integer.toString(totalSize / 1024 / 1024) + "MB";

		return phoneCapacity;
	}

	// 获取sdcard容量信息
	public static String sdcardCapacity() {
		// 获取sdcard信息
		File sdData = Environment.getExternalStorageDirectory();
		StatFs sdStatFs = new StatFs(sdData.getPath());

		int sdAvailableBlocks = sdStatFs.getAvailableBlocks();// 可用存储块的数量
		int sdBlockcount = sdStatFs.getBlockCount();// 总存储块的数量
		int sdSize = sdStatFs.getBlockSize();// 每块存储块的大小
		int sdTotalSize = sdBlockcount * sdSize;
		int sdAvailableSize = sdAvailableBlocks * sdSize;

		String sdcardCapacity = Integer.toString(sdAvailableSize / 1024 / 1024) + "MB/" + Integer.toString(sdTotalSize / 1024 / 1024) + "MB";
		return sdcardCapacity;
	}

	/**
	 * 外部存储是否可用
	 * 
	 * @return
	 */
	static public boolean externalMemoryAvailable() {
		return Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED);
	}

	/**
	 * 获取手机内部可用空间大小
	 * 
	 * @return
	 */
	static public long getAvailableInternalMemorySize() {
		File path = Environment.getDataDirectory();
		StatFs stat = new StatFs(path.getPath());
		long blockSize = stat.getBlockSize();
		long availableBlocks = stat.getAvailableBlocks();
		return availableBlocks * blockSize;
	}

	/**
	 * 获取手机内部空间大小
	 * 
	 * @return
	 */
	static public long getTotalInternalMemorySize() {
		File path = Environment.getDataDirectory();
		StatFs stat = new StatFs(path.getPath());
		long blockSize = stat.getBlockSize();
		long totalBlocks = stat.getBlockCount();
		return totalBlocks * blockSize;
	}

	static final int ERROR = -1;

	/**
	 * 获取手机外部可用空间大小
	 * 
	 * @return
	 */
	static public long getAvailableExternalMemorySize() {
		if (externalMemoryAvailable()) {
			File path = Environment.getExternalStorageDirectory();
			StatFs stat = new StatFs(path.getPath());
			long blockSize = stat.getBlockSize();
			long availableBlocks = stat.getAvailableBlocks();
			return availableBlocks * blockSize;
		} else {
			return ERROR;
		}
	}

	/**
	 * 获取手机外部空间大小
	 * 
	 * @return
	 */
	static public long getTotalExternalMemorySize() {
		if (externalMemoryAvailable()) {
			File path = Environment.getExternalStorageDirectory();
			StatFs stat = new StatFs(path.getPath());
			long blockSize = stat.getBlockSize();
			long totalBlocks = stat.getBlockCount();
			return totalBlocks * blockSize;
		} else {
			return ERROR;
		}
	}

	static public String formatSize(long size) {
		String suffix = null;

		if (size >= 1024) {
			suffix = "KiB";
			size /= 1024;
			if (size >= 1024) {
				suffix = "MiB";
				size /= 1024;
			}
		}

		StringBuilder resultBuffer = new StringBuilder(Long.toString(size));
		int commaOffset = resultBuffer.length() - 3;
		while (commaOffset > 0) {
			resultBuffer.insert(commaOffset, ',');
			commaOffset -= 3;
		}

		if (suffix != null)
			resultBuffer.append(suffix);
		return resultBuffer.toString();
	}

	// 得到本机ip地址
	public static String getLocalHostIp() {
		String ipaddress = "";
		try {
			Enumeration<NetworkInterface> en = NetworkInterface.getNetworkInterfaces();
			// 遍历所用的网络接口
			while (en.hasMoreElements()) {
				NetworkInterface nif = en.nextElement();// 得到每一个网络接口绑定的所有ip
				Enumeration<InetAddress> inet = nif.getInetAddresses();
				// 遍历每一个接口绑定的所有ip
				while (inet.hasMoreElements()) {
					InetAddress ip = inet.nextElement();
					if (!ip.isLoopbackAddress()) {//&& InetAddressUtils.isIPv4Address(ip.getHostAddress())
						return ipaddress = "本机的ip是" + "：" + ip.getHostAddress();
					}
				}

			}
		} catch (SocketException e) {
			Log.e("feige", "获取本地ip地址失败");
			e.printStackTrace();
		}
		return ipaddress;

	}

	public static String GetNetIp() {
		URL infoUrl = null;
		InputStream inStream = null;
		try {
			// http://iframe.ip138.com/ic.asp
			// infoUrl = new URL("http://city.ip138.com/city0.asp");
			infoUrl = new URL("http://iframe.ip138.com/ic.asp");
			URLConnection connection = infoUrl.openConnection();
			HttpURLConnection httpConnection = (HttpURLConnection) connection;
			int responseCode = httpConnection.getResponseCode();
			if (responseCode == HttpURLConnection.HTTP_OK) {
				inStream = httpConnection.getInputStream();
				BufferedReader reader = new BufferedReader(new InputStreamReader(inStream, "utf-8"));
				StringBuilder strber = new StringBuilder();
				String line = null;
				while ((line = reader.readLine()) != null)
					strber.append(line + "\n");
				inStream.close();
				// 从反馈的结果中提取出IP地址
				int start = strber.indexOf("[");
				int end = strber.indexOf("]", start + 1);
				line = strber.substring(start + 1, end);
				return line;
			}
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	// 获取wifi信息
	public static String getConnectWifiSsid(Context context) {

		WifiManager wifiManager = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
		WifiInfo wifiInfo = wifiManager.getConnectionInfo();

		return wifiInfo.getSSID();
	}

	// 得到本机Mac地址
	public static String getLocalMac(Context context) {

		WifiManager wifi = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
		WifiInfo info = wifi.getConnectionInfo();
		return info.getMacAddress();

	}
/*
	// 生成固定 uuid
	public static String getUUID(Context context) {
		final TelephonyManager tm = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);

		final String tmDevice, tmSerial, tmPhone, androidId;
		tmDevice = "" + tm.getDeviceId();
		tmSerial = "" + tm.getSimSerialNumber();
		androidId = "" + android.provider.Settings.Secure.getString(context.getContentResolver(), android.provider.Settings.Secure.ANDROID_ID);

		UUID deviceUuid = new UUID(androidId.hashCode(), ((long) tmDevice.hashCode() << 32) | tmSerial.hashCode());
		String uniqueId = deviceUuid.toString();
		return uniqueId;
	}
*/
	// 去掉uuid中的-
	public static String rd_uuid(String uuid) {
		String temp = uuid.substring(0, 8) + uuid.substring(9, 13) + uuid.substring(14, 18) + uuid.substring(19, 23) + uuid.substring(24);

		return temp;
	}

	// 随机生成uuid
	public static String getRandomUUID() {
		UUID uuid = UUID.randomUUID();
		String uniqueId = uuid.toString();
		Log.d("debug", "----->UUID" + uuid);
		return uniqueId;
	}
/*
	// 创建数据库
	public static void create_db(Context context) {
		DBHelper dbHelper = new DBHelper(context, "user");
		SQLiteDatabase db = dbHelper.getWritableDatabase();
		String sql = "create table if not exists Test(id int primary key,name varchar,sex varchar)";
		db.execSQL(sql);
	}

	public static void create_table(Context context) {
		DBHelper dbHelper = new DBHelper(context, "user");
		SQLiteDatabase db = dbHelper.getWritableDatabase();
		String sql = "create table if not exists Test(id int primary key,name varchar,sex varchar)";
		db.execSQL(sql);
	}

	/***
	 * 动态设置listview的高度 切记 listview_item的布局要LinearLayout,不要用其他的要不，会出错误！
	 * 
	 * @param listView
	 *                    /
	public static void setListViewHeightBasedOnChildren(ListView listView) {
		ListAdapter listAdapter = listView.getAdapter();
		if (listAdapter == null) {
			return;
		}
		int totalHeight = 0;
		for (int i = 0; i < listAdapter.getCount(); i++) {
			View listItem = listAdapter.getView(i, null, listView);
			listItem.measure(0, 0);
			totalHeight += listItem.getMeasuredHeight();
		}
		ViewGroup.LayoutParams params = listView.getLayoutParams();
		params.height = totalHeight + (listView.getDividerHeight() * (listAdapter.getCount() - 1));
		// params.height += 5;// if without this statement,the listview will be
		// a
		// little short
		// listView.getDividerHeight()获取子项间分隔符占用的高度
		// params.height最后得到整个ListView完整显示需要的高度
		listView.setLayoutParams(params);
	}

	public void open_file(Context c, String filesPath) {

		Uri uri = Uri.parse("file://" + filesPath);
		Intent intent = new Intent();
		intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		intent.setAction(Intent.ACTION_VIEW);

		String type = getMIMEType(filesPath);
		intent.setDataAndType(uri, type);
		if (!type.equals("*      /   *")) {
			try {
				c.startActivity(intent);
			} catch (Exception e) {
				c.startActivity(showOpenTypeDialog(filesPath));
			}
		} else {
			c.startActivity(showOpenTypeDialog(filesPath));
		}

	}

	public static String KEY = "";//全局搜索的关键

	
	public static List<AppInfo> getAppList(Context context) {

		List<AppInfo> list = new ArrayList<AppInfo>();// 声明并实例化1个集合
		PackageManager pm = context.getPackageManager();//获取包管理者
		List<PackageInfo> pList = pm.getInstalledPackages(0);// 获取所有的应用程序集合
		// 循环遍历
		for (int i = 0; i < pList.size(); i++) {
			PackageInfo packageInfo = pList.get(i);// 获取每一个应用的信息

			if (isThirdPartyApp(packageInfo.applicationInfo)
					// 不能包含本应用(不删除自己)
					&& !packageInfo.packageName.equals(context.getPackageName())) {

				// 从右边装到左边
				AppInfo appInfo = new AppInfo();
				appInfo.packageName = packageInfo.packageName;
				appInfo.versionName = packageInfo.versionName;
				appInfo.versionCode = packageInfo.versionCode;
				appInfo.firstInstallTime = packageInfo.firstInstallTime;
				appInfo.lastUpdateTime = packageInfo.lastUpdateTime;
				// 程序名称
				appInfo.appName = ((String) packageInfo.applicationInfo.loadLabel(pm)).trim();
				// 过渡
				appInfo.applicationInfo = packageInfo.applicationInfo;
				// 这行代码在运行时解除注释
				//appInfo.icon = packageInfo.applicationInfo.loadIcon(pm);
				// 计算应用的空间
				//publicSourceDir 是app的安装路径（文件夹）
				String dir = packageInfo.applicationInfo.publicSourceDir;
				long byteSize = new File(dir).length();
				appInfo.byteSize = byteSize;// 1024*1024 Byte字节
				appInfo.size = getSize(byteSize);// 1MB

				list.add(appInfo);// 添加到集合
			} // if

		}

		return list;
	}

	/**
	 * 字节--> Mb, 保留两位小数: 2.35M
	 *
	 * @param size
	 * @return
	 *              /
	public static String getSize(long size) {
		return new DecimalFormat("0.##").format(size * 1.0 / (1024 * 1024));
	}

	/**
	 * 时间转化函数
	 *
	 * @param millis
	 * @return
	 *           /
	public static String getTime(long millis) {
		Date date = new Date(millis);
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		return sdf.format(date);
	}

	/**
	 * 判断应用是否是第三方应用
	 *
	 * @param appInfo
	 * @return
	 *         /
	public static boolean isThirdPartyApp(ApplicationInfo appInfo) {
		boolean flag = false;
		if ((appInfo.flags & ApplicationInfo.FLAG_UPDATED_SYSTEM_APP) != 0) {
			// 可更新的系统应用
			flag = true;
		} else if ((appInfo.flags & ApplicationInfo.FLAG_SYSTEM) == 0) {
			// 非系统应用(第三方:用户自己安装)
			flag = true;
		}
		return flag;
	}

	/**
	 * 打开应用
	 *
	 * @param context
	 * @param packageName
	 * @return
	 *       /
	public static boolean openPackage(Context context, String packageName) {

		// 系统调用
		try {
			Intent intent = // 获取可以启动该应用的意图
					context.getPackageManager().getLaunchIntentForPackage(packageName);
			if (intent != null) {
				// 添加旗标-Flag
				intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);//在新的进程里启动
				context.startActivity(intent);// 普通的发送
				return true;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	/**
	 * 卸载应用
	 *
	 * @param context
	 *            上下文
	 * @param packageName
	 *            包名
	 * @param requestCode
	 *            请求码
	 *           /
	public static void uninstallApk(Activity context, String packageName, int requestCode) {
		Uri packageURI = Uri.parse("package:" + packageName);
		Intent intent = new Intent(Intent.ACTION_DELETE, // 动作:删除
				packageURI // 所要删除程序的地址
		);
		context.startActivityForResult(intent, requestCode);
		//ForResult 等待返回值的发送(扔飞镖)
	}

	//面包框里的文字信息
	public static void toast(Context context, String msg) {

		Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();

	}

	/ **
	 * 格式转换应用大小 单位"B,KB,MB,GB"
	 *        /
	public static String getSize2(float length) {
		long kb = 1024;
		long mb = 1024 * kb;
		long gb = 1024 * mb;
		if (length < kb) {
			return String.format("%dB", (int) length);
		} else if (length < mb) {
			return String.format("%.2fKB", length / kb);
		} else if (length < gb) {
			return String.format("%.2fMB", length / mb);
		} else {
			return String.format("%.2fGB", length / gb);
		}
	}

	public static List<AppInfo> getSearchResult(List<AppInfo> list, String keyword) {
		//返回实体集合
		List<AppInfo> searchResultList = new ArrayList<AppInfo>();
		//循环遍历
		for (int i = 0; i < list.size(); i++) {
			AppInfo app = list.get(i);//拿到单个的实体类
			//拿关键字和实体类比较
			if (app.appName.toLowerCase().contains(keyword.toLowerCase())) {

				searchResultList.add(app);//添加到结果集
			}

		}
		return searchResultList;
	}
*/
	// 显示打开方式
	public void open_file_type(Context c, String filesPath) {
		Intent intent = new Intent();
		intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		intent.setAction(Intent.ACTION_VIEW);
		c.startActivity(showOpenTypeDialog(filesPath));
	}

	public static Intent showOpenTypeDialog(String param) {
		Log.e("ViChildError", "showOpenTypeDialog");
		Intent intent = new Intent();
		intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		intent.setAction(Intent.ACTION_VIEW);
		Uri uri = Uri.fromFile(new File(param));
		intent.setDataAndType(uri, "*/*");
		return intent;
	}

	/**
	 * --获取文件类型 --
	 */
	public static String getMIMEType(String filePath) {
		String type = "*/*";
		String fName = filePath;

		int dotIndex = fName.lastIndexOf(".");
		if (dotIndex < 0) {
			return type;
		}

		String end = fName.substring(dotIndex, fName.length()).toLowerCase();
		if (end == "") {
			return type;
		}

		for (int i = 0; i < MIME_MapTable.length; i++) {
			if (end.equals(MIME_MapTable[i][0])) {
				type = MIME_MapTable[i][1];
			}
		}
		return type;
	}

	/**
	 * -- MIME 列表 --
	 */
	public static final String[][] MIME_MapTable = {
			// --{后缀名， MIME类型} --
			{ ".3gp", "video/3gpp" }, { ".3gpp", "video/3gpp" }, { ".aac", "audio/x-mpeg" }, { ".amr", "audio/x-mpeg" }, { ".apk", "application/vnd.android.package-archive" }, { ".avi", "video/x-msvideo" }, { ".aab", "application/x-authoware-bin" }, { ".aam", "application/x-authoware-map" }, { ".aas", "application/x-authoware-seg" }, { ".ai", "application/postscript" }, { ".aif", "audio/x-aiff" }, { ".aifc", "audio/x-aiff" }, { ".aiff", "audio/x-aiff" }, { ".als", "audio/x-alpha5" }, { ".amc", "application/x-mpeg" }, { ".ani", "application/octet-stream" }, { ".asc", "text/plain" }, { ".asd", "application/astound" }, { ".asf", "video/x-ms-asf" }, { ".asn", "application/astound" }, { ".asp", "application/x-asap" }, { ".asx", " video/x-ms-asf" }, { ".au", "audio/basic" }, { ".avb", "application/octet-stream" },
			{ ".awb", "audio/amr-wb" }, { ".bcpio", "application/x-bcpio" }, { ".bld", "application/bld" }, { ".bld2", "application/bld2" }, { ".bpk", "application/octet-stream" }, { ".bz2", "application/x-bzip2" }, { ".bin", "application/octet-stream" }, { ".bmp", "image/bmp" }, { ".c", "text/plain" }, { ".class", "application/octet-stream" }, { ".conf", "text/plain" }, { ".cpp", "text/plain" }, { ".cal", "image/x-cals" }, { ".ccn", "application/x-cnc" }, { ".cco", "application/x-cocoa" }, { ".cdf", "application/x-netcdf" }, { ".cgi", "magnus-internal/cgi" }, { ".chat", "application/x-chat" }, { ".clp", "application/x-msclip" }, { ".cmx", "application/x-cmx" }, { ".co", "application/x-cult3d-object" }, { ".cod", "image/cis-cod" }, { ".cpio", "application/x-cpio" }, { ".cpt", "application/mac-compactpro" },
			{ ".crd", "application/x-mscardfile" }, { ".csh", "application/x-csh" }, { ".csm", "chemical/x-csml" }, { ".csml", "chemical/x-csml" }, { ".css", "text/css" }, { ".cur", "application/octet-stream" }, { ".doc", "application/msword" }, { ".dcm", "x-lml/x-evm" }, { ".dcr", "application/x-director" }, { ".dcx", "image/x-dcx" }, { ".dhtml", "text/html" }, { ".dir", "application/x-director" }, { ".dll", "application/octet-stream" }, { ".dmg", "application/octet-stream" }, { ".dms", "application/octet-stream" }, { ".dot", "application/x-dot" }, { ".dvi", "application/x-dvi" }, { ".dwf", "drawing/x-dwf" }, { ".dwg", "application/x-autocad" }, { ".dxf", "application/x-autocad" }, { ".dxr", "application/x-director" }, { ".ebk", "application/x-expandedbook" }, { ".emb", "chemical/x-embl-dl-nucleotide" },
			{ ".embl", "chemical/x-embl-dl-nucleotide" }, { ".eps", "application/postscript" }, { ".epub", "application/epub+zip" }, { ".eri", "image/x-eri" }, { ".es", "audio/echospeech" }, { ".esl", "audio/echospeech" }, { ".etc", "application/x-earthtime" }, { ".etx", "text/x-setext" }, { ".evm", "x-lml/x-evm" }, { ".evy", "application/x-envoy" }, { ".exe", "application/octet-stream" }, { ".fh4", "image/x-freehand" }, { ".fh5", "image/x-freehand" }, { ".fhc", "image/x-freehand" }, { ".fif", "image/fif" }, { ".fm", "application/x-maker" }, { ".fpx", "image/x-fpx" }, { ".fvi", "video/isivideo" }, { ".flv", "video/x-msvideo" }, { ".gau", "chemical/x-gaussian-input" }, { ".gca", "application/x-gca-compressed" }, { ".gdb", "x-lml/x-gdb" }, { ".gif", "image/gif" }, { ".gps", "application/x-gps" },
			{ ".gtar", "application/x-gtar" }, { ".gz", "application/x-gzip" }, { ".gif", "image/gif" }, { ".gtar", "application/x-gtar" }, { ".gz", "application/x-gzip" }, { ".h", "text/plain" }, { ".hdf", "application/x-hdf" }, { ".hdm", "text/x-hdml" }, { ".hdml", "text/x-hdml" }, { ".htm", "text/html" }, { ".html", "text/html" }, { ".hlp", "application/winhlp" }, { ".hqx", "application/mac-binhex40" }, { ".hts", "text/html" }, { ".ice", "x-conference/x-cooltalk" }, { ".ico", "application/octet-stream" }, { ".ief", "image/ief" }, { ".ifm", "image/gif" }, { ".ifs", "image/ifs" }, { ".imy", "audio/melody" }, { ".ins", "application/x-net-install" }, { ".ips", "application/x-ipscript" }, { ".ipx", "application/x-ipix" }, { ".it", "audio/x-mod" }, { ".itz", "audio/x-mod" }, { ".ivr", "i-world/i-vrml" },
			{ ".j2k", "image/j2k" }, { ".jad", "text/vnd.sun.j2me.app-descriptor" }, { ".jam", "application/x-jam" }, { ".jnlp", "application/x-java-jnlp-file" }, { ".jpe", "image/jpeg" }, { ".jpz", "image/jpeg" }, { ".jwc", "application/jwc" }, { ".jar", "application/java-archive" }, { ".java", "text/plain" }, { ".jpeg", "image/jpeg" }, { ".jpg", "image/jpeg" }, { ".js", "application/x-javascript" }, { ".kjx", "application/x-kjx" }, { ".lak", "x-lml/x-lak" }, { ".latex", "application/x-latex" }, { ".lcc", "application/fastman" }, { ".lcl", "application/x-digitalloca" }, { ".lcr", "application/x-digitalloca" }, { ".lgh", "application/lgh" }, { ".lha", "application/octet-stream" }, { ".lml", "x-lml/x-lml" }, { ".lmlpack", "x-lml/x-lmlpack" }, { ".log", "text/plain" }, { ".lsf", "video/x-ms-asf" },
			{ ".lsx", "video/x-ms-asf" }, { ".lzh", "application/x-lzh " }, { ".m13", "application/x-msmediaview" }, { ".m14", "application/x-msmediaview" }, { ".m15", "audio/x-mod" }, { ".m3u", "audio/x-mpegurl" }, { ".m3url", "audio/x-mpegurl" }, { ".ma1", "audio/ma1" }, { ".ma2", "audio/ma2" }, { ".ma3", "audio/ma3" }, { ".ma5", "audio/ma5" }, { ".man", "application/x-troff-man" }, { ".map", "magnus-internal/imagemap" }, { ".mbd", "application/mbedlet" }, { ".mct", "application/x-mascot" }, { ".mdb", "application/x-msaccess" }, { ".mdz", "audio/x-mod" }, { ".me", "application/x-troff-me" }, { ".mel", "text/x-vmel" }, { ".mi", "application/x-mif" }, { ".mid", "audio/midi" }, { ".midi", "audio/midi" }, { ".m4a", "audio/mp4a-latm" }, { ".m4b", "audio/mp4a-latm" }, { ".m4p", "audio/mp4a-latm" },
			{ ".m4u", "video/vnd.mpegurl" }, { ".m4v", "video/x-m4v" }, { ".mov", "video/quicktime" }, { ".mp2", "audio/x-mpeg" }, { ".mp3", "audio/x-mpeg" }, { ".mp4", "video/mp4" }, { ".mpc", "application/vnd.mpohun.certificate" }, { ".mpe", "video/mpeg" }, { ".mpeg", "video/mpeg" }, { ".mpg", "video/mpeg" }, { ".mpg4", "video/mp4" }, { ".mpga", "audio/mpeg" }, { ".msg", "application/vnd.ms-outlook" }, { ".mif", "application/x-mif" }, { ".mil", "image/x-cals" }, { ".mio", "audio/x-mio" }, { ".mmf", "application/x-skt-lbs" }, { ".mng", "video/x-mng" }, { ".mny", "application/x-msmoney" }, { ".moc", "application/x-mocha" }, { ".mocha", "application/x-mocha" }, { ".mod", "audio/x-mod" }, { ".mof", "application/x-yumekara" }, { ".mol", "chemical/x-mdl-molfile" }, { ".mop", "chemical/x-mopac-input" },
			{ ".movie", "video/x-sgi-movie" }, { ".mpn", "application/vnd.mophun.application" }, { ".mpp", "application/vnd.ms-project" }, { ".mps", "application/x-mapserver" }, { ".mrl", "text/x-mrml" }, { ".mrm", "application/x-mrm" }, { ".ms", "application/x-troff-ms" }, { ".mts", "application/metastream" }, { ".mtx", "application/metastream" }, { ".mtz", "application/metastream" }, { ".mzv", "application/metastream" }, { ".nar", "application/zip" }, { ".nbmp", "image/nbmp" }, { ".nc", "application/x-netcdf" }, { ".ndb", "x-lml/x-ndb" }, { ".ndwn", "application/ndwn" }, { ".nif", "application/x-nif" }, { ".nmz", "application/x-scream" }, { ".nokia-op-logo", "image/vnd.nok-oplogo-color" }, { ".npx", "application/x-netfpx" }, { ".nsnd", "audio/nsnd" }, { ".nva", "application/x-neva1" },
			{ ".oda", "application/oda" }, { ".oom", "application/x-atlasMate-plugin" }, { ".ogg", "audio/ogg" }, { ".pac", "audio/x-pac" }, { ".pae", "audio/x-epac" }, { ".pan", "application/x-pan" }, { ".pbm", "image/x-portable-bitmap" }, { ".pcx", "image/x-pcx" }, { ".pda", "image/x-pda" }, { ".pdb", "chemical/x-pdb" }, { ".pdf", "application/pdf" }, { ".pfr", "application/font-tdpfr" }, { ".pgm", "image/x-portable-graymap" }, { ".pict", "image/x-pict" }, { ".pm", "application/x-perl" }, { ".pmd", "application/x-pmd" }, { ".png", "image/png" }, { ".pnm", "image/x-portable-anymap" }, { ".pnz", "image/png" }, { ".pot", "application/vnd.ms-powerpoint" }, { ".ppm", "image/x-portable-pixmap" }, { ".pps", "application/vnd.ms-powerpoint" }, { ".ppt", "application/vnd.ms-powerpoint" },
			{ ".pqf", "application/x-cprplayer" }, { ".pqi", "application/cprplayer" }, { ".prc", "application/x-prc" }, { ".proxy", "application/x-ns-proxy-autoconfig" }, { ".prop", "text/plain" }, { ".ps", "application/postscript" }, { ".ptlk", "application/listenup" }, { ".pub", "application/x-mspublisher" }, { ".pvx", "video/x-pv-pvx" }, { ".qcp", "audio/vnd.qcelp" }, { ".qt", "video/quicktime" }, { ".qti", "image/x-quicktime" }, { ".qtif", "image/x-quicktime" }, { ".r3t", "text/vnd.rn-realtext3d" }, { ".ra", "audio/x-pn-realaudio" }, { ".ram", "audio/x-pn-realaudio" }, { ".ras", "image/x-cmu-raster" }, { ".rdf", "application/rdf+xml" }, { ".rf", "image/vnd.rn-realflash" }, { ".rgb", "image/x-rgb" }, { ".rlf", "application/x-richlink" }, { ".rm", "audio/x-pn-realaudio" }, { ".rmf", "audio/x-rmf" },
			{ ".rmm", "audio/x-pn-realaudio" }, { ".rnx", "application/vnd.rn-realplayer" }, { ".roff", "application/x-troff" }, { ".rp", "image/vnd.rn-realpix" }, { ".rpm", "audio/x-pn-realaudio-plugin" }, { ".rt", "text/vnd.rn-realtext" }, { ".rte", "x-lml/x-gps" }, { ".rtf", "application/rtf" }, { ".rtg", "application/metastream" }, { ".rtx", "text/richtext" }, { ".rv", "video/vnd.rn-realvideo" }, { ".rwc", "application/x-rogerwilco" }, { ".rar", "application/x-rar-compressed" }, { ".rc", "text/plain" }, { ".rmvb", "audio/x-pn-realaudio" }, { ".s3m", "audio/x-mod" }, { ".s3z", "audio/x-mod" }, { ".sca", "application/x-supercard" }, { ".scd", "application/x-msschedule" }, { ".sdf", "application/e-score" }, { ".sea", "application/x-stuffit" }, { ".sgm", "text/x-sgml" }, { ".sgml", "text/x-sgml" },
			{ ".shar", "application/x-shar" }, { ".shtml", "magnus-internal/parsed-html" }, { ".shw", "application/presentations" }, { ".si6", "image/si6" }, { ".si7", "image/vnd.stiwap.sis" }, { ".si9", "image/vnd.lgtwap.sis" }, { ".sis", "application/vnd.symbian.install" }, { ".sit", "application/x-stuffit" }, { ".skd", "application/x-koan" }, { ".skm", "application/x-koan" }, { ".skp", "application/x-koan" }, { ".skt", "application/x-koan" }, { ".slc", "application/x-salsa" }, { ".smd", "audio/x-smd" }, { ".smi", "application/smil" }, { ".smil", "application/smil" }, { ".smp", "application/studiom" }, { ".smz", "audio/x-smd" }, { ".sh", "application/x-sh" }, { ".snd", "audio/basic" }, { ".spc", "text/x-speech" }, { ".spl", "application/futuresplash" }, { ".spr", "application/x-sprite" },
			{ ".sprite", "application/x-sprite" }, { ".sdp", "application/sdp" }, { ".spt", "application/x-spt" }, { ".src", "application/x-wais-source" }, { ".stk", "application/hyperstudio" }, { ".stm", "audio/x-mod" }, { ".sv4cpio", "application/x-sv4cpio" }, { ".sv4crc", "application/x-sv4crc" }, { ".svf", "image/vnd" }, { ".svg", "image/svg-xml" }, { ".svh", "image/svh" }, { ".svr", "x-world/x-svr" }, { ".swf", "application/x-shockwave-flash" }, { ".swfl", "application/x-shockwave-flash" }, { ".t", "application/x-troff" }, { ".tad", "application/octet-stream" }, { ".talk", "text/x-speech" }, { ".tar", "application/x-tar" }, { ".taz", "application/x-tar" }, { ".tbp", "application/x-timbuktu" }, { ".tbt", "application/x-timbuktu" }, { ".tcl", "application/x-tcl" }, { ".tex", "application/x-tex" },
			{ ".texi", "application/x-texinfo" }, { ".texinfo", "application/x-texinfo" }, { ".tgz", "application/x-tar" }, { ".thm", "application/vnd.eri.thm" }, { ".tif", "image/tiff" }, { ".tiff", "image/tiff" }, { ".tki", "application/x-tkined" }, { ".tkined", "application/x-tkined" }, { ".toc", "application/toc" }, { ".toy", "image/toy" }, { ".tr", "application/x-troff" }, { ".trk", "x-lml/x-gps" }, { ".trm", "application/x-msterminal" }, { ".tsi", "audio/tsplayer" }, { ".tsp", "application/dsptype" }, { ".tsv", "text/tab-separated-values" }, { ".ttf", "application/octet-stream" }, { ".ttz", "application/t-time" }, { ".txt", "text/plain" }, { ".ult", "audio/x-mod" }, { ".ustar", "application/x-ustar" }, { ".uu", "application/x-uuencode" }, { ".uue", "application/x-uuencode" },
			{ ".vcd", "application/x-cdlink" }, { ".vcf", "text/x-vcard" }, { ".vdo", "video/vdo" }, { ".vib", "audio/vib" }, { ".viv", "video/vivo" }, { ".vivo", "video/vivo" }, { ".vmd", "application/vocaltec-media-desc" }, { ".vmf", "application/vocaltec-media-file" }, { ".vmi", "application/x-dreamcast-vms-info" }, { ".vms", "application/x-dreamcast-vms" }, { ".vox", "audio/voxware" }, { ".vqe", "audio/x-twinvq-plugin" }, { ".vqf", "audio/x-twinvq" }, { ".vql", "audio/x-twinvq" }, { ".vre", "x-world/x-vream" }, { ".vrml", "x-world/x-vrml" }, { ".vrt", "x-world/x-vrt" }, { ".vrw", "x-world/x-vream" }, { ".vts", "workbook/formulaone" }, { ".wax", "audio/x-ms-wax" }, { ".wbmp", "image/vnd.wap.wbmp" }, { ".web", "application/vnd.xara" }, { ".wav", "audio/x-wav" }, { ".wma", "audio/x-ms-wma" },
			{ ".wmv", "audio/x-ms-wmv" }, { ".wi", "image/wavelet" }, { ".wis", "application/x-InstallShield" }, { ".wm", "video/x-ms-wm" }, { ".wmd", "application/x-ms-wmd" }, { ".wmf", "application/x-msmetafile" }, { ".wml", "text/vnd.wap.wml" }, { ".wmlc", "application/vnd.wap.wmlc" }, { ".wmls", "text/vnd.wap.wmlscript" }, { ".wmlsc", "application/vnd.wap.wmlscriptc" }, { ".wmlscript", "text/vnd.wap.wmlscript" }, { ".wmv", "video/x-ms-wmv" }, { ".wmx", "video/x-ms-wmx" }, { ".wmz", "application/x-ms-wmz" }, { ".wpng", "image/x-up-wpng" }, { ".wps", "application/vnd.ms-works" }, { ".wpt", "x-lml/x-gps" }, { ".wri", "application/x-mswrite" }, { ".wrl", "x-world/x-vrml" }, { ".wrz", "x-world/x-vrml" }, { ".ws", "text/vnd.wap.wmlscript" }, { ".wsc", "application/vnd.wap.wmlscriptc" },
			{ ".wv", "video/wavelet" }, { ".wvx", "video/x-ms-wvx" }, { ".wxl", "application/x-wxl" }, { ".x-gzip", "application/x-gzip" }, { ".xar", "application/vnd.xara" }, { ".xbm", "image/x-xbitmap" }, { ".xdm", "application/x-xdma" }, { ".xdma", "application/x-xdma" }, { ".xdw", "application/vnd.fujixerox.docuworks" }, { ".xht", "application/xhtml+xml" }, { ".xhtm", "application/xhtml+xml" }, { ".xhtml", "application/xhtml+xml" }, { ".xla", "application/vnd.ms-excel" }, { ".xlc", "application/vnd.ms-excel" }, { ".xll", "application/x-excel" }, { ".xlm", "application/vnd.ms-excel" }, { ".xls", "application/vnd.ms-excel" }, { ".xlt", "application/vnd.ms-excel" }, { ".xlw", "application/vnd.ms-excel" }, { ".xm", "audio/x-mod" }, { ".xml", "text/xml" }, { ".xmz", "audio/x-mod" },
			{ ".xpi", "application/x-xpinstall" }, { ".xpm", "image/x-xpixmap" }, { ".xsit", "text/xml" }, { ".xsl", "text/xml" }, { ".xul", "text/xul" }, { ".xwd", "image/x-xwindowdump" }, { ".xyz", "chemical/x-pdb" }, { ".yz1", "application/x-yz1" }, { ".z", "application/x-compress" }, { ".zac", "application/x-zaurus-zac" }, { ".zip", "application/zip" }, { "", "*/*" } };

}
