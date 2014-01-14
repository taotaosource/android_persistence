package org.cherry.persistence.persister.entity;

import java.io.Serializable;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Properties;

import org.cherry.persistence.MappingException;
import org.cherry.persistence.PersistenceException;
import org.cherry.persistence.QueryException;
import org.cherry.persistence.engine.spi.SessionImplementor;
import org.cherry.persistence.id.IdentifierGenerator;
import org.cherry.persistence.id.factory.IdentifierGeneratorFactory;
import org.cherry.persistence.loader.entity.EntityLoader;
import org.cherry.persistence.loader.entity.UniqueEntityLoader;
import org.cherry.persistence.log.Logger;
import org.cherry.persistence.mapping.Column;
import org.cherry.persistence.mapping.IdentifierProperty;
import org.cherry.persistence.mapping.PersistentClass;
import org.cherry.persistence.mapping.Property;
import org.cherry.persistence.sql.Delete;
import org.cherry.persistence.sql.Insert;
import org.cherry.persistence.sql.SelectFragment;
import org.cherry.persistence.sql.Update;
import org.cherry.persistence.tuple.entity.PojoEntityTuplizer;
import org.cherry.persistence.tuple.entity.Tuplizer;


public class SingleTableEntityPersister implements EntityPersister, PropertyMapping {
	private final PersistentClass persistentClass;
	private UniqueEntityLoader uniqueEntityLoader;
	private final Tuplizer tuplizer;
	private final String tableName;
	private final String[] propertyColumnNames;
	private final String[] propertyNames;
	private final Type[] types;
	private final HashMap<String, String> propertyMapColumn = new HashMap<String, String>();
	private final HashMap<String, Type> propertyMapType = new HashMap<String, Type>();
	private IdentifierGenerator identifierGenerator;

	public SingleTableEntityPersister(final PersistentClass persistentClass, IdentifierGeneratorFactory generatorFactory) {
		this.persistentClass = persistentClass;
		tuplizer = new PojoEntityTuplizer(persistentClass);
		tableName = persistentClass.getTable().getName();
		int span = persistentClass.getPropertiesSpan();
		propertyColumnNames = new String[span];
		propertyNames = new String[span];
		types = new Type[span];
		ArrayList<Property> properties = persistentClass.getProperties();

		for (int i = 0; i < span; i++) {
			Property property = properties.get(i);
			String propertyName = property.getName();
			Column column = property.getColumn();
			String columnName = column.getName();
			propertyColumnNames[i] = columnName;
			propertyNames[i] = propertyName;
			types[i] = column.getType();
			propertyMapColumn.put(propertyName, columnName);
			propertyMapType.put(propertyName, column.getType());
		}

		// create identifierGenerator
		IdentifierProperty identifierProperty = persistentClass.getIdentifierProperty();
		if (identifierProperty != null) {
			Properties config = new Properties();
			config.put(IdentifierGenerator.ENTITY_NAME, persistentClass.getEntityName());
			identifierGenerator = generatorFactory.createIdentifierGenerator(identifierProperty.getStrategy(), types[0], config);
		}
	}

	@Override
	public void setPropertyValues(Object object, Object[] values) {
		getTuplizer().setPropertyValues(object, values);
	}

	@Override
	public void setPropertyValue(Object entity, int i, Object value) throws PersistenceException {
		getTuplizer().setPropertyValue(entity, i, value);
	}

	@Override
	public Object[] getPropertyValues(Object object) {
		return getTuplizer().getPropertyValues(object);
	}

	@Override
	public Tuplizer getTuplizer() {
		return tuplizer;
	}

	@Override
	public String getTableName() {
		return tableName;
	}

	@Override
	public Serializable insert(Object[] fields, Object object, SessionImplementor session) throws PersistenceException {
		// boolean[] notNull = getPropertiesToInsert(fields);
		// String sql = generateInsertString(notNull);
		// Log.d("SingleTableEntityPersister", " insert Statement : " + sql);
		HashMap<String, Object> values = new HashMap<String, Object>();
		int len = propertyColumnNames.length;
		boolean empty = true;
		for (int i = 0; i < len; i++) {
			if (fields[i] != null) {
				values.put(propertyColumnNames[i], fields[i]);
				empty = false;
			}
		}
		if (empty) {
			if (hasIdentifierProperty()) {
				values.put(propertyColumnNames[0], null);
			}
		}
		return session.getDatabaseCoordinator().insert(tableName, values);
	}

