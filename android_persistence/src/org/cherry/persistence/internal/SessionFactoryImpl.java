package org.cherry.persistence.internal;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.cherry.persistence.MappingException;
import org.cherry.persistence.Session;
import org.cherry.persistence.SessionFactory;
import org.cherry.persistence.cfg.Configuration;
import org.cherry.persistence.cfg.Settings;
import org.cherry.persistence.engine.spi.SessionFactoryImplementor;
import org.cherry.persistence.engine.sqlite.DatabaseCoordinator;
import org.cherry.persistence.id.factory.IdentifierGeneratorFactory;
import org.cherry.persistence.mapping.PersistentClass;
import org.cherry.persistence.persister.entity.EntityPersister;
import org.cherry.persistence.persister.entity.SingleTableEntityPersister;
import org.cherry.persistence.tool.SchemaExport;
import org.cherry.persistence.tool.SchemaUpate;


public class SessionFactoryImpl implements SessionFactory, SessionFactoryImplementor {
	private DatabaseCoordinator databaseCoordinator;
	private final transient Map<String, EntityPersister> entityPersisters;
	private static final long serialVersionUID = -4075136113787816143L;

	public SessionFactoryImpl(Configuration configuration, Settings settings, DatabaseCoordinator transactionCoordinator) {
		this.databaseCoordinator = transactionCoordinator;
		entityPersisters = new HashMap<String, EntityPersister>();
		Iterator<PersistentClass> classMappings = configuration.getClassMappings();
		IdentifierGeneratorFactory generatorFactory = configuration.getIdentifierGeneratorFactory();
		while (classMappings.hasNext()) {
			PersistentClass model = classMappings.next();
			entityPersisters.put(model.getEntityName(), new SingleTableEntityPersister(model, generatorFactory));
		}
		if (settings.isAutoCreateSchema()) {
			new SchemaExport(configuration).execute();
		}
		if (settings.isAutoUpdateSchema()) {
			new SchemaUpate(configuration).execute();
		}
	}

	@Override
	public Session openSession() {
		return new SessionImpl(databaseCoordinator, this);
	}

	@Override
	public EntityPersister getEntityPersister(String entityName) throws MappingException {
		EntityPersister result = entityPersisters.get(entityName);
		if (result == null) {
			throw new MappingException("Unknown entity: " + entityName);
		}
		return result;
	}

}
