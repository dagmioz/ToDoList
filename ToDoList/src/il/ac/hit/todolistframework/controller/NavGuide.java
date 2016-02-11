package il.ac.hit.todolistframework.controller;

/**
 * Enum class thats lists all the possible requests that can be performed in this dynamic web project.
 * using the FrontController servlet.
 */
public enum NavGuide {
	PAGE_NOT_EXIST(0),
	
	TODOLIST_SHOW(1),
	TODOLIST_ADD(2),
	TODOLIST_EDIT(4),
	TODOLIST_REMOVE(3),
	TODOLIST_REMOVE_GROUP(5), //POST
	
	LOGIN_SHOW(6),
	LOGIN_LOGIN(7),		//POST login and create new account
	LOGIN_LOGOUT(8);
	
	private int value;
	
	private NavGuide(int value){
		this.value = value;
	}
	
}
