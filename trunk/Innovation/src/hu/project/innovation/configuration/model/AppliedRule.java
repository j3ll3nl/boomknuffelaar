package hu.project.innovation.configuration.model;

import hu.project.innovation.configuration.model.rules.AbstractRuleType;
import hu.project.innovation.configuration.model.rules.BackCallRule;
import hu.project.innovation.configuration.model.rules.InterfacesOnlyRule;
import hu.project.innovation.configuration.model.rules.SkipLayerRule;
import hu.project.innovation.utils.Log;
import hu.project.innovation.utils.UniqueID;

import java.util.ArrayList;

public class AppliedRule {

	private long id;
	private AbstractRuleType type;
	private Layer fromLayer, toLayer;
	private boolean appliedrule_enabled;
	private ArrayList<SoftwareUnitDefinition> exceptions = new ArrayList<SoftwareUnitDefinition>();;

	public AppliedRule(String ruleType, Layer fromLayer, Layer toLayer) throws Exception {
		Log.i(this, "constructor(" + ruleType + ", " + fromLayer + ", " + toLayer + ")");
		setRuleType(ruleType);
		setFromLayer(fromLayer);
		setToLayer(toLayer);
		setId();
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

	public void setRuleType(String type) throws Exception {
		// Find the ruletype
		AbstractRuleType ruletype = null;
		if (type.trim().equals(AbstractRuleType.back_call)) {
			ruletype = new BackCallRule();
		} else if (type.trim().equals(AbstractRuleType.skip_call)) {
			ruletype = new SkipLayerRule();
		} else if (type.trim().equals(AbstractRuleType.interface_only)) {
			ruletype = new InterfacesOnlyRule();
		}
		if (ruletype == null) {
			throw new Exception("Rule: " + type + " not found!");
		}
		this.type = ruletype;
	}

	public AbstractRuleType getRuleType() {
		return type;
	}

	public void setEnabled(boolean enabled) {
		this.appliedrule_enabled = enabled;
	}

	public boolean isEnabled() {
		return appliedrule_enabled;
	}

	public ArrayList<Long> getExceptions() {
		ArrayList<Long> arrayList = new ArrayList<Long>();
		for (SoftwareUnitDefinition exception : exceptions) {
			arrayList.add(exception.getId());
		}
		return arrayList;
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
		return this.type.getName();
	}

	public void setId() {
		id = UniqueID.get();
	}

	public long getId() {
		return id;
	}

	public long newException(String unitName, String unitType) {
		SoftwareUnitDefinition exception = new SoftwareUnitDefinition(unitName, unitType);
		exceptions.add(exception);

		return exception.getId();
	}

	public SoftwareUnitDefinition getExeption(long exception_id) {
		for (SoftwareUnitDefinition exception : exceptions) {
			if (exception.getId() == exception_id) {
				return exception;
			}
		}
		return null;
	}

}
