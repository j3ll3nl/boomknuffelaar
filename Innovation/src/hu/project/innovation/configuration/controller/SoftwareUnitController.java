package hu.project.innovation.configuration.controller;

import hu.project.innovation.configuration.model.Layer;
import hu.project.innovation.configuration.model.SoftwareUnitDefinition;
import hu.project.innovation.configuration.view.JFrameSoftwareUnit;
import hu.project.innovation.configuration.view.JTableException;
import hu.project.innovation.configuration.view.JTableTableModel;
import hu.project.innovation.utils.Log;
import hu.project.innovation.utils.Ui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Observable;

public class SoftwareUnitController extends Observable implements ActionListener {

	public static String ACTION_NEW = "NEW";
	public static String ACTION_EDIT = "EDIT";

	private JFrameSoftwareUnit jframe;
	private String action = SoftwareUnitController.ACTION_NEW;
	private SoftwareUnitDefinition softwareunit;
	private Layer layer;

	public SoftwareUnitController() {
		Log.i(this, "constructor()");
	}

	public void initUi() {
		Log.i(this, "initUi()");
		jframe = new JFrameSoftwareUnit();

		// Change view of jframe conforms the action
		if (getAction().equals(SoftwareUnitController.ACTION_NEW)) {
			jframe.jButtonSave.setText("Create");
		} else if (getAction().equals(SoftwareUnitController.ACTION_EDIT)) {
			jframe.jButtonSave.setText("Save");
		}

		jframe.jButtonAddExceptionRow.addActionListener(this);
		jframe.jButtonRemoveExceptionRow.addActionListener(this);
		jframe.jButtonSave.addActionListener(this);
		jframe.jButtonCancel.addActionListener(this);

		jframe.setVisible(true);
	}

	@Override
	public void actionPerformed(ActionEvent action) {
		Log.i(this, "actionPerformed()");
		if (action.getSource() == jframe.jButtonAddExceptionRow) {
			addExceptionRow();
		} else if (action.getSource() == jframe.jButtonRemoveExceptionRow) {
			removeExceptionRow();
		} else if (action.getSource() == jframe.jButtonSave) {
			pokeObservers();
		} else if (action.getSource() == jframe.jButtonCancel) {
			jframe.dispose();
		} else {
			Log.i(this, "actionPerformed(" + action + ") - unknown button event");
		}
	}

	/**
	 * Add a new empty row to the exception table
	 */
	private void addExceptionRow() {
		JTableException table = jframe.jTableException;
		JTableTableModel tablemodel = (JTableTableModel) table.getModel();

		Object[] emptyrow = { "", "" };
		tablemodel.addRow(emptyrow);
	}

	private void removeExceptionRow() {
		JTableException table = jframe.jTableException;
		int selectedrow = table.getSelectedRow();
		if (selectedrow == -1) {
			Ui.errorDialog(jframe, "Select a table row", "Error");
		} else {
			JTableTableModel tablemodel = (JTableTableModel) table.getModel();
			tablemodel.removeRow(selectedrow);
		}
	}

	public void pokeObservers() {
		setChanged();
		notifyObservers();
	}

	public void setAction(String action) {
		if (action.equals(SoftwareUnitController.ACTION_EDIT) || action.equals(SoftwareUnitController.ACTION_NEW)) {
			this.action = action;
		}
	}

	public void setParameters(Layer layer, SoftwareUnitDefinition softwareunit) {
		this.layer = layer;
		this.softwareunit = softwareunit;
	}

	public String getAction() {
		return action;
	}

	public SoftwareUnitDefinition getSoftwareunit() {
		return softwareunit;
	}

	public Layer getLayer() {
		return layer;
	}
}
