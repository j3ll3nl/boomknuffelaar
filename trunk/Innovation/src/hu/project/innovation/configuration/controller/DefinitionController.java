package hu.project.innovation.configuration.controller;

import hu.project.innovation.MainController;
import hu.project.innovation.configuration.model.ConfigurationService;
import hu.project.innovation.configuration.model.Layer;
import hu.project.innovation.configuration.model.SoftwareUnitDefinition;
import hu.project.innovation.configuration.view.DefinitionJPanel;
import hu.project.innovation.configuration.view.JPanelStatus;
import hu.project.innovation.configuration.view.JTableTableModel;
import hu.project.innovation.configuration.view.XmlFileFilter;
import hu.project.innovation.utils.Log;
import hu.project.innovation.utils.Ui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.File;
import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

import javax.swing.DefaultListModel;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

public class DefinitionController implements ActionListener, ListSelectionListener, KeyListener, Observer {

	private DefinitionJPanel definitionJPanel;
	private ConfigurationService configurationService;
	private MainController mainController;

	public DefinitionController(MainController mc) {
		Log.i(this, "constructor()");
		mainController = mc;
		definitionJPanel = new DefinitionJPanel();
		configurationService = ConfigurationService.getInstance();
	}

	/**
	 * Init the user interface for creating/editting the definition.
	 * 
	 * @return JPanel The jpanel
	 */
	public JPanel initUi() {
		Log.i(this, "initUi()");

		updateLayerList();

		// Set actionlisteners to buttons, lists etc.
		definitionJPanel.jListLayers.addListSelectionListener(this);
		definitionJPanel.jButtonNewLayer.addActionListener(this);
		definitionJPanel.jButtonRemoveLayer.addActionListener(this);
		definitionJPanel.jButtonMoveLayerUp.addActionListener(this);
		definitionJPanel.jButtonMoveLayerDown.addActionListener(this);

		definitionJPanel.jTextFieldLayerName.addKeyListener(this);
		definitionJPanel.jTextAreaLayerDescription.addKeyListener(this);

		definitionJPanel.jButtonAddComponentToLayer.addActionListener(this);
		definitionJPanel.jButtonEditComponentFromLayer.addActionListener(this);
		definitionJPanel.jButtonRemoveComponentFromLayer.addActionListener(this);

		definitionJPanel.jButtonAddRuleToLayer.addActionListener(this);
		definitionJPanel.jButtonEditRuleFromLayer.addActionListener(this);
		definitionJPanel.jButtonRemoveRuleFromLayer.addActionListener(this);

		// Return the definition jpanel
		return definitionJPanel;
	}

	/**
	 * Create an new configuration.
	 */
	public void newConfiguration() {
		Log.i(this, "newConfiguration()");
		try {
			// Ask the user for the architecture name
			String response = Ui.inputDialog(definitionJPanel, "Please enter the architecture name", "Please input a value", JOptionPane.QUESTION_MESSAGE);
			if (response != null) {
				Log.i(this, "newDefinition() - response from inputdialog: " + response);
				JPanelStatus.getInstance("Creating new configuration").start();

				// Create a new configuration
				configurationService.newConfiguration(response, "");

				// Update the layer list, this method is called because it will also clear the existing layers
				updateLayerList();

				// Set the architecture name in the jframe title
				mainController.jframe.setTitle(response);
			}
		} catch (Exception e) {
			Log.e(this, "newConfiguration() - exception: " + e.getMessage());
			Ui.errorDialog(definitionJPanel, e.getMessage(), "Error");
		} finally {
			JPanelStatus.getInstance().stop();
		}
	}

