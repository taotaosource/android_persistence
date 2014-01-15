package org.cherry.persistence.mapping;

import java.util.ArrayList;

public class PersistentClass {
	private String entityName;
	private final Class<?> mappedClass;
	private Table table;
	private ArrayList<Property> properties = new ArrayList<Property>();
	private IdentifierProperty identifierProperty;

	public PersistentClass(Class<?> annotatedClass) {
		this.mappedClass = annotatedClass;
	}

	public ArrayList<Property> getProperties() {
		return properties;
	}

	public int getPropertiesSpan() {
		return properties.size();
	}

	public void addProperty(Property property) {
		properties.add(property);
	}

	public void addProperty(int index, Property property) {
		properties.add(index, property);
	}

	public String getEntityName() {
		return entityName;
	}

	public void setEntityName(String entityName) {
		this.entityName = entityName;
	}

	public Table getTable() {
		return table;
	}

	public void setTable(Table table) {
		this.table = table;
	}

	public IdentifierProperty getIdentifierProperty() {
		return identifierProperty;
	}

	public void setIdentifierProperty(IdentifierProperty identifierProperty) {
		this.identifierProperty = identifierProperty;
	}

	public Class<?> getMappedClass() {
		return mappedClass;
	}

}
