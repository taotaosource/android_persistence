package org.cherry.persistence.property;

import java.lang.reflect.Field;

import org.cherry.persistence.PersistenceException;


public class BasicGetter implements Getter {
	private final transient Field field;

	public BasicGetter(Field field) {
		this.field = field;
	}

	@Override
	public Object get(Object owner) throws PersistenceException {
		try {
			return field.get(owner);
		} catch (Exception e) {
			throw new PersistenceException("getter error ", e);
		}
	}

}
