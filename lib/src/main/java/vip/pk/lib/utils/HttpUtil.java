package vip.pk.pklib.utils;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Map;

/**
 * Http工具类
 */
public class HttpUtil {
	// 创建HttpClient对象
	//public static HttpClient httpClient = new DefaultHttpClient();
	public static final String BASE_URL = "";

	private static final int REQUEST_TIMEOUT = 3 * 1000;//设置请求超时3秒钟  
	private static final int SO_TIMEOUT = 2 * 1000; //设置等待数据超时时间2秒钟  

	public static String PHPSESSID = null;

	/**
	 * get请求
	 * 
	 * @param url
	 *            发送请求的URL
	 * @return 服务器响应字符串
	 * @throws Exception
	 */
	/*
	public static String doGet(String url) throws Exception {
		// 创建HttpGet对象。
		HttpGet get = new HttpGet(url);
		
		//设置请求超时设置
		HttpParams params = new BasicHttpParams(); 
	    HttpConnectionParams.setConnectionTimeout(params, REQUEST_TIMEOUT);
	    HttpConnectionParams.setSoTimeout(params, SO_TIMEOUT);
	    get.setParams(params);
	
		// 第一次一般是还未被赋值，若有值则将SessionId发给服务器
		if (null != PHPSESSID) {
			get.setHeader("Cookie", "PHPSESSID=" + PHPSESSID);
		}
	
		// 发送GET请求
		HttpResponse httpResponse = httpClient.execute(get);
		// 如果服务器成功地返回响应
		if (httpResponse.getStatusLine().getStatusCode() == 200) {
			// 获取服务器响应字符串
			HttpEntity entity = httpResponse.getEntity();
			InputStream content = entity.getContent();
	
			CookieStore mCookieStore = ((AbstractHttpClient) httpClient).getCookieStore();
			List<Cookie> cookies = mCookieStore.getCookies();
			for (int i = 0; i < cookies.size(); i++) {
				// 这里是读取Cookie['PHPSESSID']的值存在静态变量中，保证每次都是同一个值
				if ("PHPSESSID".equals(cookies.get(i).getName())) {
					PHPSESSID = cookies.get(i).getValue();
					break;
				}
	
			}
	
			return convertStreamToString(content);
		}
		return null;
	}
	*/

	/**
	 * post请求
	 * 
	 * @param url
	 *            发送请求的URL
	 * @param params
	 *            请求参数 Map<String, String> url_Params; url_Params = new HashMap<String, String>(); url_Params.put("uid", user_id); url_Params.put("lpq_sn",lpq_sn);
	 * 
	 * @return 服务器响应字符串
	 * @throws Exception
	 */

	/*
	public static String doPost(String url, Map<String, String> rawParams)
			throws Exception {
		// 创建HttpPost对象。
		HttpPost post = new HttpPost(url);
		
		//设置请求超时设置
		HttpParams params_time = new BasicHttpParams(); 
		HttpConnectionParams.setConnectionTimeout(params_time, REQUEST_TIMEOUT);
		HttpConnectionParams.setSoTimeout(params_time, SO_TIMEOUT);
		post.setParams(params_time);
	
		// 如果传递参数个数比较多的话可以对传递的参数进行封装
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		for (String key : rawParams.keySet()) {
			// 封装请求参数
			params.add(new BasicNameValuePair(key, rawParams.get(key)));
		}
	
		// 第一次一般是还未被赋值，若有值则将SessionId发给服务器
		if (null != PHPSESSID) {
			post.setHeader("Cookie", "PHPSESSID=" + PHPSESSID);
		}
	
		// 设置请求参数
		post.setEntity(new UrlEncodedFormEntity(params, "utf-8"));
		// 发送POST请求
		HttpResponse httpResponse = httpClient.execute(post);
		// 如果服务器成功地返回响应
		if (httpResponse.getStatusLine().getStatusCode() == 200) {
			// 获取服务器响应字符串
			HttpEntity entity = httpResponse.getEntity();
			InputStream content = entity.getContent();
	
			CookieStore mCookieStore = ((AbstractHttpClient) httpClient).getCookieStore();
			List<Cookie> cookies = mCookieStore.getCookies();
			for (int i = 0; i < cookies.size(); i++) {
				// 这里是读取Cookie['PHPSESSID']的值存在静态变量中，保证每次都是同一个值
				if ("PHPSESSID".equals(cookies.get(i).getName())) {
					PHPSESSID = cookies.get(i).getValue();
					break;
				}
			}
	
			return convertStreamToString(content);
		}
		return null;
	}
	*/

