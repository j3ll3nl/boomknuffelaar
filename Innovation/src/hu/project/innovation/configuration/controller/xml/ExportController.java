package hu.project.innovation.configuration.controller.xml;

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
			export.append("\t\t<" + this.architecture_name + ">" + service.getArchitectureName() + "</" + this.architecture_name + ">\n");
			export.append("\t\t<" + this.architecture_description + ">" + service.getArchitectureDescription() + "</" + this.architecture_description + ">\n");

			// Layers
			ArrayList<Integer> layers = service.getLayers();
			export.append("\t\t<" + this.layer + "s>\n");
			for (int layer_id : layers) {

				// The current layer
				export.append("\t\t\t<" + this.layer + ">\n");
				export.append("\t\t\t\t<" + this.layer_id + ">" + layer_id + "</" + this.layer_id + ">\n");
				export.append("\t\t\t\t<" + this.layer_name + ">" + service.getLayerName(layer_id) + "</" + this.layer_name + ">\n");
				export.append("\t\t\t\t<" + this.layer_description + ">" + service.getLayerDescription(layer_id) + "</" + this.layer_description + ">\n");
				export.append("\t\t\t\t<" + this.layer_interfaceAccesOnly + ">" + (service.getLayerInterfaceOnly(layer_id) ? "1" : "0") + "</" + this.layer_interfaceAccesOnly + ">\n");

				// Software units
				export.append("\t\t\t\t<" + this.softwareunit + "s>\n");
				ArrayList<Long> softwareUnitDefinitions = service.getSoftwareUnits(layer_id);
				for (long softwareUnitDefinition_id : softwareUnitDefinitions) {
					export.append("\t\t\t\t\t<" + this.softwareunit + ">\n");
					export.append("\t\t\t\t\t\t<" + this.softwareunit_name + ">" + service.getSoftwareUnitName(layer_id, softwareUnitDefinition_id) + "</" + this.softwareunit_name + ">\n");
					export.append("\t\t\t\t\t\t<" + this.softwareunit_type + ">" + service.getSoftwareUnitType(layer_id, softwareUnitDefinition_id) + "</" + this.softwareunit_type + ">\n");

					// Exceptions
					export.append("\t\t\t\t\t\t<" + this.exception + "s>\n");
					ArrayList<Long> exceptions = service.getSoftwareUnitExceptions(layer_id, softwareUnitDefinition_id);
					for (long exception : exceptions) {
						export.append("\t\t\t\t\t\t\t<" + this.exception + ">\n");
						export.append("\t\t\t\t\t\t\t\t<" + this.exception_name + ">" + service.getSoftwareUnitExceptionName(layer_id, softwareUnitDefinition_id, exception) + "</" + this.exception_name + ">\n");
						export.append("\t\t\t\t\t\t\t\t<" + this.exception_type + ">" + service.getSoftwareUnitExceptionType(layer_id, softwareUnitDefinition_id, exception) + "</" + this.exception_type + ">\n");
						export.append("\t\t\t\t\t\t\t</" + this.exception + ">\n");
					}

					export.append("\t\t\t\t\t\t</" + this.exception + "s>\n");
					export.append("\t\t\t\t\t</" + this.softwareunit + ">\n");
				}
				export.append("\t\t\t\t</" + this.softwareunit + "s>\n");

				// Applied rules
				export.append("\t\t\t\t<" + this.appliedrule + "s>\n");
				ArrayList<Long> appliedRules = service.getAppliedRules(layer_id);
				for (long appliedRule_id : appliedRules) {
					export.append("\t\t\t\t\t<" + this.appliedrule + ">\n");
					export.append("\t\t\t\t\t\t<" + this.appliedrule_ruleType + ">" + service.getAppliedRuleRuleType(layer_id, appliedRule_id) + "</" + this.appliedrule_ruleType + ">\n");
					export.append("\t\t\t\t\t\t<" + this.appliedrule_tolayer + ">" + service.getAppliedRuleToLayer(layer_id, appliedRule_id) + "</" + this.appliedrule_tolayer + ">\n");

					// Exceptions
					export.append("\t\t\t\t\t\t<" + this.exception + "s>\n");
					ArrayList<Long> exceptions = service.getAppliedRuleExceptions(layer_id, appliedRule_id);
					for (long exception : exceptions) {
						export.append("\t\t\t\t\t\t\t<" + this.exception + ">\n");
						export.append("\t\t\t\t\t\t\t\t<" + this.exception_name + ">" + service.getAppliedruleExceptionName(layer_id, appliedRule_id, exception) + "</" + this.exception_name + ">\n");
						export.append("\t\t\t\t\t\t\t\t<" + this.exception_type + ">" + service.getAppliedruleExceptionType(layer_id, appliedRule_id, exception) + "</" + this.exception_type + ">\n");
						export.append("\t\t\t\t\t\t\t</" + this.exception + ">\n");
					}

					export.append("\t\t\t\t\t\t</" + this.exception + "s>\n");
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
}
