package main;

import layerchecker.analyse.AnalyseService;
import layerchecker.configuration.Configuration;
import layerchecker.configuration.ConfigurationService;
import layerchecker.definition.DefinitionService;
import layerchecker.definition.SoftwareUnitDefinition;
import layerchecker.gui.GuiController;

public class Main {
	
	public static void main(String[] args) {
		
		// Gui test
		
		GuiController gui = new GuiController();
		gui.notify();
		
		// Configuration test
		
		ConfigurationService conf = ConfigurationService.getInstance();
		conf.setRuleStatus("backcall", true);
		conf.setOutputPath("/a/path/to/the/output/");
		conf.setOutputFormat(Configuration.OUTPUT_FORMAT_TEXT);
		
		Logger.getInstance().log(conf.getOutputPath());
		
		// Definition test
		
		DefinitionService defintion = DefinitionService.getInstance();
		defintion.newArchitectureDefinition("Architecture", "A first test");
		defintion.newLayer("UI-Layer", "Presentation logic");
		defintion.newSoftwareUnit(
				"UI-Layer", "JustAPackage", SoftwareUnitDefinition.PACKAGE);
		
		// Analyse test
		
		AnalyseService analysis = AnalyseService.getInstance();
		analysis.startAnalyse();
		
	}
}