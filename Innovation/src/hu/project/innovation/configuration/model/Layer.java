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
	public ArrayList<AppliedRule> appliedRules;
	private ArrayList<SoftwareUnitDefinition> softwareUnitDefinitions;

	public Layer() {
		this.appliedRules = new ArrayList<AppliedRule>();
		this.softwareUnitDefinitions = new ArrayList<SoftwareUnitDefinition>();
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

	public void updateId(int i) {
		Log.i(this, "updateId(" + i + ")");
		setId(i);
		if (childLayer != null) {
			childLayer.updateId(i + 1);
		}
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

		// Current layer
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

		if (childLayer != null) {
			xml += childLayer.toXML();
		}

		return xml;
	}

	public String toString() {
		return getName();
	}

	public boolean hasAppliedRule(String ruleName, Layer toLayer) {
		if (appliedRules != null) {
			for (AppliedRule appliedRule : this.appliedRules) {
				if (appliedRule.getToLayer() == toLayer && appliedRule.getRuleType().getName().equals(ruleName)) {
					return true;
				}
			}
		}
		return false;
	}

	public void moveUp() throws Exception {
		Log.i(this, "moveUp()");
		if (getParentLayer() == null) {
			throw new Exception("Layer is already at the top");
		} else {
			// Imagine that the current layer is D
			Layer c = getParentLayer();
			Layer b = c.getParentLayer();
			Layer e = getChildLayer();

			setParentLayer(b);
			setChildLayer(c);
			if (b != null) {
				b.setChildLayer(this);
			}
			if (c != null) {
				c.setChildLayer(e);
				c.setParentLayer(this);
			}
			if (e != null) {
				e.setParentLayer(c);
			}
		}
	}

	public void moveDown() throws Exception {
		Log.i(this, "moveDown()");
		if (getChildLayer() == null) {
			throw new Exception("Layer is already at the bottom");
		} else {
			Layer b = getParentLayer();
			Layer d = getChildLayer();
			Layer e = d.getChildLayer();

			setParentLayer(d);
			setChildLayer(e);

			if (b != null) {
				b.setChildLayer(d);
			}
			if (d != null) {
				d.setChildLayer(this);
				d.setParentLayer(b);
			}
			if (e != null) {
				e.setParentLayer(this);
			}

		}
	}

	public void addChildLayer(Layer layer) {
		Log.i(this, "addChildLayer(" + layer + ") [" + this + "]");
		if (childLayer != null) {
			Log.i(this, "addChildLayer(" + layer + ") - asking child to add this layer");
			childLayer.addChildLayer(layer);
		} else {
			Log.i(this, "addChildLayer(" + layer + ") - adding layer to this layer");
			childLayer = layer;
			childLayer.setParentLayer(this);
		}
	}

	public Layer getFirstLayer() {
		if (parentLayer != null) {
			return parentLayer.getFirstLayer();
		} else {
			return this;
		}
	}

	public Layer getLayer(int id) {
		if (getId() == id) {
			return this;
		} else {
			if (childLayer != null) {
				return childLayer.getLayer(id);
			}
		}
		return null;
	}

	public void removeLayer(Layer layer) {
		if (this == layer) {
			Layer topLayer = getParentLayer();
			Layer childLayer = getChildLayer();

			if (topLayer != null) {
				topLayer.setChildLayer(childLayer);
			}
			if (childLayer != null) {
				childLayer.setParentLayer(topLayer);
			}
		} else {
			layer.removeLayer(layer);
		}
	}

	public Layer getLayer(String name) {
		if (getName().equals(name)) {
			return this;
		} else {
			if (childLayer != null) {
				return childLayer.getLayer(name);
			}
		}
		return null;
	}

	public Layer getLayerNameBySoftwareUnitName(String softwareUnitName) {
		for (SoftwareUnitDefinition softwareunitdefinition : softwareUnitDefinitions) {
			if (softwareunitdefinition.getName().equals(softwareUnitName)) {
				return this;
			}
		}
		return null;
	}

	public void printVolgorde() {
		String message = "Ik ben nummer " + getId() + ", mijn naam is: " + getName() + ", mijn parent is: ";
		Layer p = getParentLayer();
		Layer c = getChildLayer();

		if (p != null) {
			message += getParentLayer().getName();
		} else {
			message += "-";
		}
		message += ", mijn child is: ";
		if (c != null) {
			message += getChildLayer().getName();
		} else {
			message += "-";
		}

		Log.i(this, message);

		if (childLayer != null) {
			childLayer.printVolgorde();
		}
	}
}
