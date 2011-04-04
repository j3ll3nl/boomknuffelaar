package layerchecker.report;

import main.Logger;

public class ReportService {
	
	private static ReportService instance;
	
	private ViolationSet violations;
	
	private ReportService() {
		Logger.getInstance().log(this.getClass().getSimpleName());
		
		this.violations = new ViolationSet();
	}
	
	public void addViolation(String violation) {
		this.violations.addViolation(violation);
	}

	public static ReportService getInstance() {
		if(instance == null) {
			instance = new ReportService();
		}
		return instance;
	}
	
}
