package layerchecker.configuration;

import java.util.HashMap;
import java.util.Set;

import main.Logger;

public class RuleSet {
	
	private HashMap<String, Boolean> rules;
	
	protected RuleSet() {
		Logger.getInstance().log(this.getClass().getSimpleName());
		
		this.rules = new HashMap<String, Boolean>();
	}
	
	public void setRuleStatus(String ruleName, boolean status) {
		this.rules.put(ruleName, status);
		Logger.getInstance().log("Rule set: " + ruleName + " - " + status);
	}

	public Set<String> getRules() {
		return this.rules.keySet();
	}
}
