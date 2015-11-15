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
		u.setEmail("hey@you.com");
		u.setIdUser(0);
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
		Item i = new Item();
		i.setAllDay(true);
		i.setCompleted(false);
		i.setDetails("Buy Watermelon on the way home.");
		try {
			i.setUser(model.getUsers().get(1));
		} catch (ToDoListPlatformException e1) {
			e1.printStackTrace();
		}
		i.setName("Watermelon");
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
		//add this line to test the commit , pull and push command on shell
	}

}
