package org.cherry.persistence.loader.entity;

import java.io.Serializable;

import org.cherry.persistence.PersistenceException;
import org.cherry.persistence.engine.spi.SessionImplementor;


public interface UniqueEntityLoader {

	/**
	 * Load an entity instance. load the entity state into the given
	 * (uninitialized) object.
	 * 
	 */
	public Object load(Serializable id, SessionImplementor session) throws PersistenceException;

}
