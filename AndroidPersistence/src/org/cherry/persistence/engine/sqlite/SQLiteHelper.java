package org.cherry.persistence.engine.sqlite;

import android.database.Cursor;

public class SQLiteHelper {
	public static final void close(Cursor cursor) {
		if (cursor != null) {
			cursor.close();
		}
	}
}
