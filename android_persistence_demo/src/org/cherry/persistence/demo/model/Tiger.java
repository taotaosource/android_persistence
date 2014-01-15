package org.cherry.persistence.demo.model;

import org.cherry.persistence.annotations.GeneratedValue;
import org.cherry.persistence.annotations.GenerationType;
import org.cherry.persistence.annotations.Id;
import org.cherry.persistence.annotations.Table;

@Table(name = "tiger")
public class Tiger {
	@Id
	@GeneratedValue(strategy = GenerationType.UUID)
	private String id;

	private String name;
	private int age;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

}
