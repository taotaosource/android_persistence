package org.cherry.persistence.event.spi;

import java.io.Serializable;

public abstract class AbstractEvent implements Serializable {

	private static final long serialVersionUID = 133139227783494607L;
	private final EventSource session;

    /**
     * Constructs an event from the given event session.
     *
     * @param source The session event source.
     */
	public AbstractEvent(EventSource source) {
		this.session = source;
	}

    /**
     * Returns the session event source for this event.  This is the underlying
     * session from which this event was generated.
     *
     * @return The session event source.
     */
	public final EventSource getSession() {
		return session;
	}

}
