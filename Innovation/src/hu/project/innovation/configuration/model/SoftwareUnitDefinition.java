package hu.project.innovation.configuration.model;

import hu.project.innovation.utils.XMLable;

import java.util.ArrayList;

public class SoftwareUnitDefinition implements XMLable {

	public static final String METHOD = "method", CLASS = "class", PACKAGE = "package";

	private String name;
	private String type;
	private ArrayList<SoftwareUnitDefinition> softwareUnits;
	private ArrayList<SoftwareUnitDefinition> exceptions;

	private Layer layer;

	public SoftwareUnitDefinition(String name, String type) {
		this.name = name;
		this.setType(type);
	}

	public SoftwareUnitDefinition() {

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

	public void setLayer(Layer layer) {
		this.layer = layer;
	}

	public void addException(SoftwareUnitDefinition softwareunit) {
		exceptions.add(softwareunit);
	}

	public ArrayList<SoftwareUnitDefinition> getExceptions() {
		return exceptions;
	}

	public int getNumberOfExceptions() {
		if (getExceptions() != null) {
			return getExceptions().size();
		} else {
			return 0;
		}

	}

}
