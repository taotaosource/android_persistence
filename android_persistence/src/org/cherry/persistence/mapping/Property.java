package org.cherry.persistence.mapping;

import java.lang.reflect.Field;

import org.cherry.persistence.property.BasicGetter;
import org.cherry.persistence.property.BasicSetter;
import org.cherry.persistence.property.Getter;
import org.cherry.persistence.property.Setter;


public class Property {
	private String name;
	private boolean updateable = true;
	private boolean insertable = true;
	private boolean selectable = true;

	private PersistentClass persistentClass;
	private Field field;
	private Column column;

	public Property(Field field, PersistentClass persistentClass) {
		this.field = field;
	}

	public Getter getGetter() {
		return new BasicGetter(field);
	}

	public Setter getSetter() {
		return new BasicSetter(field);
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public boolean isUpdateable() {
		return updateable;
	}

	public void setUpdateable(boolean updateable) {
		this.updateable = updateable;
	}

	public boolean isInsertable() {
		return insertable;
	}

	public void setInsertable(boolean insertable) {
		this.insertable = insertable;
	}

	public boolean isSelectable() {
		return selectable;
	}

	public void setSelectable(boolean selectable) {
		this.selectable = selectable;
	}

	public PersistentClass getPersistentClass() {
		return persistentClass;
	}

	public void setPersistentClass(PersistentClass persistentClass) {
		this.persistentClass = persistentClass;
	}

	public Field getField() {
		return field;
	}

	public void setField(Field field) {
		this.field = field;
	}

	public Column getColumn() {
		return column;
	}

	public void setColumn(Column column) {
		this.column = column;
	}
}
