package hu.project.innovation.report.model;

import hu.project.innovation.utils.Log;

public class ReportService implements ReportServiceIF {

	private ReportService() {
		Log.i(getClass().getSimpleName(), "constructor()");
	}

	@Override
	public String getViolationtype(String ruleName, Integer ViolationType) {
		// TODO Auto-generated method stub
		return "Ruletype Backcall - Violationtype: 1.	Software unit uit lagere laag roept SU uit hogere laag aan.";
	}

}
