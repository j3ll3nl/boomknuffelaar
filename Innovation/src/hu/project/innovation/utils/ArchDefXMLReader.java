package hu.project.innovation.utils;

import hu.project.innovation.configuration.model.ArchitectureDefinition;
import hu.project.innovation.configuration.model.BackCallRule;
import hu.project.innovation.configuration.model.Layer;
import hu.project.innovation.configuration.model.SkipLayerRule;
import hu.project.innovation.configuration.model.SoftwareUnitDefinition;

import java.io.CharArrayWriter;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

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
	private SoftwareUnitDefinition currentUnit;
	private ArrayList<String> currentRule = new ArrayList<String>();
	private ArrayList<ArrayList<String>> savedRules = new ArrayList<ArrayList<String>>();
	private HashMap<SoftwareUnitDefinition, String> savedUnitExceptions = new HashMap<SoftwareUnitDefinition, String>();
	private boolean isSoftwareUnit = false;

	public void startElement(String namespaceURI, String localName, String qName, Attributes attr) throws SAXException {
		Log.i(this, "startElement(" + localName + ")");

		contents.reset();

		if (localName.equals("layer")) {
			currentLayer = new Layer();
			ar.addLayer(currentLayer);
		} else if (localName.equals("softwareUnit")) {
			currentUnit = new SoftwareUnitDefinition();
			currentUnit.setLayer(currentLayer);
			isSoftwareUnit = true;
			try {
				currentLayer.addSoftwareUnit(currentUnit);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	@SuppressWarnings("unchecked")
	public void endElement(String namespaceURI, String localName, String qName) {

		Log.i(this, "endElement(" + localName + ")");

		if (localName.equals("id")) {
			currentLayer.setId(Integer.parseInt(contents.toString()));
		} else if (localName.equals("name") && !isSoftwareUnit) {
			currentLayer.setName(contents.toString());
		} else if (localName.equals("name")) {
			currentUnit.setName(contents.toString());
		} else if (localName.equals("description")) {
			currentLayer.setDescription(contents.toString());
		} else if (localName.equals("type")) {
			currentUnit.setType(contents.toString());
		} else if (localName.equals("ruleType")) {
			currentRule.add(currentLayer.getId() + "");
			currentRule.add(contents.toString());
		} else if (localName.equals("toLayer")) {
			currentRule.add(contents.toString());
		} else if (localName.equals("appliedRule")) {
			ArrayList<String> temp = (ArrayList<String>) currentRule.clone();
			savedRules.add(temp);
			currentRule.clear();
		} else if (localName.equals("softwareUnit")) {
			isSoftwareUnit = false;
		} else if (localName.equals("unit")) {
			savedUnitExceptions.put(currentUnit, contents.toString());
		}
	}

	public void characters(char[] ch, int start, int length) throws SAXException {
		contents.write(ch, start, length);
	}

	public void endDocument() {
		Log.i(this, " endDocument()");
		for (ArrayList<String> rule : savedRules) {
			Layer layer = ar.getLayer(Integer.parseInt(rule.get(0)));
			if (rule.get(1).equals("BackCall")) {
				layer.addAppliedRule(new BackCallRule(), ar.getLayer(Integer.parseInt(rule.get(2))));
			} else if (rule.get(1).equals("SkipCall")) {
				layer.addAppliedRule(new SkipLayerRule(), ar.getLayer(Integer.parseInt(rule.get(2))));
			}
		}
		Iterator it = savedUnitExceptions.entrySet().iterator();
	    while (it.hasNext()) {
	        Map.Entry pairs = (Map.Entry)it.next();
	        System.out.println(pairs.getKey() + " = " + pairs.getValue());
	    }
	}

	public ArchitectureDefinition getArchitectureDefinition() {
		return ar;
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
