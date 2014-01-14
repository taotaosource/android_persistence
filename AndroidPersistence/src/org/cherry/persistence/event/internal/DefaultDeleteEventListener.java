package org.cherry.persistence.event.internal;

import java.io.Serializable;
import java.util.Set;

import org.cherry.persistence.PersistenceException;
import org.cherry.persistence.TransientObjectException;
import org.cherry.persistence.event.spi.DeleteEvent;
import org.cherry.persistence.event.spi.DeleteEventListener;
import org.cherry.persistence.event.spi.EventSource;
import org.cherry.persistence.persister.entity.EntityPersister;


public class DefaultDeleteEventListener implements DeleteEventListener {

	private static final long serialVersionUID = 4778975945047899695L;

	@Override
	public void onDelete(DeleteEvent event) throws PersistenceException {
		Object object = event.getObject();
		EventSource session = event.getSession();
		EntityPersister persister = session.getEntityPersister(object.getClass().getName(), object);
		Serializable id = persister.getIdentifier(object, session);
		if (id == null) {
			throw new TransientObjectException("the detached instance passed to delete() had a null identifier");
		}
		persister.delete(id, object, session);
	}

	@Override
	public void onDelete(DeleteEvent event, Set transientEntities) throws PersistenceException {

	}

}
