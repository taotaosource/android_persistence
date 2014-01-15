package org.cherry.persistence.demo.app;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class SQLiteHelper extends SQLiteOpenHelper {

	private boolean createOrUpgrade;
	
	public SQLiteHelper(Context context, String name, int version) {
		super(context, name, null, version);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		createOrUpgrade = true;
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		createOrUpgrade = true;
	}

	public boolean isCreateOrUpgrade() {
		return createOrUpgrade;
	}
}
