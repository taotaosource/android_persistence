package org.cherry.persistence.property;

import org.cherry.persistence.PersistenceException;

public interface Setter {
	public void set(Object target, Object value) throws PersistenceException;
}
