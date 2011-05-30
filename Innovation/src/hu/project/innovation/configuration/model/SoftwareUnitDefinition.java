package hu.project.innovation.configuration.model;

import hu.project.innovation.utils.Log;
import hu.project.innovation.utils.UniqueID;

import java.util.ArrayList;

public class SoftwareUnitDefinition {

	public static final String METHOD = "method", CLASS = "class", PACKAGE = "package";

	private long id;
	private String softwareUnitName;
	private String softwareUnitType;
	private ArrayList<SoftwareUnitDefinition> exceptions = new ArrayList<SoftwareUnitDefinition>();

	private Layer layer;

	public SoftwareUnitDefinition(String name, String type) throws Exception {
		Log.i(this, "constructor(" + name + ", " + type + ")");

		if(name.isEmpty()){
			throw new Exception("De naam de van de exception is niet ingevuld.");
		}
		setName(name);
		setType(type);
		setId();
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

	public long newException(String name, String type) throws Exception {
		SoftwareUnitDefinition exception = new SoftwareUnitDefinition(name, type);
		exceptions.add(exception);

		return exception.getId();
	}

	public ArrayList<Long> getExceptions() {
		ArrayList<Long> arrayList = new ArrayList<Long>();
		for (SoftwareUnitDefinition exception : exceptions) {
			arrayList.add(exception.getId());
		}
		return arrayList;
	}

	public SoftwareUnitDefinition getExeption(long id) {
		for (SoftwareUnitDefinition exception : exceptions) {
			if (exception.getId() == id) {
				return exception;
			}
		}
		return null;
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

	public void setId() {
		id = UniqueID.get();
	}

	public long getId() {
		return id;
	}
}
