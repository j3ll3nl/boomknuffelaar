package hu.project.innovation.report.model;

import hu.project.innovation.utils.Log;

public final class ReportService {

	private ReportService() {
		Log.i(getClass().getSimpleName(), "constructor()");
	}

	public String getViolationtype(String ruleName, Integer violationType) {
		// TODO Deze wordt niet gebruikt. Wat nu?
		return "Ruletype Backcall - Violationtype: 1.	Software unit uit lagere laag roept SU uit hogere laag aan.";
	}

}
