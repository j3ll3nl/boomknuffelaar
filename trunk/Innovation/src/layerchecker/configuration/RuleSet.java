package layerchecker.configuration;

import java.util.ArrayList;

import layerchecker.analyse.AbstractDefaultRule;
import main.Logger;

public class RuleSet {
	
	private ArrayList<AbstractDefaultRule> rules;
	
	public RuleSet() {
		Logger.getInstance().log(this.getClass().getSimpleName());
	}

	@SuppressWarnings("unchecked")
	protected ArrayList<AbstractDefaultRule> getRules() {
		return (ArrayList<AbstractDefaultRule>) this.rules.clone();
	}

}
