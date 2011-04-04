package layerchecker.analyse;

import java.util.ArrayList;

import main.Logger;

public abstract class AbstractDefaultRule {
	
	private boolean active;
	private String name;
	private ArrayList<SoftwareUnitRealisation> softwareUnits;
	
	public AbstractDefaultRule() {
		Logger.getInstance().log(this.getClass().getSimpleName());
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}
	
	public void checkViolation() {
		
	}
	
	public void addSoftwareUnit(SoftwareUnitRealisation sur) {
		softwareUnits.add(sur);
	}

}
