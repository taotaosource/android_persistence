package org.cherry.persistence.demo.util;

import android.util.Log;

/**
 *  
 * log util 
 */
public final class Logger {

	/** log level */
	public static final int LEVEL = Log.VERBOSE;

	public static void v(String tag, String msg) {
		if (LEVEL <= Log.VERBOSE)
			Log.v(tag, msg);
	}

	public static void v(String tag, String msg, Throwable tr) {
		if (LEVEL <= Log.VERBOSE)
			Log.v(tag, msg, tr);
	}

	public static void d(String tag, String msg) {
		if (LEVEL <= Log.DEBUG)
			Log.d(tag, msg);
	}

	public static void d(String tag, String msg, Throwable tr) {
		if (LEVEL <= Log.DEBUG)
			Log.d(tag, msg, tr);
	}

	public static void i(String tag, String msg) {
		if (LEVEL <= Log.INFO)
			Logger.i(tag, msg);
	}

	public static void i(String tag, String msg, Throwable tr) {
		if (LEVEL <= Log.INFO)
			Logger.i(tag, msg, tr);
	}

	public static void w(String tag, String msg) {
		if (LEVEL <= Log.WARN)
			Log.w(tag, msg);
	}

	public static void w(String tag, String msg, Throwable tr) {
		if (LEVEL <= Log.WARN)
			Log.w(tag, msg, tr);
	}

	public static void w(String tag, Throwable tr) {
		if (LEVEL <= Log.WARN)
			Log.w(tag, tr.getMessage(), tr);
	}

	public static void e(String tag, String msg) {
		if (LEVEL <= Log.ERROR)
			Log.e(tag, msg);
	}

	public static void e(String tag, String msg, Throwable tr) {
		if (LEVEL <= Log.ERROR)
			Log.e(tag, msg, tr);
	}

	public static void e(String tag, Throwable tr) {
		if (LEVEL <= Log.ERROR)
			Log.e(tag, tr.getMessage(), tr);
	}

	public static void wtf(String tag, String msg, Throwable tr) {
		if (LEVEL <= Log.ASSERT)
			Logger.wtf(tag, msg, tr);
	}

}
