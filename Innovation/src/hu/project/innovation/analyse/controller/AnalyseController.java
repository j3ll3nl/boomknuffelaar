package hu.project.innovation.analyse.controller;

import hu.project.innovation.Log;
import hu.project.innovation.analyse.model.AnalyseService;
import hu.project.innovation.analyse.view.AnalyseJFrame;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AnalyseController implements ActionListener {

	private AnalyseService analyseService;
	private AnalyseJFrame analyseJFrame;

	public AnalyseController() {
		Log.i(getClass().getSimpleName(), "constructor()");
		
		analyseService = AnalyseService.getInstance();
		analyseJFrame = new AnalyseJFrame(this);
	}

	public void initUi() {
		Log.i(getClass().getSimpleName(), "initUi()");
		analyseJFrame.setVisible(true);
		
	}

	public void startAnalyse() {
		Log.i(getClass().getSimpleName(), "startAnalyse()");
		analyseService.startAnalyse();
	}

	@Override
	public void actionPerformed(ActionEvent action) {
		Log.i(getClass().getSimpleName(), "actionPerformed()");
		// TODO Auto-generated method stub

	}
}