	/**
	 * Open an configuration.
	 */
	public void openConfiguration() {
		Log.i(this, "openConfiguration()");
		try {
			// Create a file chooser
			JFileChooser fc = new JFileChooser();
			fc.setFileFilter(new XmlFileFilter());

			// In response to a button click:
			int returnVal = fc.showOpenDialog(definitionJPanel);

			// The user did click on Open
			if (returnVal == JFileChooser.APPROVE_OPTION) {

				JPanelStatus.getInstance("Opening configuration").start();

				// Getting selected file from dialog
				File file = fc.getSelectedFile();
				Log.i(this, "openConfiguration() - opening file: " + file.getName());

				// Pass the file to the service
				configurationService.openConfiguration(file);

				// Set the architecture name in the jframe title
				mainController.jframe.setTitle(configurationService.getConfiguration().getName());

				Log.i(this, "openConfiguration() - updating layers list");
				updateLayerList();

				Log.i(this, "openConfiguration() - success opening configuration");
			}
		} catch (Exception e) {
			Log.e(this, "openConfiguration() - exeption: " + e.getMessage());
			Ui.errorDialog(definitionJPanel, e.getMessage(), "Error");
		} finally {
			JPanelStatus.getInstance().stop();
		}
	}

	/**
	 * Save the current configuration to a file.
	 */
	public void saveConfiguration() {
		Log.i(this, "saveConfiguration()");
		try {
			// Create a file chooser
			JFileChooser fc = new JFileChooser();
			fc.setFileFilter(new XmlFileFilter());

			// In response to a button click:
			int returnVal = fc.showSaveDialog(definitionJPanel);

			// The user did click on Save
			if (returnVal == JFileChooser.APPROVE_OPTION) {

				JPanelStatus.getInstance("Saving configuration").start();

				// Getting selected file from dialog
				File file;
				if (fc.getSelectedFile().getName().endsWith(".xml")) {
					file = fc.getSelectedFile();
				} else {
					file = new File(fc.getSelectedFile().getAbsolutePath() + ".xml");
				}
				Log.i(this, "saveConfiguration() - configuration needs to be saved to file: " + file.getName());

				// Pass the file to the service
				configurationService.saveConfiguration(file);

				Log.i(this, "saveConfiguration() - success saving configuration");
			}
		} catch (Exception e) {
			Log.e(this, "saveConfiguration() - exeption: " + e.getMessage());
			Ui.errorDialog(definitionJPanel, e.getMessage(), "Error");
		} finally {
			JPanelStatus.getInstance().stop();
		}
	}

	/**
	 * Create a new layer
	 */
	private void newLayer() {
		Log.i(this, "newLayer()");
		try {
			// Ask the user for the layer name
			String layerName = Ui.inputDialog(definitionJPanel, "Please enter layer name", "Please input a value", JOptionPane.QUESTION_MESSAGE);
			if (layerName != null) {
				JPanelStatus.getInstance("Creating new layer").start();

				Log.i(this, "newLayer() - value: " + layerName);
				// Create the layer
				configurationService.newLayer(layerName, "");

				// Update the layer list
				updateLayerList();
			}
		} catch (Exception e) {
			Log.e(this, "newLayer() - exception: " + e.getMessage());
			Ui.errorDialog(definitionJPanel, e.getMessage(), "Error");
		} finally {
			JPanelStatus.getInstance().stop();
		}
	}

	/**
	 * Remove a layer which is selected in the JPanel.
	 */
	private void removeLayer() {
		Log.i(this, "removeLayer()");
		try {
			Layer layer = definitionJPanel.getSelectedLayer();
			if (layer != null) {
				Log.i(this, "removeLayer() - selected layer: " + layer.getName());
				boolean confirm = Ui.confirmDialog(definitionJPanel, "Are you sure you want to remove layer: \"" + layer.getName() + "\"", "Remove?");
				if (confirm) {
					JPanelStatus.getInstance("Removing layer").start();

					Log.i(this, "removeLayer() - removing layer");
					configurationService.removeLayer(layer);

					// Update the layer list
					updateLayerList();
				}

			}
		} catch (Exception e) {
			Log.e(this, "removeLayer() - exception: " + e.getMessage());
			Ui.errorDialog(definitionJPanel, e.getMessage(), "Error");
		} finally {
			JPanelStatus.getInstance().stop();
		}
	}

	/**
	 * Move a layer 1 up in hierarchy
	 */
	private void moveLayerUp() {
		Log.i(this, "moveLayerUp()");
		try {
			Layer layer = definitionJPanel.getSelectedLayer();
			if (layer != null) {
				JPanelStatus.getInstance("Moving layer up").start();

				Log.i(this, "moveLayerUp() - selected layer: " + layer.getName());

				configurationService.moveLayerUp(layer);
				updateLayerList();

			}
		} catch (Exception e) {
			Log.e(this, "moveLayerUp() - exception: " + e.getMessage());
			Ui.errorDialog(definitionJPanel, e.getMessage(), "Error");
		} finally {
			JPanelStatus.getInstance().stop();
		}
	}

