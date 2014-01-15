package org.cherry.persistence.event.internal;

import org.cherry.persistence.PersistenceException;
import org.cherry.persistence.event.spi.LoadEvent;
import org.cherry.persistence.event.spi.LoadEventListener;
import org.cherry.persistence.persister.entity.EntityPersister;

public class DefaultLoadEventListener implements LoadEventListener{

	@Override
	public void onLoad(LoadEvent event) throws PersistenceException {
		 EntityPersister persister = event.getSession().getEntityPersister(event.getEntityClassName(), null);
		 Object object = persister.load( event.getEntityId(), null, event.getSession());
		 event.setResult(object);
	}
}
