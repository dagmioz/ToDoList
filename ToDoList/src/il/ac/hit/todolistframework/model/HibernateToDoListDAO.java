package il.ac.hit.todolistframework.model;

import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

//In Hibernate 3.6, org.hibernate.cfg.AnnotationConfiguration is deprecated, and all its functionality has been moved to org.hibernate.cfg.Configuration.
//Link: http://stackoverflow.com/questions/28064007/what-is-the-difference-between-annotationconfiguration-and-configuration-in-hibe
import org.hibernate.cfg.Configuration; 

/**
 * The Hibernate implementation of a Data Access Object of the to-do list framework
 * written as singleton design pattern  
 */
public class HibernateToDoListDAO implements IToDoListDAO {

	private static final HibernateToDoListDAO INSTANCE = new HibernateToDoListDAO();
	private SessionFactory factory;
	/**
	 * default Ctor
	 * loads the hibernate configuration
	 */
	private HibernateToDoListDAO(){
		/*
		 * if the user  
		 */
		if(INSTANCE != null){
			throw new IllegalStateException("HibernateToDoListDAO was already instantiated");
		}
		try{
			Configuration configuration = new Configuration().configure(); 
	        factory = configuration.buildSessionFactory();
		}
		catch(Exception e){
			System.out.println("HibernateToDoListDAO()[Ctor] Error: "+e.getMessage());
		}	
	}
	
	/**
	 * when using the this DAO this is a function that needs to be called first
	 * @return this object, its instance
	 */
	public static HibernateToDoListDAO getInstance()
	{
		return INSTANCE;
	}

	
	/*
	 * (non-Javadoc)
	 * @see il.ac.hit.todolistframework.model.IToDoListDAO#addUser(il.ac.hit.todolistframework.model.User)
	 */
	@Override
	public void addUser(User user) throws ToDoListPlatformException {
		Session session = factory.openSession();
		User generatedUser = null;
		try {
			session.beginTransaction();
			session.save(user);
			session.getTransaction().commit();
		}
		catch ( HibernateException e ) {
			if ( session.getTransaction() != null )
				session.getTransaction().rollback();
			throw new ToDoListPlatformException(e.getMessage());
		} finally {
			if (session != null)
				session.close(); 
		}
	}

	/*
	 * (non-Javadoc)
	 * @see il.ac.hit.todolistframework.model.IToDoListDAO#deleteUser(il.ac.hit.todolistframework.model.User)
	 */
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
				session.close();
		} 
		return false;
	}

	/*
	 * (non-Javadoc)
	 * @see il.ac.hit.todolistframework.model.IToDoListDAO#getUsers()
	 */
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
				session.close();
		} 
	}
	
	/*
	 * (non-Javadoc)
	 * @see il.ac.hit.todolistframework.model.IToDoListDAO#getUser(java.lang.String)
	 */
	@Override
	public User getUser(String name) throws ToDoListPlatformException {
		Session session = factory.openSession();
		try {
			Query query = session.createQuery("from User u where u.name='"+name+"'");
			List queryList = query.list();
			if(queryList != null && queryList.isEmpty())
				return null;
			else if(queryList.size() > 1) // userName should be unique, is this a DB corruption?
				throw new ToDoListPlatformException("More than single user was returned");
			else
				return (User) queryList.get(0);
		} 
		catch ( Exception e ) {
			throw new ToDoListPlatformException("Unable to get users list from the database");
		} finally {
			if (session != null)
				session.close();
		} 
	}

	/*
	 * (non-Javadoc)
	 * @see il.ac.hit.todolistframework.model.IToDoListDAO#getItems()
	 */
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
			throw new ToDoListPlatformException("Unable to get items list from the database");
		} finally {
			if (session != null)
				session.close(); 
		} 
	}

	/*
	 * (non-Javadoc)
	 * @see il.ac.hit.todolistframework.model.IToDoListDAO#addItem(il.ac.hit.todolistframework.model.Item)
	 */
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
				session.close(); 
		} 
	}

	/*
	 * (non-Javadoc)
	 * @see il.ac.hit.todolistframework.model.IToDoListDAO#deleteItem(il.ac.hit.todolistframework.model.Item)
	 */
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
				session.close(); 
		} 
		return false;
	}

	/*
	 * (non-Javadoc)
	 * @see il.ac.hit.todolistframework.model.IToDoListDAO#updateItem(il.ac.hit.todolistframework.model.Item)
	 */
	@Override
	public boolean updateItem(Item item) throws ToDoListPlatformException {
		/*
		 * using save or update:
		 * When using .saveOrUpdate() Hibernate will check if the object
		 * is transient (it has no identifier property) and if so it will
		 * make it persistent by generating it the identifier and assigning
		 * it to session. If the object has an identifier already it will perform .update().
		 */
		
		Session session = factory.openSession();
		try {
			session.beginTransaction();
			session.saveOrUpdate(item);
			session.getTransaction().commit();
			return true;
		}
		catch ( HibernateException e ) {
			if ( session.getTransaction() != null )
				session.getTransaction().rollback();
		} finally {
			if (session != null)
				session.close(); 
		}
		return false; 
	}

	/*
	 * (non-Javadoc)
	 * @see il.ac.hit.todolistframework.model.IToDoListDAO#login(il.ac.hit.todolistframework.model.User)
	 */
	@Override
	public User login(User user) throws ToDoListPlatformException {
		System.out.println("login from hibernate...");
		User authenticatedUser = getUser(user.getName());
		if(authenticatedUser!=null && authenticatedUser.getPassword().equals(user.getPassword()))
		{
			return authenticatedUser;
		}
		return null;
	}


}
