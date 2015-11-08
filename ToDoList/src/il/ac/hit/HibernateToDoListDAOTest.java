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
		HibernateToDoListDAO a = HibernateToDoListDAO.getInstance();
		try {
			for(User u:a.getUsers())
			{
				System.out.println(u);
			}
			assertTrue(true);
		} catch (ToDoListPlatformException e) {
			e.printStackTrace();
			assertTrue(false);
		}
	}

	@Test
	public void testGetItems() {
		fail("Not yet implemented");
	}

	@Test
	public void testAddItem() {
			fail("Not yet implemented");
	}

	@Test
	public void testDeleteItem() {
		fail("Not yet implemented");
	}

}
