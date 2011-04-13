package hu.project.innovation;

import hu.project.innovation.analyse.controller.AnalyseController;
import hu.project.innovation.configuration.controller.DefinitionController;

import java.util.ArrayList;

public class GuiController {
	
	private ArrayList<AbstractController> controllers;
	
	public GuiController() {
		
		this.controllers = new ArrayList<AbstractController>();
		
		this.controllers.add(new AnalyseController());
		this.controllers.add(new DefinitionController());

	}

}
