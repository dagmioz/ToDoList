package il.ac.hit.todolistframework.model;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
/**
 *
 */
@Entity
@Table(name = "user")
public class User implements java.io.Serializable{

	private static final long serialVersionUID = 5603778550260749439L;
	private int idUser;
	private String name;
	private String password;
	
	private List<Item> items;

	public User() {}
	/**
	 * 
	 * @return
	 */
	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
	public int getIdUser() {
		return idUser;
	}
	/**
	 * 
	 * @param idUser
	 */
	public void setIdUser(int idUser) {
		this.idUser = idUser;
	}
	/**
	 * 
	 * @return
	 */
	@Column(nullable=false, unique=true)
	public String getName() {
		return name;
	}
	/**
	 * 
	 * @param name
	 */
	public boolean setName(String name) {
		if(name == null || name.isEmpty()) //name must not be empty or null
			return false;
		this.name = name;
		return true;
	}
	/**
	 * 
	 * @return
	 */
	@Column(nullable=false)
	public String getPassword() {
		return password;
	}
	/**
	 * 
	 * @param email
	 */
	public boolean setPassword(String password) {
		if(password == null || password.isEmpty())
			return false;
		this.password = password;
		return true;
	}
	/**
	 * 
	 * @return
	 */
	@OneToMany(fetch = FetchType.EAGER, mappedBy = "user")
	public List<Item> getItems() {
		return items;
	}
	/**
	 * 
	 * @param items
	 */
	public void setItems(List<Item> items) {
		this.items = items;
	}

	@Override
	public String toString() {
		return "User [idUser=" + idUser + ", name=" + name + ", password=" + password + ", items=" + items + "]";
	}
}
