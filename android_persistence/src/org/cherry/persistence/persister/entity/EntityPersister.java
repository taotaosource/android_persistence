package org.cherry.persistence.persister.entity;

import java.io.Serializable;
import java.lang.reflect.Type;

import org.cherry.persistence.PersistenceException;
import org.cherry.persistence.engine.spi.SessionImplementor;
import org.cherry.persistence.id.IdentifierGenerator;
import org.cherry.persistence.tuple.entity.Tuplizer;


public interface EntityPersister extends PropertyMapping {
	public Tuplizer getTuplizer();

	public void setPropertyValues(Object object, Object[] values);

	public void setPropertyValue(Object entity, int i, Object value) throws PersistenceException;

	public Object[] getPropertyValues(Object object);

	public Serializable getIdentifier(Object entity, SessionImplementor session);

	public void setIdentifier(Object entity, Serializable id, SessionImplementor session);

	public Serializable insert(Object[] fields, Object object, SessionImplementor session) throws PersistenceException;

	public void delete(Serializable id, Object object, SessionImplementor session) throws PersistenceException;

	public void update(Serializable id, Object[] fields, Object object, SessionImplementor session) throws PersistenceException;

	/**
	 * Load an instance of the persistent class.
	 */
	public Object load(Serializable id, Object optionalObject, SessionImplementor session) throws PersistenceException;

	public String getIdentifierColumnName();

	public String getTableName();

	/**
	 * The entity name which this persister maps.
	 * 
	 * @return The name of the entity which this persister maps.
	 */
	public String getEntityName();

	/**
	 * Get the names of the class properties - doesn't have to be the names of
	 * the actual Java properties (used for XML generation only)
	 */
	public String[] getPropertyNames();

	/**
	 * Generate a list of collection index, key and element columns
	 */
	public String selectFragment(String alias, String suffix);

	/**
	 * Create a class instance initialized with the given identifier
	 * 
	 * @param id
	 *            The identifier value to use (may be null to represent no
	 *            value)
	 * @param session
	 *            The session from which the request originated.
	 * 
	 * @return The instantiated entity.
	 */
	public Object instantiate(Serializable id, SessionImplementor session);
	
	/**
	 * Determine whether the entity has a particular property holding
	 * the identifier value.
	 *
	 * @return True if the entity has a specific property holding identifier value.
	 */
	public boolean hasIdentifierProperty();
	
	/**
	 * Get the identifier type
	 */
	public Type getIdentifierType();
	
	/**
	 * Determine which identifier generation strategy is used for this entity.
	 *
	 * @return The identifier generation strategy.
	 */
	public IdentifierGenerator getIdentifierGenerator();
}
