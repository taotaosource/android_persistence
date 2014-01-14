package org.cherry.persistence.engine.spi;

import java.io.Serializable;
import java.lang.reflect.Type;

public final class TypedValue implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -5735921485186536430L;
	private final Type type;
	private final Object value;

	public TypedValue(Type type, Object value) {
		super();
		this.type = type;
		this.value = value;
	}

	public Type getType() {
		return type;
	}

	public Object getValue() {
		return value;
	}

}