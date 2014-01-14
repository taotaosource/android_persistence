package org.cherry.persistence.tuple.entity;

import java.io.Serializable;
import java.util.ArrayList;

import org.cherry.persistence.InstantiationException;
import org.cherry.persistence.PersistenceException;
import org.cherry.persistence.PropertyNotFoundException;
import org.cherry.persistence.engine.spi.SessionImplementor;
import org.cherry.persistence.internal.util.ObjectConstructor;
import org.cherry.persistence.internal.util.ReflectHelper;
import org.cherry.persistence.mapping.PersistentClass;
import org.cherry.persistence.mapping.Property;
import org.cherry.persistence.property.Getter;
import org.cherry.persistence.property.Setter;


public class PojoEntityTuplizer implements Tuplizer {

	private final Getter[] getters;
	private final Setter[] setters;
	private Getter idGetter;
	private Setter idSetter;

	private transient ObjectConstructor<?> constructor;
	private Class<?> mappedClass;

	public PojoEntityTuplizer(PersistentClass persistentClass) {
		mappedClass = persistentClass.getMappedClass();

		ArrayList<Property> properties = persistentClass.getProperties();
		int length = properties.size();
		this.getters = new Getter[length];
		this.setters = new Setter[length];
		Property property;
		for (int i = 0; i < length; i++) {
			property = properties.get(i);
			getters[i] = property.getGetter();
			setters[i] = property.getSetter();
		}
		Property identifierProperty = persistentClass.getIdentifierProperty();
		if (identifierProperty != null) {
			idGetter = getters[0];
			idSetter = setters[0];
		}

		try {
			constructor = ReflectHelper.getDefaultConstructor(mappedClass);
		} catch (PropertyNotFoundException pnfe) {
			constructor = null;
		}

	}

	@Override
	public Object[] getPropertyValues(Object entity) {
		int span = getters.length;
		final Object[] result = new Object[span];
		for (int j = 0; j < span; j++) {
			result[j] = getters[j].get(entity);
		}
		return result;
	}

	@Override
	public void setPropertyValues(Object entity, Object[] values) {
		int span = setters.length;
		for (int j = 0; j < span; j++) {
			setters[j].set(entity, values[j]);
		}
	}

	@Override
	public Serializable getIdentifier(Object entity, SessionImplementor session) {
		if (idGetter != null) {
			return (Serializable) idGetter.get(entity);
		}
		return null;
	}

	@Override
	public void setIdentifier(Object entity, Serializable id, SessionImplementor session) {
		if (idSetter != null) {
			idSetter.set(entity, id);
		}
	}

	@Override
	public Object getPropertyValue(Object entity, int i) {
		return getters[i].get(entity);
	}

	@Override
	public void setPropertyValue(Object entity, int i, Object value) throws PersistenceException {
		setters[i].set(entity, value);
	}

	@Override
	public Getter getGetter(int i) {
		return getters[i];
	}

	@Override
	public Object instantiate() {
		return instantiate(null, null);
	}

	@Override
	public Object instantiate(Serializable id, SessionImplementor session) {
		Object object = newInstance();
		if (id != null && idSetter != null) {
			idSetter.set(object, id);
		}
		return object;
	}

	private Object newInstance() {
		try {
			return constructor.construct();
		} catch (Exception e) {
			throw new InstantiationException("NewInstance Exception ", mappedClass, e);
		}
	}
}
