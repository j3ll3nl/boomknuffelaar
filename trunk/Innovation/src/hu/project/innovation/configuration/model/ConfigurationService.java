package hu.project.innovation.configuration.model;

import hu.project.innovation.utils.ArchDefXMLReader;
import hu.project.innovation.utils.Log;

import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;

import org.xml.sax.InputSource;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.XMLReaderFactory;

public class ConfigurationService implements ConfigurationServiceIF {

	private ArchitectureDefinition architecture;
	private Configuration configuration;
	private static ConfigurationService instance;

	private ConfigurationService() {
		Log.i(this, "constructor()");

		this.configuration = new Configuration();
	}

	public void newConfiguration(String name, String description) {
		Log.i(this, "newConfiguration(" + name + ", " + description + ")");
		this.architecture = new ArchitectureDefinition(name, description);
	}

	public void openConfiguration(File file) throws Exception {
		Log.i(this, "openConfiguration(" + file + ")");
		XMLReader xr = XMLReaderFactory.createXMLReader();
		ArchDefXMLReader reader = new ArchDefXMLReader();
		// reader.validateXML(file);
		xr.setContentHandler(reader);
		xr.parse(new InputSource(new FileReader(file)));

		architecture = reader.getArchitectureDefinition();
	}

	public void saveConfiguration(File file) throws Exception {
		Log.i(this, "saveConfiguration(" + file + ")");
		throw new Exception("Sample error message.");
	}

	public boolean newLayer(String name, String description) {
		if (null == this.architecture)
			return false;

		return this.architecture.addLayer(new Layer(name, description));
	}

	public void setOutputPath(String path) {
		this.configuration.setSetting(Configuration.OUTPUT_PATH, path);
	}

	public String getOutputPath() {
		return this.configuration.getSetting(Configuration.OUTPUT_PATH);
	}

	public void setOutputFormat(String format) {
		this.configuration.setSetting(Configuration.OUTPUT_FORMAT, format);
	}

	public String getOutputFormat() {
		return this.configuration.getSetting(Configuration.OUTPUT_FORMAT);
	}

	public static ConfigurationService getInstance() {
		if (instance == null) {
			instance = new ConfigurationService();
		}
		return instance;
	}

	public boolean newSoftwareUnit(String layerName, String unitName, String unitType) {
		Layer layer = this.architecture.getLayer(layerName);

		if (null != layer) {
			return this.architecture.addSoftwareUnit(new SoftwareUnitDefinition(unitName, unitType, layer));
		} else {
			return false;
		}
	}

	public void newAppliedRule(String fromLayerName, String toLayerName, String ruleName) {
		Layer fromLayer = this.architecture.getLayer(fromLayerName);
		Layer toLayer = this.architecture.getLayer(toLayerName);
		AbstractRuleType ruleType = this.configuration.getRule(ruleName);

		if (null != fromLayer && null != toLayer && null != ruleType) {
			fromLayer.addAppliedRule(ruleType, toLayer);
		}
	}

	public String architectureToXML() {
		return this.architecture.toXML();
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

	public boolean removeLayer(Layer layer) {
		if (null == this.architecture)
			return false;

		return this.architecture.removeLayer(layer);
	}

	public boolean isRuleApplied(String fromLayerName, String toLayerName, String ruleName) {
		Layer fromLayer = this.architecture.getLayer(fromLayerName);
		Layer toLayer = this.architecture.getLayer(toLayerName);
		AbstractRuleType ruleType = this.configuration.getRule(ruleName);

		if (null != fromLayer && null != toLayer && null != ruleType) {
			return fromLayer.hasAppliedRule(ruleType, toLayer);
		} else
			return false;
	}

}
