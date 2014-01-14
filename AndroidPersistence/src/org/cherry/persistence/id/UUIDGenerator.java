package org.cherry.persistence.id;

import java.io.Serializable;
import java.util.UUID;

import org.cherry.persistence.PersistenceException;
import org.cherry.persistence.engine.spi.SessionImplementor;


public class UUIDGenerator implements IdentifierGenerator {
	
	@Override
	public Serializable generate(SessionImplementor session, Object object) throws PersistenceException {
		return UUID.randomUUID().toString();
	}

}
