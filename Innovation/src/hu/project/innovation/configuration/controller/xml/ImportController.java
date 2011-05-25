package hu.project.innovation.configuration.controller.xml;

import hu.project.innovation.configuration.model.AppliedRule;
import hu.project.innovation.configuration.model.Layer;
import hu.project.innovation.configuration.model.SoftwareUnitDefinition;
import hu.project.innovation.configuration.model.rules.AbstractRuleType;
import hu.project.innovation.configuration.model.rules.BackCallRule;
import hu.project.innovation.configuration.model.rules.InterfacesOnlyRule;
import hu.project.innovation.configuration.model.rules.SkipLayerRule;

import java.io.File;
import java.util.HashMap;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

public class ImportController extends Controller {

	private HashMap<Integer, AppliedRule> unknownAppliedRules = new HashMap<Integer, AppliedRule>();

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
				int layerid = Integer.parseInt(getValue(layerElement, this.layer_id));
				Layer layer = service.newLayer(getValue(layerElement, this.layer_name), getValue(layerElement, this.layer_description));
				layer.setInterfaceAccesOnly((getValue(layerElement, this.layer_interfaceAccesOnly) == "1" ? true : false)); // Set interface only

				if (unknownAppliedRules.containsKey(layerid)) {
					AppliedRule appliedrule = unknownAppliedRules.get(layerid);
					appliedrule.setToLayer(layer);
					unknownAppliedRules.remove(layerid);
				}

				// Read software units
				NodeList softwareUnitsNode = layerElement.getElementsByTagName(this.softwareunit);
				for (int c = 0; c < softwareUnitsNode.getLength(); c++) {
					Element softwareUnitElement = (Element) softwareUnitsNode.item(c);

					// Create the software unit
					SoftwareUnitDefinition softwareunit = service.newSoftwareUnit(layer, getValue(softwareUnitElement, this.softwareunit_name), getValue(softwareUnitElement, this.softwareunit_type));

					// Each software unit could contain exceptions
					NodeList exceptionsNode = softwareUnitElement.getElementsByTagName(this.exception);
					readExceptions(exceptionsNode, softwareunit);
				}

				// Read applied rules
				NodeList appliedRulesNode = layerElement.getElementsByTagName(this.appliedrule);
				for (int d = 0; d < appliedRulesNode.getLength(); d++) {
					Element appliedRuleElement = (Element) appliedRulesNode.item(d);

					String ruletype = getValue(appliedRuleElement, this.appliedrule_ruleType);
					AbstractRuleType abstractRuleType = null;
					if (ruletype.trim().equals("SkipLayerRule")) {
						abstractRuleType = new SkipLayerRule();
					} else if (ruletype.trim().equals("BackCallRule")) {
						abstractRuleType = new BackCallRule();
					} else if (ruletype.trim().equals("InterfacesOnlyRule")) {
						abstractRuleType = new InterfacesOnlyRule();
					}

					int tolayerid = Integer.parseInt(getValue(appliedRuleElement, this.appliedrule_tolayer));
					Layer toLayer = service.getLayer(tolayerid);

					AppliedRule appliedRule = service.newAppliedRule(layer, toLayer, abstractRuleType);
					if (toLayer == null) {
						unknownAppliedRules.put(layerid, appliedRule);
					}

					// Each applied rule could contain exceptions
					NodeList exceptionsNode = appliedRuleElement.getElementsByTagName(this.exception);
					readExceptions(exceptionsNode, appliedRule);
				}
			}
		}
	}

	/**
	 * Method for reading in the exception
	 * 
	 * @param node
	 * @param softwareunit
	 */
	private void readExceptions(NodeList node, SoftwareUnitDefinition softwareunit) {
		for (int c = 0; c < node.getLength(); c++) {
			Element exceptionElement = (Element) node.item(c);
			service.addException(softwareunit, getValue(exceptionElement, this.exception_name), getValue(exceptionElement, this.exception_type));
		}
	}

	private void readExceptions(NodeList node, AppliedRule appliedRule) {
		for (int c = 0; c < node.getLength(); c++) {
			Element exceptionElement = (Element) node.item(c);
			service.addException(appliedRule, getValue(exceptionElement, this.exception_name), getValue(exceptionElement, this.exception_type));
		}
	}
}
