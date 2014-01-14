package org.cherry.persistence.event.spi;

import java.io.Serializable;

import org.cherry.persistence.PersistenceException;


public interface SaveOrUpdateEventListener extends Serializable {

    /** 
     * Handle the given update event.
     *
     * @param event The update event to be handled.
     * @throws PersistenceException
     */
	public void onSaveOrUpdate(SaveOrUpdateEvent event) throws PersistenceException;

}
