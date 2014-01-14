package org.cherry.persistence.cfg;

import java.lang.reflect.Field;

import org.cherry.persistence.MappingException;
import org.cherry.persistence.TypeResolver;
import org.cherry.persistence.annotations.GeneratedValue;
import org.cherry.persistence.annotations.GenerationType;
import org.cherry.persistence.annotations.Id;
import org.cherry.persistence.internal.util.StringHelper;
import org.cherry.persistence.mapping.Column;
import org.cherry.persistence.mapping.PersistentClass;
import org.cherry.persistence.mapping.PrimaryKey;
import org.cherry.persistence.mapping.Table;


public class EntityBinder {
	private Class<?> annotatedClass;
	private PersistentClass persistentClass;
	private Mappings mappings;
	private TypeResolver typeResolver;
	private Table table;

	public EntityBinder(Class<?> annotatedClass, PersistentClass persistentClass, Mappings mappings) {
		this.annotatedClass = annotatedClass;
		this.mappings = mappings;
		this.persistentClass = persistentClass;
		typeResolver = mappings.getTypeResolver();
	}

	public void bindEntity() {
		persistentClass.setEntityName(annotatedClass.getName());
	}

	public void bindTable(String name) {
		table = TableBinder.buildAndFillTable(annotatedClass.getName(), name, mappings);
		persistentClass.setTable(table);
	}

	public Column fillColumn(Field field) {
		Column column = new Column();
		String name = "";
		if (field.isAnnotationPresent(org.cherry.persistence.annotations.Column.class)) {
			org.cherry.persistence.annotations.Column columnAnn = field
					.getAnnotation(org.cherry.persistence.annotations.Column.class);
			column.setLength(columnAnn.length());
			column.setNullable(columnAnn.nullable());
			column.setUnique(columnAnn.unique());
			name = columnAnn.name();
		}  
		if (StringHelper.isEmpty(name)) {
			name = field.getName();
		}
	 
		Class<?> type = field.getType();
		String sqlType = typeResolver.getSqlType(type);
		if (sqlType == null) {
			throw new MappingException(annotatedClass.getName() + ": " + field.getName() +  " field is not supported data types , must be set @Transient");
		}
		column.setSqlType(sqlType);
		column.setType(type);
		column.setName(name);
		if (field.isAnnotationPresent(Id.class)) {
			column.setPrimaryKey(true);
			PrimaryKey primaryKey = new PrimaryKey();
			primaryKey.setName(column.getName());
			table.setPrimaryKey(primaryKey);
			if (field.isAnnotationPresent(GeneratedValue.class)) {
				setStrategy(field, column, name, type);
			}
		}
		table.addColumn(column);
		return column;
	}

	private void setStrategy(Field field, Column column, String name, Class<?> type) {
		GeneratedValue annotation = field.getAnnotation(GeneratedValue.class);
		GenerationType strategy = annotation.strategy();
		String strategyStr = null;
		if (strategy.equals(GenerationType.AUTO)) {
			if (type == int.class || type == Integer.class) {
				table.setIdentifier(name);
				strategyStr = "identity";
			} else {
				throw new MappingException(annotatedClass.getName() + " GenerationType.AUTO value type must be int or Integer ");
			}
		} else if (strategy.equals(GenerationType.UUID)) {
			if (type != String.class) {
				throw new MappingException(annotatedClass.getName() + " GenerationType.UUID value type must be String");
			}
			strategyStr = "uuid";
		}
		column.setStrategy(strategyStr);
	}

	public Table getTable() {
		return table;
	}

}
