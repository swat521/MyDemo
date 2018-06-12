package vip.pk.pklib.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo.State;

import java.net.InetAddress;
import java.net.UnknownHostException;

public class NetUtil {
	public static final int NETWORN_NONE = 0;
	public static final int NETWORN_WIFI = 1;
	public static final int NETWORN_MOBILE = 2;

	public static int getNetworkState(Context context) {
		ConnectivityManager connManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);

		// Wifi
		State state = connManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState();
		if (state == State.CONNECTED || state == State.CONNECTING) {
			return NETWORN_WIFI;
		}

		// 3G
		state = connManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState();
		if (state == State.CONNECTED || state == State.CONNECTING) {
			return NETWORN_MOBILE;
		}
		return NETWORN_NONE;
	}
	
	public static String GetInetAddress(String  host){  
	    String IPAddress = "";   
	    InetAddress ReturnStr1 = null;  
	    try {  
	        ReturnStr1 = java.net.InetAddress.getByName(host);  
	        IPAddress = ReturnStr1.getHostAddress();  
	    } catch (UnknownHostException e) {  
	        // TODO Auto-generated catch block  
	        e.printStackTrace();  
	        return  IPAddress;  
	    }  
	    return IPAddress;  
	}  
	
	
	
	
}
