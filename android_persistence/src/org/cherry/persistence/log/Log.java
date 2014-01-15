package org.cherry.persistence.log;

public interface Log {

	/**
	 * Priority constant for the println method; use Logger.d.
	 */
	public static final int DEBUG = 1;
	/**
	 * Priority constant for the println method; use Logger.i.
	 */
	public static final int INFO = 2;
	/**
	 * Priority constant for the println method; use Logger.w.
	 */
	public static final int WARN = 3;
	/**
	 * Priority constant for the println method; use Logger.e.
	 */
	public static final int ERROR = 4;

	public void print(int priority, String tag, String msg);
}
