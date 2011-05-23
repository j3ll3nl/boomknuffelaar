package hu.project.innovation.configuration.controller;

import hu.project.innovation.configuration.model.AppliedRule;
import hu.project.innovation.configuration.model.ConfigurationService;
import hu.project.innovation.configuration.model.Layer;
import hu.project.innovation.configuration.model.SoftwareUnitDefinition;
import hu.project.innovation.configuration.view.jframe.JFrameAppliedRules;
import hu.project.innovation.configuration.view.tables.JTableException;
import hu.project.innovation.configuration.view.tables.JTableTableModel;
import hu.project.innovation.utils.Log;
import hu.project.innovation.utils.UiDialogs;

import java.awt.event.ActionEvent;
import java.util.ArrayList;

import javax.swing.ComboBoxModel;
import javax.swing.DefaultComboBoxModel;

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

		// Change view of jframe conforms the action
		loadSelectBoxes();
		if (getAction().equals(SoftwareUnitController.ACTION_NEW)) {
			jframe.jButtonSave.setText("Create");
			jframe.setTitle("New applied rule");
		} else if (getAction().equals(SoftwareUnitController.ACTION_EDIT)) {
			jframe.jButtonSave.setText("Save");
			jframe.setTitle("Edit applied rule");
			if (appliedrule != null) {
				// Load name & type
				// jframe.jTextFieldSoftwareUnitName.setText(appliedrule.getName());
				// jframe.jComboBoxSoftwareUnitType.setSelectedItem(appliedrule.getType());

				// Load table with exceptions
				JTableException table = jframe.jTableException;
				JTableTableModel tablemodel = (JTableTableModel) table.getModel();

				ArrayList<SoftwareUnitDefinition> exceptions = appliedrule.getExceptions();
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
		UiDialogs.showOnScreen(0, jframe);

		jframe.setVisible(true);
	}

	public void loadSelectBoxes() {
		// Laden van alle layers
		ArrayList<Layer> layers = ConfigurationService.getInstance().getLayers();

		if (layers != null) {
			// Remove the current layer from the list
			layers.remove(getLayer());

			ComboBoxModel jComboBoxModel = new DefaultComboBoxModel(layers.toArray());
			jframe.jComboBox2.setModel(jComboBoxModel);
		}
	}

	@Override
	public void save() {
		// TODO Auto-generated method stub

	}

	/**
	 * Add a new empty row to the exception table
	 */
	@Override
	public void addExceptionRow() {
		JTableException table = jframe.jTableException;
		JTableTableModel tablemodel = (JTableTableModel) table.getModel();

		Object[] emptyrow = { "", "" };
		tablemodel.addRow(emptyrow);
	}

	/**
	 * Remove the selected row from the exception table
	 */
	@Override
	public void removeExceptionRow() {
		JTableException table = jframe.jTableException;
		int selectedrow = table.getSelectedRow();
		if (selectedrow == -1) {
			UiDialogs.errorDialog(jframe, "Select a table row", "Error");
		} else {
			JTableTableModel tablemodel = (JTableTableModel) table.getModel();
			tablemodel.removeRow(selectedrow);
		}
	}

	public void actionPerformed(ActionEvent action) {
		Log.i(this, "actionPerformed()");
		if (action.getSource() == jframe.jButtonAddExceptionRow) {
			addExceptionRow();
		} else if (action.getSource() == jframe.jButtonRemoveExceptionRow) {
			removeExceptionRow();
		} else if (action.getSource() == jframe.jButtonSave) {
			save();
		} else if (action.getSource() == jframe.jButtonCancel) {
			jframe.dispose();
		} else {
			Log.i(this, "actionPerformed(" + action + ") - unknown button event");
		}
	}

}
