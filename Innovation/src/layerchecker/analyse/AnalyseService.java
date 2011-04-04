package layerchecker.analyse;

import java.util.ArrayList;

import main.Logger;

public class AnalyseService {
	
	private static AnalyseService instance;
	private ArrayList<AbstractDefaultRule> rules;
	
	public AnalyseService() {
		Logger.getInstance().log(this.getClass().getSimpleName());
		
		this.rules = new ArrayList<AbstractDefaultRule>();
		this.rules.add(new BackCallPrinciple());
	}
	
	public void startAnalyse() {
		for(AbstractDefaultRule rule : rules) {
			rule.checkViolation();
		}
	}
	
	public static AnalyseService getInstance() {
		if(instance == null) {
			instance = new AnalyseService();
		}
		return instance;
	}

}
