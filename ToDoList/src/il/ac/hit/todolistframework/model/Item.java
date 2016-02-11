package il.ac.hit.todolistframework.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * 
 * @author digic
 *
 */
@Entity
@Table(name = "item")
public class Item implements java.io.Serializable{

	/*
	 * good example of annotations usage: http://www.mkyong.com/hibernate/hibernate-one-to-many-relationship-example-annotation/ 
	 * one to many and many to one annotations from there (with one exception of EagerLoading in the ManyToOne side).
	 */
	
	private static final long serialVersionUID = 8059316066589799229L;
	private int idItem;
	private String whatToDo;
	private User user;
	
	/**
	 * default Ctor
	 */
	public Item() {}
	
	/**
	 * the id of the item object.
	 * id is auto-generated when an item is added to the DB using Hibernate (or any engine that uses persistence).
	 * @return
	 */
	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
	public int getIdItem() {
		return idItem;
	}
	
	/**
	 * set an item id
	 * @param idItem - the id to set for the item
	 */
	public void setIdItem(int idItem) {
		this.idItem = idItem;
	}
	
	/**
	 * lazily fetch a user
	 * (when loading an item object, the user that it belongs to doesn't load with it)
	 * @return user that owns this item.
	 */
	@ManyToOne(fetch = FetchType.LAZY)
	public User getUser() {
		return user;
	}

	/**
	 * set a user for this item
	 * @param user to set to be the owner of this item
	 */
	public void setUser(User user) {
		this.user = user;
	}

	/**
	 * get the data, that what to do string
	 * @return string that tells what to do.
	 */
	@Column(nullable=false)
	public String getWhatToDo() {
		return whatToDo;
	}

	/**
	 * set what needs to be done message
	 * @param whatToDo - the message to store.
	 * @return true if the whatToDo string is not null or empty, false otherwise.
	 */
	public boolean setWhatToDo(String whatToDo) {
		if(whatToDo == null || whatToDo.isEmpty()) //whatToDo must not be empty or null
			return false;
		this.whatToDo = whatToDo;
		return true;
	}

	/**
	 * custom to String in this format:
	 * Item [idItem=idItem,whatToDo=whatToDo,userID=user name]
	 */
	@Override
	public String toString() {
		/**
		 *using only the id of the user for thinner output.
		 *because each user also print its items...  
		 */
		return "Item [idItem=" + idItem + ", whatToDo=" + whatToDo + ", userID=" + user.getIdUser()
				+ "]";
	}

}