package hu.project.innovation.configuration.model;

import hu.project.innovation.Log;
import hu.project.innovation.XMLable;

import java.util.ArrayList;

public class ArchitectureDefinition implements XMLable {

	private String name;
	private String description;
	private Layer topLayer;
	private ArrayList<Layer> layers = new ArrayList<Layer>();
	private ArrayList<SoftwareUnitDefinition> softwareUnits;

	public ArchitectureDefinition() {
		Log.i(this, "ArchitectureDefinition()");
		this.softwareUnits = new ArrayList<SoftwareUnitDefinition>();
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

	public boolean addSoftwareUnit(SoftwareUnitDefinition unit) {
		return softwareUnits.add(unit);
	}

	public SoftwareUnitDefinition getSoftwareUnit(String name) {

		for (SoftwareUnitDefinition sud : softwareUnits) {
			if (sud.getName().equals(name)) {
				return sud;
			}
		}
		return null;
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

	public String getLayerNameBySoftwareUnitName(String name) {
		SoftwareUnitDefinition unit = this.getSoftwareUnit(name);
		if (unit != null) {
			return unit.getLayer().getName();
		}
		return "UNKNOWN";
	}

	public ArrayList<Layer> getAllLayers() {
		return layers;
	}

	@Override
	public String toXML() {
		String xml = "<architecture>\n";
		for (Layer layer : layers) {
			xml += layer.toXML();
		}
		xml += "</architecture>\n";

		return xml;
	}

	public ArrayList<SoftwareUnitDefinition> getAllComponents() {
		return softwareUnits;
	}

}