	/**
	 * Move a layer 1 down in hierarchy
	 */
	private void moveLayerDown() {
		Log.i(this, "moveLayerDown()");
		try {
			Layer layer = definitionJPanel.getSelectedLayer();
			if (layer != null) {
				JPanelStatus.getInstance("Moving layer down").start();

				Log.i(this, "moveLayerDown() - selected layer: " + layer.getName());

				configurationService.moveLayerDown(layer);
				updateLayerList();

			}
		} catch (Exception e) {
			Log.e(this, "moveLayerDown() - exception: " + e.getMessage());
			Ui.errorDialog(definitionJPanel, e.getMessage(), "Error");
		} finally {
			JPanelStatus.getInstance().stop();
		}
	}

	/**
	 * Add a new software unit to the selected layer. This method will make pop-up a new jframe who will handle everything for creating a new sotware unit.
	 */
	private void addSoftwareUnit() {
		Log.i(this, "addSoftwareUnit()");
		// Get the current selected layer
		Layer layer = definitionJPanel.getSelectedLayer();
		if (layer != null) {
			Log.i(this, "addSoftwareUnit() - selected layer: " + layer.getName());

			// Create a new software unit controller
			SoftwareUnitController c = new SoftwareUnitController(layer, null);
			// Set the action of the view
			c.setAction(SoftwareUnitController.ACTION_NEW);
			c.addObserver(this);
			// Build and show the ui
			c.initUi();
		}
	}

	/**
	 * Edit the selected software unit. This method will make a new jframe who will handle everything for editing a new software unit
	 */
	private void editSoftwareUnit() {
		Log.i(this, "editSoftwareUnit()");
		Layer layer = definitionJPanel.getSelectedLayer();
		SoftwareUnitDefinition softwareunit = definitionJPanel.getSelectedSoftwareUnit();
		if (layer != null && softwareunit != null) {
			Log.i(this, "editSoftwareUnit() - selected layer: " + layer.getName());
			Log.i(this, "editSoftwareUnit() - selected software unit: " + softwareunit.getName());

			// Create a new software unit controller
			SoftwareUnitController c = new SoftwareUnitController(layer, softwareunit);
			// Set the action of the view
			c.setAction(SoftwareUnitController.ACTION_EDIT);
			c.addObserver(this);
			// Build and show the ui
			c.initUi();
		} else {
			Log.e(this, "editSoftwareUnit() - no software unit selected");
			Ui.errorDialog(definitionJPanel, "Select a software unit", "Error");
		}
	}

	/**
	 * Remove the selected software unit from the table
	 */
	private void removeSoftwareUnit() {
		Log.i(this, "removeSoftwareUnit()");
		try {
			// Get the selected layer & software unit
			Layer layer = definitionJPanel.getSelectedLayer();
			SoftwareUnitDefinition softwareunit = definitionJPanel.getSelectedSoftwareUnit();
			if (layer != null && softwareunit != null) {
				// Ask the user if he is sure to remove the software unit
				boolean confirm = Ui.confirmDialog(definitionJPanel, "Are you sure you want to remove software unit: \"" + softwareunit.getName() + "\"", "Remove?");
				if (confirm) {
					// Remove the software unit
					JPanelStatus.getInstance("Removing software unit").start();
					configurationService.removeSoftwareUnit(layer, softwareunit);
					// Update the software unit table
					updateSoftwareUnitTable();
				}
			}
		} catch (Exception e) {
			Log.e(this, "removeSoftwareUnit() - exception: " + e.getMessage());
			Ui.errorDialog(definitionJPanel, e.getMessage(), "Error");
		} finally {
			JPanelStatus.getInstance().stop();
		}
	}

	private void addRuleToLayer() {
		Log.i(this, "addRuleToLayer()");
		// TODO Auto-generated method stub
	}

	private void editRuleToLayer() {
		Log.i(this, "editRuleToLayer()");
		// TODO Auto-generated method stub
	}

