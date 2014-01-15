package org.cherry.persistence.criterion;

import java.util.ArrayList;

import org.cherry.persistence.Criteria;
import org.cherry.persistence.PersistenceException;
import org.cherry.persistence.engine.spi.TypedValue;


/**
 * Constrains the property to a specified list of values
 * 
  */
public class InExpression implements Criterion {

	private final String propertyName;
	private final Object[] values;

	protected InExpression(String propertyName, Object[] values) {
		this.propertyName = propertyName;
		this.values = values;
	}

	public String toSqlString(Criteria criteria, CriteriaQuery criteriaQuery) throws PersistenceException {
		String column = criteriaQuery.getColumn(criteria, propertyName);
		StringBuffer stringBuffer = new StringBuffer();
		stringBuffer.append(column);
		stringBuffer.append(" in (");
		if (values != null) {
			int i = 0;
			for (Object value : values) {
				if (i > 0) {
					stringBuffer.append(",");
				}
				stringBuffer.append("?");
				i++;
			}
		}
		stringBuffer.append(")");
		return stringBuffer.toString();

	}

	public TypedValue[] getTypedValues(Criteria criteria, CriteriaQuery criteriaQuery) throws PersistenceException {
		ArrayList<TypedValue> arrayList = new ArrayList<TypedValue>();
		if  (values != null) {
			for (Object value : values) {
				arrayList.add(criteriaQuery.getTypedValue(criteria, propertyName, value));
			}
		}
		return arrayList.toArray(new TypedValue[]{});
	}

}
