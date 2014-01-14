package org.cherry.persistence.engine.spi;

import java.io.Serializable;
import java.util.List;

import org.cherry.persistence.Criteria;
import org.cherry.persistence.PersistenceException;
import org.cherry.persistence.engine.sqlite.DatabaseCoordinator;
import org.cherry.persistence.persister.entity.EntityPersister;


public interface SessionImplementor {
	public EntityPersister getEntityPersister(String entityName, Object object) throws PersistenceException;
	
	/**
	 * Execute a criteria query
	 */
	public List list(Criteria criteria);
	
	public DatabaseCoordinator getDatabaseCoordinator();
	
	/**
	 * Instantiate the entity class, initializing with the given identifier
	 */
	public Object instantiate(String entityName, Serializable id) throws PersistenceException;
}
