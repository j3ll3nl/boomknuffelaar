package hu.project.innovation;

public class Main {

	public static void main(String[] args) throws InterruptedException {
		new TestConfiguration();

		// Start the Main controller
		MainController gui = new MainController();

		// Init the Ui
		gui.initUi();
	}
}