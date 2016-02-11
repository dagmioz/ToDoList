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
 *user object in the To Do List Framework model
 *each user has a name, password and its own list of items (to-do items)
 *this is an entity to the hibernate framework we use.
 */
@Entity
@Table(name = "user")
public class User implements java.io.Serializable{

	private static final long serialVersionUID = 5603778550260749439L;
	private int idUser;
	private String name;
	private String password;
	
	private List<Item> items;
	
	/**
	 * default Ctor - required to work as serializable (serializable to work with hibernate)
	 */
	public User() {}
	/**
	 * @return the id of the user object, the id is auto generated for each new user stored in the database
	 */
	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
	public int getIdUser() {
		return idUser;
	}
	/**
	 * set the user id. for new user, setting the id is not required because its auto generated.
	 * @param idUser
	 */
	public void setIdUser(int idUser) {
		this.idUser = idUser;
	}
	/**
	 * user name must be unique and not null.
	 * @return user name
	 */
	@Column(nullable=false, unique=true)
	public String getName() {
		return name;
	}
	/**
	 * set the name for the user, must not be empty or null
	 * @param name - the name to set for the user
	 * @return true if the user name is not empty or null, false otherwise.
	 */
	public boolean setName(String name) {
		if(name == null || name.isEmpty()) //name must not be empty or null
			return false;
		this.name = name;
		return true;
	}
	/**
	 * get the users' password
	 * @return
	 */
	@Column(nullable=false)
	public String getPassword() {
		return password;
	}
	/**
	 * set the user password, must not be null or empty
	 * @param password - the password for the user
	 * @return true of password is not null or empty. false otherwise.
	 */
	public boolean setPassword(String password) {
		if(password == null || password.isEmpty())
			return false;
		this.password = password;
		return true;
	}
	/**
	 * Eagerly fetch
	 * (loads the list of items with the user object when requested)
	 * the users' list of to-do items
	 * @return a list of to-do items that belong to the user.
	 */
	@OneToMany(fetch = FetchType.EAGER, mappedBy = "user")
	public List<Item> getItems() {
		return items;
	}
	/**
	 * set the users list of items
	 * @param items - list of items to set as the user's list
	 */
	public void setItems(List<Item> items) {
		this.items = items;
	}

	@Override
	public String toString() {
		return "User [idUser=" + idUser + ", name=" + name + ", password=" + password + ", items=" + items + "]";
	}
}
