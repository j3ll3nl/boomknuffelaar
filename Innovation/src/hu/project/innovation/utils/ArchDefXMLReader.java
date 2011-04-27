package hu.project.innovation.utils;

import hu.project.innovation.configuration.model.ArchitectureDefinition;
import hu.project.innovation.configuration.model.BackCallRule;
import hu.project.innovation.configuration.model.Layer;
import hu.project.innovation.configuration.model.SoftwareUnitDefinition;

import java.io.CharArrayWriter;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

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
	private ArchitectureDefinition architecture;
	private Layer currentLayer;
	private SoftwareUnitDefinition currentUnit;
	private ArrayList<String> currentRule;
	private ArrayList<ArrayList<String>> savedRules = new ArrayList<ArrayList<String>>();

	public void startElement(String namespaceURI, String localName, String qName, Attributes attr) throws SAXException {

		Log.i(this, "startElement(" + localName + ")");
		
		contents.reset();

		if (localName.equals("architecture")) {

			architecture = new ArchitectureDefinition();

		} else if (localName.equals("layer")) {

			currentLayer = new Layer();

		} else if (localName.equals("softwareUnit")) {

			currentUnit = new SoftwareUnitDefinition();

		} else if (localName.equals("appliedRule")) {

			currentRule = new ArrayList<String>();

		}

	}

	@SuppressWarnings("unchecked")
	public void endElement(String namespaceURI, String localName, String qName) {
		
		Log.i(this, "endElement(" + localName + ")");
		
		// Get layer data
		if (localName.equals("id")) {

			currentLayer.setId(Integer.parseInt(contents.toString()));

		} else if (localName.equals("name") && currentUnit == null) {

			currentLayer.setName(contents.toString());
			
		} else if (localName.equals("description")) {

			currentLayer.setDescription(contents.toString());
			
		} else if (localName.equals("layer")) {

			architecture.addLayer(currentLayer);
			
		} 
		
		// Get software unit data
		if (localName.equals("name") && currentUnit != null) {

			currentUnit.setName(contents.toString());
			
		} else if (localName.equals("type")) {

			currentUnit.setType(contents.toString());
			
		} else if (localName.equals("softwareUnit")) {

			currentLayer.addSoftwareUnit(currentUnit);
			currentUnit = null;
			
		} 
		
		// Get rule data
		if (localName.equals("ruleType")) {

			currentRule.add(contents.toString());
			
		} else if (localName.equals("toLayer")) {

			currentRule.add(contents.toString());
			
		} else if (localName.equals("appliedRule")) {

			ArrayList<String> temp = (ArrayList<String>) currentRule.clone();
			savedRules.add(temp);
			currentRule.clear();
			
		}

	}

	public void characters(char[] ch, int start, int length) throws SAXException {

		contents.write(ch, start, length);

	}

	public void endDocument() {
		
		Log.i(this, " endDocument()");

		for (ArrayList<String> rule : savedRules) {

			if (rule.get(1).equals("BackCall")) {

				Layer layer = architecture.getLayer(Integer.parseInt(rule.get(0)));
				layer.addAppliedRule(new BackCallRule(), architecture.getLayer(Integer.parseInt(rule.get(2))));

			}

		}
		
	}

	public ArchitectureDefinition getArchitectureDefinition() {

		return architecture;

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
