package il.ac.hit.todolistframework.model;

/**
 *  general exception for this project.
 */
public class ToDoListPlatformException extends Exception {

	private static final long serialVersionUID = 4997798834006136529L;
	/**
	 * 
	 * @param msg
	 */
	public ToDoListPlatformException(String msg) {
		super(msg);
	}
	/**
	 * 
	 * @param msg
	 * @param throwable
	 */
	public ToDoListPlatformException(String msg, Throwable throwable) {
		super(msg,throwable);
	}
}
