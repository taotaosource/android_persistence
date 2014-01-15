package org.cherry.persistence.cfg;

import java.lang.reflect.Field;

import org.cherry.persistence.MappingException;
import org.cherry.persistence.annotations.Table;
import org.cherry.persistence.annotations.Transient;
import org.cherry.persistence.internal.util.ReflectHelper;
import org.cherry.persistence.mapping.Column;
import org.cherry.persistence.mapping.IdentifierProperty;
import org.cherry.persistence.mapping.PersistentClass;
import org.cherry.persistence.mapping.Property;


@Table
public class AnnotationBinder {
	public static final void bindClass(Class<?> annotatedClass, Mappings mappings) throws MappingException {
		String table = ""; // might be no @Table annotation on the annotated
							// class
		if (annotatedClass.isAnnotationPresent(Table.class)) {
			Table tableAnn = annotatedClass.getAnnotation(Table.class);
			table = tableAnn.name();
		} else {
			table = annotatedClass.getSimpleName();
		}
		PersistentClass persistentClass = new PersistentClass(annotatedClass);
		EntityBinder entityBinder = new EntityBinder(annotatedClass, persistentClass, mappings);
		entityBinder.bindEntity();
		entityBinder.bindTable(table);

		mapClass(annotatedClass, persistentClass, entityBinder, mappings);

		mappings.addClass(persistentClass);
	}

	private static void mapClass(Class<?> annotatedClass, PersistentClass persistentClass, EntityBinder entityBinder, Mappings mappings) {
		Field[] fields = annotatedClass.getDeclaredFields();
		for (Field field : fields) {
			if (field.isAnnotationPresent(Transient.class) || ReflectHelper.isStatic(field)) {
				continue;
			}
			if (!field.isAccessible()) {
				field.setAccessible(true);
			}
			processElementAnnotations(field, persistentClass, entityBinder, mappings);
		}
		if (persistentClass.getPropertiesSpan() <= 0) {
			throw new MappingException(persistentClass.getEntityName() + " column is empty ");
		}
	}

	private static void processElementAnnotations(Field field, PersistentClass persistentClass, EntityBinder entityBinder, Mappings mappings)
			throws MappingException {
		Column column = entityBinder.fillColumn(field);
		Property property = null;
		if (column.isPrimaryKey()) {
			property = new IdentifierProperty(field, persistentClass);
			persistentClass.addProperty(0, property);
			((IdentifierProperty) property).setStrategy(column.getStrategy());
			persistentClass.setIdentifierProperty((IdentifierProperty) property);
		} else {
			property = new Property(field, persistentClass);
			persistentClass.addProperty(property);
		}
		property.setColumn(column);
		property.setName(field.getName());

		persistentClass.setTable(entityBinder.getTable());
	}
}
