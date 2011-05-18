package hu.project.innovation.configuration.controller;

import hu.project.innovation.configuration.model.ConfigurationService;
import hu.project.innovation.configuration.model.Layer;
import hu.project.innovation.configuration.model.SoftwareUnitDefinition;
import hu.project.innovation.configuration.view.jframe.JFrameSoftwareUnit;
import hu.project.innovation.configuration.view.tables.JTableException;
import hu.project.innovation.configuration.view.tables.JTableTableModel;
import hu.project.innovation.utils.Log;
import hu.project.innovation.utils.Ui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Observable;

public class SoftwareUnitController extends Observable implements ActionListener {

	public static final String ACTION_NEW = "NEW";
	public static final String ACTION_EDIT = "EDIT";

	private JFrameSoftwareUnit jframe;
	private String action = SoftwareUnitController.ACTION_NEW;
	private SoftwareUnitDefinition softwareunit;
	private Layer layer;

	public SoftwareUnitController(Layer layer, SoftwareUnitDefinition softwareunit) {
		Log.i(this, "constructor(" + layer + ", " + softwareunit + ")");
		this.layer = layer;
		this.softwareunit = softwareunit;
	}

	public void initUi() {
		Log.i(this, "initUi()");
		jframe = new JFrameSoftwareUnit();

		// Change view of jframe conforms the action
		if (getAction().equals(SoftwareUnitController.ACTION_NEW)) {
			jframe.jButtonSave.setText("Create");
			jframe.setTitle("New software unit");
		} else if (getAction().equals(SoftwareUnitController.ACTION_EDIT)) {
			jframe.jButtonSave.setText("Save");
			jframe.setTitle("Edit software unit");
			if (softwareunit != null) {
				// Load name & type
				jframe.jTextFieldSoftwareUnitName.setText(softwareunit.getName());
				jframe.jComboBoxSoftwareUnitType.setSelectedItem(softwareunit.getType());

				// Load table with exceptions
				JTableException table = jframe.jTableException;
				JTableTableModel tablemodel = (JTableTableModel) table.getModel();

				ArrayList<SoftwareUnitDefinition> exceptions = softwareunit.getExceptions();
				for (SoftwareUnitDefinition exception : exceptions) {
					Object[] row = { exception.getName(), exception.getType() };
					tablemodel.addRow(row);
				}

			}
		}

		jframe.jButtonAddExceptionRow.addActionListener(this);
		jframe.jButtonRemoveExceptionRow.addActionListener(this);
		jframe.jButtonSave.addActionListener(this);
		jframe.jButtonCancel.addActionListener(this);

		// Set the visibility of the jframe to true so the jframe is now visible
		Ui.showOnScreen(0, jframe);
		
		jframe.setVisible(true);
	}

	private void saveSoftwareUnit() {
		ConfigurationService service = ConfigurationService.getInstance();

		try {
			if (getAction().equals(SoftwareUnitController.ACTION_NEW)) {
				softwareunit = service.newSoftwareUnit(getLayer(), jframe.jTextFieldSoftwareUnitName.getText(), jframe.jComboBoxSoftwareUnitType.getSelectedItem().toString());

				JTableException table = jframe.jTableException;
				JTableTableModel tablemodel = (JTableTableModel) table.getModel();

				int tablerows = tablemodel.getRowCount();
				for (int i = 0; i < tablerows; i++) {
					service.addException(softwareunit, tablemodel.getValueAt(i, 0).toString(), tablemodel.getValueAt(i, 0).toString());
				}
			} else if (getAction().equals(SoftwareUnitController.ACTION_EDIT)) {
				softwareunit.setName(jframe.jTextFieldSoftwareUnitName.getText());
				softwareunit.setType(jframe.jComboBoxSoftwareUnitType.getSelectedItem().toString());

				softwareunit.removeAllExceptions();

				JTableException table = jframe.jTableException;
				JTableTableModel tablemodel = (JTableTableModel) table.getModel();

				int tablerows = tablemodel.getRowCount();
				for (int i = 0; i < tablerows; i++) {
					service.addException(softwareunit, tablemodel.getValueAt(i, 0).toString(), tablemodel.getValueAt(i, 0).toString());
				}
			}
			jframe.dispose();
			pokeObservers();
		} catch (Exception e) {
			Ui.errorDialog(jframe, e.getMessage(), "Error");
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

	/**
	 * Remove the selected row from the exception table
	 */
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

	/**
	 * Use this function to notify the definitioncontroller that there is a change
	 */
	private void pokeObservers() {
		setChanged();
		notifyObservers();
	}

	public void setAction(String action) {
		if (action.equals(SoftwareUnitController.ACTION_EDIT) || action.equals(SoftwareUnitController.ACTION_NEW)) {
			this.action = action;
		}
	}

	private String getAction() {
		return action;
	}

	private Layer getLayer() {
		return layer;
	}

	public void actionPerformed(ActionEvent action) {
		Log.i(this, "actionPerformed()");
		if (action.getSource() == jframe.jButtonAddExceptionRow) {
			addExceptionRow();
		} else if (action.getSource() == jframe.jButtonRemoveExceptionRow) {
			removeExceptionRow();
		} else if (action.getSource() == jframe.jButtonSave) {
			saveSoftwareUnit();
		} else if (action.getSource() == jframe.jButtonCancel) {
			jframe.dispose();
		} else {
			Log.i(this, "actionPerformed(" + action + ") - unknown button event");
		}
	}
}
