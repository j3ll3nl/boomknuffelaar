package layerchecker.analyse;

import java.util.ArrayList;

import layerchecker.configuration.ConfigurationService;
import main.Logger;

public abstract class AbstractDefaultRule {
	
	private String name;
	private ArrayList<SoftwareUnitRealisation> softwareUnits;
	
	public AbstractDefaultRule() {
		Logger.getInstance().log(this.getClass().getSimpleName());
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}
	
	public abstract void checkViolation();
	
	public void addSoftwareUnit(SoftwareUnitRealisation sur) {
		softwareUnits.add(sur);
	}
	
	protected boolean isActive() {
		return ConfigurationService.getInstance().ruleIsActive(this.getName());
	}
}
