package org.cherry.persistence.criterion;

import org.cherry.persistence.Criteria;
import org.cherry.persistence.PersistenceException;
import org.cherry.persistence.engine.spi.TypedValue;

/**
 * A criterion representing a "like" expression
 * 
   */
public class LikeExpression implements Criterion {
	private final String propertyName;
	private final Object value;
	private final Character escapeChar;
	private final boolean ignoreCase;

	protected LikeExpression(String propertyName, String value, Character escapeChar, boolean ignoreCase) {
		this.propertyName = propertyName;
		this.value = value;
		this.escapeChar = escapeChar;
		this.ignoreCase = ignoreCase;
	}

	protected LikeExpression(String propertyName, String value) {
		this(propertyName, value, null, false);
	}

	protected LikeExpression(String propertyName, String value, MatchMode matchMode) {
		this(propertyName, matchMode.toMatchString(value));
	}

	protected LikeExpression(String propertyName, String value, MatchMode matchMode, Character escapeChar, boolean ignoreCase) {
		this(propertyName, matchMode.toMatchString(value), escapeChar, ignoreCase);
	}

	public String toSqlString(Criteria criteria, CriteriaQuery criteriaQuery) throws PersistenceException {
		String escape = escapeChar == null ? "" : " escape \'" + escapeChar + "\'";
		String column = criteriaQuery.getColumn(criteria, propertyName);
		return column + " like ?" + escape;

	}

	public TypedValue[] getTypedValues(Criteria criteria, CriteriaQuery criteriaQuery) throws PersistenceException {
		return new TypedValue[] { criteriaQuery.getTypedValue(criteria, propertyName, value) };
	}
}
