package hu.project.innovation.configuration.model;

import hu.project.innovation.Logger;
import hu.project.innovation.XMLable;

import java.util.ArrayList;

public class Layer implements XMLable {

	private int id;
	private String name;
	private String description;
	private Layer parentLayer;
	private Layer childLayer;
	private ArrayList<AppliedRule> appliedRules;

	public Layer() {
		this.appliedRules = new ArrayList<AppliedRule>();
	}
	
	public Layer(String name, String description) {
		Logger.getInstance().log(this.getClass().getSimpleName());
		this.name = name;
		this.description = description;
		this.appliedRules = new ArrayList<AppliedRule>();
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
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

	public void addAppliedRule(AbstractRuleType ruleType, Layer toLayer) {
		AppliedRule r = new AppliedRule(ruleType, this, toLayer);
		this.appliedRules.add(r);
	}

	@Override
	public String toXML() {
		String xml = ""; 
		
		xml += "<layer>\n";
		xml += "<id>"+this.id+"</id>\n";
		xml += "<name>"+this.name+"</name>\n";
		xml += "<description>"+this.description+"</description>\n";
//		if(softwareUnits != null){
//			for(SoftwareUnitDefinition r : this.softwareUnits) {
//				xml += r.toXML();
//			}
//		}	
		if(appliedRules != null){
			for(AppliedRule r : this.appliedRules) {
				xml += r.toXML();
			}
		}	
		xml += "</layer>\n";
		
		return xml;
	}
}