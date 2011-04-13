package hu.project.innovation.configuration.model;

import hu.project.innovation.Logger;

import java.util.ArrayList;

public class SoftwareUnitDefinition {

	public static final String 
		CLASS = "class",
		PACKAGE = "package";
	
	private String name;
	private String type;
	private ArrayList<SoftwareUnitDefinition> softwareUnits;
	private ArrayList<RuleDefinition> ruleExceptions;
	
	public SoftwareUnitDefinition(String name, String type) {
		Logger.getInstance().log(this.getClass().getSimpleName());
		this.name = name;
		this.setType(type);
	}
	
	public String getName() {
		return name;
	}
	
	public void addSoftwareUnitDefinition(SoftwareUnitDefinition unit) {
		softwareUnits.add(unit);
	}
	
	public void addRuleException(String name, boolean permission) {
		RuleDefinition re = new RuleDefinition(name, permission);
		ruleExceptions.add(re);
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getType() {
		return type;
	}
	
}
