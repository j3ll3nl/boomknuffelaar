package hu.project.innovation.configuration.model;

import hu.project.innovation.Logger;
import hu.project.innovation.XMLable;

public class AppliedRule implements XMLable {

	private AbstractRuleType ruleType;
	private Layer fromLayer, toLayer;
	private SoftwareUnitDefinition softwareUnitDef;

	public AppliedRule() {
		Logger.getInstance().log(this.getClass().getSimpleName());
	}

	public AppliedRule(AbstractRuleType ruleType, Layer fromLayer, Layer toLayer) {
		Logger.getInstance().log(this.getClass().getSimpleName());
		this.ruleType = ruleType;
		this.fromLayer = fromLayer;
		this.toLayer = toLayer;
	}

	@Override
	public String toXML() {
		String xml = "<appliedRule>\n";
		xml += "<ruleType>"+this.ruleType.getName()+"</ruleType>\n";
		xml += "<toLayer>"+this.toLayer.getName()+"</toLayer>\n";
		xml += "</appliedRule>\n";
		return xml;
	}

}
