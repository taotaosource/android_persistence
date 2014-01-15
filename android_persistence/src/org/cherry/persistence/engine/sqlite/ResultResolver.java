package org.cherry.persistence.engine.sqlite;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import org.cherry.persistence.engine.spi.SessionImplementor;
import org.cherry.persistence.persister.entity.EntityPersister;

import android.database.Cursor;


public class ResultResolver {
	private final Cursor cursor;
	private final SessionImplementor session;
	private final String entityName;
	private final EntityPersister entityPersister;

	public ResultResolver(SessionImplementor seesion, EntityPersister entityPersister, Cursor cursor) {
		super();
		this.entityPersister = entityPersister;
		this.session = seesion;
		this.cursor = cursor;
		this.entityName = entityPersister.getEntityName();
	}

	public List list(String[] projections, boolean all) {
		ArrayList<Object> result = new ArrayList<Object>();
		if (all && cursor != null) {
			int count = cursor.getCount();
			Type[] types = entityPersister.getPropertiesTypes();
			int typeLen = types.length;
			for (int i = 0; i < count; i++) {
				cursor.moveToNext();
				Object object = session.instantiate(entityName, null);
				result.add(object);
				for (int j = 0; j < typeLen; j++) {
					Object value = get(cursor, (Class<?>) types[j], j);
					entityPersister.setPropertyValue(object, j, value);
				}
			}
		}
		return result;
	}

	private Object get(Cursor cursor, Class<?> type, int index) {
		Object result;
		if (type == int.class || type == Integer.class || type == byte.class || type == byte.class) {
			result = cursor.getInt(index);
		} else if (type == float.class || type == Float.class) {
			result = cursor.getFloat(index);
		} else if (type == double.class || type == Double.class) {
			result = cursor.getDouble(index);
		} else if (type == long.class || type == Long.class) {
			result = cursor.getLong(index);
		} else if (type == short.class || type == Short.class) {
			result = cursor.getShort(index);
		} else {
			result = cursor.getString(index);
		}
		return result;
	}

}
