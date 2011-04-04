package layerchecker.configuration;

import java.util.HashMap;

import main.Logger;

public class Configuration {
	
	public static final String OUTPUT_PATH = "output_path";
	public static final String OUTPUT_FORMAT = "output_format";
	
	private RuleSet ruleSet;
	private HashMap<String, String> settings;
	
	public Configuration() {
		Logger.getInstance().log(this.getClass().getSimpleName());
		
		this.ruleSet = new RuleSet();
	}
	
	public RuleSet getRuleSet() {
		return this.ruleSet;
	}

	public void setSetting(String key, String value) {
		this.settings.put(key, value);
		
	}

}
