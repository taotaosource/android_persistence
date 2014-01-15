package org.cherry.persistence.cfg;

import org.cherry.persistence.TypeResolver;
import org.cherry.persistence.mapping.PersistentClass;
import org.cherry.persistence.mapping.Table;

public interface Mappings {
	void addTable(String name, Table table);
	void addClass(PersistentClass persistentClass);
	TypeResolver getTypeResolver();
}
