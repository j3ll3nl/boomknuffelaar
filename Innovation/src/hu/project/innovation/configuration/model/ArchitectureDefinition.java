package hu.project.innovation.configuration.model;

import hu.project.innovation.utils.Log;
import hu.project.innovation.utils.XMLable;

import java.util.ArrayList;

public class ArchitectureDefinition implements XMLable {

	private String name;
	private String description;
	private Layer topLayer;
	private ArrayList<Layer> layers = new ArrayList<Layer>();

	public ArchitectureDefinition() {
		Log.i(this, "ArchitectureDefinition()");
		this.layers = new ArrayList<Layer>();
	}

	public ArchitectureDefinition(String name) {
		this();
		this.name = name;
		this.description = "unknown";
	}

	public ArchitectureDefinition(String name, String desc) {
		this(name);
		this.description = desc;
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

	public boolean addLayer(Layer layer) {
		return layers.add(layer);
	}

	public Layer getLayer(int id) {

		for (Layer layer : layers) {
			if (layer.getId() == id) {
				return layer;
			}
		}

		return null;
	}

	public boolean removeLayer(Layer layer) {
		layers.remove(layer);
		return true;
	}

	public Layer getLayer(String name) {
		for (Layer layer : layers) {
			if (layer.getName() == name) {
				return layer;
			}
		}
		return null;

	}

	/**
	 * Find the layer name by a software unit name. This function will iterate over the layers. If a layer has a software unit with the specified name, it will return the layer name.
	 * 
	 * @param name
	 * @return A string containing the name of the layer with the specified software unit name
	 */
	public String getLayerNameBySoftwareUnitName(String name) {
		for (Layer layer : layers) {
			if (layer.getSoftwareUnit(name) != null) {
				return layer.getName();
			}
		}
		return "UNKNOWN";
	}

	public ArrayList<Layer> getAllLayers() {
		return layers;
	}

	@Override
	public String toXML() {
		String xml = "<?xml version=\"1.0\" encoding=\"utf-8\"?>\n<architecture>\n";
		for (Layer layer : layers) {
			xml += layer.toXML();
		}
		xml += "</architecture>\n";

		return xml;
	}

	public ArrayList<SoftwareUnitDefinition> getAllComponents() {
		ArrayList<SoftwareUnitDefinition> sudList = new ArrayList<SoftwareUnitDefinition>();
		for (Layer layer : layers) {
			sudList.addAll(layer.getAllSoftwareUnitDefinitions());
		}
		return sudList;
	}
}