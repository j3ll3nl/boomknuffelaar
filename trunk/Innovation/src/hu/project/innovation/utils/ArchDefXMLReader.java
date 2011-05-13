package hu.project.innovation.utils;

import hu.project.innovation.configuration.model.AppliedRule;
import hu.project.innovation.configuration.model.ArchitectureDefinition;
import hu.project.innovation.configuration.model.BackCallRule;
import hu.project.innovation.configuration.model.Layer;
import hu.project.innovation.configuration.model.SkipLayerRule;
import hu.project.innovation.configuration.model.SoftwareUnitDefinition;

import java.io.CharArrayWriter;
import java.io.File;
import java.io.IOException;

import javax.xml.XMLConstants;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Source;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Validator;

import org.w3c.dom.Document;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

/**
 * SAMPLE HOW TO USE:
 * 
 * XMLReader xr = XMLReaderFactory.createXMLReader(); ArchDefXMLReader reader = new ArchDefXMLReader(); xr.setContentHandler(reader); xr.parse( new InputSource(new FileReader(
 * "architecture_definition.xml" )) );
 * 
 * reader.getArchitectureDefinition();
 */

public class ArchDefXMLReader extends DefaultHandler {

	private CharArrayWriter contents = new CharArrayWriter();
	private ArchitectureDefinition ar = new ArchitectureDefinition();
	private Layer currentLayer;
	private SoftwareUnitDefinition currentSoftwareUnit;
	private AppliedRule currentAppliedRule;
	private boolean isSoftwareUnit = false;
	private boolean isAppliedRule = false;

	public void startElement(String namespaceURI, String localName, String qName, Attributes attr) throws SAXException {
		Log.i(this, "startElement(" + localName + ")");
		contents.reset();
		
		// Set booleans true
		if (localName.equals("softwareUnit")) {
			isSoftwareUnit = true;
		} else if (localName.equals("appliedRule")) {
			isAppliedRule = true;
		} 
	}

	public void endElement(String namespaceURI, String localName, String qName) {
		Log.i(this, "endElement(" + localName + ")");
		
		// Get and set layer information
		if (localName.equals("id")) {
			
			if (ar.getLayer(Integer.parseInt(contents.toString())) == null) {
				currentLayer = new Layer();
				currentLayer.setId(Integer.parseInt(contents.toString()));
				ar.addLayer(currentLayer);
			} else {
				currentLayer = ar.getLayer(Integer.parseInt(contents.toString()));
			}

		} else if (localName.equals("name") && !isSoftwareUnit) {
			currentLayer.setName(contents.toString());
		} else if (localName.equals("description")) {
			currentLayer.setDescription(contents.toString());
		} 
		
		// Get and set software unit information
		if (localName.equals("name") && isSoftwareUnit) {
			try {
				currentSoftwareUnit = new SoftwareUnitDefinition();
				currentLayer.addSoftwareUnit(currentSoftwareUnit);
			} catch (Exception e) {
				e.printStackTrace();
			}
			currentSoftwareUnit.setName(contents.toString());
		} else if (localName.equals("type")) {
			currentSoftwareUnit.setType(contents.toString());
		}
		
		// Get and set applied rule information
		if (localName.equals("ruleType")) {
			
			currentAppliedRule = new AppliedRule();
			currentAppliedRule.setFromLayer(currentLayer);
					
			if (contents.toString().equals("BackCall")) {
				currentAppliedRule.setRuleType(new BackCallRule());
			} else if (contents.toString().equals("SkipCall")) {
				currentAppliedRule.setRuleType(new SkipLayerRule());
			}
			
		} else if (localName.equals("toLayer")) {
			
			if (ar.getLayer(Integer.parseInt(contents.toString())) == null) {
				Layer toLayer = new Layer();
				toLayer.setId(Integer.parseInt(contents.toString()));
				currentAppliedRule.setToLayer(toLayer);
			} else {
				currentAppliedRule.setToLayer(ar.getLayer(Integer.parseInt(contents.toString())));
			}
		} else if (localName.equals("appliedRule")) {
			currentLayer.addAppliedRule(currentAppliedRule);
		}
		
		// Exceptions toevoegen
		if (localName.equals("unit") && isSoftwareUnit) {
			//currentSoftwareUnit.addException(getSoftwareUnitDefinition(contents.toString()));
		} else if (localName.equals("unit") && isAppliedRule) {
			//currentAppliedRule.addException(getSoftwareUnitDefinition(contents.toString()));
		}
		
		// Set booleans false
		if (localName.equals("softwareUnit")) {
			isSoftwareUnit = false;
		} else if (localName.equals("appliedRule")) {
			isAppliedRule = false;
		}
	}

	public void characters(char[] ch, int start, int length) throws SAXException {
		contents.write(ch, start, length);
	}

	public void endDocument() {
		Log.i(this, " endDocument()");

	}

	public ArchitectureDefinition getArchitectureDefinition() {
		return ar;
	}
	
	private SoftwareUnitDefinition getSoftwareUnitDefinition(String name) {
		for (Layer layer : ar.getAllLayers()) {
			for (SoftwareUnitDefinition unit : layer.getAllSoftwareUnitDefinitions()) {
				if (unit.getName().equals(name)) {
					return unit;
				}
			}
		}
		return null;
	}

	public boolean validateXML(File file) throws ParserConfigurationException, SAXException, IOException {
		// parse an XML document into a DOM tree
		DocumentBuilder parser = DocumentBuilderFactory.newInstance().newDocumentBuilder();
		Document document = parser.parse(file);

		// create a SchemaFactory capable of understanding WXS schemas
		SchemaFactory factory = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);

		// load a WXS schema, represented by a Schema instance
		Source schemaFile = new StreamSource(new File("architecture_definition_schema.xsd"));
		Schema schema = factory.newSchema(schemaFile);

		// create a Validator instance, which can be used to validate an instance document
		Validator validator = schema.newValidator();

		// validate the DOM tree
		validator.validate(new DOMSource(document));

		return false;
	}
}
