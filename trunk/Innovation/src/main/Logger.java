package main;

import java.util.LinkedList;

public class Logger {
	
	private static Logger instance;
	
	private LinkedList<String> messages;
	
	private Logger() {
		this.messages = new LinkedList<String>();
	}
	
	public static Logger getInstance() {
		if(instance == null) {
			instance = new Logger();
		}
		return instance;
	}
	
	public void log(String string) {
		this.messages.add(string);
		System.out.println(string);
	}
	
	public String toString() {
		String[] strings = (String[]) this.messages.toArray();
		return this.implode(strings, "\n");
	}
	
	public String implode(String[] strings, String glue) {
		if (strings.length == 0) {
			return "";
		}
		if (glue == null) {
			glue = "\n";
		}
		StringBuilder sb = new StringBuilder();
		for (String s : strings) {
			sb.append(s).append(glue);
		}
		return sb.substring(0, sb.length() - glue.length());
	}
}
