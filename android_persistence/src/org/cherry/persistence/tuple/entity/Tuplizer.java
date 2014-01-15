package org.cherry.persistence.tuple.entity;

import java.io.Serializable;

import org.cherry.persistence.PersistenceException;
import org.cherry.persistence.engine.spi.SessionImplementor;
import org.cherry.persistence.property.Getter;


public interface Tuplizer {
	/**
	 * Extract the current values contained on the given entity.
	 * 
	 * @param entity
	 *            The entity from which to extract values.
	 * @return The current property values.
	 */
	public Object[] getPropertyValues(Object entity);
	
	
	/**
	 * Extract the value of a particular property from the given entity.
	 *
	 * @param entity The entity from which to extract the property value.
	 * @param i The index of the property for which to extract the value.
	 * @return The current value of the given property on the given entity.
	 */
	public Object getPropertyValue(Object entity, int i);	
	
	
	/**
	 * Inject the value of a particular property.
	 *
	 * @param entity The entity into which to inject the value.
	 * @param i The property's index.
	 * @param value The property value to inject.
	 * @throws PersistenceException Indicates a problem access the property
	 */
	public void setPropertyValue(Object entity, int i, Object value) throws PersistenceException;
	
	/**
	 * Inject the given values into the given entity.
	 * 
	 * @param entity
	 *            The entity.
	 * @param values
	 *            The values to be injected.
	 */
	public void setPropertyValues(Object entity, Object[] values);

	public Serializable getIdentifier(Object entity, SessionImplementor session);

	public void setIdentifier(Object entity, Serializable id, SessionImplementor session);
	

	/**
	 * Retrieve the getter for the specified property.
	 *
	 * @param i The property index.
	 * @return The property getter.
	 */
	public Getter getGetter(int i);
	
	/**
	 * Generate a new, empty entity.
	 *
	 * @return The new, empty entity instance.
	 */
	public Object instantiate();
	
	/**
     * Create an entity instance initialized with the given identifier.
     *
     * @param id The identifier value for the entity to be instantiated.
	 * @param session The session from which is requests originates
	 *
     * @return The instantiated entity.
     */
	public Object instantiate(Serializable id, SessionImplementor session);
	
}
