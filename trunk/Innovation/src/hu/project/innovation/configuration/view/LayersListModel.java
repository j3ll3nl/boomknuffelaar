package hu.project.innovation.configuration.view;

import hu.project.innovation.configuration.model.Layer;

import java.util.ArrayList;

import javax.swing.DefaultListModel;

public class LayersListModel extends DefaultListModel {

	private static final long serialVersionUID = -4731003705381872365L;

	public void setContent(ArrayList<Layer> layers) {
		removeAllElements();
		int id = 0;
		if (layers != null) {
			for (Layer layer : layers) {
				add(id++, layer);
			}
		}
	}

}
