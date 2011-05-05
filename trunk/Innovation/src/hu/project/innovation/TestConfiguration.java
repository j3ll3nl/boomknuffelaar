package hu.project.innovation;

import hu.project.innovation.configuration.model.Configuration;
import hu.project.innovation.configuration.model.ConfigurationService;

public class TestConfiguration {
	public TestConfiguration() {
		// Configuration
		ConfigurationService conf = ConfigurationService.getInstance();

		// Output settings
		conf.setProjectPath("C:\\Users\\Stefan Kemp\\Development\\eclipse\\SampleMavenProject");
		conf.setOutputPath("C:\\");
		conf.setOutputFormat(Configuration.OUTPUT_FORMAT_TEXT);

		// Architecture
		conf.newConfiguration("Test Architecture", "A first test");

		// Layers
		conf.newLayer("UI", "Presentation logic");
		conf.newLayer("Domain", "Domain logic");
		conf.newLayer("Task", "Task specific logic");

		// Software units
		conf.newComponent("UI", "hu.project.innovation.configuration.view", "package");
		conf.newComponent("UI", "hu.project.innovation.report.view", "package");
		conf.newComponent("Task", "hu.project.innovation.configuration.controller", "package");
		conf.newComponent("Domain", "hu.project.innovation.configuration.model", "package");
		conf.newComponent("Domain", "hu.project.innovation.report.model", "package");

		// Applied rules
		conf.newAppliedRule("UI", "Domain", "SkipLayerRule");
		conf.newAppliedRule("Task", "UI", "BackCallRule");
		conf.newAppliedRule("Domain", "Task", "BackCallRule");

	}
}
