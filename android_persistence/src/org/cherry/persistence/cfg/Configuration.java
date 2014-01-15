package org.cherry.persistence.cfg;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.cherry.persistence.PersistenceException;
import org.cherry.persistence.SessionFactory;
import org.cherry.persistence.TypeResolver;
import org.cherry.persistence.engine.sqlite.DatabaseCoordinator;
import org.cherry.persistence.engine.sqlite.DatabaseMetadata;
import org.cherry.persistence.id.factory.IdentifierGeneratorFactory;
import org.cherry.persistence.id.factory.internal.DefaultIdentifierGeneratorFactory;
import org.cherry.persistence.internal.SessionFactoryImpl;
import org.cherry.persistence.mapping.PersistentClass;
import org.cherry.persistence.mapping.Table;

import android.database.sqlite.SQLiteDatabase;


public class Configuration {

	private DatabaseCoordinator databaseCoordinator;
	private TypeResolver typeResolver = new TypeResolver();

	private IdentifierGeneratorFactory identifierGeneratorFactory;
	private List<Class<?>> classes = new ArrayList<Class<?>>();
	protected Map<String, Table> tables = new HashMap<String, Table>();
	private HashMap<String, PersistentClass> persistentClasses = new HashMap<String, PersistentClass>();
	private Settings settings;
	
	public Configuration() {
		identifierGeneratorFactory = new DefaultIdentifierGeneratorFactory();
		settings = buildSettings();
	}

	/**
	 * Read metadata from the annotations associated with this class.
	 * 
	 * @param annotatedClass
	 *            The class containing annotations
	 * 
	 * @return this (for method chaining)
	 */
	public Configuration addAnnotatedClass(Class<?> annotatedClass) {
		classes.add(annotatedClass);
		return this;
	}

	public Configuration setSQLiteDatabase(SQLiteDatabase sqLiteDatabase) {
		databaseCoordinator = new DatabaseCoordinator(sqLiteDatabase);
		return this;
	}

	/**
	 * Create a {@link SessionFactory} using the properties and mappings in this
	 * configuration. The {@link SessionFactory} will be immutable, so changes
	 * made to {@code this} {@link Configuration} after building the
	 * {@link SessionFactory} will not affect it.
	 * 
	 * @return The build {@link SessionFactory}
	 * 
	 * @throws PersistenceException
	 *             usually indicates an invalid configuration or invalid mapping
	 *             information
	 * 
	 */
	public SessionFactory buildSessionFactory() throws PersistenceException {
		buildMappings();
		return new SessionFactoryImpl(this, settings, databaseCoordinator);
	}

	private void buildMappings() {
		processAnnotatedClasses();
	}

	private void processAnnotatedClasses() {
		Mappings mappings = createMappings();
		for (Class<?> clazz : classes) {
			AnnotationBinder.bindClass(clazz, mappings);
		}
	}

	private Mappings createMappings() {
		return new MappingsImpl();
	}

	private Settings buildSettings() {
		Settings settings = new Settings();
		settings.setAutoUpdateSchema(true);
		return settings;
	}

	public Iterator<PersistentClass> getClassMappings() {
		return persistentClasses.values().iterator();
	}

	public String[] generateSchemaCreationScript() {
		ArrayList<String> result = new ArrayList<String>();
		Collection<Table> values = tables.values();
		for (Table table : values) {
			result.add(table.sqlDropString());
			result.add(table.sqlCreateString());
		}
		return result.toArray(new String[] {});
	}

	public DatabaseCoordinator getDatabaseCoordinator() {
		return databaseCoordinator;
	}
	
	public Settings getSettings() {
		return settings;
	}

	/**
	 * Retrieve the IdentifierGeneratorFactory in effect for this configuration.
	 *
	 * @return This configuration's IdentifierGeneratorFactory.
	 */
	public IdentifierGeneratorFactory getIdentifierGeneratorFactory() {
		return identifierGeneratorFactory;
	}
	

	public List<String> generateSchemaUpdateScriptList(DatabaseMetadata databaseMetadata) {
		ArrayList<String> scripts = new ArrayList<String>();
		Collection<Table> values = tables.values();
		List<Table> dataBaseTables = databaseMetadata.getTables();
		for (Table table : values) {
			int indexOf = dataBaseTables.indexOf(table);
			if (indexOf != -1) {
				List<String> alterStrings = table.sqlAlterStrings(dataBaseTables.get(indexOf));
				scripts.addAll(alterStrings);
				dataBaseTables.remove(indexOf);
			} else {
				scripts.add(table.sqlCreateString());
			}
		}
		for (Table table : dataBaseTables) {
			scripts.add(table.sqlDropString());
		}
		return scripts;
	}

	private class MappingsImpl implements Mappings {

		@Override
		public void addTable(String name, Table table) {
			tables.put(name, table);
		}

		@Override
		public TypeResolver getTypeResolver() {
			return typeResolver;
		}

		@Override
		public void addClass(PersistentClass persistentClass) {
			persistentClasses.put(persistentClass.getEntityName(), persistentClass);
		}
	}
}
