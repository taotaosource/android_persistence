package test;

import org.cherry.persistence.annotations.Id;
import org.cherry.persistence.annotations.Table;

@Table(name = "empty")
public class Empty {
	private String id;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
	
	
}
