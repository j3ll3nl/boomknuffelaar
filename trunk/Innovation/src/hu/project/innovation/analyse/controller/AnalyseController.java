package hu.project.innovation.analyse.controller;

import hu.project.innovation.analyse.model.AnalyseService;
import hu.project.innovation.analyse.view.JFrameAnalyse;
import hu.project.innovation.configuration.model.ConfigurationService;
import hu.project.innovation.utils.Log;
import hu.project.innovation.utils.Ui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.File;

import javax.swing.JFileChooser;

public class AnalyseController implements ActionListener, KeyListener {

	private AnalyseService analyseService;
	private JFrameAnalyse analyseJFrame = null;

	public AnalyseController() {
		Log.i(this, "constructor()");

		analyseService = AnalyseService.getInstance();

	}

	public void initUi() {
		Log.i(this, "initUi()");

		if (analyseJFrame == null) {
			analyseJFrame = new JFrameAnalyse();

			analyseJFrame.jTextFieldProjectPath.addKeyListener(this);
			analyseJFrame.jTextFieldOutputPath.addKeyListener(this);
			analyseJFrame.jButtonProjectBrowse.addActionListener(this);
			analyseJFrame.jButtonOutputBrowse.addActionListener(this);
			analyseJFrame.jButtonClose.addActionListener(this);
			analyseJFrame.jButtonStartAnalyse.addActionListener(this);
			analyseJFrame.jComboBoxOutputType.addActionListener(this);
		}

		// Set the latest project/output path settings
		analyseJFrame.jTextFieldProjectPath.setText(ConfigurationService.getInstance().getProjectPath());
		analyseJFrame.jTextFieldOutputPath.setText(ConfigurationService.getInstance().getOutputPath());

		// Try to load the pom.xml file
		updateProjectName();

		//Enable or disable the analyse button when the scan is already running
		updateAnalyseButton();

		// Set the visibility of the jframe to true so the jframe is now visible
		Ui.showOnScreen(0, analyseJFrame);
		
		analyseJFrame.setVisible(true);
	}

	private void updateProjectName() {
		String projectPath = analyseJFrame.jTextFieldProjectPath.getText();
		String pomPath = projectPath + "/pom.xml";

		File pom = new File(pomPath);

		if (pom.exists()) {
			// TODO: Read the POM.xml for project name <name>my-app</name>
			analyseJFrame.jLabelProjectName.setText("- TODO: Read POM.xml for project name -");
		} else {
			analyseJFrame.jLabelProjectName.setText("Unknown");
		}

	}

	private String browseForPath() {
		// Create a file chooser

		JFileChooser fc = new JFileChooser();

		fc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
		fc.setAcceptAllFileFilterUsed(false);

		// In response to a button click:
		int returnVal = fc.showOpenDialog(analyseJFrame);

		// The user did click on Open
		if (returnVal == JFileChooser.APPROVE_OPTION) {
			try {
				// Getting selected file from dialog
				File file = fc.getSelectedFile();
				Log.i(this, "openConfiguration() - opening file: " + file.getAbsolutePath());

				return file.getAbsolutePath();
			} catch (Exception e) {
				Log.e(this, "openConfiguration() - exeption: " + e.getMessage());
			}
		}
		return null;
	}

	private void updateAnalyseButton() {
		if (analyseService.isRunning()) {
			analyseJFrame.jButtonStartAnalyse.setEnabled(false);
		} else {
			analyseJFrame.jButtonStartAnalyse.setEnabled(true);
		}
	}

	public void actionPerformed(ActionEvent action) {
		Log.i(this, "actionPerformed()");
		if (action.getSource() == analyseJFrame.jButtonProjectBrowse) {
			Log.i(this, "actionPerformed() - project browse");
			String path = browseForPath();
			ConfigurationService.getInstance().setProjectPath(path);
			analyseJFrame.jTextFieldProjectPath.setText(path);
			updateProjectName();
		} else if (action.getSource() == analyseJFrame.jButtonOutputBrowse) {
			Log.i(this, "actionPerformed() - output browse");
			String path = browseForPath();
			ConfigurationService.getInstance().setOutputPath(path);
			analyseJFrame.jTextFieldOutputPath.setText(path);
		} else if (action.getSource() == analyseJFrame.jComboBoxOutputType) {
			Log.i(this, "actionPerformed() - output type");
			String selected = analyseJFrame.jComboBoxOutputType.getSelectedItem().toString();
			ConfigurationService.getInstance().setOutputType(selected);
		} else if (action.getSource() == analyseJFrame.jButtonClose) {
			Log.i(this, "actionPerformed() - close");
			analyseJFrame.dispose();
		} else if (action.getSource() == analyseJFrame.jButtonStartAnalyse) {
			Log.i(this, "actionPerformed() - start analyse");

			try {
				analyseService.startAnalyse();
			} catch (Exception e) {
				Log.e(this, "actionPerformed() - exception " + e.getMessage());
				Ui.errorDialog(analyseJFrame, e.getMessage(), "Error");
				analyseService.setRunning(false);
			}

			updateAnalyseButton();
		}
	}

	public void keyPressed(KeyEvent arg0) {
		// Ignore
		analyseJFrame.jTextFieldOutputPath.addKeyListener(this);
		analyseJFrame.jTextFieldProjectPath.addKeyListener(this);
	}

	public void keyReleased(KeyEvent arg0) {
		if (arg0.getSource() == analyseJFrame.jTextFieldOutputPath) {
			String text = analyseJFrame.jTextFieldOutputPath.getText();
			ConfigurationService.getInstance().setOutputPath(text);
		} else if (arg0.getSource() == analyseJFrame.jTextFieldProjectPath) {
			String text = analyseJFrame.jTextFieldProjectPath.getText();
			ConfigurationService.getInstance().setProjectPath(text);
			updateProjectName();
		}
	}

	public void keyTyped(KeyEvent arg0) {
		// Ignore
	}
}
