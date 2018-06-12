package vip.pk.pklib.utils;


import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Calendar;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.graphics.drawable.BitmapDrawable;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Log;
import android.widget.ImageView;
import android.widget.Toast;

/**
 * 类描述：FileUtil
 * 
 * @author hexiaoming
 * @version
 */
public class FileUtil {

	public static File updateDir = null;
	public static File updateFile = null;
	/*********** 保存升级APK的目录 ***********/
	public static final String KonkaApplication = "wwwpkpk8cn";

	public static boolean isCreateFileSucess;

	private static final int BUFFER = 8192;

	/**
	 * 方法描述：createFile方法
	 * 
	 * @param //String
	 *            app_name
	 * @return
	 * @see FileUtil
	 */
	public static void createFile(String app_name) {

		if (android.os.Environment.MEDIA_MOUNTED.equals(android.os.Environment.getExternalStorageState())) {
			isCreateFileSucess = true;

			updateDir = new File(Environment.getExternalStorageDirectory() + "/" + KonkaApplication + "/");
			updateFile = new File(updateDir + "/" + app_name + ".apk");

			if (!updateDir.exists()) {
				updateDir.mkdirs();
			}
			if (!updateFile.exists()) {
				try {
					updateFile.createNewFile();
				} catch (IOException e) {
					isCreateFileSucess = false;
					e.printStackTrace();
				}
			}

		} else {
			isCreateFileSucess = false;
		}
	}

	// 读取文件
	public static String readTextFile(File file) throws IOException {
		String text = null;
		InputStream is = null;
		try {
			is = new FileInputStream(file);
			text = readTextInputStream(is);
		} finally {
			if (is != null) {
				is.close();
			}
		}
		return text;
	}

	// 从流中读取文件
	public static String readTextInputStream(InputStream is) throws IOException {
		StringBuffer strbuffer = new StringBuffer();
		String line;
		BufferedReader reader = null;
		try {
			reader = new BufferedReader(new InputStreamReader(is));
			while ((line = reader.readLine()) != null) {
				strbuffer.append(line).append("\r\n");
			}
		} finally {
			if (reader != null) {
				reader.close();
			}
		}
		return strbuffer.toString();
	}

	// 将文本内容写入文件
	public static void writeTextFile(File file, String str) throws IOException {
		DataOutputStream out = null;
		try {
			out = new DataOutputStream(new FileOutputStream(file));
			out.write(str.getBytes());
		} finally {
			if (out != null) {
				out.close();
			}
		}
	}

	// 复制文件
	public static void copyFile(File sourceFile, File targetFile) throws IOException {
		BufferedInputStream inBuff = null;
		BufferedOutputStream outBuff = null;
		try {
			inBuff = new BufferedInputStream(new FileInputStream(sourceFile));
			outBuff = new BufferedOutputStream(new FileOutputStream(targetFile));
			byte[] buffer = new byte[BUFFER];
			int length;
			while ((length = inBuff.read(buffer)) != -1) {
				outBuff.write(buffer, 0, length);
			}
			outBuff.flush();
		} finally {
			if (inBuff != null) {
				inBuff.close();
			}
			if (outBuff != null) {
				outBuff.close();
			}
		}
	}

	/**
	 * 把图片压缩到200K
	 * 
	 * @param oldpath
	 *            压缩前的图片路径
	 * @param newPath
	 *            压缩后的图片路径
	 * @return
	 */
	/**
	 * 把图片压缩到200K
	 * 
	 * @param oldpath
	 *            压缩前的图片路径
	 * @param newPath
	 *            压缩后的图片路径
	 * @return
	 */
	public static File compressFile(String oldpath, String newPath) {
		Bitmap compressBitmap = FileUtil.decodeFile(oldpath);
		Bitmap newBitmap = ratingImage(oldpath, compressBitmap);
		ByteArrayOutputStream os = new ByteArrayOutputStream();
		newBitmap.compress(CompressFormat.PNG, 100, os);
		byte[] bytes = os.toByteArray();

		File file = null;
		try {
			file = FileUtil.getFileFromBytes(bytes, newPath);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (newBitmap != null) {
				if (!newBitmap.isRecycled()) {
					newBitmap.recycle();
				}
				newBitmap = null;
			}
			if (compressBitmap != null) {
				if (!compressBitmap.isRecycled()) {
					compressBitmap.recycle();
				}
				compressBitmap = null;
			}
		}
		return file;
	}

