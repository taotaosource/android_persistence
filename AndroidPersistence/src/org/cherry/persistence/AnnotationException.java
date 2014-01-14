package org.cherry.persistence;
public class AnnotationException extends MappingException {

	private static final long serialVersionUID = -8037315203847851515L;

	public AnnotationException(String msg, Throwable root) {
		super( msg, root );
	}

	public AnnotationException(Throwable root) {
		super( root );
	}

	public AnnotationException(String s) {
		super( s );
	}
}