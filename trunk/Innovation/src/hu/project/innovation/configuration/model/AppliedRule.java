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

}
