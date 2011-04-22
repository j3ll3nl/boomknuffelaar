package hu.project.innovation.analyse.model;

import hu.project.innovation.utils.Log;

public class AnalyseService {

	private static AnalyseService instance;

	public AnalyseService() {
		Log.i(this, "constructor()");
	}

	public void startAnalyse() {
		Log.i(this, "startAnalyse()");
	}

	public static AnalyseService getInstance() {
		if (instance == null) {
			instance = new AnalyseService();
		}
		return instance;
	}

}
