package hu.project.innovation.configuration.model;

import hu.project.innovation.Logger;

public class AppliedRule {

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

}
