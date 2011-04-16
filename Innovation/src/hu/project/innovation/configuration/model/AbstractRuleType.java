package hu.project.innovation.configuration.model;

import hu.project.innovation.Logger;

import java.util.ArrayList;

import net.sourceforge.pmd.AbstractJavaRule;

public abstract class AbstractRuleType extends AbstractJavaRule {

	private String name;
	private ArrayList<SoftwareUnitRealisation> softwareUnits;

	protected AbstractRuleType() {
		Logger.getInstance().log(this.getClass().getSimpleName());
	}

	@Override
	public void setName(String name) {
		this.name = name;
	}

	@Override
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
