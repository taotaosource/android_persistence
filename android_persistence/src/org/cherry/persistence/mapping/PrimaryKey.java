package org.cherry.persistence.mapping;

public class PrimaryKey {
	
	private String name;
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}


	public String sqlConstraintString() {
		StringBuilder buf = new StringBuilder("primary key (");
		buf.append(name);
		buf.append(")");
		return buf.toString();
	}
}
