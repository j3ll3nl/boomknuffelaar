package hu.project.innovation.configuration.model;

import hu.project.innovation.Logger;

public class RuleDefinition {

	private String ruleName;
	private boolean permission;
	private Layer toLayer;

	public RuleDefinition(String name, boolean permission) {
		Logger.getInstance().log(this.getClass().getSimpleName());
		ruleName = name;
		this.permission = permission;
	}

	public RuleDefinition(String name, boolean permission, Layer layer) {
		Logger.getInstance().log(this.getClass().getSimpleName());
		ruleName = name;
		this.permission = permission;
		toLayer = layer;
	}

}
