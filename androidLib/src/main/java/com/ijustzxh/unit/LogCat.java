/**
 * date:2014-04-21
 * rewrite log
 */
package com.ijustzxh.unit;

import android.util.Log;

public class LogCat {

	private static boolean showLog = true;
	private static String tag = "AndroidLog";

	public static void i(String tag, String msg) {

		if (showLog && msg!=null) {
			Log.i(tag, msg);
		}
	}

	public static void i(String msg) {

		if (showLog && msg!=null) {
			Log.i(tag, msg);
		}
	}

	public static void d(String tag, String msg) {

		if (showLog && msg!=null) {
			Log.d(tag, msg);
		}
	}

	public static void d(String msg ) {

		if (showLog && msg!=null) {
			Log.d(tag, msg);
		}
	}
	
	public static void e(String tag, String msg) {

		if (showLog && msg!=null) {
			Log.e(tag, msg);
		}
	}

	public static void e(String msg) {

		if (showLog && msg!=null) {
			Log.e(tag, msg);
		}
	}

	public static void setShowLog(boolean value) {

		showLog = value;
	}

}