package org.cherry.persistence.property;

import java.lang.reflect.Field;

import org.cherry.persistence.PersistenceException;


public class BasicSetter implements Setter {
	private final transient Field field;

	public BasicSetter(Field field) {
		this.field = field;
	}

	@Override
	public void set(Object target, Object value) throws PersistenceException {
		try {
			field.set(target, value);
		} catch (Exception e) {
			throw new PersistenceException(" set value error", e);
		}
	}

}