	private static final int TIMEOUT_IN_MILLIONS = 5000;

	public interface CallBack {
		void onRequestComplete(String result);
	}

	/**
	 * 异步的Get请求
	 *
	 * @param urlStr
	 * @param callBack
	 */
	public static void doGetAsyn(final String urlStr, final CallBack callBack) {
		new Thread() {
			public void run() {
				try {
					String result = doGet(urlStr);
					if (callBack != null) {
						callBack.onRequestComplete(result);
					}
				} catch (Exception e) {
					e.printStackTrace();
				}

			};
		}.start();
	}

	/**
	 * 异步的Post请求
	 * 
	 * @param urlStr
	 * @param //params
	 * @param callBack
	 * @throws Exception
	 */
	public static void doPostAsyn(final String urlStr,  final Map<String, String> rawParams, final CallBack callBack) throws Exception {
		
		new Thread() {
			public void run() {
				try {
					String result = doPost(urlStr, rawParams);
					if (callBack != null) {
						callBack.onRequestComplete(result);
					}
				} catch (Exception e) {
					e.printStackTrace();
				}

			};
		}.start();
	}

	/**
	 * Get请求，获得返回数据
	 *
	 * @param urlStr
	 * @return
	 * @throws Exception
	 */
	public static String doGet(String urlStr) {
		URL url = null;
		HttpURLConnection conn = null;
		InputStream is = null;
		ByteArrayOutputStream baos = null;
		try {
			url = new URL(urlStr);
			conn = (HttpURLConnection) url.openConnection();
			conn.setReadTimeout(TIMEOUT_IN_MILLIONS);
			conn.setConnectTimeout(TIMEOUT_IN_MILLIONS);
			conn.setRequestMethod("GET");
			conn.setRequestProperty("accept", "*/*");
			conn.setRequestProperty("connection", "Keep-Alive");
			if (conn.getResponseCode() == 200) {
				is = conn.getInputStream();
				baos = new ByteArrayOutputStream();
				int len = -1;
				byte[] buf = new byte[128];

				while ((len = is.read(buf)) != -1) {
					baos.write(buf, 0, len);
				}
				baos.flush();
				return baos.toString();
			} else {
				throw new RuntimeException(" responseCode is not 200 ... ");
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (is != null)
					is.close();
			} catch (IOException e) {
			}
			try {
				if (baos != null)
					baos.close();
			} catch (IOException e) {
			}
			conn.disconnect();
		}

		return null;

	}

