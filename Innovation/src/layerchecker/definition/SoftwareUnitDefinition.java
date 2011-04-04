package layerchecker.definition;

import java.util.ArrayList;

import main.Logger;

public class SoftwareUnitDefinition {

	public static final String 
		CLASS = "class",
		PACKAGE = "package";
	
	private String name;
	private String type;
	private ArrayList<SoftwareUnitDefinition> softwareUnits;
	private ArrayList<RuleException> ruleExceptions;
	
	public SoftwareUnitDefinition(String name, String type) {
		Logger.getInstance().log(this.getClass().getSimpleName());
		this.name = name;
		this.setType(type);
		softwareUnits = new ArrayList<SoftwareUnitDefinition>();
	}
	
	public String getName() {
		return name;
	}
	
	public void addSoftwareUnitDefinition(SoftwareUnitDefinition unit) {
		softwareUnits.add(unit);
	}
	
	public void addRuleException(String name, boolean permission) {
		RuleException re = new RuleException(name, permission);
		ruleExceptions.add(re);
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getType() {
		return type;
	}
	
	public String toString() {
		String s = name + " - " + type + "\n";
		for(SoftwareUnitDefinition sud : softwareUnits) {
			s = s + sud.toString() + "\n";
		}
		return s;
	}
	
}
