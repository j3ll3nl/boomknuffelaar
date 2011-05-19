package hu.project.innovation;

import hu.project.innovation.configuration.model.AppliedRule;
import hu.project.innovation.configuration.model.Configuration;
import hu.project.innovation.configuration.model.ConfigurationService;
import hu.project.innovation.configuration.model.SoftwareUnitDefinition;
import hu.project.innovation.configuration.model.rules.BackCallRule;
import hu.project.innovation.configuration.model.rules.SkipLayerRule;
import hu.project.innovation.utils.Log;

import java.io.File;

public class TestConfiguration {
	public TestConfiguration() {
		try {
			// Configuration
			ConfigurationService conf = ConfigurationService.getInstance();

			// Output settings
			File f = new File("src");
			conf.setProjectPath(f.getAbsolutePath());
			conf.setOutputPath("C:\\");
			conf.setOutputType(Configuration.OUTPUT_FORMAT_TEXT);

			// Architecture
//			conf.newConfiguration("Test Architecture", "");
//
//			String ui = "UI";
//			String task = "Task";
//			String domain = "Domain";
//
//			// Layers
//			conf.newLayer(ui, "Presentation logic");
//			conf.newLayer(task, "Task specific logic");
//			conf.newLayer(domain, "Domain logic").setInterfaceAccesOnly(true);
//			conf.getConfiguration().autoUpdateLayerSequence();
//
//			// Software units
//			conf.newSoftwareUnit(conf.getLayer(ui), "hu.project.innovation.configuration.view.*", "package");			
//			conf.newSoftwareUnit(conf.getLayer(ui), "hu.project.innovation.report.view.*", "package");			
//			conf.newSoftwareUnit(conf.getLayer(ui), "hu.project.innovation.analyse.view.*", "package");			
//			conf.newSoftwareUnit(conf.getLayer(task), "hu.project.innovation.configuration.controller.*", "package");
//			conf.newSoftwareUnit(conf.getLayer(task), "hu.project.innovation.analyse.controller.*", "package");
//			conf.newSoftwareUnit(conf.getLayer(domain), "hu.project.innovation.configuration.model.*", "package");			
//			conf.newSoftwareUnit(conf.getLayer(domain), "hu.project.innovation.report.model.*", "package");
//			conf.newSoftwareUnit(conf.getLayer(domain), "hu.project.innovation.analyse.model.*", "package");
//
//			// Applied rules
//			AppliedRule rule = conf.newAppliedRule(conf.getLayer(ui), conf.getLayer(domain), new SkipLayerRule());
//			rule.addException(new SoftwareUnitDefinition("hu.project.innovation.configuration.model.Layer", "class"));
//			conf.newAppliedRule(conf.getLayer(task), conf.getLayer(ui), new BackCallRule());
//			conf.newAppliedRule(conf.getLayer(domain), conf.getLayer(task), new BackCallRule());
		} catch (Exception e) {
			Log.e(this, e.getMessage());
		}
	}
}
