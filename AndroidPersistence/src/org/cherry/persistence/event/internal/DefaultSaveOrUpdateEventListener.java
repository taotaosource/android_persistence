package org.cherry.persistence.event.internal;

import java.io.Serializable;

import org.cherry.persistence.PersistenceException;
import org.cherry.persistence.event.spi.EventSource;
import org.cherry.persistence.event.spi.SaveOrUpdateEvent;
import org.cherry.persistence.event.spi.SaveOrUpdateEventListener;
import org.cherry.persistence.id.IdentifierGeneratorHelper;
import org.cherry.persistence.persister.entity.EntityPersister;


public class DefaultSaveOrUpdateEventListener implements SaveOrUpdateEventListener {

	private static final long serialVersionUID = 4339994907922789390L;

	@Override
	public void onSaveOrUpdate(SaveOrUpdateEvent event) throws PersistenceException {
		Object object = event.getObject();
		EventSource session = event.getSession();
		EntityPersister persister = session.getEntityPersister(object.getClass().getName(), object);
		Object[] values = persister.getPropertyValues(object);
 		if (persister.hasIdentifierProperty()) {
			Serializable id = persister.getIdentifierGenerator().generate(session, object);
			if (id == IdentifierGeneratorHelper.POST_INSERT_INDICATOR) {
				values[0] = null;
				id = persister.insert(values, object, session);
			} else {
				values[0] = id;
				persister.insert(values, object, session);
			}
			persister.setIdentifier(object, id, session);
			event.setResultId(id);
		} else {
			persister.insert(values, object, session);
 		}
	}

}
