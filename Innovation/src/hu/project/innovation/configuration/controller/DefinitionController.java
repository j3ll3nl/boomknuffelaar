package hu.project.innovation.configuration.controller;

import hu.project.innovation.CustomLogger;
import hu.project.innovation.configuration.model.ConfigurationService;
import hu.project.innovation.configuration.view.NewJPanel;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JPanel;

public class DefinitionController implements ActionListener {

	public void newDefinition() {
		ConfigurationService cs = ConfigurationService.getInstance();

	}

	public void openDefintion() {
	}

	public void saveDefintion() {
	}

	public void addLayer() {
	}

	public void addPackage() {
	}

	public void addRuleException() {
	}

	public JPanel initGUI() {
		NewJPanel jpanel = new NewJPanel(this);
		return jpanel;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		CustomLogger.i(getClass().getSimpleName(), "actionPerformed(" + e + ")");

	}

}
