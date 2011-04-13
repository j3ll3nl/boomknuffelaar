package hu.project.innovation.configuration.model;

import hu.project.innovation.Logger;

import java.util.ArrayList;

import net.sourceforge.pmd.AbstractJavaRule;

public abstract class AbstractDefaultRule extends AbstractJavaRule {
	
	private String name;
	private ArrayList<SoftwareUnitRealisation> softwareUnits;
	
	protected AbstractDefaultRule() {
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
