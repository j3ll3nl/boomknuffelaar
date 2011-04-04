package layerchecker.definition;

import java.util.ArrayList;


public class ArchitectureDefinition {

	private String name;
	private String description;
	private Layer topLayer;
	private ArrayList<Layer> layers;
	
	public ArchitectureDefinition(String name) {
		this.name = name;
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
			
		}
		
		return topLayer;
	}
	
}