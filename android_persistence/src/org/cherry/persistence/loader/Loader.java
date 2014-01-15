package org.cherry.persistence.loader;

import java.lang.reflect.Type;
import java.util.Arrays;
import java.util.List;

import org.cherry.persistence.PersistenceException;
import org.cherry.persistence.engine.spi.QueryParameters;
import org.cherry.persistence.engine.spi.RowSelection;
import org.cherry.persistence.engine.spi.SessionImplementor;
import org.cherry.persistence.engine.sqlite.ResultResolver;
import org.cherry.persistence.engine.sqlite.SQLiteHelper;
import org.cherry.persistence.persister.entity.EntityPersister;

import android.database.Cursor;


public abstract class Loader {
	private final EntityPersister persister;

	public Loader(EntityPersister persister) {
		this.persister = persister;
	}

	/**
	 * The SQL query string to be called; implemented by all subclasses
	 * 
	 * @return The sql command this loader should use to get its
	 */
	public abstract String getSQLString();

	public List doQueryAndInitializeNonLazyCollections(final SessionImplementor session, final QueryParameters queryParameters) {
		return doQuery(session, queryParameters);
	}

	private List doQuery(final SessionImplementor session, final QueryParameters queryParameters) {
		final RowSelection selection = queryParameters.getRowSelection();
		String limit = null;
		Integer firstRow = null;
		Integer maxRows = null;
		if (selection != null) {
			firstRow = selection.getFirstRow();
			maxRows = selection.getMaxRows();
			boolean hasFirstRow = firstRow != null && firstRow > 0;
			boolean hasMaxRows = maxRows != null && maxRows > 0;
			if (hasFirstRow || hasMaxRows) {
				firstRow = hasFirstRow ? firstRow : 0;
				maxRows = hasMaxRows ? maxRows : Integer.MAX_VALUE;
				limit = " limit ? , ? ";
			}
		}
		Object[] values = queryParameters.getPositionalParameterValues();
		String sql = getSQLString();
		if (limit != null) {
			sql = sql.concat(limit);
			int len = values.length;
			values = Arrays.copyOf(values, len + 2);
			values[len] = firstRow;
			values[len + 1] = maxRows;
		}
		Cursor cursor = null;
		try {
			cursor = session.getDatabaseCoordinator().query(sql, values);
			ResultResolver resolver = new ResultResolver(session, persister, cursor);
			return getResult(resolver);
		} finally {
			SQLiteHelper.close(cursor);
		}
	}

	/**
	 * Called by subclasses that load entities
	 * 
	 * @param persister
	 *            only needed for logging
	 * @param lockOptions
	 */
	protected final List loadEntity(final SessionImplementor session, final Object id, final Type identifierType,
			final EntityPersister persister) throws PersistenceException {
		QueryParameters qp = new QueryParameters();
		qp.setPositionalParameterValues(new Object[] { id });
		List result = doQueryAndInitializeNonLazyCollections(session, qp);
		return result;
	}

	public abstract List getResult(ResultResolver resultsResolver);
}
