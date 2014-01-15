package org.cherry.persistence;

public class AssertionFailure extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public AssertionFailure(String s) {
		super(s);
	}

	public AssertionFailure(String s, Throwable t) {
		super(s, t);
	}
}
