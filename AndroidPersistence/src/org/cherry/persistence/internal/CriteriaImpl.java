package org.cherry.persistence.internal;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.cherry.persistence.Criteria;
import org.cherry.persistence.NonUniqueResultException;
import org.cherry.persistence.PersistenceException;
import org.cherry.persistence.criterion.Criterion;
import org.cherry.persistence.criterion.Order;
import org.cherry.persistence.criterion.Projection;
import org.cherry.persistence.engine.spi.SessionImplementor;


/**
 * Implementation of the <tt>Criteria</tt> interface
 * 
 * 
 */
public class CriteriaImpl implements Criteria, Serializable {

	private static final long serialVersionUID = -7164136892801487269L;
	private final String entityOrClassName;
	private transient SessionImplementor session;
	private final String rootAlias;

	private List<CriterionEntry> criterionEntries = new ArrayList<CriterionEntry>();
	private List<OrderEntry> orderEntries = new ArrayList<OrderEntry>();
	private Projection projection;
	private Criteria projectionCriteria;

	private Integer maxResults;
	private Integer firstResult;
	private Integer timeout;
	private Integer fetchSize;

	private boolean cacheable;
	private String cacheRegion;
	private String comment;

	public CriteriaImpl(String entityOrClassName, String alias, SessionImplementor session) {
		this.session = session;
		this.entityOrClassName = entityOrClassName;
		this.cacheable = false;
		this.rootAlias = alias;
	}

	public String toString() {
		return "CriteriaImpl(" + entityOrClassName + ":" + (rootAlias == null ? "" : rootAlias) + criterionEntries.toString()
				+ (projection == null ? "" : projection.toString()) + ')';
	}

	// State ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

	public SessionImplementor getSession() {
		return session;
	}

	public void setSession(SessionImplementor session) {
		this.session = session;
	}

	public String getEntityOrClassName() {
		return entityOrClassName;
	}

	public Criteria getProjectionCriteria() {
		return projectionCriteria;
	}

	public Iterator<CriterionEntry> iterateExpressionEntries() {
		return criterionEntries.iterator();
	}

	public Iterator<OrderEntry> iterateOrderings() {
		return orderEntries.iterator();
	}

	public Criteria add(Criteria criteriaInst, Criterion expression) {
		criterionEntries.add(new CriterionEntry(expression, criteriaInst));
		return this;
	}

	// Criteria impl ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

	public String getAlias() {
		return rootAlias;
	}

	public Projection getProjection() {
		return projection;
	}

	public Criteria setProjection(Projection projection) {
		this.projection = projection;
		this.projectionCriteria = this;
		return this;
	}

	public Criteria add(Criterion expression) {
		add(this, expression);
		return this;
	}

	public Criteria addOrder(Order ordering) {
		orderEntries.add(new OrderEntry(ordering, this));
		return this;
	}

	public Integer getMaxResults() {
		return maxResults;
	}

	public Criteria setMaxResults(int maxResults) {
		this.maxResults = maxResults;
		return this;
	}

	public Integer getFirstResult() {
		return firstResult;
	}

	public Criteria setFirstResult(int firstResult) {
		this.firstResult = firstResult;
		return this;
	}

	public Integer getFetchSize() {
		return fetchSize;
	}

	public Criteria setFetchSize(int fetchSize) {
		this.fetchSize = fetchSize;
		return this;
	}

	public Integer getTimeout() {
		return timeout;
	}

	public Criteria setTimeout(int timeout) {
		this.timeout = timeout;
		return this;
	}

	public boolean getCacheable() {
		return this.cacheable;
	}

	public Criteria setCacheable(boolean cacheable) {
		this.cacheable = cacheable;
		return this;
	}

	public String getCacheRegion() {
		return this.cacheRegion;
	}

	public Criteria setCacheRegion(String cacheRegion) {
		this.cacheRegion = cacheRegion.trim();
		return this;
	}

	public String getComment() {
		return comment;
	}

	public Criteria setComment(String comment) {
		this.comment = comment;
		return this;
	}

	public List<?> list() throws PersistenceException {
		return session.list(this);
	}

	public Object uniqueResult() throws PersistenceException {
		List<?> list = list();
		int size = list.size();
		if (size == 0) {
			return null;
		}
		if (size == 1) {
			return list.get(0);
		} else {
			throw new NonUniqueResultException(list.size());
		}
 	}

	// Inner classes ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

	public static final class CriterionEntry implements Serializable {
		/**
		 * 
		 */
		private static final long serialVersionUID = 7245308493497574325L;
		private final Criterion criterion;
		private final Criteria criteria;

		private CriterionEntry(Criterion criterion, Criteria criteria) {
			this.criteria = criteria;
			this.criterion = criterion;
		}

		public Criterion getCriterion() {
			return criterion;
		}

		public Criteria getCriteria() {
			return criteria;
		}

		public String toString() {
			return criterion.toString();
		}
	}

	public static final class OrderEntry implements Serializable {
		/**
		 * 
		 */
		private static final long serialVersionUID = -8075249662626701756L;
		private final Order order;
		private final Criteria criteria;

		private OrderEntry(Order order, Criteria criteria) {
			this.criteria = criteria;
			this.order = order;
		}

		public Order getOrder() {
			return order;
		}

		public Criteria getCriteria() {
			return criteria;
		}

		public String toString() {
			return order.toString();
		}
	}
}
