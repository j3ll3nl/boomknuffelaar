package hu.project.innovation.configuration.model;

import hu.project.innovation.utils.Log;
import hu.project.innovation.utils.XMLable;

import java.util.ArrayList;

public class ArchitectureDefinition implements XMLable {

	private String name;
	private String description;
	private Layer topLayer;

	public ArchitectureDefinition() {
		Log.i(this, "ArchitectureDefinition()");
	}

	public ArchitectureDefinition(String name, String description) throws Exception {
		setName(name);
		setDescription(description);
	}

	public void setName(String name) throws Exception {
		if (name.equals("")) {
			throw new Exception("Architecture name cannot be empty");
		}
		this.name = name;
	}

	public String getName() {
		return this.name;
	}

	public String getDescription() {
		return this.description;
	}

	public void setDescription(String desc) {
		description = desc;
	}

	public Layer getTopLayer() {
		return topLayer;
	}

	public void addLayer(Layer layer) {
		Log.i(this, "addLayer(" + layer + ")");
		if (topLayer == null) {
			topLayer = layer;
		} else {
			topLayer.addChildLayer(layer);
		}
	}

	public Layer getLayer(int id) {
		if (topLayer != null) {
			return topLayer.getLayer(id);
		}
		return null;
	}

	public Layer getLayer(String name) {
		if (topLayer != null) {
			return topLayer.getLayer(name);
		}
		return null;
	}

	public void removeLayer(Layer layer) {
		if (topLayer != null) {
			if (topLayer == layer) {
				topLayer = layer.getChildLayer();
				topLayer.setParentLayer(null);
			} else {
				topLayer.removeLayer(layer);
			}
		}
	}

	/**
	 * Find the layer name by a software unit name. This function will iterate over the layers. If a layer has a software unit with the specified name, it will return the layer name.
	 * 
	 * @param name
	 * @return A string containing the name of the layer with the specified software unit name
	 */
	public Layer getLayerBySoftwareUnitName(String softwareUnitName) {
		if (topLayer != null) {
			return topLayer.getLayerBySoftwareUnitName(softwareUnitName);
		}
		return null;
	}

	public ArrayList<Layer> getAllLayers() {
		Log.i(this, "getAllLayers()");
		ArrayList<Layer> layers = new ArrayList<Layer>();

		if (topLayer != null) {
			Layer layer = topLayer;
			while (true) {
				if (layer == null) {
					break;
				} else {
					layers.add(layer);
				}
				layer = layer.getChildLayer();
			}
		}
		Log.i(this, "getAllLayers() - klaar: " + layers);
		return layers;
	}

	public String toXML() {
		String xml = "\t<architecture name=\"" + getName() + "\">\n";
		if (topLayer != null) {
			xml += topLayer.toXML();
		}
		xml += "\t</architecture>\n";

		return xml;
	}

	/**
	 * This method will reset the topLayer with the new FirstLayer. Method is NEEDED for correct move up/down sequence
	 */
	public void autoUpdateLayerSequence() {
		Layer firstLayer = topLayer.getFirstLayer();

		topLayer = topLayer.getFirstLayer();

		// Auto update the ID numbers for the layers. Ofcourse, the first layer should be 0 etc.
		firstLayer.updateId(0);
	}
}