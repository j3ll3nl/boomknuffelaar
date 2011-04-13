package hu.project.innovation.configuration.model;

import hu.project.innovation.Logger;

import java.util.HashMap;

public class Configuration {
	
	public static final String OUTPUT_PATH = "output_path";
	public static final String OUTPUT_FORMAT = "output_format";
	public static final String OUTPUT_FORMAT_TEXT = "text";
	
	//private RuleSet ruleSet;
	private HashMap<String, String> settings;
	
	protected Configuration() {
		Logger.getInstance().log(this.getClass().getSimpleName());
		
		//this.ruleSet = new RuleSet();
		this.settings = new HashMap<String, String>();
	}
	
	public void getRuleSet() {
		//return this.ruleSet;
	}

	public void setSetting(String key, String value) {
		this.settings.put(key, value);
		
		Logger.getInstance().log("Setting set: " + key + " - " + value);		
	}
	
	public String getSetting(String key) {
		return this.settings.get(key);		
	}
}
