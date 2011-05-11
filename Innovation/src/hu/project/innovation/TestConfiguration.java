package hu.project.innovation;

import hu.project.innovation.configuration.model.BackCallRule;
import hu.project.innovation.configuration.model.Configuration;
import hu.project.innovation.configuration.model.ConfigurationService;
import hu.project.innovation.configuration.model.SkipLayerRule;
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
			conf.newConfiguration("Test Architecture", "");

			// Layers
			conf.newLayer("UI", "Presentation logic");
			conf.newLayer("Domain", "Domain logic").setInterfaceAccesOnly(true);
			conf.newLayer("Task", "Task specific logic");
			conf.getConfiguration().autoUpdateLayerSequence();

			// Software units
			conf.newSoftwareUnit(conf.getLayer("UI"), "hu.project.innovation.configuration.view", "package");
			conf.newSoftwareUnit(conf.getLayer("UI"), "hu.project.innovation.report.view", "package");
			conf.newSoftwareUnit(conf.getLayer("Task"), "hu.project.innovation.configuration.controller", "package");
			conf.newSoftwareUnit(conf.getLayer("Domain"), "hu.project.innovation.configuration.model", "package");
			conf.newSoftwareUnit(conf.getLayer("Domain"), "hu.project.innovation.report.model", "package");

			// Applied rules
			conf.newAppliedRule(conf.getLayer("UI"), conf.getLayer("Domain"), new SkipLayerRule());
			conf.newAppliedRule(conf.getLayer("Task"), conf.getLayer("UI"), new BackCallRule());
			conf.newAppliedRule(conf.getLayer("Domain"), conf.getLayer("Task"), new BackCallRule());
		} catch (Exception e) {
			Log.e(this, "Error: " + e.getMessage());

		}

	}
}
