package org.cherry.persistence.persister.entity;

import java.lang.reflect.Type;

import org.cherry.persistence.QueryException;


public interface PropertyMapping {
 
	/**
	 * Given a property path, return the corresponding column name(s).
	 */
	public String toColumn(String propertyName) throws QueryException;

	/**
	 * Given a component path expression, get the type of the property
	 */
	public Type toType(String propertyName) throws QueryException;
	
	public Type[] getPropertiesTypes();
}
