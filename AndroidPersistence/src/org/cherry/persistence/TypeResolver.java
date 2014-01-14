package org.cherry.persistence;

import java.util.HashMap;

public class TypeResolver {
	private HashMap<Class<?>, String> sqlTypes = new HashMap<Class<?>, String>();

	public TypeResolver() {
		sqlTypes.put(String.class, "TEXT");
		sqlTypes.put(int.class, "INTEGER");
		sqlTypes.put(Integer.class, "INTEGER");
		sqlTypes.put(double.class, "DOUBLE");
		sqlTypes.put(Double.class, "DOUBLE");
		sqlTypes.put(float.class, "FLOAT");
		sqlTypes.put(Float.class, "FLOAT");
		sqlTypes.put(long.class, "BIGINT");
		sqlTypes.put(Long.class, "BIGINT");
		sqlTypes.put(short.class, "INT2");
		sqlTypes.put(Short.class, "INT2");
		sqlTypes.put(byte.class, "TINYINT");
		sqlTypes.put(Byte.class, "TINYINT");
		sqlTypes.put(char.class, "CHAR");
 	}

	public String getSqlType(Class<?> clazz) {
		return sqlTypes.get(clazz);
	}
}
