package org.cherry.persistence.criterion;

import java.io.Serializable;

import org.cherry.persistence.Criteria;
import org.cherry.persistence.PersistenceException;


/**
 * Represents an order imposed upon a <tt>Criteria</tt> result set
 * 
 */
public class Order implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -8660316518615201295L;
	private boolean ascending;
	private boolean ignoreCase;
	private String propertyName;

	public String toString() {
		return propertyName + ' ' + (ascending ? "asc" : "desc");
	}

	public Order ignoreCase() {
		ignoreCase = true;
		return this;
	}

	/**
	 * Constructor for Order.
	 */
	protected Order(String propertyName, boolean ascending) {
		this.propertyName = propertyName;
		this.ascending = ascending;
	}

	/**
	 * Render the SQL fragment
	 * 
	 */
	public String toSqlString(Criteria criteria, CriteriaQuery criteriaQuery) throws PersistenceException {
		String column = criteriaQuery.getColumn(criteria, propertyName);
		return column.concat(ascending ? " asc " : " desc ");
	}

	public String getPropertyName() {
		return propertyName;
	}

	public boolean isAscending() {
		return ascending;
	}

	public boolean isIgnoreCase() {
		return ignoreCase;
	}

	/**
	 * Ascending order
	 * 
	 * @param propertyName
	 * @return Order
	 */
	public static Order asc(String propertyName) {
		return new Order(propertyName, true);
	}

	/**
	 * Descending order
	 * 
	 * @param propertyName
	 * @return Order
	 */
	public static Order desc(String propertyName) {
		return new Order(propertyName, false);
	}

}