	private void removeRuleToLayer() {
		Log.i(this, "removeRuleToLayer()");
		// TODO Auto-generated method stub

	}

	/**
	 * Function which will save the name and description changes to the layer
	 */
	private void updateLayer() {
		Layer layer = definitionJPanel.getSelectedLayer();

		JPanelStatus.getInstance("Saving layer").start();

		if (layer != null) {
			Log.i(this, "keyReleased() - value from name: " + definitionJPanel.jTextFieldLayerName.getText());
			layer.setName(definitionJPanel.jTextFieldLayerName.getText());
			layer.setDescription(definitionJPanel.jTextAreaLayerDescription.getText());
			layer.setInterfaceAccesOnly(definitionJPanel.jCheckBoxAccess.isSelected());

			definitionJPanel.jListLayers.updateUI();
		}
		JPanelStatus.getInstance().stop();
	}

	/**
	 * This method updates the layers list in the jpanel.
	 */
	private void updateLayerList() {
		Log.i(this, "updateLayerList()");

		JPanelStatus.getInstance("Updating layers").start();

		// Get all layers from the service
		ArrayList<Layer> layers = configurationService.getLayers();

		// Get ListModel from listlayers
		DefaultListModel dlm = (DefaultListModel) definitionJPanel.jListLayers.getModel();

		// Remove all items in the list
		dlm.removeAllElements();

		// Add layers to the list
		if (layers != null) {
			int id = 0;
			for (Layer layer : layers) {
				dlm.add(id++, layer);
			}
		}

		enablePanel();

		JPanelStatus.getInstance().stop();
	}

	/**
	 * This function will load the layer name, descriptin and interface acces only checkbox. Next it will call two methods which will load the two tables.
	 */
	private void loadLayerDetail() {
		Log.i(this, "loadLayerDetail()");

		Layer layer = definitionJPanel.getSelectedLayer();
		if (layer != null) {
			Log.i(this, "loadLayerDetail() - selected layer: " + layer.getName());

			// Set the values
			definitionJPanel.jTextFieldLayerName.setText(layer.getName());
			definitionJPanel.jTextAreaLayerDescription.setText(layer.getDescription());
			definitionJPanel.jCheckBoxAccess.setSelected(layer.isInterfaceAccessOnly());

			// Update the tables
			updateSoftwareUnitTable();
			updateAppliedRulesTable();

			// Enable or disable the ui elements
			enablePanel();
		}
	}

	/**
	 * This method will check if the ui elements should be enabled or disabled
	 */
	private void enablePanel() {
		Log.i(this, "enablePanel()");
		Layer layer = definitionJPanel.getSelectedLayer();

		boolean enabled;
		if (layer == null) {
			Log.i(this, "enablePanel() - false");
			enabled = false;
		} else {
			Log.i(this, "enablePanel() - true");
			enabled = true;
		}
		// Buttons, textfields, tables etc.
		definitionJPanel.jTextFieldLayerName.setEnabled(enabled);
		definitionJPanel.jTextAreaLayerDescription.setEnabled(enabled);
		definitionJPanel.jCheckBoxAccess.setEnabled(enabled);
		definitionJPanel.jButtonAddComponentToLayer.setEnabled(enabled);
		definitionJPanel.jButtonEditComponentFromLayer.setEnabled(enabled);
		definitionJPanel.jButtonRemoveComponentFromLayer.setEnabled(enabled);
		definitionJPanel.jTableSoftwareUnits.setEnabled(enabled);
		definitionJPanel.jTableAppliedRules.setEnabled(enabled);
		definitionJPanel.jButtonAddRuleToLayer.setEnabled(enabled);
		definitionJPanel.jButtonEditRuleFromLayer.setEnabled(enabled);
		definitionJPanel.jButtonRemoveRuleFromLayer.setEnabled(enabled);
		definitionJPanel.jButtonMoveLayerUp.setEnabled(enabled);
		definitionJPanel.jButtonMoveLayerDown.setEnabled(enabled);
		definitionJPanel.jButtonRemoveLayer.setEnabled(enabled);

		// Enable or disable menu items
		if (!configurationService.isArchitectureDefinition()) {
			definitionJPanel.jButtonNewLayer.setEnabled(false);
			mainController.jframe.jMenuItemSaveArchitecture.setEnabled(false);
			mainController.jframe.jMenuItemStartAnalyse.setEnabled(false);
		} else {
			definitionJPanel.jButtonNewLayer.setEnabled(true);
			mainController.jframe.jMenuItemSaveArchitecture.setEnabled(true);
			mainController.jframe.jMenuItemStartAnalyse.setEnabled(true);
		}
	}

