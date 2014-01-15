package org.cherry.persistence.criterion;

import org.cherry.persistence.Criteria;
import org.cherry.persistence.PersistenceException;
import org.cherry.persistence.engine.spi.TypedValue;

/**
 * An identifier constraint
 * 
  */
public class IdentifierEqExpression implements Criterion {
 
	private static final long serialVersionUID = 6195858681366797828L;
	private final Object value;

	protected IdentifierEqExpression(Object value) {
		this.value = value;
	}

	public String toSqlString(Criteria criteria, CriteriaQuery criteriaQuery) throws PersistenceException {
		String column = criteriaQuery.getIdentifierColumn(criteria);
		return column.concat("=?");
 	}

	public TypedValue[] getTypedValues(Criteria criteria, CriteriaQuery criteriaQuery) throws PersistenceException {
		return new TypedValue[] { criteriaQuery.getTypedIdentifierValue(criteria, value) };
	}

	public String toString() {
		return "id = " + value;
	}

}