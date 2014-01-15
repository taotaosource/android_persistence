package org.cherry.persistence.internal;

import java.io.Serializable;
import java.util.List;

import org.cherry.persistence.Criteria;
import org.cherry.persistence.PersistenceException;
import org.cherry.persistence.Session;
import org.cherry.persistence.engine.spi.SessionImplementor;
import org.cherry.persistence.engine.sqlite.DatabaseCoordinator;
import org.cherry.persistence.event.internal.DefaultDeleteEventListener;
import org.cherry.persistence.event.internal.DefaultLoadEventListener;
import org.cherry.persistence.event.internal.DefaultSaveOrUpdateEventListener;
import org.cherry.persistence.event.internal.DefaultUpdateEventListener;
import org.cherry.persistence.event.spi.DeleteEvent;
import org.cherry.persistence.event.spi.EventSource;
import org.cherry.persistence.event.spi.LoadEvent;
import org.cherry.persistence.event.spi.SaveOrUpdateEvent;
import org.cherry.persistence.loader.criteria.CriteriaLoader;
import org.cherry.persistence.persister.entity.EntityPersister;


public class SessionImpl implements Session, SessionImplementor, EventSource {
	private static final long serialVersionUID = 8320090798608676384L;
	private DatabaseCoordinator databaseCoordinator;
	private SessionFactoryImpl factory;

	public SessionImpl(DatabaseCoordinator databaseCoordinator, SessionFactoryImpl sessionFactory) {
		this.databaseCoordinator = databaseCoordinator;
		this.factory = sessionFactory;
	}

	@Override
	public void beginTransaction() {
		databaseCoordinator.beginTransaction();
	}

	@Override
	public void setTransactionSuccessful() {
		databaseCoordinator.setTransactionSuccessful();
	}

	@Override
	public void endTransaction() {
		databaseCoordinator.endTransaction();
	}

	@Override
	public boolean inTransaction() {
		return databaseCoordinator.inTransaction();
	}

	@Override
	public Criteria createCriteria(Class<?> persistentClass) {
		return new CriteriaImpl(persistentClass.getName(), "", this);
	}

	@Override
	public Serializable save(Object object) {
		return fireSave(new SaveOrUpdateEvent(null, object, this));
	}

	private Serializable fireSave(SaveOrUpdateEvent event) {
		DefaultSaveOrUpdateEventListener listener = new DefaultSaveOrUpdateEventListener();
		listener.onSaveOrUpdate(event);
		return event.getResultId();
	}

	@Override
	public void update(Object object) {
		fireUpdate(new SaveOrUpdateEvent(null, object, this));
	}

	private void fireUpdate(SaveOrUpdateEvent event) {
		new DefaultUpdateEventListener().onSaveOrUpdate(event);
	}

	@Override
	public void delete(Object object) {
		fireDelete(new DeleteEvent(object, this));
	}

	private void fireDelete(DeleteEvent event) {
		new DefaultDeleteEventListener().onDelete(event);
	}

	@Override
	public <T> T get(Class<T> clazz, Serializable id) {
		return (T) fireLoad(new LoadEvent(id, clazz.getName(), this));
	}

	private Object fireLoad(LoadEvent event) {
		new DefaultLoadEventListener().onLoad(event);
		return event.getResult();
	}

	@Override
	public EntityPersister getEntityPersister(String entityName, Object object) throws PersistenceException {
		return factory.getEntityPersister(entityName);
	}

	@Override
	public Object getConnection() {
		return databaseCoordinator.getConnection();
	}

	@Override
	public DatabaseCoordinator getDatabaseCoordinator() {
		return databaseCoordinator;
	}

	public SessionFactoryImpl getFactory() {
		return factory;
	}

	@Override
	public List list(Criteria criteria) {
		CriteriaImpl criteriaImpl = (CriteriaImpl) criteria;
		String entityName = criteriaImpl.getEntityOrClassName();
		CriteriaLoader loader = new CriteriaLoader(factory.getEntityPersister(entityName), factory, criteriaImpl, entityName);
		return loader.list(this);
	}

	@Override
	public Object instantiate(String entityName, Serializable id) throws PersistenceException {
		return getFactory().getEntityPersister(entityName).instantiate(id, this);
	}

}
