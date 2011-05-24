package hu.project.innovation.configuration.model;

import hu.project.innovation.utils.DefaultMessages;
import hu.project.innovation.utils.Log;
import hu.project.innovation.utils.XMLable;

import java.util.ArrayList;

public class ArchitectureDefinition implements XMLable {

	private String architectureName;
	private String architectureDescription;
	private Layer topLayer;

	public ArchitectureDefinition() {
		Log.i(this, "constructor()");
	}

	public ArchitectureDefinition(String architectureName, String architectureDescription) throws Exception {
		Log.i(this, "constructor(" + architectureName + ", " + architectureDescription + ")");
		setName(architectureName);
		setDescription(architectureDescription);
	}

	public void setName(String architectureName) throws Exception {
		if (architectureName.trim().equals("")) {
			throw new Exception(DefaultMessages.MSG_NOARCHITECTURENAME);
		}
		this.architectureName = architectureName;
	}

	public String getName() {
		return architectureName;
	}

	public void setDescription(String desc) {
		architectureDescription = desc;
	}

	public String getDescription() {
		return architectureDescription;
	}

	public Layer getTopLayer() {
		return topLayer;
	}

	public void addLayer(Layer layer) {
		Log.i(this, "addLayer(" + layer + ")");
		if (getTopLayer() == null) {
			topLayer = layer;
		} else {
			getTopLayer().addChildLayer(layer);
		}
	}

	public void removeLayer(Layer layer) {
		if (getTopLayer() != null) {
			if (getTopLayer() == layer) {
				topLayer = layer.getChildLayer();
				topLayer.setParentLayer(null);
			} else {
				getTopLayer().removeLayer(layer);
			}
		}
	}

	public Layer getLayer(int id) {
		if (getTopLayer() != null) {
			return getTopLayer().getLayer(id);
		}
		return null;
	}

	public Layer getLayer(String name) {
		if (getTopLayer() != null) {
			return getTopLayer().getLayer(name);
		}
		return null;
	}

	/**
	 * Find the layer name by a software unit name. This function will iterate over the layers. If a layer has a software unit with the specified name, it will return the layer name.
	 * 
	 * @param architectureName
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

	/**
	 * This method will reset the topLayer with the new FirstLayer. Method is NEEDED for correct move up/down sequence
	 */
	public void autoUpdateLayerSequence() {
		Layer firstLayer = topLayer.getFirstLayer();
	
		topLayer = topLayer.getFirstLayer();
	
		// Auto update the ID numbers for the layers. Ofcourse, the first layer should be 0 etc.
		firstLayer.updateId(0);
	}

	public String toXML() {
		String xml = "\t<architecture name=\"" + getName() + "\">\n";
		if (topLayer != null) {
			xml += topLayer.toXML();
		}
		xml += "\t</architecture>\n";

		return xml;
	}
	public String toString(){
		return getName();
	}
}