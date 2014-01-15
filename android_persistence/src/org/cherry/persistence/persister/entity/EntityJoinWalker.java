package org.cherry.persistence.persister.entity;

import org.cherry.persistence.loader.JoinWalker;

public class EntityJoinWalker extends JoinWalker {

	public EntityJoinWalker(EntityPersister persister, String uniqueKey) {
		super(persister);
		initStatementString(persister.selectFragment(null, null), uniqueKey.concat("=?"), "", null);
	}
}
