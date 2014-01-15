package org.cherry.persistence.property;

import org.cherry.persistence.PersistenceException;

public interface Getter {
	public Object get(Object owner) throws PersistenceException;
}
