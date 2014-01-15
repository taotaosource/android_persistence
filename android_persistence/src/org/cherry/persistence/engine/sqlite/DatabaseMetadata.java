package org.cherry.persistence.engine.sqlite;

import java.util.List;

import org.cherry.persistence.mapping.Table;


public class DatabaseMetadata {
	private List<Table> tables;

	public DatabaseMetadata(List<Table> tables) {
		super();
		this.tables = tables;
	}

	public List<Table> getTables() {
		return tables;
	}

	public void setTables(List<Table> tables) {
		this.tables = tables;
	}
	
} 
