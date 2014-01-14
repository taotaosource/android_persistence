package org.cherry.persistence.criterion;

import java.io.Serializable;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.cherry.persistence.Criteria;
import org.cherry.persistence.PersistenceException;
import org.cherry.persistence.engine.spi.TypedValue;
import org.cherry.persistence.persister.entity.EntityPersister;


/**
 * Support for query by example.
 * 
 * <pre>
 * List results = session.createCriteria(Parent.class).add(Example.create(parent).ignoreCase()).createCriteria(&quot;child&quot;)
 * 		.add(Example.create(parent.getChild())).list();
 * </pre>
 * 
 * "Examples" may be mixed and matched with "Expressions" in the same
 * <tt>Criteria</tt>.
 * 
 * @see Criteria
  */

public class Example implements Criterion {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4069553381361861949L;
	private final Object entity;
	private final Set excludedProperties = new HashSet();
	private PropertySelector selector;
	private boolean isLikeEnabled;
	private Character escapeCharacter;
	private boolean isIgnoreCaseEnabled;
	private MatchMode matchMode;

	/**
	 * A strategy for choosing property values for inclusion in the query
	 * criteria
	 */

	public static interface PropertySelector extends Serializable {
		public boolean include(Object propertyValue, String propertyName, Type type);
	}

	private static final PropertySelector NOT_NULL = new NotNullPropertySelector();
	private static final PropertySelector ALL = new AllPropertySelector();
	private static final PropertySelector NOT_NULL_OR_ZERO = new NotNullOrZeroPropertySelector();

	static final class AllPropertySelector implements PropertySelector {
		public boolean include(Object object, String propertyName, Type type) {
			return true;
		}

		private Object readResolve() {
			return ALL;
		}
	}

	static final class NotNullPropertySelector implements PropertySelector {
		public boolean include(Object object, String propertyName, Type type) {
			return object != null;
		}

		private Object readResolve() {
			return NOT_NULL;
		}
	}

	static final class NotNullOrZeroPropertySelector implements PropertySelector {
		public boolean include(Object object, String propertyName, Type type) {
			return object != null && (!(object instanceof Number) || ((Number) object).longValue() != 0);
		}

		private Object readResolve() {
			return NOT_NULL_OR_ZERO;
		}
	}

	/**
	 * Set escape character for "like" clause
	 */
	public Example setEscapeCharacter(Character escapeCharacter) {
		this.escapeCharacter = escapeCharacter;
		return this;
	}

	/**
	 * Set the property selector
	 */
	public Example setPropertySelector(PropertySelector selector) {
		this.selector = selector;
		return this;
	}

	/**
	 * Exclude zero-valued properties
	 */
	public Example excludeZeroes() {
		setPropertySelector(NOT_NULL_OR_ZERO);
		return this;
	}

	/**
	 * Don't exclude null or zero-valued properties
	 */
	public Example excludeNone() {
		setPropertySelector(ALL);
		return this;
	}

	/**
	 * Use the "like" operator for all string-valued properties
	 */
	public Example enableLike(MatchMode matchMode) {
		isLikeEnabled = true;
		this.matchMode = matchMode;
		return this;
	}

	/**
	 * Use the "like" operator for all string-valued properties
	 */
	public Example enableLike() {
		return enableLike(MatchMode.EXACT);
	}

	/**
	 * Ignore case for all string-valued properties
	 */
	public Example ignoreCase() {
		isIgnoreCaseEnabled = true;
		return this;
	}

	/**
	 * Exclude a particular named property
	 */
	public Example excludeProperty(String name) {
		excludedProperties.add(name);
		return this;
	}

	/**
	 * Create a new instance, which includes all non-null properties by default
	 * 
	 * @param entity
	 * @return a new instance of <tt>Example</tt>
	 */
	public static Example create(Object entity) {
		if (entity == null)
			throw new NullPointerException("null example");
		return new Example(entity, NOT_NULL);
	}

	protected Example(Object entity, PropertySelector selector) {
		this.entity = entity;
		this.selector = selector;
	}

	public String toString() {
		return "example (" + entity + ')';
	}

	private boolean isPropertyIncluded(Object value, String name, Type type) {
		return !excludedProperties.contains(name) && selector.include(value, name, type);
	}

	public String toSqlString(Criteria criteria, CriteriaQuery criteriaQuery) throws PersistenceException {

		StringBuilder buf = new StringBuilder().append('(');
		EntityPersister meta = criteriaQuery.getFactory().getEntityPersister(criteriaQuery.getEntityName(criteria));
		String[] propertyNames = meta.getPropertyNames();
		// TODO: get all properties, not just the fetched ones!
		Object[] propertyValues = meta.getPropertyValues(entity);
		for (int i = 0; i < propertyNames.length; i++) {
			Object propertyValue = propertyValues[i];
			String propertyName = propertyNames[i];
			boolean isPropertyIncluded = isPropertyIncluded(propertyValue, propertyName, null);
			if (isPropertyIncluded) {
				appendPropertyCondition(propertyName, propertyValue, criteria, criteriaQuery, buf);
			}
		}
		if (buf.length() == 1)
			buf.append("1=1"); // yuck!
		return buf.append(')').toString();
	}

	private static final Object[] TYPED_VALUES = new TypedValue[0];

	public TypedValue[] getTypedValues(Criteria criteria, CriteriaQuery criteriaQuery) throws PersistenceException {

		EntityPersister meta = criteriaQuery.getFactory().getEntityPersister(criteriaQuery.getEntityName(criteria));
		String[] propertyNames = meta.getPropertyNames();
		// TODO: get all properties, not just the fetched ones!
		Object[] values = meta.getPropertyValues(entity);
		List<TypedValue> list = new ArrayList<TypedValue>();
		for (int i = 0; i < propertyNames.length; i++) {
			Object value = values[i];
			String name = propertyNames[i];
			boolean isPropertyIncluded = isPropertyIncluded(value, name, null);
			if (isPropertyIncluded) {
				addPropertyTypedValue(criteria, value, criteriaQuery, list, name);
			}
		}
		return (TypedValue[]) list.toArray(TYPED_VALUES);
	}

	protected void addPropertyTypedValue(Criteria criteria, Object value,  CriteriaQuery criteriaQuery, List<TypedValue> list, String propertyName) {
		if (value != null) {
			if (value instanceof String) {
				String string = (String) value;
				if (isIgnoreCaseEnabled)
					string = string.toLowerCase();
				if (isLikeEnabled)
					string = matchMode.toMatchString(string);
				value = string;
			}
			list.add(criteriaQuery.getTypedValue(criteria, propertyName, value));
		}
	}

	protected void appendPropertyCondition(String propertyName, Object propertyValue, Criteria criteria, CriteriaQuery cq, StringBuilder buf)
			throws PersistenceException {
		Criterion crit;
		if (propertyValue != null) {
			boolean isString = propertyValue instanceof String;
			if (isLikeEnabled && isString) {
				crit = new LikeExpression(propertyName, (String) propertyValue, matchMode, escapeCharacter, isIgnoreCaseEnabled);
			} else {
				crit = new SimpleExpression(propertyName, propertyValue, "=", isIgnoreCaseEnabled && isString);
			}
		} else {
			crit = new NullExpression(propertyName);
		}
		String critCondition = crit.toSqlString(criteria, cq);
		if (buf.length() > 1 && critCondition.trim().length() > 0)
			buf.append(" and ");
		buf.append(critCondition);
	}

}