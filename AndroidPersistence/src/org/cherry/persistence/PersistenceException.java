package org.cherry.persistence;


/**
 * The base {@link Throwable} type for Persistence.
 * 
 */
public class PersistenceException extends RuntimeException {
	/**
	 * 
	 */
	private static final long serialVersionUID = -7331181866524645991L;

	public PersistenceException(String message) {
		super(message);
	}

	public PersistenceException(Throwable root) {
		super(root);
	}

	public PersistenceException(String message, Throwable root) {
		super(message, root);
	}
}






