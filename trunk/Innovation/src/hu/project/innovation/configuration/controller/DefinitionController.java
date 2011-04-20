package hu.project.innovation.configuration.controller;

import hu.project.innovation.CustomLogger;
import hu.project.innovation.configuration.model.ConfigurationService;
import hu.project.innovation.configuration.view.DefinitionJPanel;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

public class DefinitionController implements ActionListener, ListSelectionListener {

	private DefinitionJPanel jpanel;
	private ConfigurationService cs;

	public DefinitionController() {
		jpanel = new DefinitionJPanel(this);
		cs = ConfigurationService.getInstance();
	}

	/**
	 * Create a new definition. This function won't ask if you want to save the current definition
	 */
	public void newDefinition() {
		CustomLogger.i(getClass().getSimpleName(), "newDefinition()");

	}

	/**
	 * Open a definition. This function will display an filebrowser
	 */
	public void openDefintion() {
		CustomLogger.i(getClass().getSimpleName(), "openDefintion()");

	}

	/**
	 * Save a definition.
	 */
	public void saveDefintion() {
		CustomLogger.i(getClass().getSimpleName(), "saveDefintion()");
	}

	/**
	 * Create a new layer
	 */
	public void newLayer() {
		CustomLogger.i(getClass().getSimpleName(), "newLayer()");

		String inputValue = "";
		while (inputValue.trim().equals("")) {
			inputValue = JOptionPane.showInputDialog(null, "Please enter layer name", "Please input a value", JOptionPane.QUESTION_MESSAGE);
			if (inputValue == null) {
				break;
			} else {
				if (!inputValue.trim().equals("")) {
					CustomLogger.i(getClass().getSimpleName(), "newLayer() - value: " + inputValue);
					cs.newLayer(inputValue, "");
				} else {
					CustomLogger.i(getClass().getSimpleName(), "newLayer() - no value" + inputValue);
					JOptionPane.showMessageDialog(null, "No value entered", "Error", JOptionPane.ERROR_MESSAGE);
				}
			}

		}
	}

	/**
	 * Remove a layer
	 */
	public void removeLayer() {
		CustomLogger.i(getClass().getSimpleName(), "removeLayer()");
		// TODO Auto-generated method stub
	}

	/**
	 * Move a layer down
	 */
	private void moveLayerDown() {
		CustomLogger.i(getClass().getSimpleName(), "moveLayerDown()");
		// TODO Auto-generated method stub

	}

	/**
	 * Move a layer up
	 */
	private void moveLayerUp() {
		CustomLogger.i(getClass().getSimpleName(), "moveLayerUp()");
		// TODO Auto-generated method stub

	}

	public void addPackage() {
		CustomLogger.i(getClass().getSimpleName(), "addPackage()");
	}

	public void addRuleException() {
		CustomLogger.i(getClass().getSimpleName(), "addRuleException()");
	}

	public JPanel initGUI() {
		return jpanel;
	}

	@Override
	public void actionPerformed(ActionEvent action) {
		if (action.getSource() == jpanel.jButtonNewLayer) {
			newLayer();
		} else if (action.getSource() == jpanel.jButtonRemoveLayer) {
			removeLayer();
		} else if (action.getSource() == jpanel.jButtonMoveLayerUp) {
			moveLayerUp();
		} else if (action.getSource() == jpanel.jButtonMoveLayerDown) {
			moveLayerDown();
		} else {
			CustomLogger.i(getClass().getSimpleName(), "actionPerformed(" + action + ")");
		}
	}

	@Override
	public void valueChanged(ListSelectionEvent event) {
		CustomLogger.i(getClass().getSimpleName(), "valueChanged(" + event + ")");

	}

}
