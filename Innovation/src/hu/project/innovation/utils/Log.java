package hu.project.innovation.utils;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Logger;

public class Log {
	private static final int tabsBetweenClassAndMessage = 3;
	public static final String INFO = "INFO", ERROR = "ERROR";
	
	private Log() {}

	/**
	 * Print an information output.
	 * 
	 * @param className Current classname: getClass().getSimpleName()
	 * @param message The message, this is often the method name like: i() - ....
	 */
	public static void i(Object o, String message) {
		log(o, message, INFO);
	}

	/**
	 * Print an error output.
	 * 
	 * @param className Current classname: getClass().getSimpleName()
	 * @param message The message, this is often the method name like: i() - ....
	 */
	public static void e(Object o, String message) {
		log(o, message, ERROR);
	}
	
	public static void log(Object o, String message, String type) {
		int numberOfTabs = (tabsBetweenClassAndMessage) - (o.getClass().getSimpleName().length() / 8);
		String fullMessage = 
			getDateTime() + "  " 
			+ type + "\t" 
			+ o.getClass().getSimpleName() 
			+ tabs(numberOfTabs) 
			+ " - " + message;
		System.out.println(fullMessage);
	}

	/**
	 * Private function for this class wich will return the current time.
	 * 
	 * @return
	 */
	private static String getDateTime() {
		DateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");
		Date date = new Date();
		return dateFormat.format(date);
	}

	private static String tabs(int num) {
		String ret = "";
		for (int i = 0; i < num; i++) {
			ret += "\t";
		}
		return ret;
	}
}
