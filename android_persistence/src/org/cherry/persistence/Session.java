package org.cherry.persistence;

import java.io.Serializable;

public interface Session extends SharedSessionContract {

	/**
	 * Persist the given transient instance, first assigning a generated identifier. (Or
	 * using the current value of the identifier property if the <tt>assigned</tt>
	 * generator is used.) 
	 *
	 * @param object a transient instance of a persistent class
	 *
	 * @return the generated identifier
	 */
	public Serializable save(Object object);

	/**
	 * Update the persistent instance with the identifier of the given detached
	 * instance. If there is a persistent instance with the same identifier,
	 * an exception is thrown.  
	 *
	 * @param object a detached instance containing updated state
	 */
	public void update(Object object);

	/**
	 * Remove a persistent instance from the datastore. The argument may be
	 * an instance associated with the receiving <tt>Session</tt> or a transient
	 * instance with an identifier associated with existing persistent state.
	 *
	 * @param object the instance to be removed
	 */
	public void delete(Object object);

	/**
	 * Return the persistent instance of the given entity class with the given identifier,
	 * or null if there is no such persistent instance. (If the instance is already associated
	 * with the session, return that instance. This method never returns an uninitialized instance.)
	 *
	 * @param clazz a persistent class
	 * @param id an identifier
	 *
	 * @return a persistent instance or null
	 */
	public <T> T  get(Class<T> clazz, Serializable id);
	
	public Object getConnection();
}
