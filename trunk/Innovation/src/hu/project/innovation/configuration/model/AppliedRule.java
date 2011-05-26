package hu.project.innovation.configuration.model;

import hu.project.innovation.configuration.model.rules.AbstractRuleType;
import hu.project.innovation.utils.Log;

import java.util.ArrayList;

public class AppliedRule {

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

	public String toString() {
		return this.ruleType.getName();
	}

}
