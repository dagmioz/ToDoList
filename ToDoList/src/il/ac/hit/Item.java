package il.ac.hit;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "item")
public class Item implements java.io.Serializable{

	private int idItem;
	private String name;
	private String details;
	private boolean allDay;
	private Calendar time_start;
	private Calendar time_end;
	private boolean completed;
	private User user;
	
	public Item() {}
	
	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
	public int getIdItem() {
		return idItem;
	}
	
	public void setIdItem(int idItem) {
		this.idItem = idItem;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDetails() {
		return details;
	}
	public void setDetails(String details) {
		this.details = details;
	}
	public boolean isAllDay() {
		return allDay;
	}
	public void setAllDay(boolean allDay) {
		this.allDay = allDay;
	}
	public Calendar getTime_start() {
		return time_start;
	}
	public void setTime_start(Calendar time_start) {
		this.time_start = time_start;
	}
	public Calendar getTime_end() {
		return time_end;
	}
	public void setTime_end(Calendar time_end) {
		this.time_end = time_end;
	}
	public boolean isCompleted() {
		return completed;
	}
	public void setCompleted(boolean completed) {
		this.completed = completed;
	}
	
	
	@ManyToOne(fetch = FetchType.LAZY)
	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	@Override
	public String toString() {
		return "Item [idItem=" + idItem + ", name=" + name + ", details=" + details + ", allDay=" + allDay
				+ ", time_start=" + time_start + ", time_end=" + time_end + ", completed=" + completed + ", user="
				+ user + "]";
	}
}