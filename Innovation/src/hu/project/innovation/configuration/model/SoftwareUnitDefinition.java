package hu.project.innovation.configuration.model;

import hu.project.innovation.utils.Log;
import hu.project.innovation.utils.XMLable;

import java.util.ArrayList;

public class SoftwareUnitDefinition implements XMLable {

	public static final String METHOD = "method", CLASS = "class", PACKAGE = "package";

	private String softwareUnitName;
	private String softwareUnitType;
	private ArrayList<SoftwareUnitDefinition> softwareUnits = new ArrayList<SoftwareUnitDefinition>();
	private ArrayList<SoftwareUnitDefinition> exceptions = new ArrayList<SoftwareUnitDefinition>();

	private Layer layer;

	public SoftwareUnitDefinition() {
		Log.i(this, "constructor()");
	}

	public SoftwareUnitDefinition(String name, String type) {
		Log.i(this, "constructor(" + name + ", " + type + ")");

		setName(name);
		setType(type);
	}

	public void setName(String name) {
		this.softwareUnitName = name;
	}

	public String getName() {
		return softwareUnitName;
	}

	public void setType(String type) {
		this.softwareUnitType = type;
	}

	public String getType() {
		return softwareUnitType;
	}

	public void setLayer(Layer layer) {
		this.layer = layer;
	}

	public Layer getLayer() {
		return this.layer;
	}

	public void addSoftwareUnitDefinition(SoftwareUnitDefinition softwareunit) {
		softwareUnits.add(softwareunit);
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

	public void removeAllExceptions() {
		exceptions.clear();
	}

	public String toXML() {
		String xml = "\t\t\t<softwareUnit>\n";
		xml += "\t\t\t\t<name>" + this.softwareUnitName + "</name>\n";
		xml += "\t\t\t\t<type>" + this.softwareUnitType + "</type>\n";
		if (exceptions != null) {
			for (SoftwareUnitDefinition unit : exceptions) {
				xml += "\t\t\t\t<exception>\n";
				xml += "\t\t\t\t\t<name>" + unit.getName() + "</name>\n";
				xml += "\t\t\t\t\t<type>" + unit.getType() + "</type>\n";
				xml += "\t\t\t\t</exception>\n";
			}
		}
		xml += "\t\t\t</softwareUnit>\n";
		return xml;
	}

	public String toString() {
		return getName();
	}

	public boolean contains(String softwareUnitName) {
		// Log.i(this, this.getName()+" vs "+softwareUnitName);
		// The called name equals the name of this unit
		if (this.getName().equals(softwareUnitName) || this.getName().equals(softwareUnitName + ".*")) {
			return true;
		}
		// This is a package
		else if (this.getName().endsWith(".*") && softwareUnitName.startsWith(this.getName().substring(0, this.getName().length() - 1))) {
			// Exceptions
			for (SoftwareUnitDefinition exception : this.exceptions) {
				if (exception.contains(softwareUnitName)) {
					return false;
				}
			}
			return true;
		}
		return false;
	}

}
