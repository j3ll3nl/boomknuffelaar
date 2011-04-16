package hu.project.innovation.analyse.model;

import hu.project.innovation.Logger;
import hu.project.innovation.configuration.model.AbstractRuleType;

import java.util.ArrayList;

public class AnalyseService {

	private static AnalyseService instance;
	private ArrayList<AbstractRuleType> rules;

	public AnalyseService() {
		Logger.getInstance().log(this.getClass().getSimpleName());
	}

	public void startAnalyse() {

	}

	public static AnalyseService getInstance() {
		if (instance == null) {
			instance = new AnalyseService();
		}
		return instance;
	}

}
