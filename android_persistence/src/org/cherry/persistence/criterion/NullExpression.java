package org.cherry.persistence.criterion;

import org.cherry.persistence.Criteria;
import org.cherry.persistence.PersistenceException;
import org.cherry.persistence.engine.spi.TypedValue;

/**
 * Constrains a property to be null
 * 
  */
public class NullExpression implements Criterion {

	private final String propertyName;

	private static final TypedValue[] NO_VALUES = new TypedValue[0];

	protected NullExpression(String propertyName) {
		this.propertyName = propertyName;
	}

	public String toSqlString(Criteria criteria, CriteriaQuery criteriaQuery) throws PersistenceException {
		String column = criteriaQuery.getColumn(criteria, propertyName);
		return column.concat("  is null");
	}

	public TypedValue[] getTypedValues(Criteria criteria, CriteriaQuery criteriaQuery) throws PersistenceException {
		return NO_VALUES;
	}

	public String toString() {
		return propertyName + " is null";
	}

}
