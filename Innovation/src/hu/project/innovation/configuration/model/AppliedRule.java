package hu.project.innovation.configuration.model;

import hu.project.innovation.configuration.model.rules.AbstractRuleType;
import hu.project.innovation.utils.Log;
import hu.project.innovation.utils.XMLable;

import java.util.ArrayList;

public class AppliedRule implements XMLable {

	private AbstractRuleType ruleType;
	private Layer fromLayer, toLayer;
	private boolean enabled;
	private ArrayList<SoftwareUnitDefinition> exceptions = new ArrayList<SoftwareUnitDefinition>();;

	public AppliedRule() {
		Log.i(this, "constructor()");
	}

	public AppliedRule(AbstractRuleType ruleType, Layer fromLayer, Layer toLayer) {
		Log.i(this, "constructor(" + ruleType + ", " + fromLayer + ", " + toLayer + ")");
		setRuleType(ruleType);
		setFromLayer(fromLayer);
		setToLayer(toLayer);
	}

	public void setFromLayer(Layer fromLayer) {
		this.fromLayer = fromLayer;
	}

	public Layer getFromLayer() {
		return fromLayer;
	}

	public void setToLayer(Layer layer) {
		this.toLayer = layer;
	}

	public Layer getToLayer() {
		return toLayer;
	}

	public void setRuleType(AbstractRuleType type) {
		this.ruleType = type;
	}

	public AbstractRuleType getRuleType() {
		return this.ruleType;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

	public boolean isEnabled() {
		return enabled;
	}

	public void addException(SoftwareUnitDefinition sud) {
		exceptions.add(sud);
	}

	public ArrayList<SoftwareUnitDefinition> getExceptions() {
		return exceptions;
	}

	public int getNumberOfExceptions() {
		if (getExceptions() != null) {
			return getExceptions().size();
		} else {
			return 0;
		}
	}

	public void removeAllExceptions() {
		exceptions.clear();
	}

	public String toXML() {
		String xml = "\t\t\t<appliedRule>\n";
		xml += "\t\t\t\t<ruleType>" + ruleType.getName() + "</ruleType>\n";
		xml += "\t\t\t\t<toLayer>" + toLayer.getId() + "</toLayer>\n";
		for (SoftwareUnitDefinition unit : exceptions) {
			xml += "\t\t\t\t<exception>\n";
			xml += "\t\t\t\t\t<name>" + unit.getName() + "</name>\n";
			xml += "\t\t\t\t\t<type>" + unit.getType() + "</type>\n";
			xml += "\t\t\t\t</exception>\n";
		}
		xml += "\t\t\t</appliedRule>\n";
		return xml;
	}

	public String toString() {
		return this.ruleType.getName();
	}

}
