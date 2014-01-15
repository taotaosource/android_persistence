package org.cherry.persistence.event.spi;

import java.io.Serializable;

/**
 * Defines an event class for the loading of an entity.
 * 

 */
public class LoadEvent extends AbstractEvent {

	private Serializable entityId;
	private String entityClassName;
	private Object instanceToLoad;
	private boolean isAssociationFetch;
	private Object result;

	public LoadEvent(Serializable entityId, String entityClassName, EventSource source) {
		this(entityId, entityClassName, null, false, source);
	}

	private LoadEvent(Serializable entityId, String entityClassName, Object instanceToLoad, boolean isAssociationFetch, EventSource source) {

		super(source);

		this.entityId = entityId;
		this.entityClassName = entityClassName;
		this.instanceToLoad = instanceToLoad;
		this.isAssociationFetch = isAssociationFetch;
	}

	public Serializable getEntityId() {
		return entityId;
	}

	public void setEntityId(Serializable entityId) {
		this.entityId = entityId;
	}

	public String getEntityClassName() {
		return entityClassName;
	}

	public void setEntityClassName(String entityClassName) {
		this.entityClassName = entityClassName;
	}

	public Object getInstanceToLoad() {
		return instanceToLoad;
	}

	public void setInstanceToLoad(Object instanceToLoad) {
		this.instanceToLoad = instanceToLoad;
	}

	public Object getResult() {
		return result;
	}

	public void setResult(Object result) {
		this.result = result;
	}
}