	/**
	 * 向指定 URL 发送POST方法的请求
	 *
	 * @param url
	 *            发送请求的 URL
	 * @param //param
	 * @param //param
	 *            
	 * @return 所代表远程资源的响应结果
	 * @throws Exception
	 */
	public static String doPost(String url, Map<String, String> rawParams ) {
		
		
		//请求参数，请求参数应该是 name1=value1&name2=value2 的形式。
		String param = "";
		PrintWriter out = null;
		BufferedReader in = null;
		String result = "";
		try {
			URL realUrl = new URL(url);
			// 打开和URL之间的连接
			HttpURLConnection conn = (HttpURLConnection) realUrl.openConnection();
			// 设置通用的请求属性
			conn.setRequestProperty("accept", "*/*");
			conn.setRequestProperty("connection", "Keep-Alive");
			conn.setRequestMethod("POST");
			conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
			conn.setRequestProperty("charset", "utf-8");
			conn.setUseCaches(false);
			// 发送POST请求必须设置如下两行
			conn.setDoOutput(true);
			conn.setDoInput(true);
			conn.setReadTimeout(TIMEOUT_IN_MILLIONS);
			conn.setConnectTimeout(TIMEOUT_IN_MILLIONS);

			for (String key : rawParams.keySet()) {
				// 封装请求参数
				if(!param.equals("")){
					param = param+"&";
				}
				param = param + key+"="+rawParams.get(key);
			}
			
			
			if (param != null && !param.trim().equals("")) {
				// 获取URLConnection对象对应的输出流
				out = new PrintWriter(conn.getOutputStream());
				// 发送请求参数
				out.print(param);
				// flush输出流的缓冲
				out.flush();
			}
			// 定义BufferedReader输入流来读取URL的响应
			in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
			String line;
			while ((line = in.readLine()) != null) {
				result += line;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		// 使用finally块来关闭输出流、输入流
		finally {
			try {
				if (out != null) {
					out.close();
				}
				if (in != null) {
					in.close();
				}
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		}
		return result;
	}

	/**
	 * 获取服务器的响应，转换为字符串
	 */
	private static String convertStreamToString(InputStream is) {
		BufferedReader reader = new BufferedReader(new InputStreamReader(is));
		StringBuilder sb = new StringBuilder();
		String line = null;
		try {
			while ((line = reader.readLine()) != null) {
				sb.append(line);
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				is.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return sb.toString();
	}

	/*
	* 
	* 上传文件至Server，uploadUrl：接收文件的处理页面
	* 
	* srcPath为要上传图片的路径
	* 
	<?php
		$target_path  = "./upload/";//接收文件目录
		$target_path = $target_path . basename( $_FILES['uploadedfile']['name']);
		if(move_uploaded_file($_FILES['uploadedfile']['tmp_name'], $target_path)) {
			echo "The file ".  basename( $_FILES['uploadedfile']['name']). " has been uploaded";
		}  else{
			echo "There was an error uploading the file, please try again!" . $_FILES['uploadedfile']['error'];
		}
	?>
	
	*/
	public static String uploadFile(String srcPath, String uploadUrl) {

		String end = "\r\n";
		String twoHyphens = "--";
		String boundary = "******";
		try {
			URL url = new URL(uploadUrl);
			HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
			// 设置每次传输的流大小，可以有效防止手机因为内存不足崩溃
			// 此方法用于在预先不知道内容长度时启用没有进行内部缓冲的 HTTP 请求正文的流。
			//	    httpURLConnection.setChunkedStreamingMode(128 * 1024);// 128K
			// 允许输入输出流
			httpURLConnection.setDoInput(true);
			httpURLConnection.setDoOutput(true);
			httpURLConnection.setUseCaches(false);
			// 使用POST方法
			httpURLConnection.setRequestMethod("POST");
			httpURLConnection.setRequestProperty("Connection", "Keep-Alive");
			httpURLConnection.setRequestProperty("Charset", "UTF-8");
			httpURLConnection.setRequestProperty("Content-Type", "multipart/form-data;boundary=" + boundary);

			DataOutputStream dos = new DataOutputStream(httpURLConnection.getOutputStream());
			dos.writeBytes(twoHyphens + boundary + end);
			dos.writeBytes("Content-Disposition: form-data; name=\"uploadedfile\"; filename=\"" + srcPath.substring(srcPath.lastIndexOf("/") + 1) + "\"" + end);
			dos.writeBytes(end);

			FileInputStream fis = new FileInputStream(srcPath);
			byte[] buffer = new byte[8192]; // 8k
			int count = 0;
			// 读取文件
			while ((count = fis.read(buffer)) != -1) {
				dos.write(buffer, 0, count);
			}
			fis.close();

			dos.writeBytes(end);
			dos.writeBytes(twoHyphens + boundary + twoHyphens + end);
			dos.flush();

			InputStream is = httpURLConnection.getInputStream();
			InputStreamReader isr = new InputStreamReader(is, "utf-8");
			BufferedReader br = new BufferedReader(isr);
			String result = br.readLine();

			dos.close();
			is.close();

			return result;

		} catch (Exception e) {
			e.printStackTrace();
			//setTitle(e.getMessage());
			return null;
		}
	}

	/*
		new Thread() {
		@Override
		public void run() {
			Map<String, String> url_Params; url_Params = new
					HashMap<String, String>(); 
					url_Params.put("uid", "00000000000");
					url_Params.put("lpq_sn","ddddddddddddddddddddddddd");
					
					String img_url = "/storage/emulated/0/2016_07_02_11_58_47.jpg";
					
					Map<String, String> files; 
					files = new HashMap<String, String>(); 
					files.put(img_url, FileUtil.File2byte(img_url));
			String aaa = HttpUtil.postFile("http://www.xiaocaoyangche.com/index.php/home/index/temp", url_Params, files);
			
			T.showLong(cur_content, aaa+"-----");
	
		
		};
	}.start();
	*/
	// 以数据流的形式传参
	public static String postFile(String actionUrl, Map<String, String> params, Map<String, String> files)
	// throws Exception
	{
		String return_str = "";

		StringBuilder sb2 = null;
		String BOUNDARY = java.util.UUID.randomUUID().toString();
		String PREFIX = "--", LINEND = "\r\n";
		String MULTIPART_FROM_DATA = "multipart/form-data";
		String CHARSET = "UTF-8";

		URL uri;
		try {
			uri = new URL(actionUrl);

			HttpURLConnection conn = (HttpURLConnection) uri.openConnection();
			conn.setReadTimeout(6 * 1000); // 缓存的最长时间
			conn.setDoInput(true);// 允许输入
			conn.setDoOutput(true);// 允许输出
			conn.setUseCaches(false); // 不允许使用缓存
			conn.setRequestMethod("POST");
			conn.setRequestProperty("connection", "keep-alive");
			conn.setRequestProperty("Charsert", "UTF-8");
			conn.setRequestProperty("Content-Type", MULTIPART_FROM_DATA + ";boundary=" + BOUNDARY);

			// 首先组拼文本类型的参数
			StringBuilder sb = new StringBuilder();
			for (Map.Entry<String, String> entry : params.entrySet()) {
				sb.append(PREFIX);
				sb.append(BOUNDARY);
				sb.append(LINEND);
				sb.append("Content-Disposition: form-data; name=\"" + entry.getKey() + "\"" + LINEND);
				sb.append("Content-Type: text/plain; charset=" + CHARSET + LINEND);
				sb.append("Content-Transfer-Encoding: 8bit" + LINEND);
				sb.append(LINEND);
				sb.append(entry.getValue());
				sb.append(LINEND);
			}

			DataOutputStream outStream = new DataOutputStream(conn.getOutputStream());
			outStream.write(sb.toString().getBytes());
			InputStream in = null;
			// 发送文件数据
			if (files != null) {
				for (Map.Entry<String, String> file : files.entrySet()) {

					StringBuilder sb1 = new StringBuilder();
					sb1.append(PREFIX);
					sb1.append(BOUNDARY);
					sb1.append(LINEND);
					sb1.append("Content-Disposition: form-data; name=\"" + file.getKey() + "\"; filename=\"" + file.getValue() + "\"" + LINEND);
					sb1.append("Content-Type: application/octet-stream; charset=" + CHARSET + LINEND);
					sb1.append(LINEND);
					outStream.write(sb1.toString().getBytes());

					// InputStream is = new FileInputStream(file.getValue());
					// byte[] buffer = new byte[1024];
					// int len = 0;
					// while ((len = is.read(buffer)) != -1)
					// {
					// outStream.write(buffer, 0, len);
					// }
					// is.close();

					byte[] temp_file = FileUtil.File2byte(file.getValue());
					outStream.write(temp_file);

					outStream.write(LINEND.getBytes());
				}

			} else {
				// return_str = "Update Fail";
			}

			// 请求结束标志
			byte[] end_data = (PREFIX + BOUNDARY + PREFIX + LINEND).getBytes();
			outStream.write(end_data);
			outStream.flush();
			// 得到响应码
			int res = conn.getResponseCode();
			if (res == 200) {
				in = conn.getInputStream();
				int ch;
				sb2 = new StringBuilder();
				while ((ch = in.read()) != -1) {
					sb2.append((char) ch);
				}
				System.out.println(sb2.toString());
			}
			outStream.close();
			conn.disconnect();
			// 解析服务器返回来的数据
			return_str = sb2.toString();// ParseJson.getEditMadIconResult(sb2.toString());

		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// return in.toString();
		catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return return_str;
	}

}