	protected String generateInsertString(boolean[] notNull) {
		Insert insert = new Insert();
		insert.setTableName(tableName);
		int len = propertyColumnNames.length;
		for (int i = 0; i < len; i++) {
			if (notNull[i]) {
				insert.addColumn(propertyColumnNames[i]);
			}
		}
		return insert.toStatementString();
	}

	protected boolean[] getPropertiesToInsert(Object[] fields) {
		boolean[] notNull = new boolean[fields.length];
		for (int i = 0; i < fields.length; i++) {
			notNull[i] = fields[i] != null;
		}
		return notNull;
	}

	@Override
	public void delete(Serializable id, Object object, SessionImplementor session) throws PersistenceException {
		String sql = generateDeleteString();
		session.getDatabaseCoordinator().execSQL(sql, new Object[] { id });
		Logger.d("SingleTableEntityPersister", " delete Statement : " + sql);
	}

	private String generateDeleteString() {
		Delete delete = new Delete();
		delete.setTableName(tableName);
		delete.addPrimaryKeyColumn(propertyColumnNames[0]);
		return delete.toStatementString();
	}

	@Override
	public void update(Serializable id, Object[] fields, Object object, SessionImplementor session) throws PersistenceException {
		fields[0] = null;
		Update update = new Update();
		update.setTableName(tableName);
		update.addPrimaryKeyColumn(propertyColumnNames[0], "?");
		int len = propertyColumnNames.length;
		ArrayList<Object> values = new ArrayList<Object>();
		for (int i = 0; i < len; i++) {
			if (fields[i] != null) {
				update.addColumn(propertyColumnNames[i]);
				values.add(fields[i]);
			}
		}
		String sql = update.toStatementString();
		values.add(id);
		session.getDatabaseCoordinator().execSQL(sql, values.toArray());
		Logger.d("SingleTableEntityPersister", " update Statement : " + sql);
	}

	@Override
	public void setIdentifier(Object entity, Serializable id, SessionImplementor session) {
		getTuplizer().setIdentifier(entity, id, session);
	}

	@Override
	public Serializable getIdentifier(Object entity, SessionImplementor session) {
		return getTuplizer().getIdentifier(entity, session);
	}

	@Override
	public String getIdentifierColumnName() {
		Property identifierProperty = persistentClass.getIdentifierProperty();
		if (identifierProperty == null) {
			throw new MappingException("unknown identifierProperty Table : " + tableName);
		}
		return propertyColumnNames[0];
	}

	@Override
	public String[] getPropertyNames() {
		return propertyNames;
	}

	@Override
	public String selectFragment(String alias, String suffix) {
		SelectFragment fragment = new SelectFragment(propertyColumnNames);
		return fragment.toFragmentString();
	}

	@Override
	public Object load(Serializable id, Object optionalObject, SessionImplementor session) throws PersistenceException {
		if (uniqueEntityLoader == null) {
			uniqueEntityLoader = new EntityLoader(this, getIdentifierColumnName());
		}
		return uniqueEntityLoader.load(id, session);
	}

	@Override
	public Object instantiate(Serializable id, SessionImplementor session) {
		return getTuplizer().instantiate(id, session);
	}

	@Override
	public String toColumn(String propertyName) throws QueryException {
		String column = propertyMapColumn.get(propertyName);
		if (column == null) {
			throw new MappingException("unknown property: " + propertyName);
		}
		return column;
	}

	@Override
	public Type toType(String propertyName) throws QueryException {
		Type type = propertyMapType.get(propertyName);
		if (type == null) {
			throw new MappingException("unknown property: " + propertyName);
		}
		return type;
	}

	@Override
	public Type getIdentifierType() {
		Property identifierProperty = persistentClass.getIdentifierProperty();
		if (identifierProperty == null) {
			throw new MappingException("unknown identifierProperty Table : " + tableName);
		}
		return propertyMapType.get(identifierProperty.getName());
	}

	@Override
	public String getEntityName() {
		return persistentClass.getEntityName();
	}

	@Override
	public Type[] getPropertiesTypes() {
		return types;
	}

	@Override
	public IdentifierGenerator getIdentifierGenerator() {
		return identifierGenerator;
	}

	@Override
	public boolean hasIdentifierProperty() {
		return persistentClass.getIdentifierProperty() != null;
	}

}