	private static Bitmap ratingImage(String filePath, Bitmap bitmap) {
		int degree = readPictureDegree(filePath);
		return rotaingImageView(degree, bitmap);
	}

	/**
	 * 旋转图片
	 * 
	 * @param angle
	 * @param bitmap
	 * @return Bitmap
	 */
	public static Bitmap rotaingImageView(int angle, Bitmap bitmap) {
		//旋转图片 动作
		Matrix matrix = new Matrix();
		;
		matrix.postRotate(angle);
		System.out.println("angle2=" + angle);
		// 创建新的图片
		Bitmap resizedBitmap = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);
		return resizedBitmap;
	}

	/**
	 * 读取图片属性：旋转的角度
	 * 
	 * @param path
	 *            图片绝对路径
	 * @return degree旋转的角度
	 */
	public static int readPictureDegree(String path) {
		int degree = 0;
		try {
			ExifInterface exifInterface = new ExifInterface(path);
			int orientation = exifInterface.getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_NORMAL);
			switch (orientation) {
			case ExifInterface.ORIENTATION_ROTATE_90:
				degree = 90;
				break;
			case ExifInterface.ORIENTATION_ROTATE_180:
				degree = 180;
				break;
			case ExifInterface.ORIENTATION_ROTATE_270:
				degree = 270;
				break;
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return degree;
	}

	/**
	 * 把字节数组保存为一个文件
	 * 
	 * @param b
	 * @param outputFile
	 * @return
	 */
	public static File getFileFromBytes(byte[] b, String outputFile) {
		File ret = null;
		BufferedOutputStream stream = null;
		try {
			ret = new File(outputFile);
			FileOutputStream fstream = new FileOutputStream(ret);
			stream = new BufferedOutputStream(fstream);
			stream.write(b);
		} catch (Exception e) {
			// log.error("helper:get file from byte process error!");
			e.printStackTrace();
		} finally {
			if (stream != null) {
				try {
					stream.close();
				} catch (IOException e) {
					// log.error("helper:get file from byte process error!");
					e.printStackTrace();
				}
			}
		}
		return ret;
	}

	/**
	 * 图片压缩
	 * 
	 * @param fPath
	 * @return
	 */
	public static Bitmap decodeFile(String fPath) {
		BitmapFactory.Options opts = new BitmapFactory.Options();
		opts.inJustDecodeBounds = true;
		opts.inDither = false; // Disable Dithering mode
		opts.inPurgeable = true; // Tell to gc that whether it needs free
		opts.inInputShareable = true; // Which kind of reference will be used to
		BitmapFactory.decodeFile(fPath, opts);
		final int REQUIRED_SIZE = 200;
		int scale = 1;
		if (opts.outHeight > REQUIRED_SIZE || opts.outWidth > REQUIRED_SIZE) {
			final int heightRatio = Math.round((float) opts.outHeight / (float) REQUIRED_SIZE);
			final int widthRatio = Math.round((float) opts.outWidth / (float) REQUIRED_SIZE);
			scale = heightRatio < widthRatio ? heightRatio : widthRatio;//
		}
		Log.i("scale", "scal =" + scale);
		opts.inJustDecodeBounds = false;
		opts.inSampleSize = scale;
		Bitmap bm = BitmapFactory.decodeFile(fPath, opts).copy(Config.ARGB_8888, false);
		return bm;
	}

	/**
	 * 创建目录
	 * 
	 * @param path
	 */
	public static void setMkdir(String path) {
		File file = new File(path);
		if (!file.exists()) {
			file.mkdirs();
			Log.e("file", "目录不存在  创建目录    ");
		} else {
			Log.e("file", "目录存在");
		}
	}

	/**
	 * 获取目录名称
	 * 
	 * @param url
	 * @return FileName
	 */
	public static String getFileName(String url) {
		int lastIndexStart = url.lastIndexOf("/");
		if (lastIndexStart != -1) {
			return url.substring(lastIndexStart + 1, url.length());
		} else {
			return null;
		}
	}

	/**
	 * 删除该目录下的文件
	 * 
	 * @param path
	 */
	public static void delFile(String path) {
		if (!TextUtils.isEmpty(path)) {
			File file = new File(path);
			if (file.exists()) {
				file.delete();
			}
		}
	}

	public static byte[] File2byte(String filePath) {
		byte[] buffer = null;
		try {
			File file = new File(filePath);
			FileInputStream fis = new FileInputStream(file);
			ByteArrayOutputStream bos = new ByteArrayOutputStream();
			byte[] b = new byte[1024];
			int n;
			while ((n = fis.read(b)) != -1) {
				bos.write(b, 0, n);
			}
			fis.close();
			bos.close();
			buffer = bos.toByteArray();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return buffer;
	}

	public static void byte2File(byte[] buf, String filePath, String fileName) {
		BufferedOutputStream bos = null;
		FileOutputStream fos = null;
		File file = null;
		try {
			File dir = new File(filePath);
			if (!dir.exists() && dir.isDirectory()) {
				dir.mkdirs();
			}
			file = new File(filePath + File.separator + fileName);
			fos = new FileOutputStream(file);
			bos = new BufferedOutputStream(fos);
			bos.write(buf);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (bos != null) {
				try {
					bos.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			if (fos != null) {
				try {
					fos.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

	public static Bitmap imageview2bitmap(Context c, ImageView iv_tui_qr) {
		BitmapDrawable bmpDrawable = (BitmapDrawable) iv_tui_qr.getDrawable();
		Bitmap bmp = bmpDrawable.getBitmap();
		return bmp;
	}

	/*
	 * 将Byte[]转换成long类型
	 * */
	private static long bytes2long(byte[] bitmaps) {
		int num = 0;
		for (int ix = 0; ix < 8; ++ix) {
			num <<= 8;
			num |= (bitmaps[ix] & 0xff);
		}
		return num;
	}

	/*
	* 将图片转换成Byte[]
	* */
	private static byte[] getbitmaptobytes(Bitmap bitmap) {
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		bitmap.compress(Bitmap.CompressFormat.PNG, 100, out);
		return out.toByteArray();
	}

	//保存图片
	public static void save_imageview_pic(Context c, ImageView iv_tui_qr) {
		if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {

			long time = Calendar.getInstance().getTimeInMillis();
			BufferedOutputStream outStream = null;
			//通过imageview 获取bitmap

			Bitmap bmp = imageview2bitmap(c, iv_tui_qr);

			/*
			 * 将图片转换成一个byte[]；
			 * */

			byte[] bitmaps = getbitmaptobytes(bmp);

			/*
			 * 将Byte[]转换成long类型
			 * */
			long longbitmaps = bytes2long(bitmaps);
			/*
			 * 判断SD卡是否有足够的空间供下载使用
			 * */
			//											boolean iscapacity = isEnaleforDownload(longbitmaps);
			if (true) {

				try {
					File sdCardDir = Environment.getExternalStorageDirectory();
					//防止出现重复名字
					String fileName = time + ".png";
					File dir;
					dir = new File(sdCardDir.getCanonicalPath() + "/myscan/");
					if (!dir.exists()) {
						dir.mkdirs();
					}

					File cacheFile = new File(dir, fileName);
					FileOutputStream fstream = new FileOutputStream(cacheFile);
					outStream = new BufferedOutputStream(fstream);

					outStream.write(bitmaps);

					Toast.makeText(c, "图片成功存至myscan目录下", Toast.LENGTH_LONG).show();

				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					//Log.d("ydh","保存本地图片异常！！！");
				} finally {

					if (outStream != null) {
						try {
							outStream.close();
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
					//TODO 

				}

			}
		} else {
			Toast.makeText(c, "SDCard存储空间不足", Toast.LENGTH_LONG).show();
		}
	}

	//
	public static void save_pic_ToGallery(Context context, Bitmap bmp) {

		if (bmp == null) {
			T.showLong(context, "保存出错了...");
			return;
		}
		// 首先保存图片
		File appDir = new File(context.getCacheDir(), "ywq");
		if (!appDir.exists()) {
			appDir.mkdir();
		}
		String fileName = System.currentTimeMillis() + ".jpg";
		File file = new File(appDir, fileName);
		try {
			FileOutputStream fos = new FileOutputStream(file);
			bmp.compress(Bitmap.CompressFormat.JPEG, 100, fos);
			fos.flush();
			fos.close();
		} catch (FileNotFoundException e) {
			T.showLong(context, "文件未发现");
			e.printStackTrace();
		} catch (IOException e) {
			T.showLong(context, "保存出错了...");
			e.printStackTrace();
		} catch (Exception e) {
			T.showLong(context, "保存出错了...");
			e.printStackTrace();
		}

		// 最后通知图库更新
		try {
			MediaStore.Images.Media.insertImage(context.getContentResolver(), file.getAbsolutePath(), fileName, null);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		Intent intent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
		Uri uri = Uri.fromFile(file);
		intent.setData(uri);
		context.sendBroadcast(intent);
		T.showLong(context, "保存成功了...");

	}

}