package hu.project.innovation.configuration.model;

import java.util.ArrayList;

import hu.project.innovation.Logger;
import hu.project.innovation.XMLable;

public class AppliedRule implements XMLable {

	private AbstractRuleType ruleType;
	private Layer fromLayer, toLayer;
	
	private ArrayList<SoftwareUnitDefinition> exceptions;

	public AppliedRule() {
		Logger.log(this);
		this.exceptions = new ArrayList<SoftwareUnitDefinition>();
	}

	public AppliedRule(AbstractRuleType ruleType, Layer fromLayer, Layer toLayer) {
		this();
		this.ruleType = ruleType;
		this.setFromLayer(fromLayer);
		this.toLayer = toLayer;
	}

	@Override
	public String toXML() {
		String xml = "\t\t<appliedRule>\n";
		xml += "\t\t\t<ruleType>" + this.ruleType.getName() + "</ruleType>\n";
		xml += "\t\t\t<toLayer>" + this.toLayer.getId() + "</toLayer>\n";
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

	public AbstractRuleType getRuleType() {
		return this.ruleType;
	}
	
	public boolean addException(SoftwareUnitDefinition sud) {
		return this.exceptions.add(sud);
	}

}