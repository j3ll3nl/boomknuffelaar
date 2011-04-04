package layerchecker.definition;

import java.util.ArrayList;

import main.Logger;

public class SoftwareUnitDefinition {

	private String name;
	private String type;
	private ArrayList<SoftwareUnitDefinition> softwareUnits;
	private ArrayList<RuleException> ruleExceptions;
	
	public SoftwareUnitDefinition(String name, String type) {
		Logger.getInstance().log(this.getClass().getSimpleName());
		this.name = name;
		this.type = type;
	}
	
	public void addSoftwareUnitDefinition(SoftwareUnitDefinition unit) {
		softwareUnits.add(unit);
	}
	
}
