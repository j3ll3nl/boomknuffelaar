package hu.project.innovation.configuration.model;

import hu.project.innovation.Log;
import hu.project.innovation.Logger;

import java.util.HashMap;

public class Configuration {

	public static final String OUTPUT_PATH = "output_path";
	public static final String OUTPUT_FORMAT = "output_format";
	public static final String OUTPUT_FORMAT_TEXT = "text";

	private HashMap<String, String> settings;
	private HashMap<String, AbstractRuleType> rules;

	protected Configuration() {
		Log.i(getClass().getSimpleName(), "constructor()");		

		this.rules = new HashMap<String, AbstractRuleType>();
		this.addRuleType(new BackCallRule());

		this.settings = new HashMap<String, String>();
	}

	public AbstractRuleType addRuleType(AbstractRuleType ruleType) {
		return this.rules.put(ruleType.getName(), ruleType);
	}

	public AbstractRuleType getRuleType(String ruleName) {
		return this.rules.get(ruleName);
	}

	public void setSetting(String key, String value) {
		this.settings.put(key, value);

		Logger.getInstance().log("Setting set: \"" + key + "\" -> \"" + value + "\"");
	}

	public String getSetting(String key) {
		return this.settings.get(key);
	}

	public AbstractRuleType getRule(String ruleName) {
		return this.rules.get(ruleName);
	}
}