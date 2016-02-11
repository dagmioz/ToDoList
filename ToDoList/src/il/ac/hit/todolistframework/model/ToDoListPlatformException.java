package il.ac.hit.todolistframework.model;

/**
 *  general exception for to do list framework model (and project).
 */
public class ToDoListPlatformException extends Exception {

	private static final long serialVersionUID = 4997798834006136529L;
	/**
	 * Ctor that creates an exception
	 * @param msg - message that explains this exception
	 */
	public ToDoListPlatformException(String msg) {
		super(msg);
	}
	/**
	 * Ctor that creates an exception with a throwable parameter
	 * @param msg - message that explains this exception
	 * @param throwable - object to pass through this exception
	 */
	public ToDoListPlatformException(String msg, Throwable throwable) {
		super(msg,throwable);
	}
}
