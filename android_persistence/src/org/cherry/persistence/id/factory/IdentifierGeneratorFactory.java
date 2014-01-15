package org.cherry.persistence.id.factory;
import java.lang.reflect.Type;
import java.util.Properties;

import org.cherry.persistence.id.IdentifierGenerator;


/**
 * Contract for a <tt>factory</tt> of {@link IdentifierGenerator} instances.
 *

 */
public interface IdentifierGeneratorFactory {
	 


	/**
	 * Given a strategy, retrieve the appropriate identifier generator instance.
	 *
	 * @param strategy The generation strategy.
	 * @param type The mapping type for the identifier values.
	 * @param config Any configuraion properties given in the generator mapping.
	 *
	 * @return The appropriate generator instance.
	 */
	public IdentifierGenerator createIdentifierGenerator(String strategy, Type type, Properties config);

	/**
	 * Retrieve the class that will be used as the {@link IdentifierGenerator} for the given strategy.
	 *
	 * @param strategy The strategy
	 * @return The generator class.
	 */
	public Class getIdentifierGeneratorClass(String strategy);
	
}
