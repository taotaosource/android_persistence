package org.cherry.persistence.cfg;

import org.cherry.persistence.mapping.Table;

public class TableBinder {

	public static Table buildAndFillTable(String className, String name, Mappings mappings) {
		Table table = new Table(name);
		mappings.addTable(className, table);
		return table;
	}
}
