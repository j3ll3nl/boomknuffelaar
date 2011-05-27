package hu.project.innovation;

import hu.project.innovation.configuration.model.Configuration;
import hu.project.innovation.configuration.model.ConfigurationService;
import hu.project.innovation.configuration.model.rules.AbstractRuleType;
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

			conf.newConfiguration("Innovation Test Architecture", "Deze architectuur definitie wordt gebruikt voor testdoeleinden van onze eigen applicatie.");

			// Layers
			int layer_ui = conf.newLayer("UI", "Presentation logic");
			int layer_task = conf.newLayer("Task", "Task specific logic");
			int layer_domain = conf.newLayer("Domain", "Domain logic");
			conf.setLayerInterfaceAccesOnly(layer_domain, true);

			// Software units
			conf.newSoftwareUnit(layer_ui, "hu.project.innovation.configuration.view.*", "package");
			conf.newSoftwareUnit(layer_ui, "hu.project.innovation.report.view.*", "package");
			conf.newSoftwareUnit(layer_ui, "hu.project.innovation.analyse.view.*", "package");
			conf.newSoftwareUnit(layer_task, "hu.project.innovation.configuration.controller.*", "package");
			conf.newSoftwareUnit(layer_task, "hu.project.innovation.analyse.controller.*", "package");
			conf.newSoftwareUnit(layer_domain, "hu.project.innovation.configuration.model.*", "package");
			conf.newSoftwareUnit(layer_domain, "hu.project.innovation.report.model.*", "package");
			conf.newSoftwareUnit(layer_domain, "hu.project.innovation.analyse.model.*", "package");

			// Applied rules
			long rule = conf.newAppliedRule(layer_ui, layer_domain, AbstractRuleType.skip_call);

			conf.newAppliedRuleException(layer_ui, rule, "hu.project.innovation.configuration.model.Layer", "class");

			conf.newAppliedRule(layer_task, layer_ui, AbstractRuleType.back_call);
			conf.newAppliedRule(layer_domain, layer_task, AbstractRuleType.back_call);
		} catch (Exception e) {
			Log.e(this, e.getMessage());
		}
	}
}
