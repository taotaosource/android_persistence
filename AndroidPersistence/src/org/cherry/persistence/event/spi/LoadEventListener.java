package org.cherry.persistence.event.spi;

import org.cherry.persistence.PersistenceException;

public interface LoadEventListener {
	/** 
	 * Handle the given load event.
     *
     * @param event The load event to be handled.
     * @throws PersistenceException
     */
	public void onLoad(LoadEvent event) throws PersistenceException;
}
