package hu.project.innovation;

import net.sourceforge.pmd.PMD;
import hu.project.innovation.analyse.model.AnalyseService;
import hu.project.innovation.configuration.model.Configuration;
import hu.project.innovation.configuration.model.ConfigurationService;
import hu.project.innovation.configuration.model.ConfigurationServiceIF;

public class MainJan {

	public static void main(String[] args) {

		// Gui test

		GuiController gui = new GuiController();
//		gui.initCommandReader();
		
		// Configuration test

		ConfigurationServiceIF conf = ConfigurationService.getInstance();
		conf.setRuleStatus("backcall", true);
		conf.setOutputPath("/a/path/to/the/output/");
		conf.setOutputFormat(Configuration.OUTPUT_FORMAT_TEXT);

		Logger.getInstance().log(conf.getOutputPath());

		// Definition test

		conf.newArchitecture("Architecture", "A first test");
		conf.newLayer("UI-Layer", "Presentation logic");
		conf.newLayer("Domain-Layer", "Domain logic");
		conf.newSoftwareUnit(
				"UI-Layer", 
				"hu.project.innovation.configuration.view", 
				"package");
		conf.newSoftwareUnit(
				"UI-Layer", 
				"hu.project.innovation.report.view", 
				"package");
		conf.newSoftwareUnit(
				"Domain-Layer", 
				"hu.project.innovation.configuration.model", 
				"package");
		conf.newAppliedRule(
				"UI-Layer",
				"Domain-Layer",
				"BackCallRule");
		
		Logger.getInstance().log(conf.architectureToXML());
		// Analyse test

		AnalyseService analysis = AnalyseService.getInstance();
		analysis.startAnalyse();
		
		PMD.main(args);

	}
}