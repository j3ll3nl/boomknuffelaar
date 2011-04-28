package hu.project.innovation.configuration.model;

import hu.project.innovation.utils.Log;
import hu.project.innovation.utils.XMLable;

import java.util.ArrayList;
import java.util.HashMap;

public class Layer implements XMLable {

	private int id;
	private String name;
	private String description;
	private Layer parentLayer;
	private Layer childLayer;
	private ArrayList<AppliedRule> appliedRules;
	private HashMap<String, SoftwareUnitDefinition> softwareUnits;

	public Layer() {
		this.appliedRules = new ArrayList<AppliedRule>();
		this.softwareUnits = new HashMap<String, SoftwareUnitDefinition>();
	}

	public Layer(String name, String description) {
		Log.i(this, "constructor(\"" + name + "\", \"" + description + "\")");
		this.name = name;
		this.description = description;

		this.appliedRules = new ArrayList<AppliedRule>();
		this.softwareUnits = new HashMap<String, SoftwareUnitDefinition>();
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
	 * Add a new software unit with the specified name and type
	 * 
	 * @param name
	 * @param type
	 * @return Return true if the specified name was not already used for a software unit
	 */
	public boolean addSoftwareUnit(String name, String type) {
		return this.addSoftwareUnit(new SoftwareUnitDefinition(name, type, this));
	}
	
	/**
	 * Add a {@link SoftwareUnitDefinition}
	 * 
	 * @param sud
	 * @return <code>true</code> if the software unit with that name wasn't already in this layer. Else
	 * return <code>false</code>
	 */
	public boolean addSoftwareUnit(SoftwareUnitDefinition sud) {
		if(this.softwareUnits.containsKey(name)) {
			return false;
		} else {
			this.softwareUnits.put(sud.getName(), sud);
			return true;
		}
	}
	
	/**
	 * Removes the SoftwareUnit with the specified name
	 * 
	 * @param name
	 * @return 
	 * the previous SoftwareUnit associated with that name, or null if there was no SoftwareUnit with that name.
	 */
	public SoftwareUnitDefinition removeSoftwareUniteDefinition(String name) {
		return this.softwareUnits.remove(name);
	}
	
	/**
	 * Get a software unit by its name
	 * 
	 * @param name
	 * @return The software unit with the specified name of null if this layer doesn't have that unit
	 */
	public SoftwareUnitDefinition getSoftwareUnit(String name) {
		return this.softwareUnits.get(name);
	}
	
	/**
	 * This function will return all the software units in an <code>ArrayList</code>.
	 * 
	 * @return
	 */
	public ArrayList<SoftwareUnitDefinition> getAllSoftwareUnitDefinitions() {
		ArrayList<SoftwareUnitDefinition> sudList = new ArrayList<SoftwareUnitDefinition>();
		sudList.addAll(this.softwareUnits.values());
		return sudList;
	}

	@Override
	public String toXML() {
		String xml = "";

		xml += "\t<layer>\n";
		xml += "\t\t<id>" + this.id + "</id>\n";
		xml += "\t\t<name>" + this.name + "</name>\n";
		xml += "\t\t<description>" + this.description + "</description>\n";
		
		if (softwareUnits != null) {
			for(SoftwareUnitDefinition sud : this.getAllSoftwareUnitDefinitions()) 
			{
				xml += sud.toXML();
			}
		}
		
		if (appliedRules != null) {
			for(AppliedRule rule : appliedRules) {
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
				if (r.getToLayer().getName().equals(toLayerName) 
						&& r.getRuleType().getName().equals(ruleName)) {
					return true;
				}
			}
		}
		return false;
	}
}
