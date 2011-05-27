package hu.project.innovation;

import hu.project.innovation.configuration.controller.xml.ImportController;

import java.io.File;

public class MainUI {

	public static void main(String[] args) throws Exception {
		ImportController importc = new ImportController();
		importc.importXML(new File("./defaultConfiguratie.xml"));

		// Start the Application controller
		ApplicationController gui = new ApplicationController();

		// Init the Ui
		gui.initUi();
	}
}