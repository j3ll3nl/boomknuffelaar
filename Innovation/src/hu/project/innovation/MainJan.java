package hu.project.innovation;

import hu.project.innovation.analyse.model.AnalyseService;
import hu.project.innovation.configuration.model.Configuration;
import hu.project.innovation.configuration.model.ConfigurationService;
import hu.project.innovation.configuration.model.ConfigurationServiceIF;
import net.sourceforge.pmd.PMD;

public class MainJan {

	public static void main(String[] args) {

		// Configuration test

		ConfigurationServiceIF conf = ConfigurationService.getInstance();
		conf.setRuleStatus("backcall", true);
		conf.setOutputPath("/a/path/to/the/output/");
		conf.setOutputFormat(Configuration.OUTPUT_FORMAT_TEXT);

		// Definition test

		conf.newArchitecture("Architecture", "A first test");

		// Layers
		conf.newLayer("UI-Layer", "Presentation logic");
		conf.newLayer("Domain-Layer", "Domain logic");
		conf.newLayer("Task-Layer", "Domain logic");

		// SU
		conf.newSoftwareUnit("UI-Layer", "hu.project.innovation.configuration.view", "package");
		conf.newSoftwareUnit("UI-Layer", "hu.project.innovation.report.view", "package");
		conf.newSoftwareUnit("Task-Layer", "hu.project.innovation.configuration.controller", "package");
		conf.newSoftwareUnit("Domain-Layer", "hu.project.innovation.configuration.model", "package");

		// Rules
		conf.newAppliedRule("UI-Layer", "Domain-Layer", "SkipLayerRule");
		conf.newAppliedRule("Task-Layer", "UI-Layer", "BackCallRule");
		conf.newAppliedRule("Domain-Layer", "Task-Layer", "BackCallRule");

//		Log.i(conf,conf.architectureToXML());
		// Analyse test

//		AnalyseService analysis = AnalyseService.getInstance();
//		analysis.startAnalyse();

		PMD.main(args);

	}
}