package org.cherry.persistence.sql;

import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;

public class Insert {
	private String tableName;
	private String comment;
	private Map<String, String> columns = new LinkedHashMap<String, String>();

	public Insert() {
	}

	public Insert setComment(String comment) {
		this.comment = comment;
		return this;
	}

	public Insert addColumn(String columnName) {
		return addColumn(columnName, "?");
	}

	public Insert addColumns(String[] columnNames) {
		for (int i = 0; i < columnNames.length; i++) {
			addColumn(columnNames[i]);
		}
		return this;
	}

	public Insert addColumns(String[] columnNames, boolean[] insertable) {
		for (int i = 0; i < columnNames.length; i++) {
			if (insertable[i]) {
				addColumn(columnNames[i]);
			}
		}
		return this;
	}

	public Insert addColumns(String[] columnNames, boolean[] insertable, String[] valueExpressions) {
		for (int i = 0; i < columnNames.length; i++) {
			if (insertable[i]) {
				addColumn(columnNames[i], valueExpressions[i]);
			}
		}
		return this;
	}

	public Insert addColumn(String columnName, String valueExpression) {
		columns.put(columnName, valueExpression);
		return this;
	}

	public Insert setTableName(String tableName) {
		this.tableName = tableName;
		return this;
	}

	public String toStatementString() {
		StringBuilder buf = new StringBuilder(columns.size() * 15 + tableName.length() + 10);
		if (comment != null) {
			buf.append("/* ").append(comment).append(" */ ");
		}
		buf.append("insert into ").append(tableName);
		if (columns.size() == 0) {
			buf.append(' ').append("values ( )");
		} else {
			buf.append(" (");
			Iterator<String> iter = columns.keySet().iterator();
			while (iter.hasNext()) {
				buf.append(iter.next());
				if (iter.hasNext()) {
					buf.append(", ");
				}
			}
			buf.append(") values (");
			iter = columns.values().iterator();
			while (iter.hasNext()) {
				buf.append(iter.next());
				if (iter.hasNext()) {
					buf.append(", ");
				}
			}
			buf.append(')');
		}
		return buf.toString();
	}
}
