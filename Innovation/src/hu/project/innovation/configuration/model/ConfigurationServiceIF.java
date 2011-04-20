package hu.project.innovation.configuration.model;


public interface ConfigurationServiceIF {

	public void newArchitecture(String name, String description);

	public boolean newLayer(String name, String description);

	public boolean newSoftwareUnit(String layerName, String unitName, String unitType);

	public void newAppliedRule(String fromLayerName, String toLayerName, String ruleName);

	public void setRuleStatus(String ruleName, boolean status);

	public void setOutputPath(String path);

	public String getOutputPath();

	public void setOutputFormat(String format);

	public String getOutputFormat();

	public String architectureToXML();
}
