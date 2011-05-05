package hu.project.innovation;

import hu.project.innovation.analyse.controller.AnalyseController;
import hu.project.innovation.configuration.controller.DefinitionController;
import hu.project.innovation.utils.Log;
import hu.project.innovation.utils.Ui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.InputStreamReader;

public class MainController implements ActionListener {
	private DefinitionController definitioncontroller = null;
	private AnalyseController analysecontroller = null;
	public ApplicationJFrame jframe;

	/**
	 * MainController constructor. This constructor will initialize a new Definition and Analyse controller.
	 */
	public MainController() {
		Log.i(this, "constructor()");

		definitioncontroller = new DefinitionController(this);
		analysecontroller = new AnalyseController();
	}

	/**
	 * Start the application with GUI by calling this method.
	 */
	public void initUi() {
		Log.i(this, "initUi()");

		// Create a new instance of the jframe
		jframe = new ApplicationJFrame();

		// Set actionlisteners for the menu
		jframe.jMenuItemNewArchitecture.addActionListener(this);
		jframe.jMenuItemOpenArchitecture.addActionListener(this);
		jframe.jMenuItemSaveArchitecture.addActionListener(this);
		jframe.jMenuItemStartAnalyse.addActionListener(this);
		jframe.jMenuItemAbout.addActionListener(this);

		// This method sets the definition jpanel in the jframe.
		jframe.setContentView(definitioncontroller.initUi());

		// Set the visibility of the jframe to true so the jframe is now visible
		Ui.showOnScreen(0, jframe);
		jframe.setVisible(true);
	}

	/**
	 * Start the application without GUI by calling this method.
	 */
	public void initCommand() {
		Log.i(this, "initCommand()");

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
			Log.i(this, "actionPerformed() - new architecture");
			definitioncontroller.newConfiguration();
		} else if (action.getSource() == jframe.jMenuItemOpenArchitecture) {
			Log.i(this, "actionPerformed() - open architecture");
			definitioncontroller.openConfiguration();
		} else if (action.getSource() == jframe.jMenuItemSaveArchitecture) {
			Log.i(this, "actionPerformed() - save architecture");
			definitioncontroller.saveConfiguration();
		} else if (action.getSource() == jframe.jMenuItemStartAnalyse) {
			Log.i(this, "actionPerformed() - start analyse");
			analysecontroller.initUi(jframe);
		} else if (action.getSource() == jframe.jMenuItemAbout) {
			Log.i(this, "actionPerformed() - about");
			Ui.messageDialog(jframe, "This application is made an project team on Hogeschool Utrecht.", "About");
		} else {
			Log.i(this, "actionPerformed(" + action + ") - unknown button event");
		}
	}

}