package org.cherry.persistence.criterion;

import java.util.Collection;

import org.cherry.persistence.Criteria;
import org.cherry.persistence.internal.util.ArrayHelper;


/**
 * The <tt>criterion</tt> package may be used by applications as a framework for
 * building new kinds of <tt>Criterion</tt>. However, it is intended that most
 * applications will simply use the built-in criterion types via the static
 * factory methods of this class.
 * 
 * @see Criteria
 * @see Projections factory methods for <tt>Projection</tt> instances
  */
public class Restrictions {

	Restrictions() {
		// cannot be instantiated
	}

	/**
	 * Apply an "equal" constraint to the identifier property
	 * 
	 * @param value
	 * @return Criterion
	 */
	public static Criterion idEq(Object value) {
		return new IdentifierEqExpression(value);
	}

	/**
	 * Apply an "equal" constraint to the named property
	 * 
	 * @param propertyName
	 * @param value
	 * @return SimpleExpression
	 */
	public static SimpleExpression eq(String propertyName, Object value) {
		return new SimpleExpression(propertyName, value, "=");
	}

	/**
	 * Apply a "not equal" constraint to the named property
	 * 
	 * @param propertyName
	 * @param value
	 * @return SimpleExpression
	 */
	public static SimpleExpression ne(String propertyName, Object value) {
		return new SimpleExpression(propertyName, value, "<>");
	}

	/**
	 * Apply a "like" constraint to the named property
	 * 
	 * @param propertyName
	 * @param value
	 * @return Criterion
	 */
	public static SimpleExpression like(String propertyName, Object value) {
		return new SimpleExpression(propertyName, value, " like ");
	}

	/**
	 * Apply a "greater than" constraint to the named property
	 * 
	 * @param propertyName
	 * @param value
	 * @return Criterion
	 */
	public static SimpleExpression gt(String propertyName, Object value) {
		return new SimpleExpression(propertyName, value, ">");
	}

	/**
	 * Apply a "less than" constraint to the named property
	 * 
	 * @param propertyName
	 * @param value
	 * @return Criterion
	 */
	public static SimpleExpression lt(String propertyName, Object value) {
		return new SimpleExpression(propertyName, value, "<");
	}

	/**
	 * Apply a "less than or equal" constraint to the named property
	 * 
	 * @param propertyName
	 * @param value
	 * @return Criterion
	 */
	public static SimpleExpression le(String propertyName, Object value) {
		return new SimpleExpression(propertyName, value, "<=");
	}

	/**
	 * Apply a "greater than or equal" constraint to the named property
	 * 
	 * @param propertyName
	 * @param value
	 * @return Criterion
	 */
	public static SimpleExpression ge(String propertyName, Object value) {
		return new SimpleExpression(propertyName, value, ">=");
	}

	/**
	 * Apply a "between" constraint to the named property
	 * 
	 * @param propertyName
	 * @param lo
	 *            value
	 * @param hi
	 *            value
	 * @return Criterion
	 */
	public static Criterion between(String propertyName, Object lo, Object hi) {
		return new BetweenExpression(propertyName, lo, hi);
	}

	/**
	 * Apply an "in" constraint to the named property
	 * 
	 * @param propertyName
	 * @param values
	 * @return Criterion
	 */
	public static Criterion in(String propertyName, Object[] values) {
		return new InExpression(propertyName, values);
	}

	
	/**
	 * Apply an "in" constraint to the named property
	 * 
	 * @param propertyName
	 * @param values
	 * @return Criterion
	 */
	public static Criterion in(String propertyName, Collection values) {
		return new InExpression(propertyName, values.toArray());
	}

	/**
	 * Return the conjuction of two expressions
	 * 
	 * @param lhs
	 * @param rhs
	 * @return Criterion
	 */
	public static LogicalExpression and(Criterion lhs, Criterion rhs) {
		return new LogicalExpression(lhs, rhs, "and");
	}

	/**
	 * Return the conjuction of multiple expressions
	 * 
	 * @param predicates
	 *            The predicates making up the initial junction
	 * 
	 * @return The conjunction
	 */
	public static Conjunction and(Criterion... predicates) {
		Conjunction conjunction = conjunction();
		if (predicates != null) {
			for (Criterion predicate : predicates) {
				conjunction.add(predicate);
			}
		}
		return conjunction;
	}

	/**
	 * Return the disjuction of two expressions
	 * 
	 * @param lhs
	 * @param rhs
	 * @return Criterion
	 */
	public static LogicalExpression or(Criterion lhs, Criterion rhs) {
		return new LogicalExpression(lhs, rhs, "or");
	}

	/**
	 * Return the disjuction of multiple expressions
	 * 
	 * @param predicates
	 *            The predicates making up the initial junction
	 * 
	 * @return The conjunction
	 */
	public static Disjunction or(Criterion... predicates) {
		Disjunction disjunction = disjunction();
		if (predicates != null) {
			for (Criterion predicate : predicates) {
				disjunction.add(predicate);
			}
		}
		return disjunction;
	}

	/**
	 * Group expressions together in a single conjunction (A and B and C...)
	 * 
	 * @return Conjunction
	 */
	public static Conjunction conjunction() {
		return new Conjunction();
	}

	/**
	 * Group expressions together in a single disjunction (A or B or C...)
	 * 
	 * @return Conjunction
	 */
	public static Disjunction disjunction() {
		return new Disjunction();
	}


	/**
	 * Apply a constraint expressed in SQL. Any occurrences of <tt>{alias}</tt>
	 * will be replaced by the table alias.
	 * 
	 * @param sql
	 * @return Criterion
	 */
	public static Criterion sqlRestriction(String sql) {
		return new SQLCriterion(sql, ArrayHelper.EMPTY_OBJECT_ARRAY, ArrayHelper.EMPTY_TYPE_ARRAY);
	}

}
