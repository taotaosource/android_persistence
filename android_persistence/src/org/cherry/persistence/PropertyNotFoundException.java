package org.cherry.persistence;


/**
 * Indicates that an expected getter or setter method could not be
 * found on a class.
 *
  */
public class PropertyNotFoundException extends MappingException {

	private static final long serialVersionUID = 2684044882251876100L;

	public PropertyNotFoundException(String s) {
		super(s);
	}

}
