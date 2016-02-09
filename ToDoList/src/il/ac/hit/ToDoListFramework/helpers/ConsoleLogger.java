package il.ac.hit.todolistframework.helpers;

import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * 
 * @author 
 * 
 */
public class ConsoleLogger {
	/**
	 * 
	 * @return
	 */
	private String now()
	{
        Calendar cal = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH-mm-ss");
		return sdf.format(cal.getTime());
	}
	/**
	 * 
	 * @return
	 */
	private String getLocation()
	{
		return Thread.currentThread().getStackTrace()[4].getMethodName();
	}
	/**
	 * 
	 * @param logType
	 * @param message
	 */
	private void write(String logType,String message){
		System.out.println(now() + " [" + logType + "] " + getLocation() + ": " + message );
	}
	/**
	 * 
	 * @param message
	 */
	public void warning(String message){
		write("WARNING",message);
	}
	/**
	 * 
	 * @param message
	 */
	public void error(String message){
		write("ERROR",message);
	}
	/**
	 * 
	 * @param message
	 */
	public void info(String message){
		write("INFO",message);
	}
	/**
	 * 
	 * @param message
	 */
	public void debug(String message){
		write("DEBUG",message);
	}
}
