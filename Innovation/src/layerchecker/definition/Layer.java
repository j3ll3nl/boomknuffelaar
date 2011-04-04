package layerchecker.definition;

import java.util.ArrayList;

import main.Logger;

public class Layer {

	private String name;
	private String description;
	private Layer parentLayer;
	private Layer childLayer;
	private ArrayList<SoftwareUnitDefinition> softwareUnits;
	private ArrayList<RuleException> ruleExceptions;
	
	public Layer(String name) {
		Logger.getInstance().log(this.getClass().getSimpleName());
		this.name = name;
		
		this.softwareUnits = new ArrayList<SoftwareUnitDefinition>();
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
		return parentLayer;
	}
	
	public void setParentLayer(Layer layer) {
		parentLayer = layer;
	}
	
	public void addSoftwareUnit(SoftwareUnitDefinition unit) {
		softwareUnits.add(unit);
	}
	
	public SoftwareUnitDefinition getSoftwareUnit() {
		
		for(SoftwareUnitDefinition sud : softwareUnits) {
			if(sud.getName() == name) {
				return sud;
			}
		}
		
		return null;
		
	}
	
	public void addRuleException(String name, boolean permission, Layer layer) {
		RuleException re = new RuleException(name, permission, layer);
		ruleExceptions.add(re);
	}	
	
	public String toString() {
		String s = "- " + name + " - " + description + "\n";
		for(SoftwareUnitDefinition sud : softwareUnits) {
			s = s + sud.toString() + "\n";
		}
		return s;
	}
	
}
