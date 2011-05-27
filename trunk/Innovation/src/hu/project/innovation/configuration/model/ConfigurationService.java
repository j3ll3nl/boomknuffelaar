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

	/**
	 * Returns an boolean if there is an architecture created
	 * 
	 * @return True = there is an architecture / False != an architecture
	 */
	public boolean hasArchitectureDefinition() {
		Log.i(this, "hasArchitectureDefinition()");

		return architectureDefinition != null;
	}

	/**
	 * Create a new configuration.
	 * 
	 * @param architectureName The configuration name
	 * @param architectureDescription The description of the configuration
	 * @throws Exception If an error occurs
	 */
	public void newConfiguration(String architectureName, String architectureDescription) throws Exception {
		Log.i(this, "newConfiguration(" + architectureName + ", " + architectureDescription + ")");

		architectureDefinition = new ArchitectureDefinition(architectureName, architectureDescription);
	}

	/**
	 * Open an configuration.
	 * 
	 * @param file <code>File</code> object where the .xml is.
	 * @throws Exception If an error occurs
	 */
	public void importConfiguration(File file) throws Exception {
		Log.i(this, "importConfiguration(" + file + ")");

		ImportController importController = new ImportController();
		importController.importXML(file);
	}

	/**
	 * Save the current configuration to the given file.
	 * 
	 * @param file <code>File</code> object where the .xml needs to be saved to.
	 * @throws Exception If an error occurs
	 */
	public void exportConfiguration(File file) throws Exception {
		Log.i(this, "exportConfiguration(" + file + ")");

		ExportController exportController = new ExportController();
		exportController.exportXML(file);
	}

	public String getArchitectureName() {
		Log.i(this, "getArchitectureName()");

		if (hasArchitectureDefinition()) {
			return architectureDefinition.getName();
		} else {
			return null;
		}
	}

	public String getArchitectureDescription() {
		Log.i(this, "getArchitectureDescription()");

		if (hasArchitectureDefinition()) {
			return architectureDefinition.getDescription();
		} else {
			return null;
		}
	}

	/**
	 * Create a new layer and add it to the current architecture definition
	 * 
	 * @param layer_name The layer name
	 * @param layer_description The layer description
	 * @return
	 * @throws Exception If an error occurs
	 */
	public int newLayer(String layer_name, String layer_description) throws Exception {
		Log.i(this, "newLayer(" + layer_name + "," + layer_description + ")");

		if (hasArchitectureDefinition()) {
			return architectureDefinition.newLayer(layer_name, layer_description);
		}
		throw new Exception("First create a new architecture");
	}

	/**
	 * Remove the given layer from the architecture definition
	 * 
	 * @param layer The layer
	 * @throws Exception If an error occurs
	 */
	public void removeLayer(int layer_id) throws Exception {
		Log.i(this, "removeLayer(" + layer_id + ")");

		if (hasArchitectureDefinition()) {
			architectureDefinition.removeLayer(layer_id);
			return;
		}
		throw new Exception("First create a new architecture");
	}

	public ArrayList<Integer> getLayers() {
		Log.i(this, "getLayers()");

		if (hasArchitectureDefinition()) {
			ArrayList<Integer> layers = architectureDefinition.getLayers();
			return layers;
		}
		return null;
	}

	public void setLayerName(int layer_id, String value) throws Exception {
		Log.i(this, "setLayerName(" + layer_id + ", " + value + ")");

		Layer layer = architectureDefinition.getLayer(layer_id);
		if (layer != null) {
			layer.setLayerName(value);
		}
	}

	public String getLayerName(int layer_id) {
		Log.i(this, "getLayerName(" + layer_id + ")");

		Layer layer = architectureDefinition.getLayer(layer_id);
		if (layer != null) {
			return layer.getLayerName();
		}
		return null;
	}

	public void setLayerDescription(int layer_id, String value) {
		Log.i(this, "setLayerDescription(" + layer_id + ", " + value + ")");

		Layer layer = architectureDefinition.getLayer(layer_id);
		if (layer != null) {
			layer.setLayerDescription(value);
		}
	}

	public String getLayerDescription(int layer_id) {
		Log.i(this, "getLayerDescription(" + layer_id + ")");

		Layer layer = architectureDefinition.getLayer(layer_id);
		if (layer != null) {
			return layer.getDescription();
		}
		return null;
	}

	public void setLayerInterfaceAccesOnly(int layer_id, boolean enabled) {
		Log.i(this, "getLayerInterfaceOnly(" + layer_id + ", " + enabled + ")");

		Layer layer = architectureDefinition.getLayer(layer_id);
		if (layer != null) {
			layer.setInterfaceAccesOnly(enabled);
		}
	}

	public boolean getLayerInterfaceOnly(int layer_id) {
		Log.i(this, "getLayerInterfaceOnly(" + layer_id + ")");

		Layer layer = architectureDefinition.getLayer(layer_id);
		if (layer != null) {
			return layer.isInterfaceAccessOnly();
		}
		return false;
	}

	/**
	 * Move the layer 1 up
	 * 
	 * @param layer_id The layer id
	 * @throws Exception
	 */
	public void moveLayerUp(int layer_id) throws Exception {
		Log.i(this, "moveLayerUp(" + layer_id + ")");

		Layer layer = architectureDefinition.getLayer(layer_id);
		if (layer != null) {
			layer.moveUp();
			architectureDefinition.autoUpdateLayerSequence();
		}
	}

	/**
	 * Move the layer 1 down
	 * 
	 * @param layer The layer ID
	 * @throws Exception
	 */
	public void moveLayerDown(int layer_id) throws Exception {
		Log.i(this, "moveLayerDown(" + layer_id + ")");

		Layer layer = architectureDefinition.getLayer(layer_id);
		if (layer != null) {
			layer.moveDown();
			architectureDefinition.autoUpdateLayerSequence();
		}
	}

	/**
	 * Create a new software unit for an layer
	 * 
	 * @param layer_id The layer id
	 * @param unitName The software unit name
	 * @param unitType The software unit type
	 * @return Returns the id of the new software unit
	 * @throws Exception
	 */
	public synchronized long newSoftwareUnit(int layer_id, String unitName, String unitType) throws Exception {
		Log.i(this, "newSoftwareUnit(" + layer_id + "," + unitName + "," + unitType + ")");

		Layer layer = architectureDefinition.getLayer(layer_id);
		if (layer != null) {
			return layer.newSoftwareUnit(unitName, unitType);
		}
		throw new Exception("Layer does not exist");
	}

	/**
	 * Remove the software unit from the layer
	 * 
	 * @param layer
	 * @param component
	 * @throws Exception
	 */
	public void removeSoftwareUnit(int layer_id, long softwareunit_id) throws Exception {
		Log.i(this, "removeSoftwareUnit(" + layer_id + "," + softwareunit_id + ")");

		Layer layer = architectureDefinition.getLayer(layer_id);
		if (layer != null) {
			layer.removeSoftwareUniteDefinition(softwareunit_id);
		}
		throw new Exception("Layer does not exist");
	}

	private SoftwareUnitDefinition getSoftwareUnit(int layer_id, long softwareunit_id) throws Exception {
		Log.i(this, "getSoftwareUnit(" + layer_id + "," + softwareunit_id + ")");

		Layer layer = architectureDefinition.getLayer(layer_id);
		if (layer != null) {
			SoftwareUnitDefinition softwareUnit = layer.getSoftwareUnit(softwareunit_id);
			if (softwareUnit != null) {
				return softwareUnit;
			}
			throw new Exception("Software unit does not exist");
		}
		throw new Exception("Layer does not exist");
	}

	public ArrayList<Long> getSoftwareUnits(int layer_id) {
		Log.i(this, "getLayerSoftwareUnits(" + layer_id + ")");

		Layer layer = architectureDefinition.getLayer(layer_id);
		if (layer != null) {
			return layer.getSoftwareUnitDefinitions();
		}
		return null;
	}

	public void setSoftwareUnitName(int layer_id, long softwareunit_id, String value) throws Exception {
		Log.i(this, "setSoftwareUnitName(" + layer_id + "," + softwareunit_id + ", " + value + ")");

		SoftwareUnitDefinition softwareUnit = getSoftwareUnit(layer_id, softwareunit_id);
		softwareUnit.setName(value);
	}

	public String getSoftwareUnitName(int layer_id, long softwareunit_id) throws Exception {
		Log.i(this, "getSoftwareUnitName(" + layer_id + "," + softwareunit_id + ")");

		SoftwareUnitDefinition softwareUnit = getSoftwareUnit(layer_id, softwareunit_id);
		return softwareUnit.getName();
	}

	public void setSoftwareUnitType(int layer_id, long softwareunit_id, String value) throws Exception {
		Log.i(this, "setSoftwareUnitType(" + layer_id + "," + softwareunit_id + ", " + value + ")");

		SoftwareUnitDefinition softwareUnit = getSoftwareUnit(layer_id, softwareunit_id);
		softwareUnit.setType(value);
	}

	public String getSoftwareUnitType(int layer_id, long softwareunit_id) throws Exception {
		Log.i(this, "getSoftwareUnitType(" + layer_id + "," + softwareunit_id + ")");

		SoftwareUnitDefinition softwareUnit = getSoftwareUnit(layer_id, softwareunit_id);
		return softwareUnit.getType();
	}

	public long newSoftwareUnitException(int layer_id, long softwareunit_id, String unitName, String unitType) throws Exception {
		Log.i(this, "newSoftwareUnitException(" + layer_id + "," + softwareunit_id + ", " + unitName + ", " + unitType + ")");

		SoftwareUnitDefinition softwareUnit = getSoftwareUnit(layer_id, softwareunit_id);
		return softwareUnit.newException(unitName, unitType);
	}

	public void removeSoftwareUnitExceptions(int layer_id, long softwareunit_id) throws Exception {
		Log.i(this, "removeSoftwareUnitExceptions(" + layer_id + "," + softwareunit_id + ")");

		SoftwareUnitDefinition softwareUnit = getSoftwareUnit(layer_id, softwareunit_id);
		softwareUnit.removeAllExceptions();
	}

	public ArrayList<Long> getSoftwareUnitExceptions(int layer_id, long softwareunit_id) throws Exception {
		Log.i(this, "getSoftwareUnitExceptions(" + layer_id + "," + softwareunit_id + ")");

		SoftwareUnitDefinition softwareUnit = getSoftwareUnit(layer_id, softwareunit_id);
		return softwareUnit.getExceptions();
	}

	public String getSoftwareUnitExceptionName(int layer_id, long softwareunit_id, long exception_id) throws Exception {
		Log.i(this, "getSoftwareUnitExceptionName(" + layer_id + "," + softwareunit_id + ", " + exception_id + ")");

		SoftwareUnitDefinition softwareUnit = getSoftwareUnit(layer_id, softwareunit_id);
		SoftwareUnitDefinition exception = softwareUnit.getExeption(exception_id);

		if (exception != null) {
			return exception.getName();
		}
		return null;
	}

	public String getSoftwareUnitExceptionType(int layer_id, long softwareunit_id, long exception_id) throws Exception {
		Log.i(this, "getSoftwareUnitExceptionType(" + layer_id + "," + softwareunit_id + ", " + exception_id + ")");

		SoftwareUnitDefinition softwareUnit = getSoftwareUnit(layer_id, softwareunit_id);
		SoftwareUnitDefinition exception = softwareUnit.getExeption(exception_id);

		if (exception != null) {
			return exception.getType();
		}
		return null;
	}

	public long newAppliedRule(int layer_id_from, int layer_id_to, String ruleType) throws Exception {
		Log.i(this, "newAppliedRule(" + layer_id_from + "," + layer_id_to + ", " + ruleType + ")");

		Layer layer_from = architectureDefinition.getLayer(layer_id_from);
		if (layer_from != null) {
			Layer layer_to = architectureDefinition.getLayer(layer_id_to);
			return layer_from.newAppliedRule(ruleType, layer_to);
		}
		throw new Exception("Layer does not exist");
	}

	public void removeAppliedRule(int layer_id_from, long appliedrule_id) throws Exception {
		Log.i(this, "removeAppliedRule(" + layer_id_from + "," + appliedrule_id + ")");

		Layer layer_from = architectureDefinition.getLayer(layer_id_from);
		if (layer_from != null) {
			layer_from.removeAppliedRule(appliedrule_id);
		}
		throw new Exception("Layer does not exist");
	}

	private AppliedRule getAppliedRule(int layer_id, long appliedrule_id) throws Exception {
		Layer layer = architectureDefinition.getLayer(layer_id);
		if (layer != null) {
			AppliedRule rule = layer.getAppliedRule(appliedrule_id);
			if (rule != null) {
				return rule;
			}
			throw new Exception("Applied rule does not exist");
		}
		throw new Exception("Layer does not exist");
	}

	public ArrayList<Long> getAppliedRules(int layer_id) {
		Log.i(this, "getAppliedRules(" + layer_id + ")");

		Layer layer = architectureDefinition.getLayer(layer_id);
		if (layer != null) {
			return layer.getAppliedRules();
		}
		return null;
	}

	public int getAppliedRuleFromLayer(long appliedrule_id) throws Exception {
		ArrayList<Integer> layers = getLayers();
		for (int layer_id : layers) {
			Layer layer = architectureDefinition.getLayer(layer_id);
			AppliedRule appliedRule = layer.getAppliedRule(appliedrule_id);
			if (appliedRule != null) {
				Layer fromLayer = appliedRule.getFromLayer();
				if (fromLayer != null) {
					return fromLayer.getId();
				}
			}
		}
		throw new Exception("Applied rule not found");
	}

	public void setAppliedRuleToLayer(int layer_id_from, long appliedrule_id, int layer_id_to) throws Exception {
		Log.i(this, "setAppliedRuleToLayer(" + layer_id_from + ", " + appliedrule_id + ", " + layer_id_to + ")");

		AppliedRule appliedRule = getAppliedRule(layer_id_from, appliedrule_id);
		Layer layer = architectureDefinition.getLayer(layer_id_to);
		if (layer != null) {
			appliedRule.setToLayer(layer);
		}
	}

	public int getAppliedRuleToLayer(int layer_id_from, long appliedrule_id) throws Exception {
		Log.i(this, "getAppliedRuleToLayer(" + layer_id_from + ", " + appliedrule_id + ")");

		AppliedRule appliedRule = getAppliedRule(layer_id_from, appliedrule_id);
		Layer layer_to = appliedRule.getToLayer();

		if (layer_to != null) {
			return layer_to.getId();
		}
		throw new Exception("Layer to does not exist");
	}

	public void setAppliedRuleIsEnabled(int layer_id_from, long appliedrule_id, boolean value) throws Exception {
		Log.i(this, "setAppliedRuleIsEnabled(" + layer_id_from + ", " + appliedrule_id + ", " + value + ")");

		AppliedRule appliedRule = getAppliedRule(layer_id_from, appliedrule_id);
		appliedRule.setEnabled(value);
	}

	public boolean getAppliedRuleIsEnabled(int layer_id_from, long appliedrule_id) throws Exception {
		Log.i(this, "getAppliedRuleIsEnabled(" + layer_id_from + ", " + appliedrule_id + ")");

		AppliedRule appliedRule = getAppliedRule(layer_id_from, appliedrule_id);
		return appliedRule.isEnabled();
	}

	public void setAppliedRuleRuleType(int layer_id_from, long appliedrule_id, String value) throws Exception {
		Log.i(this, "setAppliedRuleRuleType(" + layer_id_from + ", " + appliedrule_id + ", " + value + ")");

		AppliedRule appliedRule = getAppliedRule(layer_id_from, appliedrule_id);
		appliedRule.setRuleType(value);
	}

	public String getAppliedRuleRuleType(int layer_id_from, long appliedrule_id) throws Exception {
		Log.i(this, "getAppliedRuleRuleType(" + layer_id_from + ", " + appliedrule_id + ")");

		AppliedRule appliedRule = getAppliedRule(layer_id_from, appliedrule_id);
		AbstractRuleType ruleType = appliedRule.getRuleType();
		if (ruleType != null) {
			return ruleType.getName();
		}
		throw new Exception("Applied rule does not have an rule type");
	}

	public long newAppliedRuleException(int layer_id_from, long appliedrule_id, String unitName, String unitType) throws Exception {
		Log.i(this, "newAppliedRuleException(" + layer_id_from + "," + appliedrule_id + "," + unitName + "," + unitType + ")");

		AppliedRule appliedRule = getAppliedRule(layer_id_from, appliedrule_id);
		return appliedRule.newException(unitName, unitType);
	}

	public void removeAppliedRuleExceptions(int layer_id_from, long appliedrule_id) throws Exception {
		Log.i(this, "removeAppliedRuleExceptions(" + layer_id_from + ", " + appliedrule_id + ")");

		AppliedRule appliedRule = getAppliedRule(layer_id_from, appliedrule_id);
		appliedRule.removeAllExceptions();
	}

	public ArrayList<Long> getAppliedRuleExceptions(int layer_id_from, long appliedrule_id) throws Exception {
		Log.i(this, "removeAppliedRuleExceptions(" + layer_id_from + ", " + appliedrule_id + ")");

		AppliedRule appliedRule = getAppliedRule(layer_id_from, appliedrule_id);
		return appliedRule.getExceptions();
	}

	public String getAppliedruleExceptionName(int layer_id_from, long appliedrule_id, long exception_id) throws Exception {
		Log.i(this, "getAppliedruleExceptionName(" + layer_id_from + ", " + appliedrule_id + ")");

		AppliedRule appliedRule = getAppliedRule(layer_id_from, appliedrule_id);
		SoftwareUnitDefinition exception = appliedRule.getExeption(exception_id);

		if (exception != null) {
			return exception.getName();
		}
		return null;
	}

	public String getAppliedruleExceptionType(int layer_id_from, long appliedrule_id, long exception_id) throws Exception {
		Log.i(this, "getAppliedruleExceptionType(" + layer_id_from + ", " + appliedrule_id + ")");

		AppliedRule appliedRule = getAppliedRule(layer_id_from, appliedrule_id);
		SoftwareUnitDefinition exception = appliedRule.getExeption(exception_id);

		if (exception != null) {
			return exception.getType();
		}
		return null;
	}

	/**
	 * Set the project path
	 * 
	 * @param path A string to the project that needs to be scanned
	 */
	public void setProjectPath(String path) {
		Log.i(this, "getAppliedRules()");

		configuration.setSetting(Configuration.PROJECT_PATH, path);
	}

	/**
	 * Get project path.
	 * 
	 * @return The path to the project that needs to be scanned
	 */
	public String getProjectPath() {
		Log.i(this, "getAppliedRules()");

		return configuration.getSetting(Configuration.PROJECT_PATH);
	}

	/**
	 * Set the output path
	 * 
	 * @param path A string to the output directory
	 */
	public void setOutputPath(String path) {
		Log.i(this, "getAppliedRules()");

		configuration.setSetting(Configuration.OUTPUT_PATH, path);
	}

	/**
	 * Get output path
	 * 
	 * @return The path where the output needs to be saved
	 */
	public String getOutputPath() {
		Log.i(this, "getAppliedRules()");

		return configuration.getSetting(Configuration.OUTPUT_PATH);
	}

	/**
	 * Sets the output type.
	 * 
	 * @param format Values are: text, html or xml.
	 */
	public void setOutputType(String format) {
		Log.i(this, "getAppliedRules()");

		configuration.setSetting(Configuration.OUTPUT_FORMAT, format);
	}

	/**
	 * Get the output format
	 * 
	 * @return Output format
	 */
	public String getOutputType() {
		Log.i(this, "getAppliedRules()");

		return configuration.getSetting(Configuration.OUTPUT_FORMAT);
	}

	/**
	 * Get rule type based on String
	 * 
	 * @return Rule type
	 */
	public AbstractRuleType getRuleType(String name) {
		Log.i(this, "getAppliedRules()");

		return configuration.getRuleType(name);
	}

	public boolean isRuleApplied(Layer fromLayer, Layer toLayer, String ruleName) {
		Log.i(this, "getAppliedRules()");

		if (fromLayer != null) {
			return fromLayer.hasAppliedRule(ruleName, toLayer);
		} else {
			return false;
		}
	}

	public Layer getLayerBySoftwareUnitName(String name) {
		Log.i(this, "getAppliedRules()");

		return this.architectureDefinition.getLayerBySoftwareUnitName(name);
	}
}
