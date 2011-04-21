package hu.project.innovation;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Observable;

public class Logger extends Observable {
	
	private static Logger instance;
	
	public final static String 
		OUTPUT 	= "OUTPUT",
		ERROR 	= "ERROR",
		INFO 	= "INFO",
		OBJECT	= "OBJECT",
		
		CLEARED = "output cleared";
	
	private static LinkedList<String> messages;
	
	private Logger() {
		messages = new LinkedList<String>();
	}
	
	public static Logger getInstance() {
		if(instance == null) {
			instance = new Logger();
		}
		return instance;
	}
	
	public static void log(Object o) {
		log(o.getClass().getSimpleName(),OBJECT);
	}
	
	public static void log(String message) {
		log(message, OUTPUT);
	}
	
	public static void log(String message, String type) {
		String fullMessage = 
			getDateTime() + " [" + type.toUpperCase() + "]\t" + message; 
		
		if(type.equals(ERROR)) {
			System.err.println(fullMessage);
		} else {
			System.out.println(fullMessage);
		}
		if(!type.equals(INFO) && !type.equals(OBJECT)) {
			messages.add(message);	
			getInstance().setChanged();
		}
		getInstance().notifyObservers();
	}
	
	public String toString() {
		Iterator<String> strings = messages.listIterator();
		return this.implode(strings, "\n");
	}

	public static void clear() {
		if(messages.size() > 0) {
			messages = new LinkedList<String>();
			getInstance().setChanged();
			log(CLEARED, INFO);
		}
	}
	
	private String implode(Iterator<String> strings, String glue) {
		if (strings.hasNext() == false) {
			return "";
		}
		if (glue == null) {
			glue = "\n";
		}
		StringBuilder sb = new StringBuilder();
		while (strings.hasNext()) {
			sb.append(strings.next()).append(glue);
		}
		return sb.substring(0, sb.length() - glue.length());
	}
	
	private static String getDateTime() {
		DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy | HH:mm:ss");
		Date date = new Date();
		return dateFormat.format(date);
	}
}
