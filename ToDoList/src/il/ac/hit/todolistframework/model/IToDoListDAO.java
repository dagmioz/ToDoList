package il.ac.hit.todolistframework.model;

import java.util.List;

public interface IToDoListDAO {
	public void addUser(User user) throws ToDoListPlatformException;
	public boolean deleteUser(User user) throws ToDoListPlatformException;
	public List<User> getUsers() throws ToDoListPlatformException;
	public User getUser(String userName) throws ToDoListPlatformException;
	public User login(User user) throws ToDoListPlatformException;
	public List<Item> getItems() throws ToDoListPlatformException;
	public void addItem(Item item) throws ToDoListPlatformException;
	public boolean deleteItem(Item item) throws ToDoListPlatformException;
	//public boolean deleteItem(int itemId) throws ToDoListPlatformException;
	public boolean updateItem(Item item) throws ToDoListPlatformException;
}