package layerchecker.definition;

import java.util.ArrayList;

import main.Logger;

public class DefinitionService {

	private ArchitectureDefinition architecture;
	
	public DefinitionService() {
		Logger.getInstance().log(this.getClass().getSimpleName());
	}
	
	public void newArchitectureDefinition(String name, String desc) {
		architecture = new ArchitectureDefinition(name, desc);
	}
	
	public void openArchitectureDefinition() {
		
	}
	
	public void newLayer(String name, String desc) {
		Layer layer = new Layer(name);
		layer.setDescription(desc);
		architecture.addLayer(layer);
	}
	
	public void newSoftwareUnit(String layerName, String name, String type) {
		SoftwareUnitDefinition sud = new SoftwareUnitDefinition(name, type);
		architecture.getLayer(layerName).addSoftwareUnit(sud);
	}
	
	public void addException(String layerName, String suName, String ruleName, boolean permission, String toLayer) {
		
		if(suName == null) {
			Layer layer = architecture.getLayer(toLayer);
			architecture.getLayer(layerName).addRuleException(ruleName, permission, layer);
		} else {
			architecture.getLayer(layerName).getSoftwareUnit().addRuleException(ruleName, permission);
		}
		
	}
	
	public ArrayList<Layer> getAllLayers() {
		return architecture.getAllLayers();
	}
	
	public void saveArchitectureDefinition() {
		
	}
	
}
