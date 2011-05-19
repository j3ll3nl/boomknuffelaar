package hu.project.innovation;

import hu.project.innovation.configuration.model.ConfigurationService;

public class Main {
	
	private Main(){};

	public static void main(String[] args) throws InterruptedException {
		new TestConfiguration();

		// Start the Main controller
		MainController gui = new MainController();

		// Init the Ui
		gui.initUi();
	}
}