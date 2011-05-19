package hu.project.innovation.configuration.controller;

import hu.project.innovation.configuration.model.AppliedRule;
import hu.project.innovation.configuration.model.Layer;
import hu.project.innovation.configuration.view.jframe.JFrameAppliedRules;
import hu.project.innovation.utils.Log;

import java.awt.event.ActionEvent;

public class AppliedRulesController extends PopUpController {

	private JFrameAppliedRules jframe;
	private AppliedRule appliedrule;

	public AppliedRulesController(Layer layer, AppliedRule appliedrule) {
		Log.i(this, "constructor(" + layer + ", " + appliedrule + ")");
		this.setLayer(layer);
		this.appliedrule = appliedrule;
	}

	@Override
	public void initUi() {
		Log.i(this, "initUi()");
		jframe = new JFrameAppliedRules();

		// TODO Auto-generated method stub

	}

	@Override
	public void save() {
		// TODO Auto-generated method stub

	}

	@Override
	public void addExceptionRow() {
		// TODO Auto-generated method stub

	}

	@Override
	public void removeExceptionRow() {
		// TODO Auto-generated method stub

	}

	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub

	}

}
