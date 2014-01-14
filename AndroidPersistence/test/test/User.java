package test;

import org.cherry.persistence.annotations.Column;
import org.cherry.persistence.annotations.Entity;
import org.cherry.persistence.annotations.GeneratedValue;
import org.cherry.persistence.annotations.GenerationType;
import org.cherry.persistence.annotations.Id;
import org.cherry.persistence.annotations.Table;
import org.cherry.persistence.annotations.Transient;

@Entity
@Table(name = "user")
public class User {
	
	public static String staticName;
	public static  final String staticNameFinal ="";
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;

	@Column(name = "name3")
	private String name;
	private String password;
	private String age;
	private String avatar;
	@Column(unique=true)
	private String emial;
	private String post;
	private  String gender;
	private float price;
	@Transient
	private Object object;
	public User(String a) {
		
	}

	public int getId() {
		return id;
	}



	public void setId(int id) {
		this.id = id;
	}



	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public float getPrice() {
		return price;
	}

	public void setPrice(float price) {
		this.price = price;
	}

	public String getAge() {
		return age;
	}

	public void setAge(String age) {
		this.age = age;
	}

	public String getAvatar() {
		return avatar;
	}

	public void setAvatar(String avatar) {
		this.avatar = avatar;
	}

	public String getEmial() {
		return emial;
	}

	public void setEmial(String emial) {
		this.emial = emial;
	}

	public String getPost() {
		return post;
	}

	public void setPost(String post) {
		this.post = post;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}
 
	@Override
	public String toString() {
		return "User [id=" + id + ", name=" + name + ", password=" + password + ", age=" + age + ", avatar=" + avatar + ", emial=" + emial
				+ ", post=" + post + ", gender=" + gender + ", price=" + price + "]";
	}

}
