package hu.project.innovation.configuration.controller;

import hu.project.innovation.Log;
import hu.project.innovation.configuration.model.ConfigurationService;
import hu.project.innovation.configuration.model.Layer;
import hu.project.innovation.configuration.view.DefinitionJPanel;
import hu.project.innovation.configuration.view.LayersListModel;
import hu.project.innovation.configuration.view.XmlFileFilter;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.io.File;
import java.util.ArrayList;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

public class DefinitionController implements ActionListener, ListSelectionListener, FocusListener {

	private DefinitionJPanel definitionJPanel;
	private ConfigurationService configurationService;

	private boolean valueChanges = true;

	public DefinitionController() {
		Log.i(getClass().getSimpleName(), "constructor()");
		definitionJPanel = new DefinitionJPanel(this);
		configurationService = ConfigurationService.getInstance();
	}

	/**
	 * Init the user interface for creating/editting the definition.
	 * 
	 * @return JPanel
	 */
	public JPanel initUi() {
		Log.i(getClass().getSimpleName(), "initUi()");
		
		// Create an empty model
		LayersListModel listmodel = new LayersListModel();
	
		// Add layers to this model. We use a custom ID
		int id = 0;
		ArrayList<Layer> layers = configurationService.getLayers();
		if (layers != null) {
			for (Layer layer : layers) {
				listmodel.add(id++, layer);
			}
		}
		// Add normal
		listmodel.add(0, new Layer("UI", "Dit is de ui description"));
		listmodel.add(1, new Layer("Controller", "Dit is de controller description"));
		listmodel.add(2, new Layer("Model", "Dit is de model description"));
	
		// Set the model for the layer
		definitionJPanel.jListLayers.setModel(listmodel);
		return definitionJPanel;
	}

	/**
	 * Create a new definition. This function won't ask if you want to save the current definition
	 */
	public void newConfiguration() {
		Log.i(getClass().getSimpleName(), "newDefinition()");

		String response = inputDialog(definitionJPanel, "Please enter the architecture name", "Please input a value", JOptionPane.QUESTION_MESSAGE);
		if (response != null) {
			Log.i(getClass().getSimpleName(), "newDefinition() - value: " + response);
			configurationService.newArchitecture(response, "");
			definitionJPanel.clearJListLayers();
		}
	}

	/**
	 * Open a definition. This function will display an filebrowser and pass the result to the config service.
	 */
	public void openConfiguration() {
		Log.i(getClass().getSimpleName(), "openDefintion()");
	
		// Create a file chooser
		JFileChooser fc = new JFileChooser();
		fc.setFileFilter(new XmlFileFilter());
		// In response to a button click:
		int returnVal = fc.showOpenDialog(definitionJPanel);
	
		if (returnVal == JFileChooser.APPROVE_OPTION) {
			File file = fc.getSelectedFile();
			Log.i(getClass().getSimpleName(), "openDefintion() - opening file: " + file.getName());
			configurationService.openArchitecture(file);
		}
	}

	/**
	 * Save the current definition to a file.
	 */
	public void saveConfiguration() {
		Log.i(getClass().getSimpleName(), "saveDefintion()");
	}



	/**
	 * Create a new layer
	 */
	private void newLayer() {
		Log.i(getClass().getSimpleName(), "newLayer()");

		String response = inputDialog(definitionJPanel, "Please enter layer name", "Please input a value", JOptionPane.QUESTION_MESSAGE);
		if (response != null) {
			Log.i(getClass().getSimpleName(), "newLayer() - value: " + response);
			configurationService.newLayer(response, "");
		}
	}

	/**
	 * Remove a layer which is selected in the JPanel.
	 */
	private void removeLayer() {
		Log.i(getClass().getSimpleName(), "removeLayer()");
	
		Layer layer = definitionJPanel.getSelectedLayer();
	
		Log.i(getClass().getSimpleName(), "removeLayer() - selected layer: " + layer.getDescription());
		configurationService.removeLayer(layer);
	}

	/**
	 * Move a layer up
	 */
	private void moveLayerUp() {
		Log.i(getClass().getSimpleName(), "moveLayerUp()");
		// TODO Auto-generated method stub
	}

	/**
	 * Move a layer down
	 */
	private void moveLayerDown() {
		Log.i(getClass().getSimpleName(), "moveLayerDown()");
		// TODO Auto-generated method stub
	
	}

	private String inputDialog(Component component, String message, String title, int type) {
		String inputValue = "";
		while (inputValue.trim().equals("")) {
			inputValue = JOptionPane.showInputDialog(component, message, title, type);
			if (inputValue == null) {
				return null;
			} else {
				if (!inputValue.trim().equals("")) {
					return inputValue;
				} else {
					JOptionPane.showMessageDialog(null, "Please enter an value!", "Error", JOptionPane.ERROR_MESSAGE);
				}
			}
		}
		return inputValue;
	}

	/**
	 * 
	 */
	private void loadLayerDetail() {
		Log.i(getClass().getSimpleName(), "loadLayerDetail()");
	
		Layer layer = definitionJPanel.getSelectedLayer();
	
		Log.i(getClass().getSimpleName(), "loadLayerDetail() - selected layer: " + layer.getName());
	
		definitionJPanel.jTextFieldLayerName.setText(layer.getName());
		definitionJPanel.jTextAreaLayerDescription.setText(layer.getDescription());
	}

	public void addComponent() {
		Log.i(getClass().getSimpleName(), "addPackage()");
		// TODO Auto-generated method stub
	}

	public void addRuleToLayer() {
		Log.i(getClass().getSimpleName(), "addRuleException()");
		// TODO Auto-generated method stub
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
		} else {
			Log.i(getClass().getSimpleName(), "actionPerformed(" + action + ")");
		}
	}

	@Override
	public void valueChanged(ListSelectionEvent event) {
		if (event.getSource() == definitionJPanel.jListLayers) {
			if (valueChanges) {
				loadLayerDetail();
				valueChanges = false;
			} else {
				valueChanges = true;
			}
		} else {
			Log.i(getClass().getSimpleName(), "valueChanged(" + event + ")");
		}
	}

	@Override
	public void focusGained(FocusEvent event) {
		// Ignore
	}

	@Override
	public void focusLost(FocusEvent event) {
		if (event.getSource() == definitionJPanel.jTextFieldLayerName) {
			Layer layer = definitionJPanel.getSelectedLayer();
			layer.setName(definitionJPanel.jTextFieldLayerName.getText());
		} else {
			Log.i(getClass().getSimpleName(), "focusLost(" + event + ")");
		}
	}

}
