package vip.pk.pklib.utils;

import java.io.UnsupportedEncodingException;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.StringTokenizer;
import java.util.Vector;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @title :字符串常用操作
 * @description :字符串常用操作公共方法
 * @data:
 */

/**
 * String工具类
 * 
 * @author andy
 * @date 2015-5-16 下午4:04:22
 * 
 */
public class StringUtil {

	@SuppressWarnings("unchecked")
	public static String[] split(String str, String splitsign) {
		int index;
		if (str == null || splitsign == null) {
			return null;
		}
		ArrayList al = new ArrayList();
		while ((index = str.indexOf(splitsign)) != -1) {
			al.add(str.substring(0, index));
			str = str.substring(index + splitsign.length());
		}
		al.add(str);
		return (String[]) al.toArray(new String[0]);
	}

	public static String replace(String from, String to, String source) {
		if (source == null || from == null || to == null)
			return null;
		StringBuffer str = new StringBuffer("");
		int index = -1;
		while ((index = source.indexOf(from)) != -1) {
			str.append(source.substring(0, index) + to);
			source = source.substring(index + from.length());
			index = source.indexOf(from);
		}
		str.append(source);
		return str.toString();
	}

	public static String htmlencode(String str) {
		if (str == null) {
			return null;
		}
		return replace("\"", "\"", replace("<", "<", str));
	}

	public static String htmldecode(String str) {
		if (str == null) {
			return null;
		}

		return replace("\"", "\"", replace("<", "<", str));
	}

	private static final String _BR = "";

	public static String htmlshow(String str) {
		if (str == null) {
			return null;
		}

		str = replace("<", "<", str);
		str = replace(" ", " ", str);
		str = replace("\r\n", _BR, str);
		str = replace("\n", _BR, str);
		str = replace("\t", "    ", str);
		return str;
	}

	public static String toLength(String str, int length) {
		if (str == null) {
			return null;
		}
		if (length <= 0) {
			return "";
		}
		try {
			if (str.getBytes("GBK").length <= length) {
				return str;
			}
		} catch (Exception e) {
		}
		StringBuffer buff = new StringBuffer();

		int index = 0;
		char c;
		length -= 3;
		while (length > 0) {
			c = str.charAt(index);
			if (c < 128) {
				length--;
			} else {
				length--;
				length--;
			}
			buff.append(c);
			index++;
		}
		buff.append("...");
		return buff.toString();
	}

	public static boolean isInteger(String str) {
		Pattern pattern = Pattern.compile("^[-\\+]?[\\d]+$");
		return pattern.matcher(str).matches();
	}

	public static boolean isDouble(String str) {
		Pattern pattern = Pattern.compile("^[-\\+]?\\d+\\.\\d+$");
		return pattern.matcher(str).matches();
	}

	public static boolean isLetter(String str) {
		if (str == null || str.length() < 0) {
			return false;
		}
		Pattern pattern = Pattern.compile("[\\w\\.-_]*");
		return pattern.matcher(str).matches();
	}

	public static String parse(String content) {
		String email = null;
		if (content == null || content.length() < 1) {
			return email;
		}
		// 找出含有@
		int beginPos;
		int i;
		String token = "@";
		String preHalf = "";
		String sufHalf = "";

		beginPos = content.indexOf(token);
		if (beginPos > -1) {
			// 前项扫描
			String s = null;
			i = beginPos;
			while (i > 0) {
				s = content.substring(i - 1, i);
				if (isLetter(s))
					preHalf = s + preHalf;
				else
					break;
				i--;
			}
			// 后项扫描
			i = beginPos + 1;
			while (i < content.length()) {
				s = content.substring(i, i + 1);
				if (isLetter(s))
					sufHalf = sufHalf + s;
				else
					break;
				i++;
			}
			// 判断合法性
			email = preHalf + "@" + sufHalf;
			if (isEmail(email)) {
				return email;
			}
		}
		return null;
	}

	public static boolean isEmail(String email) {
		if (email == null || email.length() < 1 || email.length() > 256) {
			return false;
		}
		Pattern pattern = Pattern.compile("^\\w+([-+.]\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*$");
		return pattern.matcher(email).matches();
	}

	public static boolean isChinese(String str) {
		Pattern pattern = Pattern.compile("[\u0391-\uFFE5]+$");
		return pattern.matcher(str).matches();
	}

	public static boolean isBlank(String str) {
		return str == null || str.trim().length() == 0;
	}

	public static boolean isPrime(int x) {
		if (x <= 7) {
			if (x == 2 || x == 3 || x == 5 || x == 7)
				return true;
		}
		int c = 7;
		if (x % 2 == 0)
			return false;
		if (x % 3 == 0)
			return false;
		if (x % 5 == 0)
			return false;
		int end = (int) Math.sqrt(x);
		while (c <= end) {
			if (x % c == 0) {
				return false;
			}
			c += 4;
			if (x % c == 0) {
				return false;
			}
			c += 2;
			if (x % c == 0) {
				return false;
			}
			c += 4;
			if (x % c == 0) {
				return false;
			}
			c += 2;
			if (x % c == 0) {
				return false;
			}
			c += 4;
			if (x % c == 0) {
				return false;
			}
			c += 6;
			if (x % c == 0) {
				return false;
			}
			c += 2;
			if (x % c == 0) {
				return false;
			}
			c += 6;
		}
		return true;
	}

