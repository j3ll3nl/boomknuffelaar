package layerchecker.analyse;

import java.util.ArrayList;

import main.Logger;

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

	public void setName(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getType() {
		return type;
	}
	
}
