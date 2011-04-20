package hu.project.innovation.configuration.model;

import hu.project.innovation.Logger;

import java.io.File;
import java.util.ArrayList;

public class ConfigurationService implements ConfigurationServiceIF {

	private ArchitectureDefinition architecture;
	private Configuration configuration;
	private static ConfigurationService instance;

	private ConfigurationService() {
		Logger.getInstance().log(this.getClass().getSimpleName());

		this.configuration = new Configuration();
	}

	public void newArchitecture(String name, String description) {
		this.architecture = new ArchitectureDefinition(name, description);
	}

	public void openArchitecture(File file) {

	}

	public boolean newLayer(String name, String description) {
		if (null == this.architecture)
			return false;

		return this.architecture.addLayer(new Layer(name, description));
	}

	public void setRuleStatus(String ruleName, boolean status) {
		// this.configuration.getRuleSet().setRuleStatus(ruleName, status);
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
		// TODO Auto-generated method stub
		return null;

	}

	public void removeLayer(Object selectedObject) {
		// TODO Auto-generated method stub
		
	}
}
