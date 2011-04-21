package hu.project.innovation.configuration.model;

import hu.project.innovation.Log;

import java.util.HashMap;

public class Configuration {

	public static final String OUTPUT_PATH = "output_path";
	public static final String OUTPUT_FORMAT = "output_format";
	public static final String OUTPUT_FORMAT_TEXT = "text";
	public static final String OUTPUT_FORMAT_XML = "xml";

	private HashMap<String, String> settings;
	private HashMap<String, AbstractRuleType> rules;

	protected Configuration() {
		Log.i(this, "constructor()");

		this.rules = new HashMap<String, AbstractRuleType>();
		this.addRuleType(new BackCallRule());
		this.addRuleType(new SkipLayerRule());

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

		Log.i(this, "Setting set: \"" + key + "\" -> \"" + value + "\"");
	}

	public String getSetting(String key) {
		return this.settings.get(key);
	}

	public AbstractRuleType getRule(String ruleName) {
		return this.rules.get(ruleName);
	}
}
