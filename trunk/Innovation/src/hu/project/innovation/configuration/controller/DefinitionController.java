package hu.project.innovation.configuration.controller;

import hu.project.innovation.configuration.model.ConfigurationService;
import hu.project.innovation.configuration.model.Layer;
import hu.project.innovation.configuration.model.SoftwareUnitDefinition;
import hu.project.innovation.configuration.view.JTableTableModel;
import hu.project.innovation.configuration.view.DefinitionJPanel;
import hu.project.innovation.configuration.view.JPanelStatus;
import hu.project.innovation.configuration.view.XmlFileFilter;
import hu.project.innovation.utils.Log;
import hu.project.innovation.utils.Ui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.File;
import java.util.ArrayList;

import javax.swing.DefaultListModel;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

public class DefinitionController implements ActionListener, ListSelectionListener, KeyListener {

	private DefinitionJPanel definitionJPanel;
	private ConfigurationService configurationService;

	public DefinitionController() {
		Log.i(this, "constructor()");
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
	 * Create an new definition.
	 */
	public void newConfiguration() {
		Log.i(this, "newConfiguration()");

		String response = Ui.inputDialog(definitionJPanel, "Please enter the architecture name", "Please input a value", JOptionPane.QUESTION_MESSAGE);
		if (response != null) {
			Log.i(this, "newDefinition() - response from inputdialog: " + response);
			configurationService.newConfiguration(response, "");

			updateLayerList();
		}
	}

	/**
	 * Open an definition.
	 */
	public void openConfiguration() {
		Log.i(this, "openConfiguration()");

		// Create a file chooser
		JFileChooser fc = new JFileChooser();
		fc.setFileFilter(new XmlFileFilter());

		// In response to a button click:
		int returnVal = fc.showOpenDialog(definitionJPanel);

		// The user did click on Open
		if (returnVal == JFileChooser.APPROVE_OPTION) {
			try {
				JPanelStatus.getInstance("Opening configuration").start();

				// Getting selected file from dialog
				File file = fc.getSelectedFile();
				Log.i(this, "openConfiguration() - opening file: " + file.getName());

				// Pass the file to the service
				configurationService.openConfiguration(file);

				Log.i(this, "openConfiguration() - success opening configuration");
			} catch (Exception e) {
				Log.e(this, "openConfiguration() - exeption: " + e.getMessage());
				Ui.errorDialog(definitionJPanel, e.getMessage(), "Error");
			} finally {
				JPanelStatus.getInstance().stop();
			}

			Log.i(this, "openConfiguration() - updating layers list");
			updateLayerList();

		}
	}

	/**
	 * Save the current definition to a file.
	 */
	public void saveConfiguration() {
		Log.i(this, "saveConfiguration()");
		// Create a file chooser
		JFileChooser fc = new JFileChooser();
		fc.setFileFilter(new XmlFileFilter());

		// In response to a button click:
		int returnVal = fc.showSaveDialog(definitionJPanel);

		// The user did click on Save
		if (returnVal == JFileChooser.APPROVE_OPTION) {
			try {
				JPanelStatus.getInstance("Saving configuration").start();

				// Getting selected file from dialog
				File file = fc.getSelectedFile();
				Log.i(this, "saveConfiguration() - configuration needs to be saved to file: " + file.getName());

				// Pass the file to the service
				configurationService.saveConfiguration(file);

				Log.i(this, "saveConfiguration() - success saving configuration");
			} catch (Exception e) {
				Log.e(this, "saveConfiguration() - exeption: " + e.getMessage());
				Ui.errorDialog(definitionJPanel, e.getMessage(), "Error");
			} finally {
				JPanelStatus.getInstance().stop();
			}
		}
	}

	/**
	 * Create a new layer
	 */
	private void newLayer() {
		Log.i(this, "newLayer()");

		String layerName = Ui.inputDialog(definitionJPanel, "Please enter layer name", "Please input a value", JOptionPane.QUESTION_MESSAGE);
		if (layerName != null) {
			try {
				Log.i(this, "newLayer() - value: " + layerName);
				configurationService.newLayer(layerName, "");
			} catch (Exception e) {
				Ui.errorDialog(definitionJPanel, e.getMessage(), "Error");
			}
			updateLayerList();
		}
	}

	/**
	 * Remove a layer which is selected in the JPanel.
	 */
	private void removeLayer() {
		Log.i(this, "removeLayer()");

		Layer layer = definitionJPanel.getSelectedLayer();
		if (layer != null) {
			try {
				Log.i(this, "removeLayer() - selected layer: " + layer.getName());
				configurationService.removeLayer(layer);
			} catch (Exception e) {
				Ui.errorDialog(definitionJPanel, e.getMessage(), "Error");
			}

			updateLayerList();
		}
	}

	/**
	 * Move a layer up in hierarchy
	 */
	private void moveLayerUp() {
		Log.i(this, "moveLayerUp()");

		Layer layer = definitionJPanel.getSelectedLayer();
		if (layer != null) {
			Log.i(this, "loadLayerDetail() - selected layer: " + layer.getName());

			configurationService.moveLayerUp(layer);
		}
	}

	/**
	 * Move a layer down in hierarchy
	 */
	private void moveLayerDown() {
		Log.i(this, "moveLayerDown()");
		Layer layer = definitionJPanel.getSelectedLayer();
		if (layer != null) {
			Log.i(this, "loadLayerDetail() - selected layer: " + layer.getName());

			configurationService.moveLayerDown(layer);
		}
	}

	private void loadLayerDetail() {
		Log.i(this, "loadLayerDetail()");

		Layer layer = definitionJPanel.getSelectedLayer();
		if (layer != null) {
			Log.i(this, "loadLayerDetail() - selected layer: " + layer.getName());

			definitionJPanel.jTextFieldLayerName.setText(layer.getName());
			definitionJPanel.jTextAreaLayerDescription.setText(layer.getDescription());

			updateComponentTable(layer);
		}
	}

	private void addSoftwareUnit() {
		Log.i(this, "addComponent()");
		// TODO Auto-generated method stub
	}

	private void editSoftwareUnit() {
		Log.i(this, "editComponent()");
		// TODO Auto-generated method stub
	}

	private void removeSoftwareUnit() {
		Log.i(this, "removeComponent()");
		// TODO Auto-generated method stub

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

		Log.i(this, "keyReleased() - value from name: " + definitionJPanel.jTextFieldLayerName.getText());
		layer.setName(definitionJPanel.jTextFieldLayerName.getText());
		layer.setDescription(definitionJPanel.jTextAreaLayerDescription.getText());

		definitionJPanel.jListLayers.updateUI();
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

		JPanelStatus.getInstance().stop();
	}

	/**
	 * This method updates the component table in the jpanel
	 * 
	 * @param layer
	 */
	private void updateComponentTable(Layer layer) {
		Log.i(this, "updateComponentTable()");

		JPanelStatus.getInstance("Updating component table").start();

		// Get all components from the service
		ArrayList<SoftwareUnitDefinition> components = layer.getAllSoftwareUnitDefinitions();

		// Get the tablemodel from the table
		JTableTableModel atm = (JTableTableModel) definitionJPanel.jTable1.getModel();

		// Remove all items in the table
		atm.getDataVector().removeAllElements();
		if (components != null) {
			for (SoftwareUnitDefinition component : components) {
				String rowdata[] = { component.getName(), component.getType(), "" };
				atm.addRow(rowdata);
			}
		}
		atm.fireTableDataChanged();

		JPanelStatus.getInstance().stop();
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

}
