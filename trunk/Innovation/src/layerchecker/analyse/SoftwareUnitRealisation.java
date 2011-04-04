package layerchecker.analyse;

import java.util.ArrayList;

import main.Logger;

public class SoftwareUnitRealisation {
	
	private String name;
	private String type;
	private ArrayList<SoftwareUnitRealisation> softwareUnits;
	
	public SoftwareUnitRealisation() {
		Logger.getInstance().log(this.getClass().getSimpleName());
	}

	public void addSoftwareUnit(SoftwareUnitRealisation sur) {
		softwareUnits.add(sur);
	}
	
}
