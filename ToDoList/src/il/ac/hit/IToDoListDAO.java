package il.ac.hit;

import java.util.List;

public interface IToDoListDAO {
	public void addUser(User user) throws ToDoListPlatformException;
	public boolean deleteUser(User user) throws ToDoListPlatformException;
	public List<User> getUsers() throws ToDoListPlatformException; 
	public List<Item> getItems() throws ToDoListPlatformException;
	public void addItem(Item item) throws ToDoListPlatformException;
	public boolean deleteItem(Item item) throws ToDoListPlatformException;
	public boolean updateItem(Item item) throws ToDoListPlatformException;
}