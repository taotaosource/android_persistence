package org.cherry.persistence.criterion;

import java.lang.reflect.Type;

import org.cherry.persistence.Criteria;
import org.cherry.persistence.PersistenceException;
import org.cherry.persistence.engine.spi.TypedValue;


/**
 * A SQL fragment. The string {alias} will be replaced by the alias of the root
 * entity.
 */
public class SQLCriterion implements Criterion {

	private final String sql;
	private final TypedValue[] typedValues;

	public String toSqlString(Criteria criteria, CriteriaQuery criteriaQuery) throws PersistenceException {
		return sql;
	}

	public TypedValue[] getTypedValues(Criteria criteria, CriteriaQuery criteriaQuery) throws PersistenceException {
		return typedValues;
	}

	public String toString() {
		return sql;
	}

	protected SQLCriterion(String sql, Object[] values, Type[] types) {
		this.sql = sql;
		typedValues = new TypedValue[values.length];
		for (int i = 0; i < typedValues.length; i++) {
			typedValues[i] = new TypedValue(types[i], values[i]);
		}
	}

}
