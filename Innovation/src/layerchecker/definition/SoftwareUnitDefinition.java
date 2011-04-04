package layerchecker.definition;

import java.util.ArrayList;

public class SoftwareUnitDefinition {

	private String name;
	private String type;
	private ArrayList<SoftwareUnitDefinition> softwareUnits;
	private ArrayList<RuleException> ruleExceptions;
	
	public SoftwareUnitDefinition(String name, String type) {
		this.name = name;
		this.type = type;
	}
	
	public void addSoftwareUnitDefinition(SoftwareUnitDefinition unit) {
		softwareUnits.add(unit);
	}
	
}
