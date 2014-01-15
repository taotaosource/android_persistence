package org.cherry.persistence.mapping;

import java.lang.reflect.Type;

public class Column {
	public static final int DEFAULT_LENGTH = 255;
	private String name;
	private int length = DEFAULT_LENGTH;
	private boolean nullable = true;
	private boolean unique;
	private String sqlType;
	private int sqlTypeCode;
	private boolean isPrimaryKey;
	private Type type;
	private String strategy = "assigned";

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name.toLowerCase();
	}

	public int getLength() {
		return length;
	}

	public void setLength(int length) {
		this.length = length;
	}

	public int getSqlTypeCode() {
		return sqlTypeCode;
	}

	public void setSqlTypeCode(int sqlTypeCode) {
		this.sqlTypeCode = sqlTypeCode;
	}

	public boolean isNullable() {
		return nullable;
	}

	public void setNullable(boolean nullable) {
		this.nullable = nullable;
	}

	public String getSqlType() {
		return sqlType;
	}

	public void setSqlType(String sqlType) {
		this.sqlType = sqlType;
	}

	public boolean isPrimaryKey() {
		return isPrimaryKey;
	}

	public void setPrimaryKey(boolean isPrimaryKey) {
		this.isPrimaryKey = isPrimaryKey;
	}

	public Type getType() {
		return type;
	}

	public void setType(Type type) {
		this.type = type;
	}

	public String getStrategy() {
		return strategy;
	}

	public void setStrategy(String strategy) {
		if (strategy != null) {
			this.strategy = strategy;
		}
	}

	public boolean isUnique() {
		return unique;
	}

	public void setUnique(boolean unique) {
		this.unique = unique;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Column other = (Column) obj;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		return true;
	}

}
