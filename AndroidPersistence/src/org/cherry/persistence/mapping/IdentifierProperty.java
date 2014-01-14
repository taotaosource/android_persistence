package org.cherry.persistence.mapping;

import java.lang.reflect.Field;

public class IdentifierProperty extends Property {

	private String strategy;

	public IdentifierProperty(Field field, PersistentClass persistentClass) {
		super(field, persistentClass);
	}

	public String getStrategy() {
		return strategy;
	}

	public void setStrategy(String strategy) {
		this.strategy = strategy;
	}

}
