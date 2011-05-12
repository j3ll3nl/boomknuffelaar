package hu.project.innovation.analyse.model;

import hu.project.innovation.configuration.model.ConfigurationService;
import hu.project.innovation.utils.Log;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import net.sourceforge.pmd.PMD;

public class AnalyseService {

	private static AnalyseService instance;
	private boolean running;

	public AnalyseService() {
		Log.i(this, "constructor()");
	}

	public static AnalyseService getInstance() {
		if (instance == null) {
			instance = new AnalyseService();
		}
		return instance;
	}

	public void startAnalyse() {
		Log.i(this, "startAnalyse()");

		Log.i(this, "startAnalyse() - configuration settings:");
		String projectPath = ConfigurationService.getInstance().getProjectPath();
		String outputPath = ConfigurationService.getInstance().getOutputPath();
		String outputType = ConfigurationService.getInstance().getOutputFormat();
		String ruleset = "..\\bin\\hu\\project\\innovation\\configuration\\model\\ruleset.xml";

		Log.i(this, "startAnalyse() - Project: " + projectPath);
		Log.i(this, "startAnalyse() - Output: " + outputPath);
		Log.i(this, "startAnalyse() - Output type: " + outputType);

		setRunning(true);
		
		Calendar cal = Calendar.getInstance();
		SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy H-mm-ss");

		String[] pmdArgs = { 
				projectPath, 
				outputType, 
				ruleset, 
				"-reportfile", 
				outputPath + "/Report " + sdf.format(cal.getTime()) + "." + outputType 
		};

		// Start PMD
		PMD.main(pmdArgs);

		setRunning(false);
	}

	public void setRunning(boolean running) {
		this.running = running;
	}

	public boolean isRunning() {
		return running;
	}

}
