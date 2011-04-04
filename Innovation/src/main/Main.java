package main;

import layerchecker.configuration.ConfigurationService;
import layerchecker.gui.GuiController;

public class Main {
	
	public static void main(String[] args) {
		GuiController gui = new GuiController();
		
		ConfigurationService.getInstance();
	}
}