package hu.project.innovation.configuration.model;


public interface ConfigurationServiceIF {

	public void newConfiguration(String name, String description);

	public boolean newLayer(String name, String description);

	public boolean newSoftwareUnit(String layerName, String unitName, String unitType);

	public void newAppliedRule(String fromLayerName, String toLayerName, String ruleName);

	public void setOutputPath(String path);

	public String getOutputPath();

	public void setOutputFormat(String format);

	public String getOutputFormat();

	public String architectureToXML();

}
