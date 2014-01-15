package org.cherry.persistence.criterion;
import java.io.Serializable;

import org.cherry.persistence.Criteria;
import org.cherry.persistence.PersistenceException;



/**
 * An object-oriented representation of a query result set projection  in a {@link Criteria} query.
 * Built-in projection types are provided  by the {@link Projections} factory class.  This interface might be
 * implemented by application classes that define custom projections.
 *
 * @see Projections
 * @see Criteria
 */
public interface Projection extends Serializable {

	/**
	 * Render the SQL fragment to be used in the <tt>SELECT</tt> clause.
	 *
	 * @param criteria The local criteria to which this project is attached (for resolution).
	 * @param position The number of columns rendered in the <tt>SELECT</tt> clause before this projection.  Generally
	 * speaking this is useful to ensure uniqueness of the individual columns aliases.
	 * @param criteriaQuery The overall criteria query instance.
	 * @return The SQL fragment to plug into the <tt>SELECT</tt>
	 * @throws PersistenceException Indicates a problem performing the rendering
	 */
	public String toSqlString(Criteria criteria, int position, CriteriaQuery criteriaQuery)
			throws PersistenceException;

	/**
	 * Render the SQL fragment to be used in the <tt>GROUP BY</tt> clause
	 *
	 * @param criteria The local criteria to which this project is attached (for resolution).
	 * @param criteriaQuery The overall criteria query instance.
	 * @return The SQL fragment to plug into the <tt>GROUP BY</tt>
	 * @throws PersistenceException Indicates a problem performing the rendering
	 */
	public String toGroupSqlString(Criteria criteria, CriteriaQuery criteriaQuery)
			throws PersistenceException;

	 
	public String[] getColumnAliases(int position);

	/**
	 * Get the SQL column aliases used by this projection for the columns it writes for inclusion into the
	 * <tt>SELECT</tt> clause ({@link #toSqlString} <i>for a particular criteria-level alias</i>.
	 *
	 * @param alias The criteria-level alias
	 * @param position Just as in {@link #toSqlString}, represents the number of <b>columns</b> rendered
	 * prior to this projection.
	 * @return The columns aliases pertaining to a particular criteria-level alias; expected to return null if
	 * this projection does not understand this alias.
	 */
	public String[] getColumnAliases(String alias, int position);

	public String[] getAliases();

	/**
	 * Is this projection fragment (<tt>SELECT</tt> clause) also part of the <tt>GROUP BY</tt>
	 *
	 * @return True if the projection is also part of the <tt>GROUP BY</tt>; false otherwise.
	 */
	public boolean isGrouped();

}
