package hu.project.innovation;
import hu.project.innovation.configuration.model.ArchitectureDefinition;
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
		
		for(int i=0; i<listOfLayers.getLength(); i++) {
			
			Node layerNode = listOfLayers.item(i);
			
			if(layerNode.getNodeType() == Node.ELEMENT_NODE) {
				
				Element layerElement = (Element) layerNode;
				
				// Get Layer ID
				NodeList idList = layerElement.getElementsByTagName("id");
				Element idElement = (Element) idList.item(0);
				String layerId = idElement.getChildNodes().item(0).getNodeValue().trim();
				System.out.println(layerId);
				
				// Get Layer Description
				NodeList descList = layerElement.getElementsByTagName("description");
				Element descElement = (Element) descList.item(0);
				String layerDescription = descElement.getChildNodes().item(0).getNodeValue().trim();
				System.out.println(layerDescription);
				
				// Add Layer to Architecture Definition
				Layer layer = new Layer(layerId);
				layer.setDescription(layerDescription);
				archDef.addLayer(layer);

				// Get Software Units
				NodeList listOfSoftwareUnits = layerElement.getElementsByTagName("softwareUnit");
				
				for(int j=0; j<listOfSoftwareUnits.getLength(); j++) {
					
					Node softwareUnitNode = listOfSoftwareUnits.item(j);
					
					if(softwareUnitNode.getNodeType() == Node.ELEMENT_NODE) {
						
						Element softwareUnitElement = (Element) softwareUnitNode;
						
						// Get Software Unit Type
						NodeList typeList = softwareUnitElement.getElementsByTagName("type");
						Element typeElement = (Element) typeList.item(0);
						String suType = typeElement.getChildNodes().item(0).getNodeValue().trim();
						System.out.println(suType);
						
						// Add Software Unit to Layer
						SoftwareUnitDefinition sud = new SoftwareUnitDefinition();
						sud.setType(suType);
						layer.addSoftwareUnit(sud);
					
					}
					
				}
				
				// Get Rule Definitions
				NodeList listOfRuleDefinitions = layerElement.getElementsByTagName("ruleDefinition");
				
				for(int j=0; j<listOfRuleDefinitions.getLength(); j++) {
					
					Node ruleDefinitionNode = listOfRuleDefinitions.item(j);
					
					if(ruleDefinitionNode.getNodeType() == Node.ELEMENT_NODE) {
						
						Element ruleDefinitionElement = (Element) ruleDefinitionNode;
						
						// Get Rule Definition Rule
						NodeList ruleList = ruleDefinitionElement.getElementsByTagName("rule");
						Element ruleElement = (Element) ruleList.item(0);
						String rdRule = ruleElement.getChildNodes().item(0).getNodeValue().trim();
						System.out.println(rdRule);
						
						// Get Rule Definition To-Layer
						NodeList toLayerList = ruleDefinitionElement.getElementsByTagName("toLayer");
						Element toLayerElement = (Element) toLayerList.item(0);
						String toLayer = toLayerElement.getChildNodes().item(0).getNodeValue().trim();
						System.out.println(toLayer);
						
						// TODO Add Rule Definition to Layer
						
						// Get Rule Exceptions
						NodeList listOfRuleExceptions = ruleDefinitionElement.getElementsByTagName("exceptions");
						
						for(int k=0; k<listOfRuleExceptions.getLength(); k++) {
							
							Node ruleExceptionNode = listOfRuleExceptions.item(k);
							
							if(ruleExceptionNode.getNodeType() == Node.ELEMENT_NODE) {
								
								Element ruleExceptionElement = (Element) ruleExceptionNode;
								
								// Get Software Unit
								NodeList exceptionList = ruleExceptionElement.getElementsByTagName("softwareUnitt");
								Element exceptionElement = (Element) exceptionList.item(0);
								String ruleException = exceptionElement.getChildNodes().item(0).getNodeValue().trim();
								System.out.println(ruleException);
								
								// TODO Add Exception to Rule Definition
							
							}
							
						}
					
					}
							
				}
				
			}
			
		}
		
		return archDef;
	
	}
	
}
