package org.cherry.persistence;

public class MappingException extends PersistenceException {

	private static final long serialVersionUID = 8297100340579950691L;

	public MappingException(String msg, Throwable root) {
		super(msg, root);
	}

	public MappingException(Throwable root) {
		super(root);
	}

	public MappingException(String s) {
		super(s);
	}
}