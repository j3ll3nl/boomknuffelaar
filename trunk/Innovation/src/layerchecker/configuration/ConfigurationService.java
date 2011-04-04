package layerchecker.configuration;

import java.util.Set;

import main.Logger;

public class ConfigurationService {
	
	private Configuration configuration;
	private static ConfigurationService instance;
	
	private ConfigurationService() {
		Logger.getInstance().log(this.getClass().getSimpleName());
		
		this.configuration = new Configuration();
	}
	
	public Set<String> getRules() {
		return this.configuration.getRuleSet().getRules();
	}
	
	public void setRuleStatus(String ruleName, boolean status){
		this.configuration.getRuleSet().setRuleStatus(ruleName, status);
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

}
