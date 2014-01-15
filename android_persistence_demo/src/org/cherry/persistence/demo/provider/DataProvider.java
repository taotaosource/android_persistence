package org.cherry.persistence.demo.provider;

import org.cherry.persistence.demo.app.PersistenceApplication;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;

public class DataProvider extends ContentProvider {

	public static final String AUTHORITY = "org.cherry.persistence.demo";

	private static final String TABLE_USER = "user";

	private static final Uri BASE_URI = Uri.parse("content://" + AUTHORITY);
	public static final Uri URI_USER = Uri.withAppendedPath(BASE_URI, TABLE_USER);
	private static final UriMatcher URI_MATCHER = new UriMatcher(UriMatcher.NO_MATCH);

	private static final int USER = 0;

	static {
		URI_MATCHER.addURI(AUTHORITY, TABLE_USER, USER);
	}

	@Override
	public boolean onCreate() {
		return false;
	}

	@Override
	public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
		int match = URI_MATCHER.match(uri);
		switch (match) {
		case USER:
			SQLiteDatabase database = getSQLiteDatabase();
			Cursor cursor = database.query(TABLE_USER, projection, selection, selectionArgs, null, null, sortOrder);
			if (cursor != null) {
				cursor.setNotificationUri(getContext().getContentResolver(), URI_USER);
				return cursor;
			}
			break;
		}
		return null;
	}

	@Override
	public String getType(Uri uri) {
		return null;
	}

	@Override
	public Uri insert(Uri uri, ContentValues values) {
		return null;
	}

	@Override
	public int delete(Uri uri, String selection, String[] selectionArgs) {
		return 0;
	}

	@Override
	public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
		return 0;
	}

	private SQLiteDatabase getSQLiteDatabase() {
 		return ((SQLiteDatabase) ((PersistenceApplication) getContext().getApplicationContext()).getSession().getConnection());
	}

}
