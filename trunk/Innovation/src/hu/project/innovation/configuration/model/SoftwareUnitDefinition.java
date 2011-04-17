package hu.project.innovation.configuration.model;

import hu.project.innovation.Logger;

import java.util.ArrayList;

public class SoftwareUnitDefinition {

	public static final String METHOD = "method", CLASS = "class", PACKAGE = "package";

	private String name;
	private String type;
	private ArrayList<SoftwareUnitDefinition> softwareUnits;

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

	public void setType(String type) {
		this.type = type;
	}

	public String getType() {
		return type;
	}

}
