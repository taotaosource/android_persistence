package org.cherry.persistence.engine.sqlite;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

import org.cherry.persistence.log.Logger;
import org.cherry.persistence.mapping.Column;
import org.cherry.persistence.mapping.Table;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;


public class DatabaseCoordinator {
	private static final String TAG = "DatabaseCoordinator";
	private SQLiteDatabase sqLiteDatabase;
	private Field field;

	public DatabaseCoordinator(SQLiteDatabase sqLiteDatabase) {
		this.sqLiteDatabase = sqLiteDatabase;
		try {
			field = ContentValues.class.getDeclaredField("mValues");
			field.setAccessible(true);
		} catch (NoSuchFieldException e) {
			throw new RuntimeException(e);
		}
	}

	public void beginTransaction() {
		if (sqLiteDatabase == null) {
			return;
		}
		sqLiteDatabase.beginTransaction();
	}

	public void setTransactionSuccessful() {
		if (sqLiteDatabase == null) {
			return;
		}
		sqLiteDatabase.setTransactionSuccessful();
	}

	public void endTransaction() {
		if (sqLiteDatabase == null) {
			return;
		}
		sqLiteDatabase.endTransaction();
	}

	public boolean inTransaction() {
		if (sqLiteDatabase == null) {
			return false;
		}
		return sqLiteDatabase.inTransaction();
	}

	public Object getConnection() {
		return sqLiteDatabase;
	}

	public int insert(String table, HashMap<String, Object> values) {
		if (sqLiteDatabase == null) {
			return -1;
		}
		if (values.size() > 0) {
			ContentValues contentValues = null;
			try {
				contentValues = new ContentValues();
				field.set(contentValues, values);
				Logger.d(TAG, "insert %s", contentValues);
				return (int) sqLiteDatabase.insert(table, null, contentValues);
			} catch (Exception e) {
				throw new RuntimeException(e);
			}
		} else {
			String sql = "insert into ".concat(table).concat(" default values ");
			Logger.d(TAG, sql);
			sqLiteDatabase.execSQL(sql);
			return -1;
		}
	}

	public void execSQL(String sql) {
		Logger.d(TAG, " sql = " + sql);
		if (sqLiteDatabase == null) {
			return;
		}
		sqLiteDatabase.execSQL(sql);
	}

	public void execSQL(String sql, Object[] bindArgs) {
		Logger.d(TAG, " sql = " + sql);
		if (sqLiteDatabase == null) {
			return;
		}
		sqLiteDatabase.execSQL(sql, bindArgs);
	}

	public Cursor query(String sql, Object[] bindArgs) {
		Logger.d(TAG, " sql = " + sql);
		Logger.d(TAG, " bindArgs = " + Arrays.asList(bindArgs));
		if (sqLiteDatabase == null) {
			return null;
		}

		String[] selectionArgs = null;
		if (bindArgs != null) {
			int len = bindArgs.length;
			selectionArgs = new String[len];
			for (int i = 0; i < len; i++) {
				Object object = bindArgs[i];
				if (object != null) {
					selectionArgs[i] = object.toString();
				}
			}
		}
		return sqLiteDatabase.rawQuery(sql, selectionArgs);
	}

	public DatabaseMetadata getDatabaseMetadata() {
		ArrayList<Table> tables = new ArrayList<Table>();
		DatabaseMetadata metadata = new DatabaseMetadata(tables);
		if (sqLiteDatabase == null) {
  			return metadata;
		}
		Cursor cursor = null;
		try {
			cursor = sqLiteDatabase.rawQuery("select tbl_name from sqlite_master where type = ? ", new String[] { "table" });
			while (cursor.moveToNext()) {
				String tableName = cursor.getString(0);
				//Ignore sqlite_sequence and android_metadata table
				if (tableName.equalsIgnoreCase("sqlite_sequence") || tableName.equalsIgnoreCase("android_metadata")) {
					continue;
				}
				final Table table = new Table(tableName);
				tables.add(table);
				Cursor pragmaCursor = null;
				try {
					pragmaCursor = sqLiteDatabase.rawQuery("pragma table_info (" + tableName + ")", null);
					int nameIndex = pragmaCursor.getColumnIndex("name");
					while (pragmaCursor.moveToNext()) {
						String name = pragmaCursor.getString(nameIndex);
						Column column = new Column();
						column.setName(name);
						table.addColumn(column);
					}
				} finally {
					SQLiteHelper.close(pragmaCursor);
				}
			}
		} finally {
			SQLiteHelper.close(cursor);
		}
		return metadata;
	}
}
