package hu.project.innovation.configuration.model;

import hu.project.innovation.Logger;
import hu.project.innovation.XMLable;

import java.util.ArrayList;

public class Layer implements XMLable {

	private String name;
	private String description;
	private Layer parentLayer;
	private Layer childLayer;
	private ArrayList<SoftwareUnitDefinition> softwareUnits;
	private ArrayList<AppliedRule> appliedRules;

	public Layer() {
		
	}
	
	public Layer(String name, String description) {
		Logger.getInstance().log(this.getClass().getSimpleName());
		this.name = name;
		this.description = description;

		this.softwareUnits = new ArrayList<SoftwareUnitDefinition>();
		this.appliedRules = new ArrayList<AppliedRule>();
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

	public boolean addSoftwareUnit(SoftwareUnitDefinition unit) {
		return softwareUnits.add(unit);
	}

	public SoftwareUnitDefinition getSoftwareUnit(String name) {

		for (SoftwareUnitDefinition sud : softwareUnits) {
			if (sud.getName().equals(name)) {
				return sud;
			}
		}
		return null;
	}

	public void addAppliedRule(AbstractRuleType ruleType, Layer toLayer) {
		AppliedRule r = new AppliedRule(ruleType, this, toLayer);
		this.appliedRules.add(r);
	}

	@Override
	public String toXML() {
		String xml = "<layer>\n";
		xml += "<name>"+this.name+"</name>\n";
		for(AppliedRule r : this.appliedRules) {
			xml += r.toXML();
		}		
		xml += "</layer>\n";
		
		return xml;
	}
}
