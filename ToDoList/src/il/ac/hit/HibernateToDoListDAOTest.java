package il.ac.hit;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.Test;

public class HibernateToDoListDAOTest {

	@Test
	public void testGetInstance() {
		HibernateToDoListDAO model = HibernateToDoListDAO.getInstance();
		if(model != null)
			assertTrue(true);
		else
			assertTrue(false);
	}

	@Test
	public void testAddUser() {
		HibernateToDoListDAO model = HibernateToDoListDAO.getInstance();
		
		User u = new User();
		u.setName("Peanut");
		u.setPassword("AddM3");
		
		try {
			model.addUser(u);
			System.out.println("testAddUser : Created user - " + u);
			assertTrue(true);
		} catch (ToDoListPlatformException e) {
			System.out.println("testAddUser : Failed to create user - " + u);
			e.printStackTrace();
			assertTrue(false);
			return;
		}
		
		try
		{
			model.deleteUser(u);
			System.out.println("testAddUser : Created user - " + u);
		} catch(ToDoListPlatformException e)
		{
			System.out.println("testAddUser : Failed to delete user.");
			e.printStackTrace();
		}
	}

	@Test
	public void testDeleteUser()
	{
		HibernateToDoListDAO model = HibernateToDoListDAO.getInstance();
		
		User u = new User();
		u.setName("Butter");
		u.setPassword("DeleteM3");
		
		try {
			model.addUser(u);
			System.out.println(u);
		} catch (ToDoListPlatformException e) {
			System.out.println("testDeleteUser : Failed to create user. There will be nothing to delete - failing this test...");
			e.printStackTrace();
			assertTrue(false);
			return;
		}
		
		try
		{
			model.deleteUser(u);
			System.out.println("testDeleteUser : Successfully Deleted User - " + u.getName());
			assertTrue(true);
		} catch(ToDoListPlatformException e)
		{
			System.out.println("testDeleteUser : Failed to delete user.");
			e.printStackTrace();
			assertTrue(false);
		}
	}

	@Test
	public void testGetUsers() {
		HibernateToDoListDAO model = HibernateToDoListDAO.getInstance();
		
		//Prepare database - Create users
		User u1 = new User();
		u1.setName("Time");
		u1.setPassword("1GetM3");
		
		User u2 = new User();
		u2.setName("Warner");
		u2.setPassword("2GetM3");
		
		try {
			model.addUser(u1);
			model.addUser(u2);
			System.out.println("testGetUsers : Successfully created users: \n\t[\n\t\t"+u1.getName()+ ",\n\t\t" + u2.getName() +"\n\t]");
		} catch (ToDoListPlatformException e) {
			System.out.println("testGetUsers : Failed to create user. There will be nothing to get - failing this test...");
			e.printStackTrace();
			assertTrue(false);
			return;
		}
		
		//the test itself
		ArrayList<User> allUsers = new ArrayList<User>();//empty list
		try {
			allUsers.addAll(model.getUsers());
			System.out.println("testGetUsers : Successfully Retrieved a list of users - " + allUsers);
			assertTrue(true);
		} catch (ToDoListPlatformException e) {
			System.out.println("testGetUsers : Failed to retrieve a list of users.");
			e.printStackTrace();
			assertTrue(false);
			return;
		}
		
		//Clean database - Delete users
		try
		{
			for(User u:allUsers){
				model.deleteUser(u);
				System.out.println("testGetUsers : Successfully Deleted User - " + u.getName());
			}
		} catch(ToDoListPlatformException e)
		{
			System.out.println("testGetUsers : Unable to delete users.");
			e.printStackTrace();
		}
	}

	@Test
	public void testGetItems() {
		HibernateToDoListDAO model = HibernateToDoListDAO.getInstance();
		
		try {
			System.out.println(model.getItems());
			assertTrue(true);
		} catch (ToDoListPlatformException e) {
			e.printStackTrace();
			assertTrue(false);
		}
	}

	@Test
	public void testAddItem() {
		HibernateToDoListDAO model = HibernateToDoListDAO.getInstance();
		
		//Prepare database - Create users
		User u = new User();
		u.setName("Jelly");
		u.setPassword("AddIt3m");
		
		try {
			model.addUser(u);
			System.out.println("testAddItem : Successfully created user - " + u.getName());
		} catch (ToDoListPlatformException e) {
			System.out.println("testAddItem : Failed to create user. There will be nothing to get - failing this test...");
			e.printStackTrace();
			assertTrue(false);
			return;
		}
		
		//The objective
		Item i = new Item();
		i.setWhatToDo("Buy Bananas on the way home.");
		ArrayList<User> allUsers = new ArrayList<User>();//empty list
		try {
			allUsers.addAll(model.getUsers());
			i.setUser(allUsers.stream().filter(s -> s.getName() == "Jelly").findFirst().get());
			model.addItem(i);
			assertTrue(true);
		} catch (ToDoListPlatformException e) {
			e.printStackTrace();
			assertTrue(false);
		}
		
		//Clean database - Delete users
		try
		{
			User tempUser = i.getUser();
			model.deleteItem(i);
			model.deleteUser(tempUser);
			System.out.println("testAddItem : Successfully Deleted User and item");
		} catch(ToDoListPlatformException e)
		{
			System.out.println("testAddItem : Unable to delete item.");
			e.printStackTrace();
		}
	}

	@Test
	public void testDeleteItem() {
		fail("Not yet implemented");
	}

}
