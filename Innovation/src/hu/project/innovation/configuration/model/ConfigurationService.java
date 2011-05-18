package hu.project.innovation.configuration.model;

import hu.project.innovation.configuration.model.dependencies.AllowedDependency;
import hu.project.innovation.configuration.model.dependencies.Dependencies;
import hu.project.innovation.configuration.model.dependencies.Dependency;
import hu.project.innovation.configuration.model.dependencies.Dependency.DepSoftwareComponent;
import hu.project.innovation.configuration.model.rules.AbstractRuleType;
import hu.project.innovation.utils.ArchDefXMLReader;
import hu.project.innovation.utils.Log;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;

import org.xml.sax.InputSource;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.XMLReaderFactory;

public final class ConfigurationService {

	private ArchitectureDefinition architectureDefinition;
	private Configuration configuration;
	private static ConfigurationService instance;
	private Dependencies dependencies;
	private Dependencies allowedDependencies;

	private ConfigurationService() {
		Log.i(this, "constructor()");

		this.configuration = new Configuration();
		
		//(allowed) dependencies
		this.dependencies = new Dependency();
		this.allowedDependencies = new AllowedDependency();
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

		XMLReader xr = XMLReaderFactory.createXMLReader();
		ArchDefXMLReader reader = new ArchDefXMLReader();
		//reader.validateXML(file);
		xr.setContentHandler(reader);
		xr.parse(new InputSource(new FileReader(file)));

		architectureDefinition = reader.getArchitectureDefinition();
		setProjectPath(reader.getProjectPath());
		setOutputPath(reader.getOutputPath());
		setOutputType(reader.getOutputFormat());
	}

	/**
	 * Save the current configuration to the given file.
	 * 
	 * @param file <code>File</code> object where the .xml needs to be saved to.
	 * @throws Exception If an error occurs
	 */
	public void saveConfiguration(File file) throws Exception {
		Log.i(this, "saveConfiguration(" + file + ")");
		
		if (!hasArchitectureDefinition()) {

		} else {
			
			String configurationXML = "<?xml version=\"1.0\" encoding=\"utf-8\"?>\n";
			configurationXML += "<configuration>\n";
			configurationXML += architectureDefinition.toXML();
			configurationXML += "\t<paths>\n";
			configurationXML += "\t\t<project>" + getProjectPath() + "</project>\n";
			configurationXML += "\t\t<output format=\"" + getOutputFormat() + "\">" + getOutputPath() + "</output>\n";
			configurationXML += "\t</paths>\n";
			configurationXML += "</configuration>";
			
			FileWriter fstream = new FileWriter(file);
			BufferedWriter out = new BufferedWriter(fstream);
			out.write(configurationXML);
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

	public AppliedRule newAppliedRule(Layer fromLayer, Layer toLayer, AbstractRuleType ruleType) {
		if (null != fromLayer && null != toLayer && null != ruleType) {
			return fromLayer.addAppliedRule(ruleType, toLayer);
		}
		return null;
	}

	public void removeAppliedRule(Layer layer, AppliedRule appliedrule) {
		
		//TODO: nog te maken..
	}

	public Layer getLayerBySoftwareUnitName(String name) {
		return this.architectureDefinition.getLayerBySoftwareUnitName(name);
	}

	public ArrayList<Layer> getLayers() {
		if (!hasArchitectureDefinition()) {
			return null;
		}
		return this.architectureDefinition.getAllLayers();
	}

	public String architectureToXML() {
		return this.architectureDefinition.toXML();
	}

	public boolean isRuleApplied(Layer fromLayer, Layer toLayer, String ruleName) {
		if (fromLayer != null) {
			return fromLayer.hasAppliedRule(ruleName, toLayer);
		} else {
			return false;
		}
	}
	
	// custom dependencies

	/**
	 * Add a dependency
	 */
	public void addDependency(String groupId, String artifactId, String version, String type, String scope) {
		this.dependencies.addDepSoftwareComponent(groupId, artifactId, version, type, scope);
	}
	
	/**
	 * Remove a dependency
	 * 
	 * @param groupId the component name to remove
	 */
	public void removeDependency(String artifactId) {
		dependencies.removeDepSoftwareComponent(artifactId);
	}
	
	/**
	 * return a dependency (if exists)
	 * @param groupId the component to return
	 */
	public DepSoftwareComponent getDependency(String artifactId) {
		return dependencies.getDepSoftwareComponent(artifactId);
	}
	
	/**
	 * @return all dependencies
	 */
	public DepSoftwareComponent[] getDependencies() {
		return dependencies.getDepSoftwareComponents();
	}
	
	// Allowed dependencies
	
	/**
	 * Add a allowed dependency
	 */
	public void addAllowedDependency(String groupId, String artifactId, String version, String type, String scope) {
		this.allowedDependencies.addDepSoftwareComponent(groupId, artifactId, version, type, scope);
	}
	
	/**
	 * Remove a allowed dependency
	 * 
	 * @param groupId the component name to remove
	 */
	public void removeAllowedDependency(String artifactId) {
		allowedDependencies.removeDepSoftwareComponent(artifactId);
	}
	
	/**
	 * return a allowed dependency (if exists)
	 * @param groupId the component to return
	 */
	public DepSoftwareComponent getAllowedDependency(String artifactId) {
		return allowedDependencies.getDepSoftwareComponent(artifactId);
	}
	
	/**
	 * @return all allowed dependencies
	 */
	public DepSoftwareComponent[] getAllowedDependencies() {
		return allowedDependencies.getDepSoftwareComponents();
	}
	
}
