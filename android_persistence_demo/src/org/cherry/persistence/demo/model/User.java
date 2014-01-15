package org.cherry.persistence.demo.model;

import org.cherry.persistence.annotations.Column;
import org.cherry.persistence.annotations.GeneratedValue;
import org.cherry.persistence.annotations.GenerationType;
import org.cherry.persistence.annotations.Id;
import org.cherry.persistence.annotations.Table;

@Table(name = "user")
public class User {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "_id")
	private int id;
	private int age;
	private String name;
	private String gender;
	private String email;
	private String avatar;
	private String username;
	private String password;
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getAvatar() {
		return avatar;
	}

	public void setAvatar(String avatar) {
		this.avatar = avatar;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	@Override
	public String toString() {
		return "User [id=" + id + ", age=" + age + ", name=" + name + ", gender=" + gender + ", email=" + email + ", avatar=" + avatar
				+ ", username=" + username + ", password=" + password + "]";
	}
}
