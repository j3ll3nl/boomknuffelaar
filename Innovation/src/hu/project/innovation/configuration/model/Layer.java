package hu.project.innovation.configuration.model;

import hu.project.innovation.utils.Log;

import java.util.ArrayList;

public class Layer {

	private int layer_id;
	private String layer_name;
	private String layer_description;
	private boolean interfaceAccessOnly = false;

	private Layer parentLayer;
	private Layer childLayer;
	private ArrayList<AppliedRule> appliedRules = new ArrayList<AppliedRule>();
	private ArrayList<SoftwareUnitDefinition> softwareUnitDefinitions = new ArrayList<SoftwareUnitDefinition>();

	public Layer(String name, String description) throws Exception {
		Log.i(this, "constructor(\"" + name + "\", \"" + description + "\")");

		setLayerName(name);
		setLayerDescription(description);
	}

	public int getId() {
		return layer_id;
	}

	public void setId(int id) {
		this.layer_id = id;
	}

	public void updateId(int i) {
		Log.i(this, "updateId(" + i + ")");
		setId(i);
		if (childLayer != null) {
			childLayer.updateId(i + 1);
		}
	}

	public String getLayerName() {
		return layer_name;
	}

	public void setLayerName(String layer_name) throws Exception {
		if (layer_name.trim().equals("")) {
			throw new Exception("No layer name");
		}
		this.layer_name = layer_name;
	}

	public String getLayerDescription() {
		return layer_description;
	}

	public void setLayerDescription(String description) {
		this.layer_description = description;
	}

	public boolean isInterfaceAccessOnly() {
		return this.interfaceAccessOnly;
	}

	public void setInterfaceAccessOnly(boolean bool) {
		this.interfaceAccessOnly = bool;
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

	public long newAppliedRule(String ruleType, Layer toLayer) throws Exception {
		AppliedRule appliedRule = new AppliedRule(ruleType, this, toLayer);
		appliedRules.add(appliedRule);

		return appliedRule.getId();
	}

	public AppliedRule getAppliedRule(long appliedrule_id) {
		for (AppliedRule appliedRule : appliedRules) {
			if (appliedRule.getId() == appliedrule_id) {
				return appliedRule;
			}
		}
		return null;
	}

	public void removeAppliedRule(long appliedrule_id) throws Exception {
		for (AppliedRule appliedRule : appliedRules) {
			if (appliedRule.getId() == appliedrule_id) {
				appliedRules.remove(appliedRule);
				return;
			}
		}
		throw new Exception("Applied rule does not exist");
	}

	public long newSoftwareUnit(String unitName, String unitType) throws Exception {
		if (softwareUnitDefinitions.contains(unitName)) {
			throw new Exception("Software unit is already added to this layer");
		}
		SoftwareUnitDefinition softwareunitdefinition = new SoftwareUnitDefinition(unitName, unitType);
		softwareunitdefinition.setLayer(this);
		softwareUnitDefinitions.add(softwareunitdefinition);

		return softwareunitdefinition.getId();
	}

	/**
	 * Removes the SoftwareUnit with the specified name
	 * 
	 * @param layer_name
	 * @return the previous SoftwareUnit associated with that name, or null if there was no SoftwareUnit with that name.
	 */
	public void removeSoftwareUnitDefinition(long softwareunit_id) throws Exception {
		for (SoftwareUnitDefinition softwareunitdefinition : softwareUnitDefinitions) {
			if (softwareunitdefinition.getId() == softwareunit_id) {
				softwareUnitDefinitions.remove(softwareunitdefinition);
				return;
			}
		}
		throw new Exception("Software unit does not exist in this layer");
	}

	/**
	 * Get a software unit by its id
	 * 
	 * @param name
	 * @return The software unit with the specified name of null if this layer doesn't have that unit
	 */
	public SoftwareUnitDefinition getSoftwareUnit(long id) {
		for (SoftwareUnitDefinition softwareunitdefinition : softwareUnitDefinitions) {
			if (softwareunitdefinition.getId() == id) {
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
	public ArrayList<Long> getSoftwareUnitDefinitions() {
		ArrayList<Long> arrayList = new ArrayList<Long>();
		for (SoftwareUnitDefinition softwareUnitDefinition : softwareUnitDefinitions) {
			arrayList.add(softwareUnitDefinition.getId());
		}
		return arrayList;
	}

	public boolean hasAppliedRule(String ruleName, Layer toLayer) {
		if (toLayer == null)
			return false;

		if (appliedRules != null) {
			for (AppliedRule appliedRule : this.appliedRules) {
				if (appliedRule == null) {
					Log.e(this, "appliedrule = null");
				}
				if (appliedRule.getToLayer() == null) {
					Log.e(this, "appliedrule.getToLayer = null");
				}
				if (appliedRule.getRuleType() == null) {
					Log.e(this, "appliedrule.getRuleType = null");
				}

				if (appliedRule.getToLayer().getId() == toLayer.getId() && appliedRule.getRuleType().getName().equals(ruleName)) {
					return true;
				}
			}
		}
		return false;
	}

	/**
	 * Returning the applied rules for this layer
	 * 
	 * @return The applied rules
	 */
	public ArrayList<Long> getAppliedRules() {
		ArrayList<Long> arrayList = new ArrayList<Long>();
		for (AppliedRule appliedRule : appliedRules) {
			arrayList.add(appliedRule.getId());
		}
		return arrayList;
	}

	public void moveUp() throws Exception {
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
		} else if (childLayer != null) {
			return childLayer.getLayer(id);
		} else {
			return null;
		}
	}

	public void removeLayer(Layer layer) {
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

	/**
	 * Find the layer containing the software unit
	 * 
	 * @param softwareUnitName
	 * @return
	 */
	public Layer getLayerBySoftwareUnitName(String softwareUnitName) {
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
		else
			return null;
	}

	public void printOrder() {
		String message = "I am " + getId() + ", my name is: " + getLayerName() + ", my parent is: ";
		Layer p = getParentLayer();
		Layer c = getChildLayer();

		if (p != null) {
			message += getParentLayer().getLayerName();
		} else {
			message += "-";
		}
		message += ", my child is: ";
		if (c != null) {
			message += getChildLayer().getLayerName();
		} else {
			message += "-";
		}

		Log.i(this, message);

		if (childLayer != null) {
			childLayer.printOrder();
		}
	}

	public String toString() {
		return getLayerName();
	}
}
