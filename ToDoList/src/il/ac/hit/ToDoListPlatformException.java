package il.ac.hit;

public class ToDoListPlatformException extends Exception {
	public ToDoListPlatformException(String msg) {
		super(msg);
	}
	public ToDoListPlatformException(String msg, Throwable throwable) {
		super(msg,throwable);
	}
}
