package hu.project.innovation.configuration.model;

import hu.project.innovation.Logger;
import hu.project.innovation.XMLable;

import net.sourceforge.pmd.AbstractJavaRule;

public abstract class AbstractRuleType 
	extends AbstractJavaRule implements XMLable {

	private String name, description;
	private int priority;

	protected AbstractRuleType() {
		Logger.getInstance().log(this.getClass().getSimpleName());
		this.setName(this.getClass().getSimpleName());
		Logger.getInstance().log(this.formattedName());
	}

	@Override
	public void setName(String name) {
		this.name = name;
	}

	@Override
	public String getName() {
		return name;
	}
	
	private String formattedName() {
		return this.name.replaceAll("(.)([A-Z])", "$1_$2").toLowerCase();
	}

	public abstract void checkViolation();
	
	public String toXML() {
		String xml = ""; 
		xml += "<rule name=\""+this.formattedName()+"\" ";
		xml += "message=\""+this.name+"\" ";
		xml += "class=\""+this.getClass().getPackage()+this.name+"\">";
		xml += "<description>"+this.description+"</description>";
		xml += "<priority>"+this.priority+"</priority>";
		xml += "</rule>";
		return "";
	}
}
