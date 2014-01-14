package org.cherry.persistence.engine.spi;

import org.cherry.persistence.MappingException;
import org.cherry.persistence.SessionFactory;
import org.cherry.persistence.persister.entity.EntityPersister;

public interface SessionFactoryImplementor extends SessionFactory {
	/**
	 * Get the persister for the named entity
	 * 
	 * @param entityName
	 *            The name of the entity for which to retrieve the persister.
	 * @return The persister
	 * @throws MappingException
	 *             Indicates persister could not be found with that name.
	 */
	public EntityPersister getEntityPersister(String entityName) throws MappingException;

}
