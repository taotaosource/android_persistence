package org.cherry.persistence.demo.app;

import org.cherry.persistence.Session;
import org.cherry.persistence.cfg.Configuration;
import org.cherry.persistence.demo.model.Tiger;
import org.cherry.persistence.demo.model.User;
import org.cherry.persistence.demo.util.Logger;
import org.cherry.persistence.log.Log;
import org.cherry.persistence.log.LoggerManager;

import android.app.Application;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;

public class PersistenceApplication extends Application implements Log {
	public static final String SESSION_SERVICE = "session";
	
	private static final String PERSISTENCE_TAG = "Persistence";
	private static final int DATABASE_VERSION = 2;
	private static final String DB_FILE_NAME = "persitence.db";
	private static final String TAG = "PersistenceApplication";
	private Object mObject = new Object();

	private Session mSession;

	@Override
	public void onCreate() {
		super.onCreate();
		// async init Session
		initSession();
	}

	private void initSession() {
		new AsyncTask<Void, Void, Session>() {

			@Override
			protected Session doInBackground(Void... params) {
				long startTime = System.currentTimeMillis();
				SQLiteHelper sqLiteHelper = new SQLiteHelper(PersistenceApplication.this, DB_FILE_NAME, DATABASE_VERSION);
				SQLiteDatabase sqLiteDatabase = sqLiteHelper.getReadableDatabase();

				// init Logger
				LoggerManager loggerManager = LoggerManager.getInstance();
				loggerManager.setDebug(true);
				loggerManager.setLog(PersistenceApplication.this);

				// open session
				Configuration configuration = new Configuration();
				configuration.addAnnotatedClass(User.class);
				configuration.addAnnotatedClass(Tiger.class);
				configuration.setSQLiteDatabase(sqLiteDatabase);

				// the need to update the table
				boolean createOrUpgrade = sqLiteHelper.isCreateOrUpgrade();
				configuration.getSettings().setAutoUpdateSchema(createOrUpgrade);

				synchronized (mObject) {
					mSession = configuration.buildSessionFactory().openSession();
					mObject.notifyAll();
				}
				Logger.d(TAG, " init time : " + (System.currentTimeMillis() - startTime) + " createOrUpgrade : " + createOrUpgrade);
				return null;
			}
		}.execute();
	}

	public Session getSession() {
		if (mSession != null) {
			return mSession;
		}
		synchronized (mObject) {
			while (mSession == null && !Thread.interrupted()) {
				try {
					mObject.wait();
				} catch (InterruptedException e) {
					org.cherry.persistence.demo.util.Logger.e(TAG, e);
				}
			}
		}
		return mSession;
	}
	
	@Override
	public Object getSystemService(String name) {
		if (SESSION_SERVICE.equals(name)) {
			return getSession();
		}
		return super.getSystemService(name);
	}
	

	@Override
	public void print(int priority, String tag, String msg) {
		if (Log.ERROR == priority) {
			org.cherry.persistence.demo.util.Logger.e(PERSISTENCE_TAG, msg);
		} else {
			org.cherry.persistence.demo.util.Logger.d(PERSISTENCE_TAG, msg);
		}
	}
}
