package org.cherry.persistence.loader.criteria;

import org.cherry.persistence.engine.spi.SessionFactoryImplementor;
import org.cherry.persistence.internal.CriteriaImpl;
import org.cherry.persistence.loader.JoinWalker;
import org.cherry.persistence.persister.entity.EntityPersister;

public class CriteriaJoinWalker extends JoinWalker {
	private final EntityPersister persister;

	public CriteriaJoinWalker(final EntityPersister persister, final CriteriaQueryTranslator translator,
			final SessionFactoryImplementor factory, final CriteriaImpl criteria, final String rootEntityName) {
		super(persister);
		this.persister = persister;
		initStatementString(persister.selectFragment(null, null), translator.getWhereCondition(), translator.getOrderBy(), null);
	}

}
