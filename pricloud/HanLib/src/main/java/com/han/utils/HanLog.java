package com.han.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import android.content.Context;
import android.os.Environment;
import android.util.Log;

public class HanLog {
	private static String BASE_DIR = Environment.getExternalStorageDirectory().getPath() + "/log_new/";
	private static String LOG_FILE = "busplayer.log";
	private static String LOG_FILE_BAK = "busplayer_bak.log";

	private static int LOG_FILE_SIZE = (1024 * 1204 * 20);
	private static File file = null;
	private static FileWriter writer = null;
	private static Map<String, String> FILE_NAME_DICTIONARY = new HashMap<String, String>();

	public static void writeDisk(String log) {
		write (log, true);
	}

	public static void writeDisk(Context c, String log) {
		write (c.getPackageName(), log, true);
	}

	public static void writeDisk(String tag, String log) {
		write (tag, log, true);
	}

	public static void write(Context ctx, String log) {
		write(ctx.getPackageName(), log, false);
	}

	public static void write(String tag, String log) {
		write(tag, log, false);
	}
	
	public static void write(String log) {
		write (log, false);
	}

	@Deprecated
	public static void write(String log, boolean writeTodisk) {
//		write("BUS_APP", log, writeTodisk);
	}

	public static void write(String tag, String log, boolean writeTodisk) {
		//Log.i("PLAYER", log);

    	TryRenameFileSize(BASE_DIR + LOG_FILE, BASE_DIR + LOG_FILE_BAK);
		  
		  try { String curTime = new
				  SimpleDateFormat("yy-MM-dd HH:mm:ss").format(new java.util.Date());
		  		
		  		  Log.i (tag, curTime + " " + log + "\n");
		  			  
		  		  if (!writeTodisk) {	return;}
		  
				  file = new File(BASE_DIR); if (file.exists() == false) file.mkdirs();	 file = null;			  
				  file = new File(BASE_DIR + LOG_FILE); if (file.exists() == false) file.createNewFile();
				  
				  writer = new FileWriter(new File(BASE_DIR + tag), true);
				  writer.write("[" + tag + "] " + curTime + " " + log + "\n");  
		  } catch (Exception e) {System.out.println ("MyLog.write Exception. " + e.toString());}
		  finally { 
			  try {
				  if (writer == null) return;
				  writer.flush();
				  writer.close(); 
			  } catch (Exception e){} 
		 }
	}

	private static boolean TryRenameFileSize(String o, String n) {
		int size = 0;

		File f = new File(o);
		if (f.exists() == false) // 文件不存在
			return true;

		FileInputStream fis = null;
		try {
			fis = new FileInputStream(f);
			size = fis.available();
		} catch (Exception e) {
		} finally {
			try {
				fis.close();
			} catch (Exception e) {
			}
		}

		if (size >= LOG_FILE_SIZE) {
			f.renameTo(new File(n));
		}

		return true;
	}
	
	private static String LOG_PATH = "//sdcard/log_new/trans/";
	
	private static long getDateFromStamp(long stamp) {
		long date = 0;
		String strDate = null;
		Date tmpDate = new Date(stamp);
		
		strDate  = new SimpleDateFormat("MM").format(tmpDate);
		date = Integer.parseInt(strDate);
		date *= 100;
		strDate  = new SimpleDateFormat("dd").format(tmpDate);
		date += Integer.parseInt(strDate);
		
//		MyLog.writeDisk("getDateFromStamp: stamp:" + stamp + "  -> date:" + date);
			
		return date;
	}
	
	private static boolean isStampSameDate(long stamp1, long stamp2) {
		long tt1 = getDateFromStamp(stamp1);
		long tt2 = getDateFromStamp(stamp2);
				
		return tt1 == tt2;
	}
	
	public static void runTimeLog (String name, String log) {		
		String logFile = FILE_NAME_DICTIONARY.get(name);
	
		Log.i(name, log);
		if (null == logFile) {
			HanLog.write(" RUNTIMELOG, map key:" + name + " val:" + logFile);
			logFile = name + "_" + (System.currentTimeMillis()/1000) + ".log";
			FILE_NAME_DICTIONARY.put(name, logFile);
		} else {
			if (!(new File(LOG_PATH + logFile).exists())) { //文件不存在，可能是尚未生成文件，可能是生成的文件已被上传 suffix_time_stamp ++ .  
				logFile = name + "_" + (System.currentTimeMillis()/1000) + ".log";
				FILE_NAME_DICTIONARY.put(name, logFile);
			}
		}

		new RunTimeLog(logFile, log);
	}
	
	private static class RunTimeLog {
		public RunTimeLog(String file, String log){
			
			try { 			  
			  	  File dir = new File(LOG_PATH); 
			  	  if (!dir.exists()) 
			  		  dir.mkdirs(); 
				  writer = new FileWriter(new File(LOG_PATH + file), true);
				  writer.write(log + "\n");  
			  } catch (Exception e) {writeDisk("writeRunTime Exception. " + e.toString());}
			  finally { 
				  try {
					  if (writer == null) return;
					  writer.flush();
					  writer.close(); 
				  } catch (Exception e){} 
			 }
		}
	}
}