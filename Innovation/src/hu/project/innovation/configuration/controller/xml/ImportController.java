package hu.project.innovation.configuration.controller.xml;

import java.io.File;
import java.util.HashSet;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

public class ImportController extends Controller {

	private HashSet<AppliedRuleData> unknownAppliedRules = new HashSet<AppliedRuleData>();

	public void importXML(File file) throws Exception {
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		DocumentBuilder db = dbf.newDocumentBuilder();
		Document doc = db.parse(file);
		doc.getDocumentElement().normalize();

		// Start with reading the settings
		NodeList settingNode = doc.getElementsByTagName(this.setting);
		for (int a = 0; a < settingNode.getLength(); a++) {
			Element settingElement = (Element) settingNode.item(a);

			service.setProjectPath(getValue(settingElement, this.project_path));
			service.setOutputPath(getValue(settingElement, this.output_path));
			service.setOutputType(getValue(settingElement, this.output_type));
		}

		// Start with reading the architecture tag
		NodeList architectureNode = doc.getElementsByTagName(this.archtiecture);
		for (int a = 0; a < architectureNode.getLength(); a++) {
			Element architectureElement = (Element) architectureNode.item(a);

			// Create a new architecture definition
			service.newConfiguration(getValue(architectureElement, this.architecture_name), getValue(architectureElement, this.architecture_description));

			// Find any layers
			NodeList layersNode = architectureElement.getElementsByTagName(this.layer);
			for (int b = 0; b < layersNode.getLength(); b++) {
				Element layerElement = (Element) layersNode.item(b);

				// Create a new layer
				int layer_id = service.newLayer(getValue(layerElement, this.layer_name), getValue(layerElement, this.layer_description));
				service.setLayerInterfaceAccesOnly(layer_id, (getValue(layerElement, this.layer_interfaceAccesOnly) == "1" ? true : false));


				// Read software units
				NodeList softwareUnitsNode = layerElement.getElementsByTagName(this.softwareunit);
				for (int c = 0; c < softwareUnitsNode.getLength(); c++) {
					Element softwareUnitElement = (Element) softwareUnitsNode.item(c);

					// Create the software unit
					long softwareunit_id = service.newSoftwareUnit(layer_id, getValue(softwareUnitElement, this.softwareunit_name), getValue(softwareUnitElement, this.softwareunit_type));

					// Each software unit could contain exceptions
					NodeList exceptionsNode = softwareUnitElement.getElementsByTagName(this.exception);
					for (int d = 0; d < exceptionsNode.getLength(); d++) {
						Element exceptionElement = (Element) exceptionsNode.item(d);
						service.newSoftwareUnitException(layer_id, softwareunit_id, getValue(exceptionElement, this.exception_name), getValue(exceptionElement, this.exception_type));
					}
				}

				// Read applied rules
				NodeList appliedRulesNode = layerElement.getElementsByTagName(this.appliedrule);
				for (int e = 0; e < appliedRulesNode.getLength(); e++) {
					Element appliedRuleElement = (Element) appliedRulesNode.item(e);

					int layer_id_to = Integer.parseInt(getValue(appliedRuleElement, this.appliedrule_tolayer));

					long appliedRule_id = service.newAppliedRule(layer_id, layer_id_to, getValue(appliedRuleElement, this.appliedrule_ruleType));
					
					unknownAppliedRules.add(new AppliedRuleData(layer_id_to, appliedRule_id));
					
					// Each applied rule could contain exceptions
					NodeList exceptionsNode = appliedRuleElement.getElementsByTagName(this.exception);
					for (int f = 0; f < exceptionsNode.getLength(); f++) {
						Element exceptionElement = (Element) exceptionsNode.item(f);
						service.newAppliedRuleException(layer_id, appliedRule_id, getValue(exceptionElement, this.exception_name), getValue(exceptionElement, this.exception_type));
					}
				}
			}
		}
		
		for(AppliedRuleData unknownAppliedRule : unknownAppliedRules){
			long applied_rule_id = unknownAppliedRule.getAppliedRule_id();
			int layer_id_to = unknownAppliedRule.getLayer_id_to();
			
			int appliedRuleFromLayer = service.getAppliedRuleFromLayer(applied_rule_id);
						
			service.setAppliedRuleToLayer(appliedRuleFromLayer, applied_rule_id, layer_id_to);
			
		}
	}
}
