package org.cherry.persistence.cfg;

public class Settings {
	private boolean autoCreateSchema;
	private boolean autoUpdateSchema;

	public boolean isAutoCreateSchema() {
		return autoCreateSchema;
	}

	public void setAutoCreateSchema(boolean autoCreateSchema) {
		this.autoCreateSchema = autoCreateSchema;
	}

	public boolean isAutoUpdateSchema() {
		return autoUpdateSchema;
	}

	public void setAutoUpdateSchema(boolean autoUpdateSchema) {
		this.autoUpdateSchema = autoUpdateSchema;
	}
}
