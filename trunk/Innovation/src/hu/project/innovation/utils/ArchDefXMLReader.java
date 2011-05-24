package hu.project.innovation.utils;

import hu.project.innovation.configuration.model.AppliedRule;
import hu.project.innovation.configuration.model.ArchitectureDefinition;
import hu.project.innovation.configuration.model.ConfigurationService;
import hu.project.innovation.configuration.model.Layer;
import hu.project.innovation.configuration.model.SoftwareUnitDefinition;

import java.io.CharArrayWriter;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
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
 * EXAMPLE HOW TO USE:
 * 
 * XMLReader xr = XMLReaderFactory.createXMLReader(); ArchDefXMLReader reader = new ArchDefXMLReader(); xr.setContentHandler(reader); xr.parse(new InputSource(new
 * FileReader("architecture_definition.xml")));
 * 
 * reader.getArchitectureDefinition();
 */

public class ArchDefXMLReader extends DefaultHandler {

	private CharArrayWriter contents = new CharArrayWriter();
	private ArchitectureDefinition ar = new ArchitectureDefinition();
	private HashMap<Integer, Layer> layers = new HashMap<Integer, Layer>();
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

	/**
	 * Get the ArchitectureDefinition
	 * 
	 * @return Returns an object of ArchitectureDefinition when parsing has been done.
	 */
	public ArchitectureDefinition getArchitectureDefinition() {
		return ar;
	}

	/**
	 * Get project path
	 * 
	 * @return Returns a String of the project path when parsing has been done.
	 */
	public String getProjectPath() {
		return projectPath;
	}

	/**
	 * Get output path
	 * 
	 * @return Returns a String of the output path when parsing has been done.
	 */
	public String getOutputPath() {
		return outputPath;
	}

	/**
	 * Get output format
	 * 
	 * @return Returns a String of the output format when parsing has been done.
	 */
	public String getOutputFormat() {
		return outputFormat;
	}

	/**
	 * Validate an XML-file with pre-defined XML-schema.
	 * 
	 * @exception Throws an exception when the XML-file isn't valid.
	 */
	public void validateXML(File file) throws ParserConfigurationException, SAXException, IOException {
		DocumentBuilder parser = DocumentBuilderFactory.newInstance().newDocumentBuilder();
		Document document = parser.parse(file);
		SchemaFactory factory = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
		Source schemaFile = new StreamSource(new File("architecture_definition_schema.xsd"));
		Schema schema = factory.newSchema(schemaFile);
		Validator validator = schema.newValidator();
		validator.validate(new DOMSource(document));
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
			// Check if layer already exists
			if (layers.containsKey(Integer.parseInt(contents.toString()))) {
				currentLayer = layers.get(Integer.parseInt(contents.toString()));
			} else {
				currentLayer = new Layer();
				currentLayer.setId(Integer.parseInt(contents.toString()));
				layers.put(Integer.parseInt(contents.toString()), currentLayer);
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
		} else if (localName.equals("type") && isSoftwareUnit && !isException) {
			currentSoftwareUnit.setType(contents.toString());
		}

		// Get and set applied rule information
		if (localName.equals("ruleType")) {
			currentAppliedRule = new AppliedRule();
			currentAppliedRule.setFromLayer(currentLayer);
			currentAppliedRule.setRuleType(ConfigurationService.getInstance().getRuleType(contents.toString()));
		} else if (localName.equals("toLayer")) {
			// Check if layer already exists
			if (layers.containsKey(Integer.parseInt(contents.toString()))) {
				currentAppliedRule.setToLayer(layers.get(Integer.parseInt(contents.toString())));
			} else {
				Layer toLayer = new Layer();
				toLayer.setId(Integer.parseInt(contents.toString()));
				currentAppliedRule.setToLayer(toLayer);
				layers.put(Integer.parseInt(contents.toString()), toLayer);
			}
		} else if (localName.equals("appliedRule")) {
			currentLayer.addAppliedRule(currentAppliedRule);
		}

		// Add exceptions
		if (localName.equals("name") && isSoftwareUnit && isException) {
			currentSoftwareUnitException = new SoftwareUnitDefinition();
			currentSoftwareUnitException.setName(contents.toString());
			currentSoftwareUnit.addException(currentSoftwareUnitException);
		} else if (localName.equals("type") && isSoftwareUnit && isException) {
			currentSoftwareUnitException.setType(contents.toString());
		} else if (localName.equals("name") && isAppliedRule && isException) {
			currentSoftwareUnitException = new SoftwareUnitDefinition();
			currentSoftwareUnitException.setName(contents.toString());
			currentAppliedRule.addException(currentSoftwareUnitException);
		} else if (localName.equals("type") && isAppliedRule && isException) {
			currentSoftwareUnitException.setType(contents.toString());
		}

		// Get and set paths
		if (localName.equals("project")) {
			projectPath = contents.toString();
		} else if (localName.equals("output")) {
			outputPath = contents.toString();
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
	}

	public void characters(char[] ch, int start, int length) throws SAXException {
		contents.write(ch, start, length);
	}

	public void endDocument() {
		Log.i(this, " endDocument()");
		// Add all layers to ArchitectureDefinition
		for(Map.Entry<Integer, Layer> entry : layers.entrySet()) {
			ar.addLayer(entry.getValue());
		}
	}
}
