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
	private boolean interfaceAccessOnly;

	public Layer() {
		this.appliedRules = new ArrayList<AppliedRule>();
		this.softwareUnitDefinitions = new ArrayList<SoftwareUnitDefinition>();
		this.interfaceAccessOnly = false;
	}

	public Layer(String name, String description) {
		this();
		Log.i(this, "constructor(\"" + name + "\", \"" + description + "\")");

		setName(name);
		setDescription(description);
	}

	public final int getId() {
		return id;
	}

	public final void setId(int id) {
		this.id = id;
	}

	public final void updateId(int i) {
		Log.i(this, "updateId(" + i + ")");
		setId(i);
		if (childLayer != null) {
			childLayer.updateId(i + 1);
		}
	}

	public final String getName() {
		return name;
	}

	public final void setName(String name) {
		this.name = name;
	}

	public final String getDescription() {
		return description;
	}

	public final void setDescription(String description) {
		this.description = description;
	}

	public final Layer getChildLayer() {
		return childLayer;
	}

	public final void setChildLayer(Layer layer) {
		childLayer = layer;
	}

	public final Layer getParentLayer() {
		return parentLayer;
	}

	public final void setParentLayer(Layer layer) {
		parentLayer = layer;
	}
	
	public final boolean isInterfaceAccessOnly() {
		return this.interfaceAccessOnly;
	}
	
	public final void setInterfaceAccesOnly(boolean bool) {
		this.interfaceAccessOnly = bool;
	}

	public final AppliedRule addAppliedRule(AbstractRuleType ruleType, Layer toLayer) {
		AppliedRule r = new AppliedRule(ruleType, this, toLayer);
		this.appliedRules.add(r);
		return r;
	}
	
	public void addAppliedRule(AppliedRule rule) {
		this.appliedRules.add(rule);
	}

	/**
	 * Add a {@link SoftwareUnitDefinition}
	 * 
	 * @param name
	 * @param type
	 * @return Return true if the specified name was not already used for a software unit
	 */
	public final void addSoftwareUnit(SoftwareUnitDefinition unit) throws Exception {
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
	public final void removeSoftwareUniteDefinition(SoftwareUnitDefinition unit) throws Exception {
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
	public final SoftwareUnitDefinition getSoftwareUnit(String name) {
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
	public final ArrayList<SoftwareUnitDefinition> getAllSoftwareUnitDefinitions() {
		return softwareUnitDefinitions;
	}

	public final String toXML() {
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

	public final String toString() {
		return getName();
	}

	public final boolean hasAppliedRule(String ruleName, Layer toLayer) {
		if(toLayer == null) return false;
		
		if (appliedRules != null) {
			for (AppliedRule appliedRule : this.appliedRules) {
				if (appliedRule.getToLayer().getId() == toLayer.getId() 
						&& appliedRule.getRuleType().getName().equals(ruleName)) {
					return true;
				}
			}
		}
		return false;
	}

	public final void moveUp() throws Exception {
		Log.i(this, "moveUp()");
		if (getParentLayer() == null) {
			throw new Exception("Layer is already at the top");
		} else {
			Layer parent = getParentLayer();
			Layer superParent = parent.getParentLayer();
			Layer child = getChildLayer();

			setParentLayer(superParent);
			setChildLayer(parent);
			// Make this the child of the superParent
			if (superParent != null) {
				superParent.setChildLayer(this);
			}
			// Make this the parent of the current parent
			if (parent != null) {
				parent.setChildLayer(child);
				parent.setParentLayer(this);
			}
			// Make the parent of this child this currents parent
			if (child != null) {
				child.setParentLayer(parent);
			}
		}
	}

	public final void moveDown() throws Exception {
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

	public final void addChildLayer(Layer layer) {
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

	public final Layer getFirstLayer() {
		if (parentLayer != null) {
			return parentLayer.getFirstLayer();
		} else {
			return this;
		}
	}

	public final Layer getLayer(int id) {
		if (getId() == id) {
			return this;
		} else if (childLayer != null) {
			return childLayer.getLayer(id);
		} else {
			return null;
		}
	}

	public final void removeLayer(Layer layer) {
		if (this == layer) {
			if (getParentLayer() != null) {
				getParentLayer().setChildLayer(getChildLayer());
			}
			if (getChildLayer() != null) {
				getChildLayer().setParentLayer(getParentLayer());
			}
		} else {
			layer.removeLayer(layer);
		}
	}

	public final Layer getLayer(String name) {
		if (getName().equals(name)) {
			return this;
		} else if (childLayer != null) {
			return childLayer.getLayer(name);
		} else {
			return null;
		}
	}

	/**
	 * Find the layer containing the software unit
	 * 
	 * @param softwareUnitName
	 * @return
	 */
	public final Layer getLayerBySoftwareUnitName(String softwareUnitName) {
		// First look in this layer
		for (SoftwareUnitDefinition sud : softwareUnitDefinitions) {
			if (sud.contains(softwareUnitName)) {
				return this;
			}
		}
		// If you didn't find it, look in the child layer
		if (childLayer != null) {
			return childLayer.getLayerBySoftwareUnitName(softwareUnitName);
		}
		// Else return null
		else return null;
	}

	public final void printOrder() {
		String message = "I am " + getId() + ", my name is: " + getName() + ", my parent is: ";
		Layer p = getParentLayer();
		Layer c = getChildLayer();

		if (p != null) {
			message += getParentLayer().getName();
		} else {
			message += "-";
		}
		message += ", my child is: ";
		if (c != null) {
			message += getChildLayer().getName();
		} else {
			message += "-";
		}

		Log.i(this, message);

		if (childLayer != null) {
			childLayer.printOrder();
		}
	}
}
