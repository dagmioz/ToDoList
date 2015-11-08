package il.ac.hit;

import java.util.ArrayList;

public interface IToDoListDAO {
	public void addUser(User user) throws ToDoListPlatformException;
	public boolean deleteUser(User user) throws ToDoListPlatformException;
	public ArrayList<User> getUsers() throws ToDoListPlatformException; 
	public ArrayList<Item> getItems() throws ToDoListPlatformException;
	public void addItem(Item item) throws ToDoListPlatformException;
	public boolean deleteItem(Item item) throws ToDoListPlatformException;
	public void updateItem(Item item) throws ToDoListPlatformException;
	//public void completeItem() throws ToDoListPlatformException;
}