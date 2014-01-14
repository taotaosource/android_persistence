package org.cherry.persistence.log;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.net.UnknownHostException;

public final class Logger {
	
	private static boolean debug = false;
	private static int level = Log.DEBUG;
	private static Log log;

	private static void print(int priority, String tag, String msg) {
		if (log != null) {
			log.print(priority, tag, msg);
		}
	}

	static void setDebug(boolean debug) {
		Logger.debug = debug;
	}

	static void setLog(Log log) {
		Logger.log = log;
	}

	static void setLevel(int level) {
		Logger.level = level;
	}

	public static void d(String tag, String msg) {
		if (debug && level <= Log.DEBUG) {
			print(Log.DEBUG, tag, msg);
		}
	}

	public static void d(String tag, Object object) {
		if (debug && level <= Log.DEBUG) {
			print(Log.DEBUG, tag, object == null ? "null" : object.toString());
		}
	}

	public static void d(String tag, String format, Object... args) {
		if (debug && level <= Log.DEBUG) {
			print(Log.DEBUG, tag, String.format(format, args));
		}
	}

	public static void d(String tag, String msg, Throwable tr) {
		if (debug && level <= Log.DEBUG) {
			print(Log.DEBUG, tag, tr.getMessage() + '\n' + getStackTraceString(tr));
		}
	}

	public static void i(String tag, String msg) {
		if (debug && level <= Log.INFO) {
			print(Log.INFO, tag, msg);
		}
	}

	public static void i(String tag, String msg, Throwable tr) {
		if (debug && level <= Log.INFO) {
			print(Log.INFO, tag, msg + '\n' + getStackTraceString(tr));
		}
	}

	public static void w(String tag, String msg) {
		if (debug && level <= Log.WARN) {
			print(Log.WARN, tag, msg);
		}
	}

	public static void w(String tag, String msg, Throwable tr) {
		if (debug && level <= Log.WARN) {
			print(Log.WARN, tag, msg + '\n' + getStackTraceString(tr));
		}
	}

	public static void w(String tag, Throwable tr) {
		if (debug && level <= Log.WARN) {
			print(Log.WARN, tag, tr.getMessage() + '\n' + getStackTraceString(tr));
		}
	}

	public static void e(String tag, String msg) {
		if (debug && level <= Log.ERROR) {
			print(Log.ERROR, tag, msg);
		}
	}

	public static void e(String tag, String msg, Throwable tr) {
		if (debug && level <= Log.ERROR) {
			print(Log.ERROR, tag, msg + '\n' + getStackTraceString(tr));
		}
	}

	public static void e(String tag, Throwable tr) {
		if (debug && level <= Log.ERROR) {
			print(Log.ERROR, tag, tr.getMessage() + '\n' + getStackTraceString(tr));
		}
	}

	public static String getStackTraceString(Throwable tr) {
		if (tr == null) {
			return "";
		}
		// This is to reduce the amount of log spew that apps do in the
		// non-error
		// condition of the network being unavailable.
		Throwable t = tr;
		while (t != null) {
			if (t instanceof UnknownHostException) {
				return "";
			}
			t = t.getCause();
		}

		StringWriter sw = new StringWriter();
		PrintWriter pw = new PrintWriter(sw);
		tr.printStackTrace(pw);
		return sw.toString();
	}

}
