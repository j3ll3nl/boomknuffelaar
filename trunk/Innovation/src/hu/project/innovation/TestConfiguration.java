package hu.project.innovation;

import hu.project.innovation.configuration.model.Configuration;
import hu.project.innovation.configuration.model.ConfigurationService;
import hu.project.innovation.utils.Log;

public class TestConfiguration {
	public TestConfiguration() {
		try {
			// Configuration
			ConfigurationService conf = ConfigurationService.getInstance();

			// Output settings
			conf.setProjectPath("C:\\Users\\Stefan Kemp\\Development\\eclipse\\SampleMavenProject");
			conf.setOutputPath("C:\\");
			conf.setOutputType(Configuration.OUTPUT_FORMAT_TEXT);

			// Architecture
			conf.newConfiguration("Test Architecture", "A first test");

			// Layers
			conf.newLayer("A", "Presentation logic");
			conf.newLayer("B", "Domain logic");
			conf.newLayer("C", "Task specific logic");
			conf.newLayer("D", "Task specific logic");
			conf.newLayer("E", "Task specific logic");
			conf.newLayer("F", "Task specific logic");
			conf.getConfiguration().autoUpdateLayerSequence();

			// Software units
			conf.newSoftwareUnit(conf.getLayer("UI"), "hu.project.innovation.configuration.view", "package");
			conf.newSoftwareUnit(conf.getLayer("UI"), "hu.project.innovation.report.view", "package");
			conf.newSoftwareUnit(conf.getLayer("Task"), "hu.project.innovation.configuration.controller", "package");
			conf.newSoftwareUnit(conf.getLayer("Domain"), "hu.project.innovation.configuration.model", "package");
			conf.newSoftwareUnit(conf.getLayer("Domain"), "hu.project.innovation.report.model", "package");

			// Applied rules
			conf.newAppliedRule("UI", "Domain", "SkipLayerRule");
			conf.newAppliedRule("Task", "UI", "BackCallRule");
			conf.newAppliedRule("Domain", "Task", "BackCallRule");
		} catch (Exception e) {
			Log.e(this, "Error: " + e.getMessage());

		}

	}
}
