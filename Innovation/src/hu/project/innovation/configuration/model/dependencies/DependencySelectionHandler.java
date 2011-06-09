package hu.project.innovation.configuration.model.dependencies;

import hu.project.innovation.configuration.model.DependencyService;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JTextField;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

public class DependencySelectionHandler implements ListSelectionListener, ActionListener {
	private JButton button;
	@SuppressWarnings("unused")
	private DependencyService dependencyService; // TODO Wordt niet gebruikt? Misschien verwijderen?

	public DependencySelectionHandler() {

	}

	public DependencySelectionHandler(JButton button) {
		this.button = button;
		this.button.setEnabled(false);
	}

	public DependencySelectionHandler(DependencyService dependencyService) {
		this.dependencyService = dependencyService;
	}

	public void valueChanged(ListSelectionEvent arg0) {
		if (!button.isEnabled())
			button.setEnabled(true);
	}

	public void actionPerformed(ActionEvent event) {
		if (event.getSource() instanceof JTextField) {

		}
	}

}