	/**
	 * This method updates the component table in the jpanel
	 * 
	 * @param layer
	 */
	private void updateSoftwareUnitTable() {
		Log.i(this, "updateSoftwareUnitTable()");

		Layer layer = definitionJPanel.getSelectedLayer();
		if (layer != null) {
			JPanelStatus.getInstance("Updating software unit table").start();

			// Get all components from the service
			ArrayList<SoftwareUnitDefinition> components = layer.getAllSoftwareUnitDefinitions();

			// Get the tablemodel from the table
			JTableTableModel atm = (JTableTableModel) definitionJPanel.jTableSoftwareUnits.getModel();

			// Remove all items in the table
			atm.getDataVector().removeAllElements();
			if (components != null) {
				for (SoftwareUnitDefinition component : components) {
					Object rowdata[] = { component, component.getType(), component.getNumberOfExceptions() + "" };
					atm.addRow(rowdata);
				}
			}
			atm.fireTableDataChanged();

			JPanelStatus.getInstance().stop();
		}
	}

	private void updateAppliedRulesTable() {
		Log.i(this, "updateAppliedRulesTable()");

		Layer layer = definitionJPanel.getSelectedLayer();
		if (layer != null) {
			JPanelStatus.getInstance("Updating rules applied table").start();

			// TODO implement this function
			JPanelStatus.getInstance().stop();
		}
	}

	@Override
	public void actionPerformed(ActionEvent action) {
		if (action.getSource() == definitionJPanel.jButtonNewLayer) {
			newLayer();
		} else if (action.getSource() == definitionJPanel.jButtonRemoveLayer) {
			removeLayer();
		} else if (action.getSource() == definitionJPanel.jButtonMoveLayerUp) {
			moveLayerUp();
		} else if (action.getSource() == definitionJPanel.jButtonMoveLayerDown) {
			moveLayerDown();
		} else if (action.getSource() == definitionJPanel.jButtonAddComponentToLayer) {
			addSoftwareUnit();
		} else if (action.getSource() == definitionJPanel.jButtonEditComponentFromLayer) {
			editSoftwareUnit();
		} else if (action.getSource() == definitionJPanel.jButtonRemoveComponentFromLayer) {
			removeSoftwareUnit();
		} else if (action.getSource() == definitionJPanel.jButtonAddRuleToLayer) {
			addRuleToLayer();
		} else if (action.getSource() == definitionJPanel.jButtonEditRuleFromLayer) {
			editRuleToLayer();
		} else if (action.getSource() == definitionJPanel.jButtonRemoveRuleFromLayer) {
			removeRuleToLayer();
		} else {
			Log.i(this, "actionPerformed(" + action + ")");
		}
	}

	@Override
	public void valueChanged(ListSelectionEvent event) {
		if (event.getSource() == definitionJPanel.jListLayers && !event.getValueIsAdjusting()) {
			loadLayerDetail();
		}
	}

	@Override
	public void keyPressed(KeyEvent arg0) {
		// Ignore
	}

	@Override
	public void keyReleased(KeyEvent arg0) {
		Log.i(this, "keyReleased(" + arg0 + ")");

		if (arg0.getSource() == definitionJPanel.jTextFieldLayerName || arg0.getSource() == definitionJPanel.jTextAreaLayerDescription) {
			updateLayer();
		} else {
			Log.i(this, "keyReleased(" + arg0 + ")");
		}
	}

	@Override
	public void keyTyped(KeyEvent arg0) {
		// Ignore
	}

	@Override
	public void update(Observable o, Object arg) {
		Log.i(this, "update(" + o + ", " + arg + ")");
		updateAppliedRulesTable();
		updateSoftwareUnitTable();
	}

}
