package il.ac.hit.todolistframework.model;

import java.util.List;

/**
 * Data Access Object Interface of the to-do list framework
 */
public interface IToDoListDAO {
	/**
	 * add new user to the database
	 * @param user - the user to add
	 * @throws ToDoListPlatformException
	 */
	public void addUser(User user) throws ToDoListPlatformException;
	/**
	 * delete existing user from the database
	 * @param user - the same user object to delete
	 * @return true of the deletion was successful
	 * @throws ToDoListPlatformException
	 */
	public boolean deleteUser(User user) throws ToDoListPlatformException;
	/**
	 * get the list of users from the database
	 * @return return the list of all users from the database
	 * @throws ToDoListPlatformException
	 */
	public List<User> getUsers() throws ToDoListPlatformException;
	/**
	 * get a user by its user name (user names are unique)
	 * @param userName - the user name of the user object to retrieve.
	 * @return the user object. null if it was not found.
	 * @throws ToDoListPlatformException - if multiple users with the same name exist (DB corruption?) or some other DB issue
	 */
	public User getUser(String userName) throws ToDoListPlatformException;
	/**
	 * given user with its user name and password, find the user in the database by its name and match between the passwords
	 * @param user - the user to check
	 * @return true if the given password and the password in the DB match, false otherwise.
	 * @throws ToDoListPlatformException
	 */
	public User login(User user) throws ToDoListPlatformException;
	/**
	 * get the list of all to-do items in the DB
	 * @return the list of all to-do items in the DB
	 * @throws ToDoListPlatformException
	 */
	public List<Item> getItems() throws ToDoListPlatformException;
	/**
	 * add a new item to the database
	 * @param item - the to-do item to add
	 * @throws ToDoListPlatformException
	 */
	public void addItem(Item item) throws ToDoListPlatformException;
	/**
	 * delete an item
	 * @param item - the item to delete
	 * @return true if the item was deleted. false otherwise.
	 * @throws ToDoListPlatformException
	 */
	public boolean deleteItem(Item item) throws ToDoListPlatformException;
	/**
	 * update a to-do item in the database with the changes done in the given to-do item
	 * @param item - the item to update
	 * @return true if the update was successful. false otherwise.
	 * @throws ToDoListPlatformException
	 */
	public boolean updateItem(Item item) throws ToDoListPlatformException;
}