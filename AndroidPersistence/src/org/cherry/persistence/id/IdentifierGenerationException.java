package org.cherry.persistence.id;
import org.cherry.persistence.PersistenceException;

/**
 * Thrown by <tt>IdentifierGenerator</tt> implementation class when
 * ID generation fails.
 *
 * @see IdentifierGenerator
 * 
 */

public class IdentifierGenerationException extends PersistenceException {

	private static final long serialVersionUID = 6517250661168265545L;

	public IdentifierGenerationException(String msg) {
		super(msg);
	}

	public IdentifierGenerationException(String msg, Throwable t) {
		super(msg, t);
	}

}