package org.cherry.persistence.engine.spi;

import org.cherry.persistence.persister.entity.EntityPersister;

public class EntityEntry {
	private transient EntityPersister persister;
	
	public EntityEntry(EntityPersister persister) {
	}
}
