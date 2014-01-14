package org.cherry.persistence.event.spi;

import java.io.Serializable;
import java.util.Set;

import org.cherry.persistence.PersistenceException;


/**
 * Defines the contract for handling of deletion events generated from a session.
 *

 */
public interface DeleteEventListener extends Serializable {

    /** Handle the given delete event.
     *
     * @param event The delete event to be handled.
     * @throws PersistenceException
     */
	public void onDelete(DeleteEvent event) throws PersistenceException;

	public void onDelete(DeleteEvent event, Set transientEntities) throws PersistenceException;
}
