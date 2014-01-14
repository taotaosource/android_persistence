package test;
import org.cherry.persistence.annotations.Entity;
import org.cherry.persistence.annotations.Table;

@Entity
@Table(name = "user3")
public class User3 {
	private int id;
	private String name;
	private String password;
	private float price;

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

}
