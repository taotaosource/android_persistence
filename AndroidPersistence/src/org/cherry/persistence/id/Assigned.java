package org.cherry.persistence.id;
import java.io.Serializable;
import java.lang.reflect.Type;
import java.util.Properties;

import org.cherry.persistence.MappingException;
import org.cherry.persistence.PersistenceException;
import org.cherry.persistence.engine.spi.SessionImplementor;


/**
 * <b>assigned</b><br>
 * <br>
 * An <tt>IdentifierGenerator</tt> that returns the current identifier assigned
 * to an instance.
 *
 * 
 */

public class Assigned implements IdentifierGenerator, Configurable {
	
	private String entityName;

	public Serializable generate(SessionImplementor session, Object obj) throws PersistenceException {
		//TODO: cache the persister, this shows up in yourkit
		final Serializable id = session.getEntityPersister( entityName, obj ).getIdentifier( obj, session );
		if ( id == null ) {
			throw new IdentifierGenerationException("ids for this class must be manually assigned before calling save(): " + entityName);
		}
		
		return id;
	}

	public void configure(Type type, Properties params) throws MappingException {
		entityName = params.getProperty(ENTITY_NAME);
		if ( entityName == null ) {
			throw new MappingException("no entity name");
		}
	}
}






