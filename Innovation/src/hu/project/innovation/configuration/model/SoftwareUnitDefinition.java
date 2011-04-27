package hu.project.innovation.configuration.model;

import hu.project.innovation.utils.Log;
import hu.project.innovation.utils.XMLable;

import java.util.ArrayList;

public class SoftwareUnitDefinition implements XMLable {

	public static final String METHOD = "method", CLASS = "class", PACKAGE = "package";

	private String name;
	private String type;
	private ArrayList<SoftwareUnitDefinition> softwareUnits;

	private Layer layer;

	public SoftwareUnitDefinition() {

	}

	public SoftwareUnitDefinition(Layer layer) {
		Log.i(this, "SoftwareUnitDefinition()");
		this.layer = layer;
	}

	public SoftwareUnitDefinition(String name, String type, Layer layer) {
		this(layer);
		this.name = name;
		this.setType(type);
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void addSoftwareUnitDefinition(SoftwareUnitDefinition unit) {
		softwareUnits.add(unit);
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getType() {
		return type;
	}

	@Override
	public String toXML() {
		String xml = "\t\t<softwareUnit>\n";
		xml += "\t\t\t<name>" + this.name + "</name>\n";
		xml += "\t\t\t<type>" + this.type + "</type>\n";
		xml += "\t\t</softwareUnit>\n";
		return xml;
	}

	public Layer getLayer() {
		return this.layer;
	}

}
