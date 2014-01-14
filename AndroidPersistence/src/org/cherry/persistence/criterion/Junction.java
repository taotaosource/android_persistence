package org.cherry.persistence.criterion;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import org.cherry.persistence.Criteria;
import org.cherry.persistence.PersistenceException;
import org.cherry.persistence.engine.spi.TypedValue;


/**
 * A sequence of a logical expressions combined by some associative logical
 * operator
 * 
  */
public class Junction implements Criterion {
	private final Nature nature;
	private final List<Criterion> conditions = new ArrayList<Criterion>();

	protected Junction(Nature nature) {
		this.nature = nature;
	}

	public Junction add(Criterion criterion) {
		conditions.add(criterion);
		return this;
	}

	public Nature getNature() {
		return nature;
	}

	public Iterable<Criterion> conditions() {
		return conditions;
	}

	@Override
	public TypedValue[] getTypedValues(Criteria crit, CriteriaQuery criteriaQuery) throws PersistenceException {
		ArrayList<TypedValue> typedValues = new ArrayList<TypedValue>();
		for (Criterion condition : conditions) {
			TypedValue[] subValues = condition.getTypedValues(crit, criteriaQuery);
			Collections.addAll(typedValues, subValues);
		}
		return typedValues.toArray(new TypedValue[typedValues.size()]);
	}

	@Override
	public String toSqlString(Criteria crit, CriteriaQuery criteriaQuery) throws PersistenceException {
		if (conditions.size() == 0) {
			return "1=1";
		}

		StringBuilder buffer = new StringBuilder().append('(');
		Iterator<Criterion> itr = conditions.iterator();
		while (itr.hasNext()) {
			buffer.append((itr.next()).toSqlString(crit, criteriaQuery));
			if (itr.hasNext()) {
				buffer.append(' ').append(nature.getOperator()).append(' ');
			}
		}
		return buffer.append(')').toString();
	}

	@Override
	public String toString() {
		return "";
	}

	public static enum Nature {
		AND, OR;

		public String getOperator() {
			return name().toLowerCase();
		}
	}
}
