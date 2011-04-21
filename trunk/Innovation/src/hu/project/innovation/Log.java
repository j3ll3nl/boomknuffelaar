package hu.project.innovation;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Log {
	private static int tabsBetweenClassAndMessage = 3;

	/**
	 * Print an information output.
	 * 
	 * @param className Current classname: getClass().getSimpleName()
	 * @param message The message, this is often the method name like: i() - ....
	 */
	public static void i(Object className, String message) {
		int numberOfTabs = (tabsBetweenClassAndMessage) - (className.getClass().getSimpleName().length() / 8);

		System.out.println(getDateTime() + "  INFO\t" + className.getClass().getSimpleName() + tabs(numberOfTabs) + " - " + message);
	}

	/**
	 * Print an error output.
	 * 
	 * @param className Current classname: getClass().getSimpleName()
	 * @param message The message, this is often the method name like: i() - ....
	 */
	public static void e(Object className, String message) {
		int numberOfTabs = (tabsBetweenClassAndMessage) - (className.getClass().getSimpleName().length() / 8);

		System.out.println(getDateTime() + "  ERROR\t" + className.getClass().getSimpleName() + tabs(numberOfTabs) + " - " + message);
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
