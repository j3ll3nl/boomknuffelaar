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
	private ArrayList<SoftwareUnitDefinition> softwareUnitDefinitions;

	public Layer() {
	}

	public Layer(String name, String description) {
		Log.i(this, "constructor(\"" + name + "\", \"" + description + "\")");

		setName(name);
		setDescription(description);

		this.appliedRules = new ArrayList<AppliedRule>();
		this.softwareUnitDefinitions = new ArrayList<SoftwareUnitDefinition>();
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

	/**
	 * Add a {@link SoftwareUnitDefinition}
	 * 
	 * @param name
	 * @param type
	 * @return Return true if the specified name was not already used for a software unit
	 */
	public void addSoftwareUnit(SoftwareUnitDefinition unit) throws Exception {
		if (softwareUnitDefinitions.contains(unit)) {
			throw new Exception("Software unit is already added to this layer");
		} else {
			softwareUnitDefinitions.add(unit);
			unit.setLayer(this);
		}
	}

	/**
	 * Removes the SoftwareUnit with the specified name
	 * 
	 * @param name
	 * @return the previous SoftwareUnit associated with that name, or null if there was no SoftwareUnit with that name.
	 */
	public void removeSoftwareUniteDefinition(SoftwareUnitDefinition unit) throws Exception {
		if (!softwareUnitDefinitions.contains(unit)) {
			throw new Exception("Software unit does not exist in this layer");
		} else {
			this.softwareUnitDefinitions.remove(unit);
		}
	}

	/**
	 * Get a software unit by its name
	 * 
	 * @param name
	 * @return The software unit with the specified name of null if this layer doesn't have that unit
	 */
	public SoftwareUnitDefinition getSoftwareUnit(String name) {
		for (SoftwareUnitDefinition softwareunitdefinition : softwareUnitDefinitions) {
			if (softwareunitdefinition.getName().equals(name)) {
				return softwareunitdefinition;
			}
		}
		return null;
	}

	/**
	 * This function will return all the software units in an <code>ArrayList</code>.
	 * 
	 * @return
	 */
	public ArrayList<SoftwareUnitDefinition> getAllSoftwareUnitDefinitions() {
		return softwareUnitDefinitions;
	}

	@Override
	public String toXML() {
		String xml = "";

		xml += "\t<layer>\n";
		xml += "\t\t<id>" + this.id + "</id>\n";
		xml += "\t\t<name>" + this.name + "</name>\n";
		xml += "\t\t<description>" + this.description + "</description>\n";

		if (softwareUnitDefinitions != null) {
			for (SoftwareUnitDefinition sud : this.getAllSoftwareUnitDefinitions()) {
				xml += sud.toXML();
			}
		}

		if (appliedRules != null) {
			for (AppliedRule rule : appliedRules) {
				xml += rule.toXML();
			}
		}

		xml += "\t</layer>\n";

		return xml;
	}

	public String toString() {
		return getName();
	}

	public boolean hasAppliedRule(String ruleName, String toLayerName) {
		if (appliedRules != null) {
			for (AppliedRule r : this.appliedRules) {
				if (r.getToLayer().getName().equals(toLayerName) && r.getRuleType().getName().equals(ruleName)) {
					return true;
				}
			}
		}
		return false;
	}
}
