package org.cherry.persistence.criterion;
import java.io.Serializable;

import org.cherry.persistence.Criteria;
import org.cherry.persistence.PersistenceException;
import org.cherry.persistence.engine.spi.TypedValue;


 
/**
 * An object-oriented representation of a query criterion that may be used 
 * as a restriction in a <tt>Criteria</tt> query.
 * Built-in criterion types are provided by the <tt>Restrictions</tt> factory 
 * class. This interface might be implemented by application classes that 
 * define custom restriction criteria.
 *
 * @see Restrictions
   */
public interface Criterion extends Serializable {

	/**
	 * Render the SQL fragment
	 *
	 * @param criteria The local criteria
	 * @param criteriaQuery The overal criteria query
	 *
	 * @return The generated SQL fragment
	 * @throws PersistenceException Problem during rendering.
	 */
	public String toSqlString(Criteria criteria, CriteriaQuery criteriaQuery) throws PersistenceException;
	
	public TypedValue[] getTypedValues(Criteria criteria, CriteriaQuery criteriaQuery) throws PersistenceException ;
}
