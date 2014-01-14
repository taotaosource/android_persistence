package org.cherry.persistence.sql;

import org.cherry.persistence.internal.util.StringHelper;

/**
 * A simple SQL <tt>SELECT</tt> statement
 * 
 * 
 */
public class Select {

	private String selectClause;
	private String fromClause;
	private String outerJoinsAfterFrom;
	private String whereClause;
	private String outerJoinsAfterWhere;
	private String orderByClause;
	private String groupByClause;
	private String comment;

	private int guesstimatedBufferSize = 20;

	/**
	 * Construct an SQL <tt>SELECT</tt> statement from the given clauses
	 */
	public String toStatementString() {
		StringBuilder buf = new StringBuilder(guesstimatedBufferSize);
		if (StringHelper.isNotEmpty(comment)) {
			buf.append("/* ").append(comment).append(" */ ");
		}

		buf.append("select ").append(selectClause).append(" from ").append(fromClause);

		if (StringHelper.isNotEmpty(outerJoinsAfterFrom)) {
			buf.append(outerJoinsAfterFrom);
		}

		if (StringHelper.isNotEmpty(whereClause) || StringHelper.isNotEmpty(outerJoinsAfterWhere)) {
			buf.append(" where ");
			// the outerJoinsAfterWhere needs to come before where clause to
			// properly
			// handle dynamic filters
			if (StringHelper.isNotEmpty(outerJoinsAfterWhere)) {
				buf.append(outerJoinsAfterWhere);
				if (StringHelper.isNotEmpty(whereClause)) {
					buf.append(" and ");
				}
			}
			if (StringHelper.isNotEmpty(whereClause)) {
				buf.append(whereClause);
			}
		}

		if (StringHelper.isNotEmpty(groupByClause)) {
			buf.append(" group by ").append(groupByClause);
		}

		if (StringHelper.isNotEmpty(orderByClause)) {
			buf.append(" order by ").append(orderByClause);
		}

		return buf.toString();
	}

	/**
	 * Sets the fromClause.
	 * 
	 * @param fromClause
	 *            The fromClause to set
	 */
	public Select setFromClause(String fromClause) {
		this.fromClause = fromClause;
		this.guesstimatedBufferSize += fromClause.length();
		return this;
	}

	public Select setFromClause(String tableName, String alias) {
		this.fromClause = tableName + ' ' + alias;
		this.guesstimatedBufferSize += fromClause.length();
		return this;
	}

	public Select setOrderByClause(String orderByClause) {
		this.orderByClause = orderByClause;
		this.guesstimatedBufferSize += orderByClause.length();
		return this;
	}

	public Select setGroupByClause(String groupByClause) {
		this.groupByClause = groupByClause;
		this.guesstimatedBufferSize += groupByClause.length();
		return this;
	}

	public Select setOuterJoins(String outerJoinsAfterFrom, String outerJoinsAfterWhere) {
		this.outerJoinsAfterFrom = outerJoinsAfterFrom;

		// strip off any leading 'and' token
		String tmpOuterJoinsAfterWhere = outerJoinsAfterWhere.trim();
		if (tmpOuterJoinsAfterWhere.startsWith("and")) {
			tmpOuterJoinsAfterWhere = tmpOuterJoinsAfterWhere.substring(4);
		}
		this.outerJoinsAfterWhere = tmpOuterJoinsAfterWhere;

		this.guesstimatedBufferSize += outerJoinsAfterFrom.length() + outerJoinsAfterWhere.length();
		return this;
	}

	/**
	 * Sets the selectClause.
	 * 
	 * @param selectClause
	 *            The selectClause to set
	 */
	public Select setSelectClause(String selectClause) {
		this.selectClause = selectClause;
		this.guesstimatedBufferSize += selectClause.length();
		return this;
	}

	/**
	 * Sets the whereClause.
	 * 
	 * @param whereClause
	 *            The whereClause to set
	 */
	public Select setWhereClause(String whereClause) {
		this.whereClause = whereClause;
		this.guesstimatedBufferSize += whereClause.length();
		return this;
	}

	public Select setComment(String comment) {
		this.comment = comment;
		this.guesstimatedBufferSize += comment.length();
		return this;
	}

}
