package il.ac.hit.todolistframework.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

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
	
	public Item() {}
	
	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
	public int getIdItem() {
		return idItem;
	}
	
	public void setIdItem(int idItem) {
		this.idItem = idItem;
	}
	
	@ManyToOne(fetch = FetchType.LAZY)
	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	@Column(nullable=false)
	public String getWhatToDo() {
		return whatToDo;
	}

	public boolean setWhatToDo(String whatToDo) {
		if(whatToDo == null || whatToDo.isEmpty()) //whatToDo must not be empty or null
			return false;
		this.whatToDo = whatToDo;
		return true;
	}

	@Override
	public String toString() {
		return "Item [idItem=" + idItem + ", whatToDo=" + whatToDo + ", userID=" + user.getIdUser()
				+ "]";
	}

}