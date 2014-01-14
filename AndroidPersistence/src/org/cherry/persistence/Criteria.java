package org.cherry.persistence;
import java.util.List;

import org.cherry.persistence.criterion.Criterion;
import org.cherry.persistence.criterion.Order;


/**
 * <tt>Criteria</tt> is a simplified API for retrieving entities
 * by composing <tt>Criterion</tt> objects. This is a very
 * convenient approach for functionality like "search" screens
 * where there is a variable number of conditions to be placed
 * upon the result set.<br>
 * <br>
 * The <tt>Session</tt> is a factory for <tt>Criteria</tt>.
 * <tt>Criterion</tt> instances are usually obtained via
 * the factory methods on <tt>Restrictions</tt>. eg.
 * <pre>
 * List cats = session.createCriteria(Cat.class)
 *     .add( Restrictions.like("name", "Iz%") )
 *     .add( Restrictions.gt( "weight", new Float(minWeight) ) )
 *     .addOrder( Order.asc("age") )
 *     .list();
 * </pre>
 */
public interface Criteria  {

	/**
	 * Add a {@link Criterion restriction} to constrain the results to be
	 * retrieved.
	 *
	 * @param criterion The {@link Criterion criterion} object representing the
	 * restriction to be applied.
	 * @return this (for method chaining)
	 */
	public Criteria add(Criterion criterion);
	
	/**
	 * Add an {@link Order ordering} to the result set.
	 *
	 * @param order The {@link Order order} object representing an ordering
	 * to be applied to the results.
	 * @return this (for method chaining)
	 */
	public Criteria addOrder(Order order);

	/**
	 * Set a limit upon the number of objects to be retrieved.
	 *
	 * @param maxResults the maximum number of results
	 * @return this (for method chaining)
	 */
	public Criteria setMaxResults(int maxResults);
	
	/**
	 * Set the first result to be retrieved.
	 *
	 * @param firstResult the first result to retrieve, numbered from <tt>0</tt>
	 * @return this (for method chaining)
	 */
	public Criteria setFirstResult(int firstResult);

	/**
	 * Get the results.
	 *
	 * @return The list of matched query results.
	 *
	 * @throws PersistenceException Indicates a problem either translating the criteria to SQL,
	 * exeucting the SQL or processing the SQL results.
	 */
	@SuppressWarnings("rawtypes")
	public List list() throws PersistenceException;
	
	/**
	 * Convenience method to return a single instance that matches
	 * the query, or null if the query returns no results.
	 *
	 * @return the single result or <tt>null</tt>
	 * @throws PersistenceException if there is more than one matching result
	 */
	public Object uniqueResult() throws PersistenceException;
	
}
