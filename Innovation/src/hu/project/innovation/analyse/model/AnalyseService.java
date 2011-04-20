package hu.project.innovation.analyse.model;

import hu.project.innovation.Log;

public class AnalyseService {

	private static AnalyseService instance;

	public AnalyseService() {
		Log.i(getClass().getSimpleName(), "constructor()");		
	}

	public void startAnalyse() {
		Log.i(getClass().getSimpleName(), "startAnalyse()");
	}

	public static AnalyseService getInstance() {		
		if (instance == null) {
			instance = new AnalyseService();
		}
		return instance;
	}

}
