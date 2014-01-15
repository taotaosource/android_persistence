package org.cherry.persistence.loader.criteria;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.cherry.persistence.Criteria;
import org.cherry.persistence.PersistenceException;
import org.cherry.persistence.criterion.CriteriaQuery;
import org.cherry.persistence.engine.spi.QueryParameters;
import org.cherry.persistence.engine.spi.RowSelection;
import org.cherry.persistence.engine.spi.SessionFactoryImplementor;
import org.cherry.persistence.engine.spi.TypedValue;
import org.cherry.persistence.internal.CriteriaImpl;
import org.cherry.persistence.internal.CriteriaImpl.CriterionEntry;
import org.cherry.persistence.internal.CriteriaImpl.OrderEntry;
import org.cherry.persistence.persister.entity.EntityPersister;


public class CriteriaQueryTranslator implements CriteriaQuery {

	private final CriteriaImpl rootCriteria;
	private final String rootEntityName;
	private final EntityPersister entityPersister;
	private final SessionFactoryImplementor factory;

	public CriteriaQueryTranslator(SessionFactoryImplementor factory, CriteriaImpl rootCriteria, String rootEntityName) {
		super();
		this.rootCriteria = rootCriteria;
		this.rootEntityName = rootEntityName;
		this.entityPersister = factory.getEntityPersister(rootEntityName);
		this.factory = factory;
	}

	@Override
	public String getColumn(Criteria criteria, String propertyPath) throws PersistenceException {
		return entityPersister.toColumn(propertyPath);
	}

	@Override
	public String getIdentifierColumn(Criteria subcriteria) {
		return entityPersister.getIdentifierColumnName();
	}

	@Override
	public SessionFactoryImplementor getFactory() {
		return factory;
	}

	@Override
	public String getEntityName(Criteria criteria) {
		return rootEntityName;
	}
	
	public boolean hasProjection() {
		return rootCriteria.getProjection() != null;
	}

	public QueryParameters getQueryParameters() {
		RowSelection selection = new RowSelection();
		selection.setFirstRow(rootCriteria.getFirstResult());
		selection.setMaxRows(rootCriteria.getMaxResults());
		List<Object> values = new ArrayList<Object>();
		Iterator<CriterionEntry> iter = rootCriteria.iterateExpressionEntries();
		while (iter.hasNext()) {
			CriterionEntry ce = iter.next();
			TypedValue[] typedValues = ce.getCriterion().getTypedValues(rootCriteria, this);
			for (TypedValue typedValue : typedValues) {
				values.add(typedValue.getValue());
			}
		}
		return new QueryParameters(selection, values.toArray());
	}

	public String getWhereCondition() {
		StringBuilder condition = new StringBuilder(30);
		Iterator<CriterionEntry> criterionIterator = rootCriteria.iterateExpressionEntries();
		while (criterionIterator.hasNext()) {
			CriteriaImpl.CriterionEntry entry = (CriteriaImpl.CriterionEntry) criterionIterator.next();
			String sqlString = entry.getCriterion().toSqlString(entry.getCriteria(), this);
			condition.append(sqlString);
			if (criterionIterator.hasNext()) {
				condition.append(" and ");
			}
		}
		return condition.toString();
	}

	public String getOrderBy() {
		StringBuilder orderBy = new StringBuilder(30);
		Iterator<OrderEntry> criterionIterator = rootCriteria.iterateOrderings();
		while (criterionIterator.hasNext()) {
			CriteriaImpl.OrderEntry oe = (CriteriaImpl.OrderEntry) criterionIterator.next();
			orderBy.append(oe.getOrder().toSqlString(oe.getCriteria(), this));
			if (criterionIterator.hasNext()) {
				orderBy.append(", ");
			}
		}
		return orderBy.toString();
	}

	@Override
	public TypedValue getTypedValue(Criteria criteria, String propertyPath, Object value) throws PersistenceException {
		return new TypedValue(null, value);
	}

	@Override
	public TypedValue getTypedIdentifierValue(Criteria subcriteria, Object value) {
		return new TypedValue(null, value);
	}
	
	
}
