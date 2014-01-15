package org.cherry.persistence.event.internal;

import java.io.Serializable;

import org.cherry.persistence.PersistenceException;
import org.cherry.persistence.TransientObjectException;
import org.cherry.persistence.event.spi.EventSource;
import org.cherry.persistence.event.spi.SaveOrUpdateEvent;
import org.cherry.persistence.event.spi.SaveOrUpdateEventListener;
import org.cherry.persistence.persister.entity.EntityPersister;


public class DefaultUpdateEventListener implements SaveOrUpdateEventListener {

	private static final long serialVersionUID = -675000350080666107L;

	@Override
	public void onSaveOrUpdate(SaveOrUpdateEvent event) throws PersistenceException {
		Object object = event.getObject();
		EventSource session = event.getSession();
		EntityPersister persister = session.getEntityPersister(object.getClass().getName(), object);
		Object[] fields = persister.getPropertyValues(object);
		
		Serializable id = persister.getIdentifier(object, session);
		if (id == null) {
			throw new TransientObjectException("the detached instance passed to delete() had a null identifier");
		}
		persister.update(id, fields, object, session);
	}
}
