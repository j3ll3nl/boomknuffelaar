package hu.project.innovation.configuration.model;

import hu.project.innovation.Logger;

import java.util.ArrayList;

public class Layer {

	private String name;
	private String description;
	private Layer parentLayer;
	private Layer childLayer;
	private ArrayList<SoftwareUnitDefinition> softwareUnits;
	private ArrayList<AppliedRule> appliedRules;

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
}
