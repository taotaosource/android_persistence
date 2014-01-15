package org.cherry.persistence.loader.criteria;

import java.util.List;

import org.cherry.persistence.PersistenceException;
import org.cherry.persistence.engine.spi.SessionFactoryImplementor;
import org.cherry.persistence.engine.spi.SessionImplementor;
import org.cherry.persistence.engine.sqlite.ResultResolver;
import org.cherry.persistence.internal.CriteriaImpl;
import org.cherry.persistence.loader.Loader;
import org.cherry.persistence.persister.entity.EntityPersister;


public class CriteriaLoader extends Loader {
	private final EntityPersister persister;
	private final CriteriaQueryTranslator translator;
	protected String sql;

	public CriteriaLoader(final EntityPersister persister, final SessionFactoryImplementor factory, final CriteriaImpl criteria,
			final String rootEntityName) throws PersistenceException {
		super(persister);
 		this.persister = persister;
		this.translator = new CriteriaQueryTranslator(factory, criteria, rootEntityName);
		CriteriaJoinWalker criteriaJoinWalker = new CriteriaJoinWalker(persister, translator, factory, criteria, rootEntityName);
		sql = criteriaJoinWalker.getSQLString();
	}

 

	public List<?> list(SessionImplementor session) throws PersistenceException {
 		return doQueryAndInitializeNonLazyCollections(session, translator.getQueryParameters());
	}

	@Override
	public String getSQLString() {
 		return sql;
	}



	@Override
	public List<?> getResult(ResultResolver resultsResolver) {
		return resultsResolver.list(persister.getPropertyNames(), true);
	}
}
