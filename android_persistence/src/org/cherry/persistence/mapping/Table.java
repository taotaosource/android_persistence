package org.cherry.persistence.mapping;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.cherry.persistence.MappingException;
import org.cherry.persistence.PersistenceException;


public class Table implements RelationalModel {

	private String name;
	private Map<String, Column> columns = new LinkedHashMap<String, Column>();

	private PrimaryKey primaryKey;
	private String identifier;

	public Table() {

	}

	public Table(String name) {
		super();
		this.name = name.toLowerCase();
	}

	public void addColumn(Column column) {
		Column oldColumn = columns.put(column.getName(), column);
		if (oldColumn != null) {
			throw new MappingException(" same column , Table " + name + ",  columnName : " + column.getName());
		}
	}

	@Override
	public String sqlCreateString() throws PersistenceException {
		StringBuilder builder = new StringBuilder();
		builder.append(" CREATE TABLE ").append(getName()).append(" (");

		Collection<Column> values = columns.values();
		Iterator<Column> iterator = values.iterator();
		boolean hasIdentifier = identifier != null;
		PrimaryKey pk = this.primaryKey;
		String pkName = pk == null ? null : pk.getName();
		while (iterator.hasNext()) {
			Column column = iterator.next();

			String columNname = column.getName();
			builder.append(columNname).append(" ");
			builder.append(column.getSqlType());
			if (hasIdentifier && columNname.equals(pkName)) {
				builder.append(" primary key autoincrement ");
				pk = null;
			}

			if (!column.isNullable()) {
				builder.append(" not null");
			}
			
			if (column.isUnique()) {
				builder.append(" unique ");
			}
			
			if (iterator.hasNext()) {
				builder.append(", ");
			}
		}
		if (pk != null) {
			builder.append(", ");
			builder.append(pk.sqlConstraintString());
		}

		builder.append(')');
		return builder.toString();
	}

	@Override
	public String sqlDropString() {
		return "drop table if exists ".concat(name);
	}

	public List<String> sqlAlterStrings(Table oldTable) {
		ArrayList<String> alterStrings = new ArrayList<String>();
		Collection<Column> columnList = columns.values();
		for (Column column : columnList) {
			final String columnName = column.getName();
			if (!oldTable.columns.containsKey(columnName)) {
				StringBuffer sqlBuffer = new StringBuffer("alter table ");
				sqlBuffer.append(name).append(" add column ").append(columnName).append(" ").append(column.getSqlType());
				
				if (!column.isNullable()) {
					sqlBuffer.append(" not null default '' ");
				}
				
				alterStrings.add(sqlBuffer.toString());
			}
		}
		return alterStrings;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name.toLowerCase();
	}

	public PrimaryKey getPrimaryKey() {
		return primaryKey;
	}

	public void setPrimaryKey(PrimaryKey primaryKey) {
		if (this.primaryKey != null) {
			throw new MappingException(name.concat(" multiple primary keys ColumnName: ".concat(primaryKey.getName())));
		}
		this.primaryKey = primaryKey;
	}

	public void setIdentifier(String name) {
		this.identifier = name;
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
		Table other = (Table) obj;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		return true;
	}
}
