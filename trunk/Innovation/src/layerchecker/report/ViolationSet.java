package layerchecker.report;

import java.util.ArrayList;

import main.Logger;

public class ViolationSet {
	
	private ArrayList<String> violations;
	
	protected ViolationSet() {
		Logger.getInstance().log(this.getClass().getSimpleName());
		
		this.violations = new ArrayList<String>();
	}

	public void addViolation(String violation) {
		Logger.getInstance().log("Violation added: " + violation);
		
		this.violations.add(violation);
	}
}
