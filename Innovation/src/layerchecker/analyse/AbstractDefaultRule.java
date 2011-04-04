package layerchecker.analyse;

import java.util.ArrayList;

import net.sourceforge.pmd.AbstractJavaRule;

import layerchecker.configuration.ConfigurationService;
import main.Logger;

public abstract class AbstractDefaultRule extends AbstractJavaRule {
	
	private String name;
	private ArrayList<SoftwareUnitRealisation> softwareUnits;
	
	protected AbstractDefaultRule() {
		Logger.getInstance().log(this.getClass().getSimpleName());
		
		this.softwareUnits = new ArrayList<SoftwareUnitRealisation>();
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
		return ConfigurationService.getInstance()
			.ruleIsActive(this.getClass().getSimpleName());
	}
}