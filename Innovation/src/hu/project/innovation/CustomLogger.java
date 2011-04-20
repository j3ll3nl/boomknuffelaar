package hu.project.innovation;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class CustomLogger {
	/**
	 * Print an information output.
	 * 
	 * @param className Current classname: getClass().getSimpleName()
	 * @param message The message, this is often the method name like: i() - ....
	 */
	public static void i(String className, String message) {
		System.out.println(getDateTime() + "  INFO\t" + className + " - " + message);
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
}
