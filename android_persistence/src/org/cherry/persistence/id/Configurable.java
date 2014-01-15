package org.cherry.persistence.id;
import java.lang.reflect.Type;
import java.util.Properties;

import org.cherry.persistence.MappingException;


/**
 * An <tt>IdentifierGenerator</tt> that supports "configuration".
 *
 * @see IdentifierGenerator
 * 
 */
public interface Configurable {

	/**
	 * Configure this instance, given the value of parameters
	 * specified by the user as <tt>&lt;param&gt;</tt> elements.
	 * This method is called just once, following instantiation.
	 *
	 * @param params param values, keyed by parameter name
	 */
	public void configure(Type type, Properties params) throws MappingException;

}
