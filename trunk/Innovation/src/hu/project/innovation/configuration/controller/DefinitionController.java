package hu.project.innovation.configuration.controller;

import hu.project.innovation.CustomLogger;
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

		String response = inputDialog(jpanel, "Please enter the architecture name", "Please input a value", JOptionPane.QUESTION_MESSAGE);
		if (response != null) {
			CustomLogger.i(getClass().getSimpleName(), "newDefinition() - value: " + response);
			cs.newArchitecture(response, "");
			clearJListLayers();
		}
	}

	/**
	 * Use this function to clear the user interface
	 */
	private void clearJListLayers() {
		CustomLogger.i(getClass().getSimpleName(), "clearUI()");
		LayersListModel listmodel = new LayersListModel();

		// Set the model for the layer
		jpanel.jListLayers.setModel(listmodel);
	}

	/**
	 * 
	 */
	private void loadLayerDetail() {
		CustomLogger.i(getClass().getSimpleName(), "loadLayerDetail()");

		Layer layer = jpanel.getSelectedLayer();

		CustomLogger.i(getClass().getSimpleName(), "loadLayerDetail() - selected layer: " + layer.getName());

		jpanel.jTextFieldLayerName.setText(layer.getName());
		jpanel.jTextAreaLayerDescription.setText(layer.getDescription());
	}

	/**
	 * Open a definition. This function will display an filebrowser and pass the result to the config service.
	 */
	public void openDefintion() {
		CustomLogger.i(getClass().getSimpleName(), "openDefintion()");

		// Create a file chooser
		JFileChooser fc = new JFileChooser();
		fc.setFileFilter(new XmlFileFilter());
		// In response to a button click:
		int returnVal = fc.showOpenDialog(jpanel);

		if (returnVal == JFileChooser.APPROVE_OPTION) {
			File file = fc.getSelectedFile();
			CustomLogger.i(getClass().getSimpleName(), "openDefintion() - opening file: " + file.getName());
			cs.openArchitecture(file);
		}
	}

	/**
	 * Save the current definition to a file.
	 */
	public void saveDefintion() {
		CustomLogger.i(getClass().getSimpleName(), "saveDefintion()");
	}

	/**
	 * Create a new layer
	 */
	private void newLayer() {
		CustomLogger.i(getClass().getSimpleName(), "newLayer()");

		String response = inputDialog(jpanel, "Please enter layer name", "Please input a value", JOptionPane.QUESTION_MESSAGE);
		if (response != null) {
			CustomLogger.i(getClass().getSimpleName(), "newLayer() - value: " + response);
			cs.newLayer(response, "");
		}
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
	 * Remove a layer which is selected in the JPanel.
	 */
	private void removeLayer() {
		CustomLogger.i(getClass().getSimpleName(), "removeLayer()");

		Layer layer = jpanel.getSelectedLayer();

		CustomLogger.i(getClass().getSimpleName(), "removeLayer() - selected layer: " + layer.getDescription());
		cs.removeLayer(layer);
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

	/**
	 * Init the
	 * 
	 * @return JPanel
	 */
	public JPanel initGUI() {
		// Create an empty model
		LayersListModel listmodel = new LayersListModel();

		// Add layers to this model. We use a custom ID
		int id = 0;
		ArrayList<Layer> layers = cs.getLayers();
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
		jpanel.jListLayers.setModel(listmodel);
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

	private boolean valueChanges = true;

	@Override
	public void valueChanged(ListSelectionEvent event) {
		if (event.getSource() == jpanel.jListLayers) {
			if (valueChanges) {
				loadLayerDetail();
				valueChanges = false;
			} else {
				valueChanges = true;
			}
		} else {
			CustomLogger.i(getClass().getSimpleName(), "valueChanged(" + event + ")");
		}
	}

	@Override
	public void focusGained(FocusEvent event) {
		// Ignore
	}

	@Override
	public void focusLost(FocusEvent event) {
		if (event.getSource() == jpanel.jTextFieldLayerName) {
			Layer layer = jpanel.getSelectedLayer();
			layer.setName(jpanel.jTextFieldLayerName.getText());
		} else {
			CustomLogger.i(getClass().getSimpleName(), "focusLost(" + event + ")");
		}
	}

}
