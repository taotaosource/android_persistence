package org.cherry.persistence.sql;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * A fragment of an SQL <tt>SELECT</tt> clause
 * 
 * 
 */
public class SelectFragment {

	private List<String> columns = new ArrayList<String>();

	public SelectFragment() {
	}

	public SelectFragment(String[] columns) {
		this.columns = Arrays.asList(columns);
	}

	public List<String> getColumns() {
		return columns;
	}

	public void addColumns(String column) {
		columns.add(column);
	}

	public String toFragmentString() {
		int len = columns.size();
		StringBuilder builder = new StringBuilder();
		for (int i = 0; i < len; i++) {
			if (i > 0) {
				builder.append(",");
			}
			builder.append(columns.get(i));
		}
		return builder.toString();
	}

}
