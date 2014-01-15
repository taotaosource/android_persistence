package org.cherry.persistence;


/**
 * Thrown if can't instantiate an entity or component
 * class at runtime.
 */

public class InstantiationException extends PersistenceException {

	private static final long serialVersionUID = 4733012087305707474L;
	private final Class<?> clazz;

	public InstantiationException(String s, Class<?> clazz, Throwable root) {
		super(s, root);
		this.clazz = clazz;
	}

	public InstantiationException(String s, Class<?> clazz) {
		super(s);
		this.clazz = clazz;
	}

	public InstantiationException(String s, Class<?> clazz, Exception e) {
		super(s, e);
		this.clazz = clazz;
	}

	public Class<?> getPersistentClass() {
		return clazz;
	}

	public String getMessage() {
		return super.getMessage() + clazz.getName();
	}

}






