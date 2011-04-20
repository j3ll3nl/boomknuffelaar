package hu.project.innovation.configuration.controller;

import hu.project.innovation.CustomLogger;
import hu.project.innovation.configuration.view.DefinitionJPanel;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JPanel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

public class DefinitionController implements ActionListener, ListSelectionListener {

	public void newDefinition() {

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
		DefinitionJPanel jpanel = new DefinitionJPanel(this);
		return jpanel;
	}

	@Override
	public void actionPerformed(ActionEvent action) {
		CustomLogger.i(getClass().getSimpleName(), "actionPerformed(" + action + ")");

	}

	@Override
	public void valueChanged(ListSelectionEvent event) {
		CustomLogger.i(getClass().getSimpleName(), "valueChanged(" + event + ")");
		
	}

}
