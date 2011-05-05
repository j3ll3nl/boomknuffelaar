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

	private ArchitectureDefinition architecture;
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

	/**
	 * Create a new configuration.
	 * 
	 * @param name The configuration name
	 * @param description The description of the configuration
	 */
	public void newConfiguration(String name, String description) {
		Log.i(this, "newConfiguration(" + name + ", " + description + ")");
		this.architecture = new ArchitectureDefinition(name, description);
	}

	/**
	 * Open an configuration.
	 * 
	 * @param file <code>File</code> object where the .xml is.
	 * @throws Exception If an error occurs
	 */
	public void openConfiguration(File file) throws Exception {
		Log.i(this, "openConfiguration(" + file + ")");

		// TODO: door: Stefan, aan: Martijn; Zie opmerking bij saveConfiguration()
		XMLReader xr = XMLReaderFactory.createXMLReader();
		ArchDefXMLReader reader = new ArchDefXMLReader();
		reader.validateXML(file);
		xr.setContentHandler(reader);
		xr.parse(new InputSource(new FileReader(file)));

		architecture = reader.getArchitectureDefinition();
	}

	/**
	 * Save the current configuration to the given file.
	 * 
	 * @param file <code>File</code> object where the .xml needs to be saved to.
	 * @throws Exception If an error occurs
	 */
	public void saveConfiguration(File file) throws Exception {
		Log.i(this, "saveConfiguration(" + file + ")");

		// TODO: door: Stefan, aan: Martijn; Het wegschrijven van de architectuur mag niet in de facade gebeuren maar moet je in een andere klasse opnemen
		FileWriter fstream = new FileWriter(file);
		BufferedWriter out = new BufferedWriter(fstream);
		out.write(architecture.toXML());
		out.close();
	}

	/**
	 * Create a new layer and add it to the current architecture definition
	 * 
	 * @param name The layer name
	 * @param description The layer description
	 * @throws Exception If an error occurs
	 */
	public void newLayer(String name, String description) throws Exception {
		if (null == this.architecture) {
			throw new Exception(Messages.ERROR_NEWARCHITECTURE);
		}
		this.architecture.addLayer(new Layer(name, description));
	}

	/**
	 * Remove the given layer from the architecture definition
	 * 
	 * @param layer The layer
	 * @throws Exception If an error occurs
	 */
	public void removeLayer(Layer layer) throws Exception {
		if (null == this.architecture) {
			throw new Exception(Messages.ERROR_NEWARCHITECTURE);
		}
		this.architecture.removeLayer(layer);
	}

	/**
	 * Temporary method for the TestConfiguration class.
	 * 
	 * @param name The layer name
	 */
	public Layer getLayer(String name) {
		// TODO: Remove this method
		return this.architecture.getLayer(name);
	}

	/**
	 * Move a layer up
	 * 
	 * @param layer
	 */
	public void moveLayerUp(Layer layer) {
		// TODO: nog te schrijven, vanuit de gui wordt de layer meegegeven die omlaag moet.
	}

	/**
	 * Move a layer down
	 * 
	 * @param layer
	 */
	public void moveLayerDown(Layer layer) {
		// TODO: not te schrijven, vanuit de gui wordt de layer meegegeven die omhoog moet.
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
		if (layer == null || (this.architecture.getLayer(layer.getName()) == null)) {
			throw new Exception(Messages.ERROR_LAYERDOESNOTEXIST);
		} else {
			SoftwareUnitDefinition softwareunitdefinition = new SoftwareUnitDefinition(unitName, unitType);
			layer.addSoftwareUnit(softwareunitdefinition);			
		}
	}

	/**
	 * Remove the 
	 * @param layer
	 * @param component
	 * @throws Exception
	 */
	public void removeSoftwareUnit(Layer layer, SoftwareUnitDefinition component) throws Exception {
		if (layer == null || (this.architecture.getLayer(layer.getName()) == null)) {
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
		Layer fromLayer = this.architecture.getLayer(fromLayerName);
		Layer toLayer = this.architecture.getLayer(toLayerName);
		AbstractRuleType ruleType = this.configuration.getRule(ruleName);

		if (null != fromLayer && null != toLayer && null != ruleType) {
			fromLayer.addAppliedRule(ruleType, toLayer);
		}
	}

	public void removeAppliedRule() {

	}

	public String getLayerNameBySoftwareUnitName(String name) {
		return this.architecture.getLayerNameBySoftwareUnitName(name);
	}

	public ArrayList<Layer> getLayers() {
		return this.architecture.getAllLayers();
	}

	public ArrayList<SoftwareUnitDefinition> getComponents() {
		return this.architecture.getAllComponents();
	}

	public String architectureToXML() {
		return this.architecture.toXML();
	}

	public boolean isRuleApplied(String fromLayerName, String toLayerName, String ruleName) {
		Layer fromLayer = this.architecture.getLayer(fromLayerName);

		if (null != fromLayer) {
			return fromLayer.hasAppliedRule(ruleName, toLayerName);
		} else {
			return false;
		}
	}

}
