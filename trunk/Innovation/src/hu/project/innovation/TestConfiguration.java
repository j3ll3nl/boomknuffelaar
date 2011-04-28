package hu.project.innovation;

import hu.project.innovation.configuration.model.Configuration;
import hu.project.innovation.configuration.model.ConfigurationService;
import hu.project.innovation.configuration.model.ConfigurationServiceIF;

public class TestConfiguration {
	public TestConfiguration() {
		// Configuration
		ConfigurationServiceIF conf = ConfigurationService.getInstance();

		// Output settings
		conf.setOutputPath("/a/path/to/the/output/");
		conf.setOutputFormat(Configuration.OUTPUT_FORMAT_TEXT);

		// Architecture
		conf.newConfiguration("Test Architecture", "A first test");

		// Layers
		conf.newLayer("UI", "Presentation logic");
		conf.newLayer("Domain", "Domain logic");
		conf.newLayer("Task", "Task specific logic");

		// Software units
		conf.newSoftwareUnit("UI", "hu.project.innovation.configuration.view", "package");
		conf.newSoftwareUnit("UI", "hu.project.innovation.report.view", "package");
		conf.newSoftwareUnit("Task", "hu.project.innovation.configuration.controller", "package");
		conf.newSoftwareUnit("Domain", "hu.project.innovation.configuration.model", "package");
		conf.newSoftwareUnit("Domain", "hu.project.innovation.report.model", "package");

		// Applied rules
		conf.newAppliedRule("UI", "Domain", "SkipLayerRule");
		conf.newAppliedRule("Task", "UI", "BackCallRule");
		conf.newAppliedRule("Domain", "Task", "BackCallRule");

	}
}
