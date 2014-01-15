package org.cherry.persistence.criterion;

import org.cherry.persistence.Criteria;
import org.cherry.persistence.PersistenceException;
import org.cherry.persistence.engine.spi.TypedValue;

/**
 * Constrains a property to between two values
 * 
  */
public class BetweenExpression implements Criterion {

	private final String propertyName;
	private final Object lo;
	private final Object hi;

	protected BetweenExpression(String propertyName, Object lo, Object hi) {
		this.propertyName = propertyName;
		this.lo = lo;
		this.hi = hi;
	}

	public String toSqlString(Criteria criteria, CriteriaQuery criteriaQuery) throws PersistenceException {
		String column = criteriaQuery.getColumn(criteria, propertyName);
		return column.concat(" between ? and ?");
	}

	public TypedValue[] getTypedValues(Criteria criteria, CriteriaQuery criteriaQuery) throws PersistenceException {
		return new TypedValue[] { criteriaQuery.getTypedValue(criteria, propertyName, lo),
				criteriaQuery.getTypedValue(criteria, propertyName, hi) };
	}

	public String toString() {
		return propertyName + " between " + lo + " and " + hi;
	}

}
