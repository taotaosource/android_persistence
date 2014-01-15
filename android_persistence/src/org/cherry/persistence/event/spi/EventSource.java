package org.cherry.persistence.event.spi;

import org.cherry.persistence.Session;
import org.cherry.persistence.engine.spi.SessionImplementor;
 
public interface EventSource extends SessionImplementor, Session {
	
}
