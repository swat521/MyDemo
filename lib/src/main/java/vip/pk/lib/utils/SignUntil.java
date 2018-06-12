package vip.pk.pklib.utils;

import java.util.Map;
import java.util.TreeMap;


public class SignUntil {
	 public static String getSign(Map<String, String> params) {
	        Map<String, String> sortMap = new TreeMap<String, String>();
	        sortMap.putAll(params);
	        // 以k1=v1&k2=v2...方式拼接参数
	        StringBuilder builder = new StringBuilder();
	        for (Map.Entry<String, String> s : sortMap.entrySet()) {
	            String k = s.getKey();
	            String v = s.getValue();
	            if (StringUtil.isBlank(v)) {// 过滤空值
	                continue;
	            }
	            builder.append(k).append("=").append(v).append("&");
	        }



	        if (!sortMap.isEmpty() && builder.length() != 0) {
	            builder.deleteCharAt(builder.length() - 1);
	        }
	        return Md5.MD5(builder.toString()+"511vip.vip").toUpperCase();
	    }
	  
}
