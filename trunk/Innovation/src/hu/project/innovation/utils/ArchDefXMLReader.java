package hu.project.innovation.utils;

import hu.project.innovation.configuration.model.AppliedRule;
import hu.project.innovation.configuration.model.ArchitectureDefinition;
import hu.project.innovation.configuration.model.Layer;
import hu.project.innovation.configuration.model.SoftwareUnitDefinition;
import hu.project.innovation.configuration.model.rules.BackCallRule;
import hu.project.innovation.configuration.model.rules.SkipLayerRule;

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
 * XMLReader xr = XMLReaderFactory.createXMLReader(); ArchDefXMLReader reader = new ArchDefXMLReader(); 
 * xr.setContentHandler(reader); xr.parse(new InputSource(new FileReader("architecture_definition.xml")));
 * 
 * reader.getArchitectureDefinition();
 */

public class ArchDefXMLReader extends DefaultHandler {

	private CharArrayWriter contents = new CharArrayWriter();
	private ArchitectureDefinition ar = new ArchitectureDefinition();
	private Layer currentLayer;
	private SoftwareUnitDefinition currentSoftwareUnit;
	private AppliedRule currentAppliedRule;
	private SoftwareUnitDefinition currentSoftwareUnitException;
	private String projectPath;
	private String outputPath;
	private String outputFormat;
	private boolean isLayer = false;
	private boolean isSoftwareUnit = false;
	private boolean isAppliedRule = false;
	private boolean isException = false;
	
	public ArchitectureDefinition getArchitectureDefinition() {
		return ar;
	}
	
	public String getProjectPath() {
		return projectPath;
	}
	
	public String getOutputPath() {
		return outputPath;
	}

	public String getOutputFormat() {
		return outputFormat;
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
	
	// Extended methods ///////////////////////////////////////////////////////////////////////////////////////////////////

	public void startElement(String namespaceURI, String localName, String qName, Attributes attr) throws SAXException {
		Log.i(this, "startElement(" + localName + ")");
		contents.reset();
		
		// Open complex XML element
		if (localName.equals("layer")) {
			isLayer = true;
		} else if (localName.equals("softwareUnit")) {
			isSoftwareUnit = true;
		} else if (localName.equals("appliedRule")) {
			isAppliedRule = true;
		} else if (localName.equals("exception")) {
			isException = true;
		}
		
		// Get element attributes
		if (localName.equals("architecture")) {
			try {
				ar.setName(attr.getValue(0));
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else if (localName.equals("output")) {
			outputFormat = attr.getValue(0);
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

		} else if (localName.equals("name") && !isSoftwareUnit && !isAppliedRule && isLayer) {
			currentLayer.setName(contents.toString());
		} else if (localName.equals("description")) {
			currentLayer.setDescription(contents.toString());
		} else if (localName.equals("interfaceAccessOnly")) {
			currentLayer.setInterfaceAccesOnly((Integer.parseInt(contents.toString()) != 0));
		}
		
		// Get and set software unit information
		if (localName.equals("name") && isSoftwareUnit && !isException) {
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
					
			if (contents.toString().equals("BackCallRule")) {
				currentAppliedRule.setRuleType(new BackCallRule());
			} else if (contents.toString().equals("SkipLayerRule")) {
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
		
		// Add exceptions
		if (localName.equals("name") && isSoftwareUnit && isException) {
			System.out.println("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!! exception name");
			currentSoftwareUnitException = new SoftwareUnitDefinition();
			currentSoftwareUnitException.setName(contents.toString());
			currentSoftwareUnit.addException(currentSoftwareUnitException);
		} else if (localName.equals("type") && isSoftwareUnit && isException) {
			System.out.println("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!! exception type");
			currentSoftwareUnitException.setType(contents.toString());
		} else if (localName.equals("name") && isAppliedRule && isException) {
			currentSoftwareUnitException = new SoftwareUnitDefinition();
			currentSoftwareUnitException.setName(contents.toString());
			currentAppliedRule.addException(currentSoftwareUnitException);
		} else if (localName.equals("type") && isAppliedRule && isException) {
			currentSoftwareUnitException.setType(contents.toString());
		}
		
		// Closing complex XML element
		if (localName.equals("layer")) {
			isLayer = false;
		} else if (localName.equals("softwareUnit")) {
			isSoftwareUnit = false;
		} else if (localName.equals("appliedRule")) {
			isAppliedRule = false;
		} else if (localName.equals("exception")) {
			isException = false;
		}
		
		// Get and set paths
		if (localName.equals("project")) {
			projectPath = contents.toString();
		} else if (localName.equals("output")) {
			outputPath = contents.toString();
		}
		
	}

	public void characters(char[] ch, int start, int length) throws SAXException {
		contents.write(ch, start, length);
	}

	public void endDocument() {
		Log.i(this, " endDocument()");

	}
}
