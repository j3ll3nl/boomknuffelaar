package hu.project.innovation.configuration.model;

import hu.project.innovation.Logger;

import java.util.Set;

public class ConfigurationService {
	
	private Configuration configuration;
	private static ConfigurationService instance;
	
	private ConfigurationService() {
		Logger.getInstance().log(this.getClass().getSimpleName());
		
		this.configuration = new Configuration();
	}
	
	public Set<String> getRules() {
		//return this.configuration.getRuleSet().getRules();
		return null;
	}
	
	public void setRuleStatus(String ruleName, boolean status){
		//this.configuration.getRuleSet().setRuleStatus(ruleName, status);
	}
	
	public void setOutputPath(String path) {
		this.configuration.setSetting(Configuration.OUTPUT_PATH, path);
	}
	
	public String getOutputPath() {
		return this.configuration.getSetting(Configuration.OUTPUT_PATH);
	}
	
	public void setOutputFormat(String format) {
		this.configuration.setSetting(Configuration.OUTPUT_FORMAT, format);
	}
	
	public static ConfigurationService getInstance() {
		if(instance == null) {
			instance = new ConfigurationService();
		}
		return instance;
	}

	public boolean ruleIsActive(String name) {
//		return this.configuration.getRuleSet().ruleIsActive(name);
		return true;
	}
}