	public static String hangeToBig(String str) {
		double value;
		try {
			value = Double.parseDouble(str.trim());
		} catch (Exception e) {
			return null;
		}
		char[] hunit = { '拾', '佰', '仟' }; // 段内位置表示
		char[] vunit = { '万', '亿' }; // 段名表示
		char[] digit = { '零', '壹', '贰', '叁', '肆', '伍', '陆', '柒', '捌', '玖' }; // 数字表示
		long midVal = (long) (value * 100); // 转化成整形
		String valStr = String.valueOf(midVal); // 转化成字符串

		String head = valStr.substring(0, valStr.length() - 2); // 取整数部分
		String rail = valStr.substring(valStr.length() - 2); // 取小数部分

		String prefix = ""; // 整数部分转化的结果
		String suffix = ""; // 小数部分转化的结果
		// 处理小数点后面的数
		if (rail.equals("00")) { // 如果小数部分为0
			suffix = "整";
		} else {
			suffix = digit[rail.charAt(0) - '0'] + "角" + digit[rail.charAt(1) - '0'] + "分"; // 否则把角分转化出来
		}
		// 处理小数点前面的数
		char[] chDig = head.toCharArray(); // 把整数部分转化成字符数组
		char zero = '0'; // 标志'0'表示出现过0
		byte zeroSerNum = 0; // 连续出现0的次数
		for (int i = 0; i < chDig.length; i++) { // 循环处理每个数字
			int idx = (chDig.length - i - 1) % 4; // 取段内位置
			int vidx = (chDig.length - i - 1) / 4; // 取段位置
			if (chDig[i] == '0') { // 如果当前字符是0
				zeroSerNum++; // 连续0次数递增
				if (zero == '0') { // 标志
					zero = digit[0];
				} else if (idx == 0 && vidx > 0 && zeroSerNum < 4) {
					prefix += vunit[vidx - 1];
					zero = '0';
				}
				continue;
			}
			zeroSerNum = 0; // 连续0次数清零
			if (zero != '0') { // 如果标志不为0,则加上,例如万,亿什么的
				prefix += zero;
				zero = '0';
			}
			prefix += digit[chDig[i] - '0']; // 转化该数字表示
			if (idx > 0)
				prefix += hunit[idx - 1];
			if (idx == 0 && vidx > 0) {
				prefix += vunit[vidx - 1]; // 段结束位置应该加上段名如万,亿
			}
		}

		if (prefix.length() > 0)
			prefix += '圆'; // 如果整数部分存在,则有圆的字样
		return prefix + suffix; // 返回正确表示
	}

	@SuppressWarnings("unused")
	private static String removeSameString(String str) {
		Set mLinkedSet = new LinkedHashSet();// set集合的特征：其子集不可以重复
		String[] strArray = str.split(" ");// 根据空格(正则表达式)分割字符串
		StringBuffer sb = new StringBuffer();

		for (int i = 0; i < strArray.length; i++) {
			if (!mLinkedSet.contains(strArray[i])) {
				mLinkedSet.add(strArray[i]);
				sb.append(strArray[i] + " ");
			}
		}
		System.out.println(mLinkedSet);
		return sb.toString();
	}

	public static String encoding(String src) {
		if (src == null)
			return "";
		StringBuilder result = new StringBuilder();
		if (src != null) {
			src = src.trim();
			for (int pos = 0; pos < src.length(); pos++) {
				switch (src.charAt(pos)) {
				case '"':
					result.append("\"");
					break;
				case '<':
					result.append("<");
					break;
				case '>':
					result.append(">");
					break;
				case '\'':
					result.append("'");
					break;
				case '&':
					result.append("&");
					break;
				case '%':
					result.append("&pc;");
					break;
				case '_':
					result.append("&ul;");
					break;
				case '#':
					result.append("&shap;");
					break;
				case '?':
					result.append("&ques;");
					break;
				default:
					result.append(src.charAt(pos));
					break;
				}
			}
		}
		return result.toString();
	}

	public static boolean isHandset(String handset) {
		try {
			String regex = "^1[\\d]{10}$";
			Pattern pattern = Pattern.compile(regex);
			Matcher matcher = pattern.matcher(handset);
			return matcher.matches();

		} catch (RuntimeException e) {
			return false;
		}
	}

	public static String decoding(String src) {
		if (src == null)
			return "";
		String result = src;
		result = result.replace("\"", "\"").replace("'", "\'");
		result = result.replace("<", "<").replace(">", ">");
		result = result.replace("&", "&");
		result = result.replace("&pc;", "%").replace("&ul", "_");
		result = result.replace("&shap;", "#").replace("&ques", "?");
		return result;
	}

	public static void main(String[] args) {
		String source = "abcdefgabcdefgabcdefgabcdefgabcdefgabcdefg";
		String from = "efg";
		String to = "房贺威";
		System.out.println("在字符串source中，用to替换from，替换结果为：" + replace(from, to, source));
		System.out.println("返回指定字节长度的字符串：" + toLength("abcdefgabcdefgabcdefgabcdefgabcdefgabcdefg", 9));
		System.out.println("判断是否为整数：" + isInteger("+0"));
		System.out.println("判断是否为浮点数，包括double和float：" + isDouble("+0.36"));
		System.out.println("判断输入的字符串是否符合Email样式：" + isEmail("fhwbj@163.com"));
		System.out.println("判断输入的字符串是否为纯汉字：" + isChinese("你好！"));
		System.out.println("判断输入的数据是否是质数：" + isPrime(12));
		System.out.println("人民币转换成大写：" + hangeToBig("10019658"));
		System.out.println("去掉字符串中重复的子字符串：" + removeSameString("100 100 9658"));
		System.out.println("过滤特殊字符：" + encoding("100\"s<>fdsd100 9658"));
		System.out.println("判断是不是合法的手机号码：" + isHandset("15981807340"));

		System.out.println("从字符串中取值Email：" + parse("159818 fwhbj@163.com07340"));
	}
}
