package hu.project.innovation.configuration.model;

import hu.project.innovation.utils.ArchDefXMLReader;
import hu.project.innovation.utils.Log;
import hu.project.innovation.utils.Messages;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;

import org.xml.sax.InputSource;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.XMLReaderFactory;

public class ConfigurationService {

	private ArchitectureDefinition architectureDefinition;
	private Configuration configuration;
	private static ConfigurationService instance;

	private ConfigurationService() {
		Log.i(this, "constructor()");

		this.configuration = new Configuration();
	}

	/**
	 * Get the singleton instance of this object
	 * 
	 * @return The ConfigurationService Object
	 */
	public static ConfigurationService getInstance() {
		if (instance == null) {
			instance = new ConfigurationService();
		}
		return instance;
	}

	public boolean isArchitectureDefinition() {
		if (architectureDefinition == null) {
			return false;
		} else {
			return true;
		}
	}

	/**
	 * Create a new configuration.
	 * 
	 * @param name The configuration name
	 * @param description The description of the configuration
	 */
	public void newConfiguration(String name, String description) {
		Log.i(this, "newConfiguration(" + name + ", " + description + ")");
		this.architectureDefinition = new ArchitectureDefinition(name, description);
	}

	/**
	 * Open an configuration.
	 * 
	 * @param file <code>File</code> object where the .xml is.
	 * @throws Exception If an error occurs
	 */
	public void openConfiguration(File file) throws Exception {
		Log.i(this, "openConfiguration(" + file + ")");

		XMLReader xr = XMLReaderFactory.createXMLReader();
		ArchDefXMLReader reader = new ArchDefXMLReader();
		reader.validateXML(file);
		xr.setContentHandler(reader);
		xr.parse(new InputSource(new FileReader(file)));

		architectureDefinition = reader.getArchitectureDefinition();
	}

	/**
	 * Save the current configuration to the given file.
	 * 
	 * @param file <code>File</code> object where the .xml needs to be saved to.
	 * @throws Exception If an error occurs
	 */
	public void saveConfiguration(File file) throws Exception {
		Log.i(this, "saveConfiguration(" + file + ")");

		if (!isArchitectureDefinition()) {

		} else {
			FileWriter fstream = new FileWriter(file);
			BufferedWriter out = new BufferedWriter(fstream);
			out.write(architectureDefinition.toXML());
			out.close();
		}
	}

	/**
	 * Get the current architecture definition
	 * 
	 * @return
	 */
	public ArchitectureDefinition getConfiguration() {
		return this.architectureDefinition;
	}

	/**
	 * Create a new layer and add it to the current architecture definition
	 * 
	 * @param name The layer name
	 * @param description The layer description
	 * @throws Exception If an error occurs
	 */
	public void newLayer(String name, String description) throws Exception {
		if (null == this.architectureDefinition) {
			throw new Exception("Please create a new architecture");
		}
		Layer layer = new Layer(name, description);

		this.architectureDefinition.addLayer(layer);
	}

	/**
	 * Remove the given layer from the architecture definition
	 * 
	 * @param layer The layer
	 * @throws Exception If an error occurs
	 */
	public void removeLayer(Layer layer) throws Exception {
		if (null == this.architectureDefinition) {
			throw new Exception("Please create a new architecture");
		}
		this.architectureDefinition.removeLayer(layer);
	}

	/**
	 * Temporary method for the TestConfiguration class.
	 * 
	 * @param name The layer name
	 */
	public Layer getLayer(String name) {
		// TODO: Remove this method
		return this.architectureDefinition.getLayer(name);
	}

	/**
	 * Move a layer up
	 * 
	 * @param layer
	 */
	public void moveLayerUp(Layer layer) throws Exception {
		Log.i(this, "moveLayerUp(" + layer + ")");
		layer.moveUp();

		architectureDefinition.autoUpdateLayerSequence();
	}

