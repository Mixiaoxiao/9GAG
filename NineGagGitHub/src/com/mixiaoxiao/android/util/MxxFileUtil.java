package com.mixiaoxiao.android.util;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import android.app.Activity;
import android.content.Context;
import android.os.Environment;
import android.widget.Toast;

/**
 * 
 *  FileUtil
 */
public class MxxFileUtil {
	
	static String ROOT_PATH = "/9gag_mxx";
	static String DOWNLOAD_PATH = "/download";
	static String IMAGE_PATH = "/Image";
	static String AUDIO_PATH = "/Audio";
	static String CRASH_PATH = "/Crash";

	/**
	 *  检测当前设备SD是否可用
	 *  
	 * @return  返回"true"表示可用，否则不可用
	 */
	public static boolean haveSdCard(){
		return Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED) ;
	}
	
	/**
	 *  获得SD卡根目录路径 
	 *  
	 * @return String类型  SD卡根目录路径
	 */
	public static String getSdCardAbsolutePath(){
			return Environment.getExternalStorageDirectory().getAbsolutePath();
	}
	/**
	 *  获得SD卡本程序的缓存目录
	 *  
	 * @return String类型  获得SD卡本程序的缓存目录
	 */
	public static String getCachePath(Context c){
			return c.getExternalCacheDir().getAbsolutePath();
	}
	/**
	 * 获得图片缓存文件的文件夹
	 * 
	 * @return String类型 
	 *         存储图片缓存文件的文件夹
	 */
	public static String getImageCacheDir(Context c){
		File file = new File(getCachePath(c) + IMAGE_PATH) ;
		if (!file.exists()) {// 此处可能会创建失败，暂不考虑
			file.mkdirs();
		}
		return file.getAbsolutePath();
	}
	/**
	 * 获得存放录音文件的文件夹
	 * 
	 * @return String类型 
	 *         存储 录音文件的文件夹
	 */
	public static String getAudioCacheDir(Context c){
		File audioFile = new File(getCachePath(c)  + AUDIO_PATH) ;
		if (!audioFile.exists()) {// 此处可能会创建失败，暂不考虑
			audioFile.mkdirs();
		}
		return audioFile.getAbsolutePath();
	}
	
	/**
	 * 获得存放 应用私有文件的文件夹
	 * 
	 * @return String类型 
	 *         私有文件的文件夹
	 */
	public static String getPrivateAudioDir(Context c){
		return c.getExternalFilesDir("Audio").getAbsolutePath();
	}
	/**
	 * 获得存放 应用私有文件的文件夹 + Crash
	 * 
	 * @return String类型 
	 *         私有文件的文件夹
	 */
	public static String getPrivateCrashDir(Context c){
		return c.getExternalFilesDir("Crash").getAbsolutePath();
	}
	
	public static String getSystemAlbumDir(){
		File file = new File(getSdCardAbsolutePath() + "/DCIM/Camera");
		if(!file.exists()) file.mkdirs();
		return file.getAbsolutePath();
	}
	
	public File getAlbumStorageDir(Context context, String albumName) { 
		// Get the directory for the app's private pictures directory. 
		    File file = new File(context.getExternalFilesDir( Environment.DIRECTORY_PICTURES), albumName); 
		    if (!file.mkdirs()) {
		        //Log.e(LOG_TAG, "Directory not created"); 
		    }
		    return file; 
	}
	/**
	 * 附件存放地址
	 * @param c
	 * @return
	 */
	public static String getPrivateAttachmentDir(Context c){
		return c.getExternalFilesDir("Attachment").getAbsolutePath();
	}
	
	public static String getPrivateDbDir(Context c){
		return c.getExternalFilesDir("Db").getAbsolutePath();
	}
	
	public static String getAppRootPath(){
		File file = new File(getSdCardAbsolutePath() + ROOT_PATH) ;
		if (!file.exists()) {// 此处可能会创建失败，暂不考虑
			file.mkdirs();
		}
		return file.getAbsolutePath();
	}
	/**
	 * sdcard/approot/download
	 * @return
	 */
	public static String getDownloadPath(){
		File file = new File(getAppRootPath() + DOWNLOAD_PATH) ;
		if (!file.exists()) {// 此处可能会创建失败，暂不考虑
			file.mkdirs();
		}
		return file.getAbsolutePath();
	}
	/**
	 * sdcard/approot/image
	 * @return
	 */
	public static String getImagePath(){
		File file = new File(getAppRootPath() + IMAGE_PATH) ;
		if (!file.exists()) {// 此处可能会创建失败，暂不考虑
			file.mkdirs();
		}
		return file.getAbsolutePath();
	}
	/** 
	  * 获取现在时间 
	  *  
	  * @return返回字符串格式 yyyy_MM_dd_HH_mm_ss
	  */  
	public static String getStringDate() {  
	  Date currentTime = new Date();  
	  SimpleDateFormat formatter = new SimpleDateFormat("yyyy_MM_dd_HH_mm_ss");  
	  String dateString = formatter.format(currentTime);  
	  return dateString;  
	} 
	
	
	
	
	/**
	 * 保存文本文件 输入参数：要保存的String，文件名，上下文
	 */
	public static void savedata(String data, String filename, Context context) {
		if (data == null || data.trim().equals("")) {
			return; // 王斌修正 如果为空则不保存
		}
		FileOutputStream outStream;
		try {
			outStream = context.openFileOutput(filename, Context.MODE_PRIVATE);
			outStream.write(data.getBytes());
			outStream.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	/**
	 * 读取文本数据 如果filename不存在则返回null，否则返回其string内容
	 */
	public static String loaddata(String filename, Context context) {
		// Toast.makeText(context, "尝试显示缓存数据", Toast.LENGTH_SHORT).show();
		if (!isFileExist(filename, context)) {
			// Log.e("Tools", "loaddata" + filename + "不存在");
			return null;
		}
		// Log.e("Tools", "loaddata" + filename + "存在!!");
		ByteArrayOutputStream stream = new ByteArrayOutputStream();
		try {
			FileInputStream inStream = context.openFileInput(filename);
			byte[] buffer = new byte[10 * 1024];
			int length = -1;
			while ((length = inStream.read(buffer)) != -1) {
				stream.write(buffer, 0, length);
			}
			stream.close();
			inStream.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return stream.toString();
	}
	
	public static boolean isFileExist(String filename, Context context) {
		boolean isExist = false;
		File file = context.getFileStreamPath(filename);
		if (file.exists()) {
			isExist = true;
		}
		return isExist;
	}
	
}
