package il.ac.hit;

import static org.junit.Assert.*;

import org.junit.Test;

public class HibernateToDoListDAOTest {

	@Test
	public void testGetInstance() {
		fail("Not yet implemented");
	}

	@Test
	public void testAddUser() {
		HibernateToDoListDAO a = HibernateToDoListDAO.getInstance();
		User u = new User();
		u.setPassword("123456");
		u.setIdUser(1);
		u.setName("me");
		try {
			a.addUser(u);
			System.out.println(u);
			assertTrue(true);
		} catch (ToDoListPlatformException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			assertTrue(false);
		}
	}

	@Test
	public void testDeleteUser() {
		fail("Not yet implemented");
	}

	@Test
	public void testGetUsers() {
		HibernateToDoListDAO model = HibernateToDoListDAO.getInstance();
		try {
			System.out.println(model.getUsers());
			assertTrue(true);
		} catch (ToDoListPlatformException e) {
			e.printStackTrace();
			assertTrue(false);
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
		
		User u = new User();
		u.setPassword("123456");
		u.setName("moses");
		try {
			model.addUser(u);
			System.out.println(u);
			assertTrue(true);
		} catch (ToDoListPlatformException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			assertTrue(false);
		}
		
		
		Item i = new Item();
		i.setWhatToDo("Buy Watermelon on the way home.");
		try {
			i.setUser(model.getUsers().get(0));
		} catch (ToDoListPlatformException e1) {
			e1.printStackTrace();
		}
		try {
			model.addItem(i);
			System.out.println(i);
			assertTrue(true);
		} catch (ToDoListPlatformException e) {
			e.printStackTrace();
			assertTrue(false);
		}
	}

	@Test
	public void testDeleteItem() {
		fail("Not yet implemented");
	}

}
