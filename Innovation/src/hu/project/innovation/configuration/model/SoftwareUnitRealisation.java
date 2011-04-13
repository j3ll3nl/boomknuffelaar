package hu.project.innovation.configuration.model;

import hu.project.innovation.Logger;

import java.util.ArrayList;

public class SoftwareUnitRealisation {

	private String name;
	private String type;
	private ArrayList<SoftwareUnitRealisation> softwareUnits;

	protected SoftwareUnitRealisation() {
		Logger.getInstance().log(this.getClass().getSimpleName());
	}

	public void addSoftwareUnit(SoftwareUnitRealisation sur) {
		softwareUnits.add(sur);
	}

}
