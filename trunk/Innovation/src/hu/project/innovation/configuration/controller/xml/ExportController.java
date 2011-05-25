package hu.project.innovation.configuration.controller.xml;

import hu.project.innovation.configuration.model.AppliedRule;
import hu.project.innovation.configuration.model.Layer;
import hu.project.innovation.configuration.model.SoftwareUnitDefinition;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.util.ArrayList;

public class ExportController extends Controller {

	public void exportXML(File file) throws Exception {
		StringBuilder export = new StringBuilder();

		export.append("<?xml version=\"1.0\" encoding=\"utf-8\"?>\n");
		export.append("<configuration>\n");

		// Architecture
		if (service.hasArchitectureDefinition()) {
			// Normal settings like project & output settings
			export.append("\t<" + this.setting + ">\n");
			export.append("\t\t<" + this.project_path + ">" + service.getProjectPath() + "</" + this.project_path + ">\n");
			export.append("\t\t<" + this.output_path + ">" + service.getOutputPath() + "</" + this.output_path + ">\n");
			export.append("\t\t<" + this.output_type + ">" + service.getOutputType() + "</" + this.output_type + ">\n");
			export.append("\t</" + this.setting + ">\n");

			export.append("\t<" + this.archtiecture + ">\n");
			export.append("\t\t<" + this.architecture_name + ">" + service.getConfiguration().getName() + "</" + this.architecture_name + ">\n");
			export.append("\t\t<" + this.architecture_description + ">" + service.getConfiguration().getDescription() + "</" + this.architecture_description + ">\n");

			// Layers
			ArrayList<Layer> layers = service.getLayers();
			export.append("\t\t<" + this.layer + "s>\n");
			for (Layer layer : layers) {
				// The current layer
				export.append("\t\t\t<" + this.layer + ">\n");
				export.append("\t\t\t\t<" + this.layer_id + ">" + layer.getId() + "</" + this.layer_id + ">\n");
				export.append("\t\t\t\t<" + this.layer_name + ">" + layer.getName() + "</" + this.layer_name + ">\n");
				export.append("\t\t\t\t<" + this.layer_description + ">" + layer.getDescription() + "</" + this.layer_description + ">\n");
				export.append("\t\t\t\t<" + this.layer_interfaceAccesOnly + ">" + (layer.isInterfaceAccessOnly() ? "1" : "0") + "</" + this.layer_interfaceAccesOnly + ">\n");

				// Software units
				export.append("\t\t\t\t<" + this.softwareunit + "s>\n");
				ArrayList<SoftwareUnitDefinition> softwareUnitDefinitions = layer.getSoftwareUnitDefinitions();
				for (SoftwareUnitDefinition softwareUnitDefinition : softwareUnitDefinitions) {
					export.append("\t\t\t\t\t<" + this.softwareunit + ">\n");
					export.append("\t\t\t\t\t\t<" + this.softwareunit_name + ">" + softwareUnitDefinition.getName() + "</" + this.softwareunit_name + ">\n");
					export.append("\t\t\t\t\t\t<" + this.softwareunit_type + ">" + softwareUnitDefinition.getType() + "</" + this.softwareunit_type + ">\n");

					// Exceptions
					export.append(exportExceptions(softwareUnitDefinition));

					export.append("\t\t\t\t\t</" + this.softwareunit + ">\n");
				}
				export.append("\t\t\t\t</" + this.softwareunit + "s>\n");

				// Applied rules
				export.append("\t\t\t\t<" + this.appliedrule + "s>\n");
				ArrayList<AppliedRule> appliedRules = layer.getAppliedRules();
				for (AppliedRule appliedRule : appliedRules) {
					export.append("\t\t\t\t\t<" + this.appliedrule + ">\n");
					export.append("\t\t\t\t\t\t<" + this.appliedrule_ruleType + ">" + appliedRule.getRuleType() + "</" + this.appliedrule_ruleType + ">\n");
					export.append("\t\t\t\t\t\t<" + this.appliedrule_tolayer + ">" + appliedRule.getToLayer().getId() + "</" + this.appliedrule_tolayer + ">\n");

					// Exceptions
					export.append(exportExceptions(appliedRule));

					export.append("\t\t\t\t</" + this.appliedrule + ">\n");

				}
				export.append("\t\t\t\t</" + this.appliedrule + "s>\n");

				export.append("\t\t\t</" + this.layer + ">\n");
			}

			export.append("\t\t</" + this.layer + "s>\n");
			export.append("\t</" + this.archtiecture + ">\n");
		}
		export.append("</configuration>\n");

		FileWriter fstream = new FileWriter(file);
		BufferedWriter out = new BufferedWriter(fstream);
		out.write(export.toString());
		out.close();
	}

	private String exportExceptions(SoftwareUnitDefinition softwareUnitDefinition) {
		StringBuilder export = new StringBuilder();
		export.append("\t\t\t\t\t\t<" + this.exception + "s>\n");

		ArrayList<SoftwareUnitDefinition> exceptions = softwareUnitDefinition.getExceptions();
		for (SoftwareUnitDefinition exception : exceptions) {
			export.append("\t\t\t\t\t\t\t<" + this.exception + ">\n");
			export.append("\t\t\t\t\t\t\t\t<" + this.exception_name + ">" + exception.getName() + "</" + this.exception_name + ">\n");
			export.append("\t\t\t\t\t\t\t\t<" + this.exception_type + ">" + exception.getType() + "</" + this.exception_type + ">\n");
			export.append("\t\t\t\t\t\t\t</" + this.exception + ">\n");
		}

		export.append("\t\t\t\t\t\t</" + this.exception + "s>\n");
		return export.toString();
	}

	private String exportExceptions(AppliedRule appliedRule) {
		StringBuilder export = new StringBuilder();
		export.append("\t\t\t\t\t\t\t<" + this.exception + "s>\n");

		ArrayList<SoftwareUnitDefinition> exceptions = appliedRule.getExceptions();
		for (SoftwareUnitDefinition exception : exceptions) {
			export.append("\t\t\t\t\t\t\t\t<" + this.exception + ">\n");
			export.append("\t\t\t\t\t\t\t\t\t<" + this.exception_name + ">" + exception.getName() + "</" + this.exception_name + ">\n");
			export.append("\t\t\t\t\t\t\t\t\t<" + this.exception_type + ">" + exception.getType() + "</" + this.exception_type + ">\n");
			export.append("\t\t\t\t\t\t\t\t</" + this.exception + ">\n");
		}

		export.append("\t\t\t\t\t\t\t</" + this.exception + "s>\n");
		return export.toString();
	}
}
