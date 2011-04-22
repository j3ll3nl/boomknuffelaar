package hu.project.innovation.configuration.model;

import hu.project.innovation.utils.Log;

import java.util.ArrayList;

public class SoftwareUnitRealisation {

	@SuppressWarnings("unused")
	private String name;
	@SuppressWarnings("unused")
	private String type;
	private ArrayList<SoftwareUnitRealisation> softwareUnits;

	protected SoftwareUnitRealisation() {
		Log.i(this, "SoftwareUnitRealisation()");
	}

	public void addSoftwareUnit(SoftwareUnitRealisation sur) {
		softwareUnits.add(sur);
	}

}
