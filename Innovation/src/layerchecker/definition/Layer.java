package layerchecker.definition;

import java.util.ArrayList;

public class Layer {

	private String name;
	private String description;
	private Layer parentLayer;
	private Layer childLayer;
	private ArrayList<SoftwareUnitDefinition> softwareUnits;
	
	public Layer(String name) {
		this.name = name;
	}
	
	public String getName() {
		return name;
	}
	
	public void setDescription(String desc) {
		description = desc;
	}
	
	public Layer getChildLayer() {
		return childLayer;
	}
	
	public void setChildLayer(Layer layer) {
		childLayer = layer;
	}
	
	public Layer getParentLayer() {
		return childLayer;
	}
	
	public void setParentLayer(Layer layer) {
		childLayer = layer;
	}
	
	public void addSoftwareUnit(SoftwareUnitDefinition unit) {
		softwareUnits.add(unit);
	}
	
	public String toString() {
		return "";
	}
	
}

