package org.cherry.persistence.tool;

import java.util.List;

import org.cherry.persistence.cfg.Configuration;
import org.cherry.persistence.engine.sqlite.DatabaseCoordinator;
import org.cherry.persistence.engine.sqlite.DatabaseMetadata;


public class SchemaUpate {
	private Configuration configuration;

	public SchemaUpate(Configuration configuration) {
		super();
		this.configuration = configuration;
	}

	public void execute() {
		DatabaseCoordinator coordinator = configuration.getDatabaseCoordinator();
		DatabaseMetadata metadata = coordinator.getDatabaseMetadata();
		List<String> updateScripts = configuration.generateSchemaUpdateScriptList(metadata);
		try {
			coordinator.beginTransaction();
			for (String script : updateScripts) {
				coordinator.execSQL(script);
			}
			coordinator.setTransactionSuccessful();
		} finally {
			coordinator.endTransaction();
		}
	}
}
