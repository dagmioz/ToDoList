package il.ac.hit;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

//In Hibernate 3.6, org.hibernate.cfg.AnnotationConfiguration is deprecated, and all its functionality has been moved to org.hibernate.cfg.Configuration.
//Link: http://stackoverflow.com/questions/28064007/what-is-the-difference-between-annotationconfiguration-and-configuration-in-hibe
import org.hibernate.cfg.Configuration; 

//there is a better way: as ENUM, to ask Haim:
//Link: http://stackoverflow.com/questions/70689/what-is-an-efficient-way-to-implement-a-singleton-pattern-in-java
public class HibernateToDoListDAO implements IToDoListDAO {

	private static final HibernateToDoListDAO INSTANCE = new HibernateToDoListDAO();
	private SessionFactory factory;
	
	private HibernateToDoListDAO(){
		if(INSTANCE != null){
			throw new IllegalStateException("Already instantiated");
		}
		try{
			System.out.println("Connect to DB: Configure configuration.");
			Configuration configuration = new Configuration().configure(); 
	        System.out.println("Connect to DB: Build session factory.");
	        factory = configuration.buildSessionFactory();
		}
		catch(Exception e){
			e.printStackTrace();
		}	
	}
	
	public static HibernateToDoListDAO getInstance()
	{
		return INSTANCE;
	}

	@Override
	public void addUser(User user) throws ToDoListPlatformException {
		Session session = factory.openSession();
		try {
			session.beginTransaction();
			session.save(user);
			session.getTransaction().commit();
		}
		catch ( HibernateException e ) {
			if ( session.getTransaction() != null )
				session.getTransaction().rollback();
		} finally {
if (session != null)
			session.close(); //may want to add try - catch
		} 
	}

	@Override
	public boolean deleteUser(User user) throws ToDoListPlatformException {
		Session session = factory.openSession();
		try {
			session.beginTransaction();
			session.delete(user);
			session.getTransaction().commit();
			return true;
		}
		catch ( HibernateException e ) {
			if ( session.getTransaction() != null )
				session.getTransaction().rollback();
		} finally {
if (session != null)
			session.close(); //may want to add try - catch
		} 
		return false;
	}

	@Override
	public List<User> getUsers() throws ToDoListPlatformException {
		Session session = factory.openSession();
		try {
			Query query = session.createQuery("from User u");
			List queryList = query.list();
			if(queryList != null && queryList.isEmpty())
				return null;
			else
				return (List<User>)queryList;
		} 
		catch ( Exception e ) {
			throw new ToDoListPlatformException("unable to get users list from database");
		} finally {
			if (session != null)
				session.close(); //may want to add try - catch
		} 
	}

	@Override
	public List<Item> getItems() throws ToDoListPlatformException {
		Session session = factory.openSession();
		try {
			Query query = session.createQuery("from Item i");
			List queryList = query.list();
			if(queryList != null && queryList.isEmpty())
				return null;
			else
				return (List<Item>)queryList;
		}
		catch ( HibernateException e ) {
			throw new ToDoListPlatformException("unable to get items list from database");
		} finally {
			if (session != null)
				session.close(); //may want to add try - catch
		} 
	}

	@Override
	public void addItem(Item item) throws ToDoListPlatformException {
		Session session = factory.openSession();
		try {
			session.beginTransaction();
			session.save(item);
			session.getTransaction().commit();
		}
		catch ( HibernateException e ) {
			if ( session.getTransaction() != null )
				session.getTransaction().rollback();
		} finally {
			if (session != null)
				session.close(); //may want to add try - catch
		} 
		
	}

	@Override
	public boolean deleteItem(Item item) throws ToDoListPlatformException {
		Session session = factory.openSession();
		try {
			session.beginTransaction();
			session.delete(item);
			session.getTransaction().commit();
			return true;
		}
		catch ( HibernateException e ) {
			if ( session.getTransaction() != null )
				session.getTransaction().rollback();
		} finally {
			if (session != null)
				session.close(); //may want to add try - catch
		} 
		return false;
	}
}
