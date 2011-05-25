package hu.project.innovation.configuration.controller.xml;

import hu.project.innovation.configuration.model.Layer;

public class Controller {

	public Controller() {
	}

	public void configuration() {

	}

	public void layer(Object layer) {
		if (layer instanceof Layer) {
			// Layer -> XML
			
		} else if (layer instanceof String) {
			// XML -> Layer

		}
	}

}
