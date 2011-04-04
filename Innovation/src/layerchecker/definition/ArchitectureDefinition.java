package layerchecker.definition;

import java.util.ArrayList;

import main.Logger;


public class ArchitectureDefinition {

	private String name;
	private String description;
	private Layer topLayer;
	private ArrayList<Layer> layers;
	
	public ArchitectureDefinition(String name) {
		Logger.getInstance().log(this.getClass().getSimpleName());
		this.name = name;
		this.description = "unkown";
		
		this.layers = new ArrayList<Layer>();
	}
	
	public ArchitectureDefinition(String name, String desc) {
		this(name);
		description = desc;
	}
	
	public void setDescription(String desc){
		description = desc;
	}
	
	public Layer getTopLayer() {
		return topLayer;
	}
	
	public void addLayer(Layer layer) {
		layers.add(layer);
	}
	
	public Layer getLayer(String name) {
		
		for(Layer layer : layers) {
			if(layer.getName() == name) {
				return layer;
			}
		}
		
		return null;
		
	}
	
	public ArrayList<Layer> getAllLayers() {
		return layers;
	}
	
	public String toString() {
		
		String s = "ArchitectureDefinition:\n- " + name + " - " + description + "\n\nLayers:\n";
		for(Layer layer : layers) {
			s = s + layer.toString() + "\n";
		}
		
		return s;
	}
	
}