	/**
	 * Move a layer down
	 * 
	 * @param layer
	 */
	public void moveLayerDown(Layer layer) throws Exception {
		Log.i(this, "moveLayerDown(" + layer + ")");
		layer.moveDown();
				
		architectureDefinition.autoUpdateLayerSequence();
	}

	/**
	 * Create a new softwareunit for the given layer.
	 * 
	 * @param layer The layer where the software unit needs to be added to
	 * @param unitName
	 * @param unitType
	 * @throws Exception
	 */
	public void newSoftwareUnit(Layer layer, String unitName, String unitType) throws Exception {
		if (layer == null || (this.architectureDefinition.getLayer(layer.getName()) == null)) {
			throw new Exception(Messages.ERROR_LAYERDOESNOTEXIST);
		} else {
			SoftwareUnitDefinition softwareunitdefinition = new SoftwareUnitDefinition(unitName, unitType);
			layer.addSoftwareUnit(softwareunitdefinition);
		}
	}

	/**
	 * Remove the
	 * 
	 * @param layer
	 * @param component
	 * @throws Exception
	 */
	public void removeSoftwareUnit(Layer layer, SoftwareUnitDefinition component) throws Exception {
		if (layer == null || (this.architectureDefinition.getLayer(layer.getName()) == null)) {
			throw new Exception(Messages.ERROR_LAYERDOESNOTEXIST);
		} else {
			layer.removeSoftwareUniteDefinition(component);
		}
	}

	/**
	 * Set the project path
	 * 
	 * @param path A string to the project that needs to be scanned
	 */
	public void setProjectPath(String path) {
		this.configuration.setSetting(Configuration.PROJECT_PATH, path);
	}

	/**
	 * Set the output path
	 * 
	 * @param path A string to the output directory
	 */
	public void setOutputPath(String path) {
		this.configuration.setSetting(Configuration.OUTPUT_PATH, path);
	}

	/**
	 * Sets the output type.
	 * 
	 * @param format Values are: text, html or xml.
	 */
	public void setOutputType(String format) {
		this.configuration.setSetting(Configuration.OUTPUT_FORMAT, format);
	}

	/**
	 * Get project path.
	 * 
	 * @return The path to the project that needs to be scanned
	 */
	public String getProjectPath() {
		return this.configuration.getSetting(Configuration.PROJECT_PATH);
	}

	/**
	 * Get output path
	 * 
	 * @return The path where the output needs to be saved
	 */
	public String getOutputPath() {
		return this.configuration.getSetting(Configuration.OUTPUT_PATH);
	}

	/**
	 * Get the output format
	 * 
	 * @return Output format
	 */
	public String getOutputFormat() {
		return this.configuration.getSetting(Configuration.OUTPUT_FORMAT);
	}

	public void newAppliedRule(String fromLayerName, String toLayerName, String ruleName) {
		Layer fromLayer = this.architectureDefinition.getLayer(fromLayerName);
		Layer toLayer = this.architectureDefinition.getLayer(toLayerName);
		AbstractRuleType ruleType = this.configuration.getRule(ruleName);

		if (null != fromLayer && null != toLayer && null != ruleType) {
			fromLayer.addAppliedRule(ruleType, toLayer);
		}
	}

	public void removeAppliedRule() {

	}

	public Layer getLayerNameBySoftwareUnitName(String name) {
		return this.architectureDefinition.getLayerNameBySoftwareUnitName(name);
	}

	public ArrayList<Layer> getLayers() {
		if (!isArchitectureDefinition()) {
			return null;
		}
		return this.architectureDefinition.getAllLayers();
	}

	public String architectureToXML() {
		return this.architectureDefinition.toXML();
	}

	public boolean isRuleApplied(Layer fromLayer, Layer toLayer, String ruleName) {
		if (null != fromLayer) {
			return fromLayer.hasAppliedRule(ruleName, toLayer);
		} else {
			return false;
		}
	}

}
