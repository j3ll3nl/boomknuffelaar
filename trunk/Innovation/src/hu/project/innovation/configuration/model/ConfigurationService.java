package hu.project.innovation.configuration.model;

import hu.project.innovation.configuration.controller.xml.ExportController;
import hu.project.innovation.configuration.controller.xml.ImportController;
import hu.project.innovation.configuration.model.rules.AbstractRuleType;
import hu.project.innovation.utils.Log;

import java.io.File;
import java.util.ArrayList;

public final class ConfigurationService {

	private ArchitectureDefinition architectureDefinition;
	private Configuration configuration = new Configuration();
	private static ConfigurationService instance;

	private ConfigurationService() {
		Log.i(this, "constructor()");
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

	public boolean hasArchitectureDefinition() {
		return this.architectureDefinition != null;
	}

	/**
	 * Create a new configuration.
	 * 
	 * @param name The configuration name
	 * @param description The description of the configuration
	 * @throws Exception If an error occurs
	 */
	public void newConfiguration(String name, String description) throws Exception {
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

		ImportController importController = new ImportController();
		importController.importXML(file);
	}

	/**
	 * Save the current configuration to the given file.
	 * 
	 * @param file <code>File</code> object where the .xml needs to be saved to.
	 * @throws Exception If an error occurs
	 */
	public void saveConfiguration(File file) throws Exception {
		Log.i(this, "saveConfiguration(" + file + ")");

		ExportController exportController = new ExportController();
		exportController.exportXML(file);
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
	 * @return
	 * @throws Exception If an error occurs
	 */
	public Layer newLayer(String name, String description) throws Exception {
		if (null == this.architectureDefinition) {
			throw new Exception("Please create a new architecture");
		}
		Layer layer = new Layer(name, description);

		this.architectureDefinition.addLayer(layer);
		architectureDefinition.autoUpdateLayerSequence();
		return layer;
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
		architectureDefinition.autoUpdateLayerSequence();
	}

	/**
	 * Temporary method for the TestConfiguration class.
	 * 
	 * @param name The layer name
	 */
	public Layer getLayer(String name) {
		return this.architectureDefinition.getLayer(name);
	}

	public Layer getLayer(int id) {
		return null;
	}

	public ArrayList<Layer> getLayers() {
		if (!hasArchitectureDefinition()) {
			return null;
		}
		return this.architectureDefinition.getLayers();
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
	public SoftwareUnitDefinition newSoftwareUnit(Layer layer, String unitName, String unitType) throws Exception {
		if (layer == null || (this.architectureDefinition.getLayer(layer.getName()) == null)) {
			throw new Exception("Layer does not exist");
		} else {
			SoftwareUnitDefinition softwareunitdefinition = new SoftwareUnitDefinition(unitName, unitType);
			layer.addSoftwareUnit(softwareunitdefinition);

			return softwareunitdefinition;
		}
	}

	/**
	 * Remove the software unit from the layer
	 * 
	 * @param layer
	 * @param component
	 * @throws Exception
	 */
	public void removeSoftwareUnit(Layer layer, SoftwareUnitDefinition component) throws Exception {
		if (layer == null || (this.architectureDefinition.getLayer(layer.getName()) == null)) {
			throw new Exception("Layer does not exist");
		} else {
			layer.removeSoftwareUniteDefinition(component);
		}
	}

	public void addException(SoftwareUnitDefinition softwareunit, String unitName, String unitType) {
		SoftwareUnitDefinition exception = new SoftwareUnitDefinition(unitName, unitType);
		softwareunit.addException(exception);
	}

	public void addException(AppliedRule appliedrule, String unitName, String unitType) {
		SoftwareUnitDefinition exception = new SoftwareUnitDefinition(unitName, unitType);
		appliedrule.addException(exception);
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
	 * Get project path.
	 * 
	 * @return The path to the project that needs to be scanned
	 */
	public String getProjectPath() {
		return this.configuration.getSetting(Configuration.PROJECT_PATH);
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
	 * Get output path
	 * 
	 * @return The path where the output needs to be saved
	 */
	public String getOutputPath() {
		return this.configuration.getSetting(Configuration.OUTPUT_PATH);
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
	 * Get the output format
	 * 
	 * @return Output format
	 */
	public String getOutputType() {
		return this.configuration.getSetting(Configuration.OUTPUT_FORMAT);
	}

	/**
	 * Get rule type based on String
	 * 
	 * @return Rule type
	 */
	public AbstractRuleType getRuleType(String name) {
		return this.configuration.getRuleType(name);
	}

	public AppliedRule newAppliedRule(Layer fromLayer, Layer toLayer, AbstractRuleType ruleType) {
		if (null != fromLayer && null != ruleType) {
			return fromLayer.addAppliedRule(ruleType, toLayer);
		}
		return null;
	}

	public boolean isRuleApplied(Layer fromLayer, Layer toLayer, String ruleName) {
		if (fromLayer != null) {
			return fromLayer.hasAppliedRule(ruleName, toLayer);
		} else {
			return false;
		}
	}

	public void removeAppliedRule(Layer layer, AppliedRule appliedrule) throws Exception {
		if (layer == null || (this.architectureDefinition.getLayer(layer.getName()) == null)) {
			throw new Exception("Layer does not exist");
		} else {
			layer.removeAppliedRule(appliedrule);
		}
	}

	public Layer getLayerBySoftwareUnitName(String name) {
		return this.architectureDefinition.getLayerBySoftwareUnitName(name);
	}
}
