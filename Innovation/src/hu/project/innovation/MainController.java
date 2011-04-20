package hu.project.innovation;

import hu.project.innovation.analyse.controller.AnalyseController;
import hu.project.innovation.configuration.controller.DefinitionController;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.InputStreamReader;

public class MainController implements ActionListener {
	private DefinitionController definitioncontroller = null;
	private AnalyseController analysecontroller = null;
	private ApplicationJFrame jframe;

	/**
	 * MainController constructor. This constructor will initialize a new Definition and Analyse controller.
	 */
	public MainController() {
		Log.i(getClass().getSimpleName(), "constructor()");

		definitioncontroller = new DefinitionController();
		analysecontroller = new AnalyseController();
	}

	/**
	 * Start the application with GUI by calling this method.
	 */
	public void initUi() {
		Log.i(getClass().getSimpleName(), "init()");

		jframe = new ApplicationJFrame(this);		
		// Setcontentview is called after initGui because the application needs to build up first.
		jframe.setContentView(definitioncontroller.initUi());
		// Set the visibility of the jframe to true
		jframe.setVisible(true);
	}

	/**
	 * Start the application without GUI by calling this method.
	 */
	public void initCommand() {
		Log.i(getClass().getSimpleName(), "initCommand()");

		BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
		String helpCommando = "Available commands are:\n" + "Help\tRequest help\n" + "Exit\tExit the application";
		String lineseperator = "----------------------------------------------------------------------------------------";
		String cmdLine = "Enter command: ";
		try {
			System.out.println(helpCommando);
			System.out.println(lineseperator);
			System.out.print(cmdLine);

			String line = "";
			while ((line = in.readLine()) != null) {
				if (line.startsWith("help")) {
					System.out.println(helpCommando);
				} else if (line.startsWith("exit")) {
					System.exit(0);
				} else {
					System.out.println("Sorry, unknown commando");
				}
				System.out.println(lineseperator);
				System.out.print(cmdLine);
			}
		} catch (Exception e) {
			Log.e(getClass().getSimpleName(), "initCommand() - Exception: " + e);
		}
	}

	@Override
	public void actionPerformed(ActionEvent action) {
		if (action.getSource() == jframe.jMenuItemNewArchitecture) {
			Log.i(getClass().getSimpleName(), "actionPerformed() - new architecture");
			definitioncontroller.newConfiguration();
		} else if (action.getSource() == jframe.jMenuItemOpenArchitecture) {
			Log.i(getClass().getSimpleName(), "actionPerformed() - open architecture");
			definitioncontroller.openConfiguration();
		} else if (action.getSource() == jframe.jMenuItemSaveArchitecture) {
			Log.i(getClass().getSimpleName(), "actionPerformed() - save architecture");
			definitioncontroller.saveConfiguration();
		} else if (action.getSource() == jframe.jMenuItemStartAnalyse) {
			Log.i(getClass().getSimpleName(), "actionPerformed() - start analyse");
			analysecontroller.initUi();
		} else {
			Log.i(getClass().getSimpleName(), "actionPerformed(" + action + ")");
		}
	}
}
