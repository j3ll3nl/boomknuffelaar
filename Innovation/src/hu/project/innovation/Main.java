package hu.project.innovation;

import hu.project.innovation.analyse.model.AnalyseService;
import hu.project.innovation.configuration.model.Configuration;
import hu.project.innovation.configuration.model.ConfigurationService;

public class Main {

	public static void main(String[] args) {

		// Gui test

		GuiController gui = new GuiController();

		// Configuration test

		ConfigurationService conf = ConfigurationService.getInstance();
		conf.setRuleStatus("backcall", true);
		conf.setOutputPath("/a/path/to/the/output/");
		conf.setOutputFormat(Configuration.OUTPUT_FORMAT_TEXT);

		Logger.getInstance().log(conf.getOutputPath());

		// Definition test

		conf.newArchitecture("Architecture", "A first test");
		conf.newLayer("UI-Layer", "Presentation logic");
		conf.newLayer("Domain-Layer", "Domain logic");
		conf.newSoftwareUnit("UI-Layer", "JustAPackage", "package");
		conf.newSoftwareUnit("UI-Layer", "AnotherPackage", "package");
		conf.newSoftwareUnit("Domain-Layer", "DomainPackage", "package");
		conf.newAppliedRule("UI-Layer","Domain-Layer","BackCallRule");
		
		Logger.getInstance().log(conf.architectureToXML());
		// Analyse test

		AnalyseService analysis = AnalyseService.getInstance();
		analysis.startAnalyse();

	}
}