package il.ac.hit.todolistframework.helpers;

import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * Simple logger class that logs given messages to the console.
 * This is a helper class. 
 */
public class ConsoleLogger {
	/**
	 * returns the date and time at this moment (now).
	 * @return a string that represents now time, date and time in this format: "yyyy-MM-dd'T'HH-mm-ss"
	 */
	private String now()
	{
        Calendar cal = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH-mm-ss");
		return sdf.format(cal.getTime());
	}
	/**
	 * get the current location from where the function was called; 
	 * @return the name of the function from which this function is called.
	 */
	private String getLocation()
	{
		/*
		 * it retrieves the current function from the stack trace.
		 * it was determined from trial and error that the current function was represented in index number 4.
		 */
		return Thread.currentThread().getStackTrace()[4].getMethodName();
	}
	/**
	 * the basic logging function
	 * @param logType - what type of message is logged
	 * @param message - the message to log
	 */
	private void write(String logType,String message){
		System.out.println(now() + " [" + logType + "] " + getLocation() + ": " + message );
	}
	/**
	 * log a warning message
	 */
	public void warning(String message){
		write("WARNING",message);
	}
	/**
	 * log an error message
	 */
	public void error(String message){
		write("ERROR",message);
	}
	/**
	 * log an info message
	 */
	public void info(String message){
		write("INFO",message);
	}
	/**
	 * log a debug message
	 */
	public void debug(String message){
		write("DEBUG",message);
	}
}
