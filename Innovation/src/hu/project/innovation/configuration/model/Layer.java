package hu.project.innovation.configuration.model;

import hu.project.innovation.utils.Log;
import hu.project.innovation.utils.XMLable;

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
		Log.i(this, "constructor(\"" + name + "\", \"" + description + "\")");
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

		xml += "\t<layer>\n";
		xml += "\t\t<id>" + this.id + "</id>\n";
		xml += "\t\t<name>" + this.name + "</name>\n";
		xml += "\t\t<description>" + this.description + "</description>\n";
		if (appliedRules != null) {
			for (AppliedRule r : this.appliedRules) {
				xml += r.toXML();
			}
		}
		xml += "\t</layer>\n";

		return xml;
	}

	public String toString() {
		return getName();
	}

	public boolean hasAppliedRule(AbstractRuleType ruleType, Layer toLayer) {
		if (appliedRules != null) {
			for (AppliedRule r : this.appliedRules) {
				if (r.getToLayer().getName().equals(toLayer.getName()) && r.getRuleType().getName().equals(ruleType.getName())) {
					return true;
				}
			}
		}
		return false;
	}
}
