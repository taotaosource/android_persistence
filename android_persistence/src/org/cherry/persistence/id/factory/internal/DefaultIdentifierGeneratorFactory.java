package org.cherry.persistence.id.factory.internal;

import java.io.Serializable;
import java.lang.reflect.Type;
import java.util.Properties;
import java.util.concurrent.ConcurrentHashMap;

import org.cherry.persistence.MappingException;
import org.cherry.persistence.id.Assigned;
import org.cherry.persistence.id.Configurable;
import org.cherry.persistence.id.IdentifierGenerator;
import org.cherry.persistence.id.IdentityGenerator;
import org.cherry.persistence.id.UUIDGenerator;
import org.cherry.persistence.id.factory.IdentifierGeneratorFactory;


/**
 * Basic <tt>templated</tt> support for
 * {@link IdentifierGeneratorFactory} implementations.
 * 

 */
public class DefaultIdentifierGeneratorFactory implements IdentifierGeneratorFactory, Serializable {

	private ConcurrentHashMap<String, Class> generatorStrategyToClassNameMap = new ConcurrentHashMap<String, Class>();

	/**
	 * Constructs a new DefaultIdentifierGeneratorFactory.
	 */
	public DefaultIdentifierGeneratorFactory() {
		register("uuid", UUIDGenerator.class); // "deprecated" for new use
		register("assigned", Assigned.class);
		register("identity", IdentityGenerator.class);
	}

	public void register(String strategy, Class generatorClass) {
		generatorStrategyToClassNameMap.put(strategy, generatorClass);
	}

	@Override
	public IdentifierGenerator createIdentifierGenerator(String strategy, Type type, Properties config) {
		try {
			Class clazz = getIdentifierGeneratorClass(strategy);
			IdentifierGenerator identifierGenerator = (IdentifierGenerator) clazz.newInstance();
			if (identifierGenerator instanceof Configurable) {
				((Configurable) identifierGenerator).configure(type, config);
			}
			return identifierGenerator;
		} catch (Exception e) {
			final String entityName = config.getProperty(IdentifierGenerator.ENTITY_NAME);
			throw new MappingException(String.format("Could not instantiate id generator [entity-name=%s]", entityName), e);
		}
	}

	@Override
	public Class getIdentifierGeneratorClass(String strategy) {

		Class generatorClass = generatorStrategyToClassNameMap.get(strategy);
		try {
			if (generatorClass == null) {
				generatorClass = Class.forName(strategy);
			}
		} catch (ClassNotFoundException e) {
			throw new MappingException(String.format("Could not interpret id generator strategy [%s]", strategy));
		}
		return generatorClass;
	}

}
