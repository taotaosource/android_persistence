package org.cherry.persistence.mapping;

import org.cherry.persistence.PersistenceException;

public interface RelationalModel {
	public String sqlCreateString() throws PersistenceException;
	public String sqlDropString();
}