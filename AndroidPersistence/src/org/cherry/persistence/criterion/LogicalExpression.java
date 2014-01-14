package org.cherry.persistence.criterion;

import org.cherry.persistence.Criteria;
import org.cherry.persistence.PersistenceException;
import org.cherry.persistence.engine.spi.TypedValue;

/**
 * Superclass of binary logical expressions
 * 
  */
public class LogicalExpression implements Criterion {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6397843550906822632L;
	private final Criterion lhs;
	private final Criterion rhs;
	private final String op;

	protected LogicalExpression(Criterion lhs, Criterion rhs, String op) {
		this.lhs = lhs;
		this.rhs = rhs;
		this.op = op;
	}

	public TypedValue[] getTypedValues(Criteria criteria, CriteriaQuery criteriaQuery) throws PersistenceException {
		TypedValue[] lhstv = lhs.getTypedValues(criteria, criteriaQuery);
		TypedValue[] rhstv = rhs.getTypedValues(criteria, criteriaQuery);
		TypedValue[] result = new TypedValue[lhstv.length + rhstv.length];
		System.arraycopy(lhstv, 0, result, 0, lhstv.length);
		System.arraycopy(rhstv, 0, result, lhstv.length, rhstv.length);
		return result;
	}

	public String toSqlString(Criteria criteria, CriteriaQuery criteriaQuery) throws PersistenceException {

		return '(' + lhs.toSqlString(criteria, criteriaQuery) + ' ' + getOp() + ' ' + rhs.toSqlString(criteria, criteriaQuery) + ')';
	}

	public String getOp() {
		return op;
	}

	public String toString() {
		return lhs.toString() + ' ' + getOp() + ' ' + rhs.toString();
	}
}
