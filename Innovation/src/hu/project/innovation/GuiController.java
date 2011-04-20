package hu.project.innovation;

import hu.project.innovation.analyse.controller.AnalyseController;
import hu.project.innovation.configuration.controller.DefinitionController;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.InputStreamReader;

import javax.swing.JPanel;

public class GuiController implements ActionListener {
	private DefinitionController definitioncontroller = null;
	private AnalyseController analysecontroller = null;
	private ApplicationJFrame jframe;

	/**
	 * GuiController constructor. This constructor will initialize a new Definition and Analyse controller.
	 */
	public GuiController() {
		CustomLogger.i(getClass().getSimpleName(), "constructor()");
		definitioncontroller = new DefinitionController();
		analysecontroller = new AnalyseController();
	}

	/**
	 * Start the application with GUI by calling this method.
	 */
	public void init() {
		CustomLogger.i(getClass().getSimpleName(), "init()");
		JPanel definitionjpanel = definitioncontroller.initGUI();

		jframe = new ApplicationJFrame(this);
		jframe.initGUI();
		jframe.setContentView(definitionjpanel);
		jframe.setVisible(true);
	}

	/**
	 * Start the application without GUI by calling this method.
	 */
	public void initCommandReader() {
		CustomLogger.i(getClass().getSimpleName(), "initCommandReader()");
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
			System.err.println("GuiController.initCommandReader() - Exception: " + e);
		}
	}

	@Override
	public void actionPerformed(ActionEvent action) {
		//This class will
		if (action.getSource() == jframe.jMenuItemNewArchitecture) {
			CustomLogger.i(getClass().getSimpleName(), "actionPerformed() - new architecture");
			definitioncontroller.newDefinition();
		} else if (action.getSource() == jframe.jMenuItemOpenArchitecture) {
			CustomLogger.i(getClass().getSimpleName(), "actionPerformed() - open architecture");
			definitioncontroller.openDefintion();
		} else if (action.getSource() == jframe.jMenuItemSaveArchitecture) {
			CustomLogger.i(getClass().getSimpleName(), "actionPerformed() - save architecture");
			definitioncontroller.saveDefintion();
		} else if (action.getSource() == jframe.jMenuItemStartAnalyse) {
			CustomLogger.i(getClass().getSimpleName(), "actionPerformed() - start analyse");
			analysecontroller.startAnalyse();
		} else {
			CustomLogger.i(getClass().getSimpleName(), "actionPerformed(" + action + ")");
		}
	}
}
