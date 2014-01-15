package org.cherry.persistence.criterion;

import org.cherry.persistence.Criteria;
import org.cherry.persistence.PersistenceException;
import org.cherry.persistence.engine.spi.SessionFactoryImplementor;
import org.cherry.persistence.engine.spi.TypedValue;

/**
 * An instance of <tt>CriteriaQuery</tt> is passed to criterion, order and
 * projection instances when actually compiling and executing the query. This
 * interface is not used by application code.
 * 
  */
public interface CriteriaQuery {

	public SessionFactoryImplementor getFactory();

	/**
	 * Get the names of the columns mapped by a property path, ignoring
	 * projection aliases
	 * 
	 * @throws PersistenceException
	 *             if the property maps to more than 1 column
	 */
	public String getColumn(Criteria criteria, String propertyPath) throws PersistenceException;

	/**
	 * Get the identifier column names of this entity
	 */
	public String getIdentifierColumn(Criteria subcriteria);

	/**
	 * Get the entity name of an entity
	 */
	public String getEntityName(Criteria criteria);

	/**
	 * Get the a typed value for the given property value.
	 */
	public TypedValue getTypedValue(Criteria criteria, String propertyPath, Object value) throws PersistenceException;

	public TypedValue getTypedIdentifierValue(Criteria subcriteria, Object value);
}