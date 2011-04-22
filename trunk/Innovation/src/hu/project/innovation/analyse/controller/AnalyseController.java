package hu.project.innovation.analyse.controller;

import hu.project.innovation.analyse.model.AnalyseService;
import hu.project.innovation.analyse.view.AnalyseJFrame;
import hu.project.innovation.utils.Log;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AnalyseController implements ActionListener {

	private AnalyseService analyseService;
	private AnalyseJFrame analyseJFrame;

	public AnalyseController() {
		Log.i(this, "constructor()");

		analyseService = AnalyseService.getInstance();
		analyseJFrame = new AnalyseJFrame();
	}

	public void initUi() {
		Log.i(this, "initUi()");
		analyseJFrame.setVisible(true);

	}

	public void startAnalyse() {
		Log.i(this, "startAnalyse()");
		analyseService.startAnalyse();
	}

	@Override
	public void actionPerformed(ActionEvent action) {
		Log.i(this, "actionPerformed()");
		// TODO Auto-generated method stub

	}
}
