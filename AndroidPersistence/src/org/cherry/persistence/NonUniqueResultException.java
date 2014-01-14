package org.cherry.persistence;

/**
 * Thrown when the application calls <tt>Query.uniqueResult()</tt> and the query
 * returned more than one result. Unlike all other exceptions, this one is
 * recoverable!
 * 
 */
public class NonUniqueResultException extends PersistenceException {

	private static final long serialVersionUID = -1452421451181301915L;

	public NonUniqueResultException(int resultCount) {
		super("query did not return a unique result: " + resultCount);
	}

}
