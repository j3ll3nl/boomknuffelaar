package main;

import layerchecker.configuration.Configuration;
import layerchecker.configuration.ConfigurationService;
import layerchecker.gui.GuiController;

public class Main {
	
	public static void main(String[] args) {
		
		// Gui test
		
		GuiController gui = new GuiController();
		
		ConfigurationService conf = ConfigurationService.getInstance();
		conf.setRuleStatus("backcall", false);
		conf.setOutputPath("/sjaak/klaas/output/here/");
		conf.setOutputFormat(Configuration.OUTPUT_FORMAT_TEXT);
		
		Logger.getInstance().log(conf.getOutputPath());
		
		
		
	}
}