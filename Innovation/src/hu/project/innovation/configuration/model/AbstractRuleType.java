package hu.project.innovation.configuration.model;

import hu.project.innovation.Logger;

import net.sourceforge.pmd.AbstractJavaRule;

public abstract class AbstractRuleType extends AbstractJavaRule {

	private String name;

	protected AbstractRuleType() {
		Logger.getInstance().log(this.getClass().getSimpleName());
		this.setName(this.getClass().getSimpleName());
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
}
