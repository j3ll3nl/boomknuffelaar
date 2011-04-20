package hu.project.innovation;

import hu.project.innovation.configuration.model.ArchitectureDefinition;
import hu.project.innovation.configuration.model.BackCallRule;
import hu.project.innovation.configuration.model.Layer;
import hu.project.innovation.configuration.model.SoftwareUnitDefinition;

import java.io.File;
import java.io.IOException;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class ArchDefXMLReader {

	public static void main(String[] args) {

		ArchDefXMLReader reader = new ArchDefXMLReader();
		reader.setArchDefPath("architecture_definition.xml");
		reader.createArchitectureDefinition();

	}

	private DocumentBuilderFactory docBuilderFactory;
	private DocumentBuilder docBuilder;
	private Document doc;

	public ArchDefXMLReader() {

		try {

			docBuilderFactory = DocumentBuilderFactory.newInstance();
			docBuilder = docBuilderFactory.newDocumentBuilder();

		} catch (ParserConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public ArchDefXMLReader(String path) {

		try {

			docBuilderFactory = DocumentBuilderFactory.newInstance();
			docBuilder = docBuilderFactory.newDocumentBuilder();
			doc = docBuilder.parse(new File(path));
			doc.getDocumentElement().normalize();

		} catch (ParserConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SAXException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public void setArchDefPath(String path) {

		try {

			doc = docBuilder.parse(new File(path));
			doc.getDocumentElement().normalize();

		} catch (SAXException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	
	
	public ArchitectureDefinition createArchitectureDefinition() {

		ArchitectureDefinition archDef = new ArchitectureDefinition();

		// Get Layers
		NodeList listOfLayers = doc.getElementsByTagName("layer");

		for (int i = 0; i < listOfLayers.getLength(); i++) {

			Node layerNode = listOfLayers.item(i);

			if (layerNode.getNodeType() == Node.ELEMENT_NODE) {

				// Get Layer Info
				Element layerElement = (Element) layerNode;
				String layerId = getElementValue(layerElement, "id");
				String layerName = getElementValue(layerElement, "name");
				String layerDescription = getElementValue(layerElement, "description");

				// Add Layer to Architecture Definition
				Layer layer = new Layer(layerId, layerDescription);
				archDef.addLayer(layer);

				// Get Software Units
				NodeList listOfSoftwareUnits = layerElement.getElementsByTagName("softwareUnit");

				for (int j = 0; j < listOfSoftwareUnits.getLength(); j++) {

					Node softwareUnitNode = listOfSoftwareUnits.item(j);

					if (softwareUnitNode.getNodeType() == Node.ELEMENT_NODE) {

						// Get Software Unit Info
						Element softwareUnitElement = (Element) softwareUnitNode;
						String suType = getElementValue(softwareUnitElement, "type");

						// Add Software Unit to Layer
						SoftwareUnitDefinition sud = new SoftwareUnitDefinition();
						sud.setType(suType);
						layer.addSoftwareUnit(sud);

					}

				}

				// Get Rule Definitions
				NodeList listOfRuleDefinitions = layerElement.getElementsByTagName("ruleDefinition");

				for (int j = 0; j < listOfRuleDefinitions.getLength(); j++) {

					Node ruleDefinitionNode = listOfRuleDefinitions.item(j);

					if (ruleDefinitionNode.getNodeType() == Node.ELEMENT_NODE) {

						// Get Rule Definition Info
						Element ruleDefinitionElement = (Element) ruleDefinitionNode;
						String rule = getElementValue(ruleDefinitionElement, "rule");
						String toLayer = getElementValue(ruleDefinitionElement, "toLayer");

						// TODO Add Rule Definition to Layer
						BackCallRule ruleType = new BackCallRule();
						layer.addAppliedRule(ruleType, null);

						// Get Rule Exceptions
						NodeList listOfRuleExceptions = ruleDefinitionElement.getElementsByTagName("exceptions");

						for (int k = 0; k < listOfRuleExceptions.getLength(); k++) {

							Node ruleExceptionNode = listOfRuleExceptions.item(k);

							if (ruleExceptionNode.getNodeType() == Node.ELEMENT_NODE) {

								// Get Software Unit
								Element ruleExceptionElement = (Element) ruleExceptionNode;
								String softwareUnitt = getElementValue(ruleExceptionElement, "softwareUnitt");

								// TODO Add Exception to Rule Definition

							}

						}

					}

				}

			}

		}

		return archDef;

	}

	private void getElements(Element element, String tagName, String[] elementTagNames) {
		
		NodeList list = element.getElementsByTagName(tagName);
		
		for(int i=0; i<list.getLength(); i++) {
			
			Node node = list.item(i);
			
			if(node.getNodeType() == Node.ELEMENT_NODE) {
							
				// Get Software Unit
				Element elementt = (Element) node;
				
				for (int j=0; j<elementTagNames.length; j++) {
					String value = getElementValue(elementt, elementTagNames[j]);
				}

			}
			
		}
	}
	
	private String getElementValue(Element element, String tagName) {

		NodeList list = element.getElementsByTagName(tagName);
		Element valueElement = (Element) list.item(0);
		System.out.println(tagName + " : " + valueElement.getChildNodes().item(0).getNodeValue().trim());
		return valueElement.getChildNodes().item(0).getNodeValue().trim();

	}

}
