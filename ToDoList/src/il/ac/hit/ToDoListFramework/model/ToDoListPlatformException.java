package il.ac.hit.ToDoListFramework.model;

public class ToDoListPlatformException extends Exception {
	public ToDoListPlatformException(String msg) {
		super(msg);
	}
	public ToDoListPlatformException(String msg, Throwable throwable) {
		super(msg,throwable);
	}
}
