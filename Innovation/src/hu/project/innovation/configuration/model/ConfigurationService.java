package hu.project.innovation.configuration.model;

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

public class ConfigurationService {

	private ArchitectureDefinition architecture;
	private Configuration configuration;
	private static ConfigurationService instance;

	private ConfigurationService() {
		Log.i(this, "constructor()");

		this.configuration = new Configuration();
	}

	public static ConfigurationService getInstance() {
		if (instance == null) {
			instance = new ConfigurationService();
		}
		return instance;
	}

	public void newConfiguration(String name, String description) {
		Log.i(this, "newConfiguration(" + name + ", " + description + ")");
		this.architecture = new ArchitectureDefinition(name, description);
	}

	public void openConfiguration(File file) throws Exception {
		Log.i(this, "openConfiguration(" + file + ")");
		XMLReader xr = XMLReaderFactory.createXMLReader();
		ArchDefXMLReader reader = new ArchDefXMLReader();
		reader.validateXML(file);
		xr.setContentHandler(reader);
		xr.parse(new InputSource(new FileReader(file)));

		architecture = reader.getArchitectureDefinition();
	}

	public void saveConfiguration(File file) throws Exception {
		Log.i(this, "saveConfiguration(" + file + ")");
		FileWriter fstream = new FileWriter(file);
		BufferedWriter out = new BufferedWriter(fstream);
		out.write(architecture.toXML());
		out.close();
	}

	public boolean newLayer(String name, String description) {
		if (null == this.architecture)
			return false;

		return this.architecture.addLayer(new Layer(name, description));
	}

	public boolean removeLayer(Layer layer) {
		if (null == this.architecture) {
			return false;
		}
		return this.architecture.removeLayer(layer);
	}

	public void moveLayerUp(Layer layer) {
		// TODO: nog te schrijven
	}

	public void moveLayerDown(Layer layer) {
		// TODO: not te schrijven
	}

	public void setProjectPath(String path) {
		this.configuration.setSetting(Configuration.PROJECT_PATH, path);
	}

	public void setOutputPath(String path) {
		this.configuration.setSetting(Configuration.OUTPUT_PATH, path);
	}

	public void setOutputFormat(String format) {
		this.configuration.setSetting(Configuration.OUTPUT_FORMAT, format);
	}

	public String getProjectPath() {
		return this.configuration.getSetting(Configuration.PROJECT_PATH);
	}

	public String getOutputPath() {
		return this.configuration.getSetting(Configuration.OUTPUT_PATH);
	}

	public String getOutputFormat() {
		return this.configuration.getSetting(Configuration.OUTPUT_FORMAT);
	}

	public boolean newComponent(String layerName, String unitName, String unitType) {
		Layer layer = this.architecture.getLayer(layerName);

		if (null != layer) {
			return layer.addSoftwareUnit(unitName, unitType);
		} else {
			return false;
		}
	}

	public void removeComponent(SoftwareUnitDefinition component) {

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
