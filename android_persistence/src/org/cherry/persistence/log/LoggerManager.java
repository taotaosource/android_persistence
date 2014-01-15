package org.cherry.persistence.log;


/**
 * 
 * <li>debuggable  default : false</li> 
 * <li>debug level default : {@link Log#DEBUG}</li>
 */

public class LoggerManager {
	private static final LoggerManager instance = new LoggerManager();

	private LoggerManager() {

	}

	public static LoggerManager getInstance() {
		return instance;
	}

	public void setDebug(boolean debug) {
		Logger.setDebug(debug);
	}

	public void setLog(Log log) {
		Logger.setLog(log);
	}

	public void setLevel(int level) {
		Logger.setLevel(level);
	}
}
