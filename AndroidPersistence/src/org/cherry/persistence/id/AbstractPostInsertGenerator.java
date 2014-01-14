package org.cherry.persistence.id;

import java.io.Serializable;

import org.cherry.persistence.engine.spi.SessionImplementor;


/**
 * Basic implementation of the {@link PostInsertIdentifierGenerator} contract.
 *
 * 
 */
/**
 * Basic implementation of the {@link PostInsertIdentifierGenerator} contract.
 *
 * 
 */
public abstract class AbstractPostInsertGenerator implements IdentifierGenerator {
	@Override
	public Serializable generate(SessionImplementor session, Object obj) {
		return IdentifierGeneratorHelper.POST_INSERT_INDICATOR;
	}
	 
}