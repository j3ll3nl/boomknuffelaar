package hu.project.innovation.analyse.model;

import hu.project.innovation.analyse.controller.AnalyseController;
import hu.project.innovation.configuration.model.ConfigurationService;
import hu.project.innovation.utils.Log;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class AnalyseService {

	private static AnalyseService instance;

	private AnalyseService() {
		Log.i(this, "constructor()");
	}

	public static AnalyseService getInstance() {
		if (instance == null) {
			instance = new AnalyseService();
		}
		return instance;
	}

	public void startAnalyse(AnalyseController analyseController) {
		Log.i(this, "startAnalyse()");

		Log.i(this, "startAnalyse() - configuration settings:");
		String projectPath = ConfigurationService.getInstance().getProjectPath();
		String outputPath = ConfigurationService.getInstance().getOutputPath();
		String outputType = ConfigurationService.getInstance().getOutputType();
		String ruleset = "\\hu\\project\\innovation\\configuration\\model\\rules\\ruleset.xml";

		Log.i(this, "startAnalyse() - Project: " + projectPath);
		Log.i(this, "startAnalyse() - Output: " + outputPath);
		Log.i(this, "startAnalyse() - Output type: " + outputType);

		Calendar cal = Calendar.getInstance();
		SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy H-mm-ss");

		String[] pmdArgs = { projectPath, outputType, ruleset, "-reportfile", outputPath + "/Report " + sdf.format(cal.getTime()) + "." + (outputType.equals("text") ? "txt" : outputType) };

		PmdThread p = new PmdThread();
		p.addObserver(analyseController);
		p.setArguments(pmdArgs);

		new Thread(p).start();
	}
}
