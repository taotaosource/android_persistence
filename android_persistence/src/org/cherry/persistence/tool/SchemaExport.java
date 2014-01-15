package org.cherry.persistence.tool;

import org.cherry.persistence.cfg.Configuration;
import org.cherry.persistence.engine.sqlite.DatabaseCoordinator;

public class SchemaExport {
	private Configuration configuration;

	public SchemaExport(Configuration configuration) {
		super();
		this.configuration = configuration;
	}

	public void execute() {
		DatabaseCoordinator coordinator = configuration.getDatabaseCoordinator();
		configuration.generateSchemaCreationScript();
		coordinator.beginTransaction();
		try {
			String[] creaeScripts = configuration.generateSchemaCreationScript();
			for (String script : creaeScripts) {
				coordinator.execSQL(script);
			}
			coordinator.setTransactionSuccessful();
		} finally {
			coordinator.endTransaction();
		}
	}
}
