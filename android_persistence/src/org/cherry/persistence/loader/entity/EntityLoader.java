package org.cherry.persistence.loader.entity;

import java.io.Serializable;
import java.util.List;

import org.cherry.persistence.PersistenceException;
import org.cherry.persistence.engine.spi.SessionImplementor;
import org.cherry.persistence.engine.sqlite.ResultResolver;
import org.cherry.persistence.loader.Loader;
import org.cherry.persistence.persister.entity.EntityJoinWalker;
import org.cherry.persistence.persister.entity.EntityPersister;


public class EntityLoader extends Loader implements UniqueEntityLoader {
	private EntityPersister persister;
	private EntityJoinWalker joinWalker;

	public EntityLoader(EntityPersister persister, String uniqueKey) {
		super(persister);
		this.persister = persister;
		joinWalker = new EntityJoinWalker(persister, uniqueKey);
	}

	@Override
	public String getSQLString() {
		return joinWalker.getSQLString();
	}

	@Override
	public Object load(Serializable id, SessionImplementor session) throws PersistenceException {
		List<?> list = loadEntity(session, id, null, persister);
		if (list.size() > 0) {
			return list.get(0);
		} else {
			return null;
		}
	}

	@Override
	public List<?> getResult(ResultResolver resultsResolver) {
		return resultsResolver.list(persister.getPropertyNames(), true);
	}
}
