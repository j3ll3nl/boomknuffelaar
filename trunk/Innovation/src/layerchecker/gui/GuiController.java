package layerchecker.gui;

import java.util.ArrayList;

import layerchecker.controller.AbstractController;
import layerchecker.controller.AnalysisController;
import layerchecker.controller.DefinitionController;
import layerchecker.controller.RulesController;

public class GuiController {
	
	private ArrayList<AbstractController> controllers;
	
	public GuiController() {
		this.controllers.add(new AnalysisController());
		this.controllers.add(new DefinitionController());
		this.controllers.add(new RulesController());
	}

}
