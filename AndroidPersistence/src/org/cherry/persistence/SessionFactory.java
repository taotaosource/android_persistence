package org.cherry.persistence;

import java.io.Serializable;

import org.cherry.persistence.cfg.Configuration;
 
/**
 * The main contract here is the creation of {@link Session} instances.  Usually
 * an application has a single {@link SessionFactory} instance and threads
 * servicing client requests obtain {@link Session} instances from this factory.
 * <p/>
 * The internal state of a {@link SessionFactory} is immutable.  Once it is created
 * this internal state is set.  This internal state includes all of the metadata
 * about Object/Relational Mapping.
 * <p/>
 * Implementors <strong>must</strong> be threadsafe.
 *
 * @see Configuration
 *
   */
public interface SessionFactory extends Serializable {

  

	/**
	 * Open a new  session 
	 *
	 * @return The created  session.
	 */
	public Session openSession();
}
