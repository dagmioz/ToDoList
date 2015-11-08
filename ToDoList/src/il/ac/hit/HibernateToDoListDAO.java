package il.ac.hit;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Hibernate;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
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
		//else
		//factory = new Configuration().configure().buildSessionFactory();
		System.out.println("Connect to DB: Configure configuration.");
		Configuration configuration = new Configuration().configure(); 
		//System.out.println("Connect to DB: Apply settings.");
        //StandardServiceRegistryBuilder builder = new StandardServiceRegistryBuilder().applySettings(configuration.getProperties());
        System.out.println("Connect to DB: Build session factory.");
        factory = configuration.buildSessionFactory();
		}
		catch(Exception e){
			e.printStackTrace();
		}
        /*
		//code was taken from http://docs.jboss.org/hibernate/orm/5.0/quickstart/html/
		final StandardServiceRegistry registry = new StandardServiceRegistryBuilder()
				.configure() // configures settings from hibernate.cfg.xml
				.build();
		try {
			factory = new MetadataSources( registry ).buildMetadata().buildSessionFactory();
		}
		catch (Exception e) {
			// The registry would be destroyed by the SessionFactory, but we had trouble building the SessionFactory
			// so destroy it manually.
			StandardServiceRegistryBuilder.destroy( registry );
		}
		*/
		
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
	public ArrayList<User> getUsers() throws ToDoListPlatformException {
		Session session = factory.openSession();
		try {
			session.beginTransaction();
			List<User> result = session.createSQLQuery("select * from user").list();//createQuery("from user u").list();
			session.getTransaction().commit();
			return (ArrayList<User>)result;
		}
		catch ( HibernateException e ) {
			if ( session.getTransaction() != null )
				session.getTransaction().rollback();
		} finally {
			if (session != null)
			session.close(); //may want to add try - catch
		} 
		
		return null;
	}

	@Override
	public ArrayList<Item> getItems() throws ToDoListPlatformException {
		Session session = factory.openSession();
		try {
			session.beginTransaction();
			List result = session.createQuery("from Item").list();
			session.getTransaction().commit();
			return (ArrayList<Item>)result;
		}
		catch ( HibernateException e ) {
			if ( session.getTransaction() != null )
				session.getTransaction().rollback();
		} finally {
			if (session != null)
			session.close(); //may want to add try - catch
		} 
		return null;
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
	
	public void updateItem(Item item) throws ToDoListPlatformException{
		Session session = factory.openSession();
		try {
			session.beginTransaction();
			session.update(item);
			session.getTransaction().commit();
		}
		catch (HibernateException e){
			if (session.getTransaction() != null)
				session.getTransaction().rollback();
		}
		finally {
			if (session != null)
			session.close();
		}
	}

}
