package org.cherry.persistence;

public class TransientObjectException extends PersistenceException {

	private static final long serialVersionUID = -2171919638471722994L;

	public TransientObjectException(String s) {
		super(s);
	}

}