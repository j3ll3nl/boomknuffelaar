package hu.project.innovation.configuration.model;

import hu.project.innovation.utils.DefaultMessages;
import hu.project.innovation.utils.Log;

import java.util.ArrayList;

public class ArchitectureDefinition {

	private String architectureName;
	private String architectureDescription;
	private Layer topLayer;

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

	public int newLayer(String name, String description) throws Exception {
		Layer layer = new Layer(name, description);

		// Add the layer to the architecture definition
		if (getTopLayer() == null) {
			topLayer = layer;
		} else {
			getTopLayer().addChildLayer(layer);
		}

		autoUpdateLayerSequence();

		return layer.getId();
	}

	public void removeLayer(int layer_id) {
		Layer layer = getLayer(layer_id);
		if (getTopLayer() != null) {
			if (getTopLayer() == layer) {
				topLayer = layer.getChildLayer();
				if (topLayer != null) {
					topLayer.setParentLayer(null);
				}
			} else {
				getTopLayer().removeLayer(layer);
			}
		}
		autoUpdateLayerSequence();
	}

	public Layer getLayer(int id) {
		if (getTopLayer() != null) {
			return getTopLayer().getLayer(id);
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

	public ArrayList<Integer> getLayers() {
		Log.i(this, "getLayers()");
		ArrayList<Integer> layers = new ArrayList<Integer>();

		if (topLayer != null) {
			Layer layer = topLayer;
			while (true) {
				if (layer == null) {
					break;
				} else {
					layers.add(layer.getId());
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
		if (topLayer != null) {
			Layer firstLayer = topLayer.getFirstLayer();

			topLayer = topLayer.getFirstLayer();

			// Auto update the ID numbers for the layers. Ofcourse, the first layer should be 0 etc.
			firstLayer.updateId(0);

		}
	}

	public String toString() {
		return getName();
	}
}