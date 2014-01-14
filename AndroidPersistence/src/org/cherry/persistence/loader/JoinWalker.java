package org.cherry.persistence.loader;

import org.cherry.persistence.MappingException;
import org.cherry.persistence.persister.entity.EntityPersister;
import org.cherry.persistence.sql.Select;

public class JoinWalker {
	private final EntityPersister persister;
	protected String sql;

	public JoinWalker(EntityPersister persister) {
		super();
		this.persister = persister;
	}

	protected void initStatementString(final String projection, final String condition, final String orderBy, final String groupBy)
			throws MappingException {
		Select select = new Select().setSelectClause(projection).setFromClause(persister.getTableName()).setWhereClause(condition)
				.setOrderByClause(orderBy);
		sql = select.toStatementString();
	}

	public String getSQLString() {
		return sql;
	}
}
