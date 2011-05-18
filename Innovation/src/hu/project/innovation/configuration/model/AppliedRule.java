package hu.project.innovation.configuration.model;

import hu.project.innovation.configuration.model.rules.AbstractRuleType;
import hu.project.innovation.utils.Log;
import hu.project.innovation.utils.XMLable;

import java.util.ArrayList;

public class AppliedRule implements XMLable {

	private AbstractRuleType ruleType;
	private Layer fromLayer, toLayer;
	private boolean enabled;
	private ArrayList<SoftwareUnitDefinition> exceptions;

	public AppliedRule() {
		Log.i(this, this.getClass().getSimpleName() + "()");
		this.exceptions = new ArrayList<SoftwareUnitDefinition>();
	}

	public AppliedRule(AbstractRuleType ruleType, Layer fromLayer, Layer toLayer) {
		this();
		this.ruleType = ruleType;
		this.setFromLayer(fromLayer);
		this.toLayer = toLayer;
	}

	public String toXML() {
		String xml = "\t\t<appliedRule>\n";
		xml += "\t\t\t<ruleType>" + ruleType.getName() + "</ruleType>\n";
		xml += "\t\t\t<toLayer>" + toLayer.getId() + "</toLayer>\n";
		for (SoftwareUnitDefinition unit : exceptions) {
			xml += "\t\t\t<exception>\n";
			xml += "\t\t\t\t<name>" + unit.getName() + "</name>\n";
			xml += "\t\t\t\t<type>" + unit.getType() + "</type>\n";
			xml += "\t\t\t</exception>\n";
		}
		xml += "\t\t</appliedRule>\n";
		return xml;
	}

	public void setFromLayer(Layer fromLayer) {
		this.fromLayer = fromLayer;
	}

	public Layer getFromLayer() {
		return fromLayer;
	}

	public Layer getToLayer() {
		return toLayer;
	}

	public void setToLayer(Layer layer) {
		this.toLayer = layer;
	}

	public AbstractRuleType getRuleType() {
		return this.ruleType;
	}

	public void setRuleType(AbstractRuleType type) {
		this.ruleType = type;
	}

	public boolean addException(SoftwareUnitDefinition sud) {
		return this.exceptions.add(sud);
	}

	public boolean hasException(SoftwareUnitDefinition sud) {
		for (SoftwareUnitDefinition exception : this.exceptions) {
			if (sud.getName().equals(exception.getName()) && sud.getType().equals(exception.getType())) {
				return true;
			}
		}
		return false;
	}

	public String toString() {
		return this.ruleType.getName();
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

	public boolean isEnabled() {
		return enabled;
	}

	public int getNumberOfExceptions() {
		if (getExceptions() != null) {
			return getExceptions().size();
		} else {
			return 0;
		}
	}

	private ArrayList<SoftwareUnitDefinition> getExceptions() {
		return exceptions;
	}

}
