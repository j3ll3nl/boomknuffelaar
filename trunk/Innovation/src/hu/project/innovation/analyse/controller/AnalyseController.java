package hu.project.innovation.analyse.controller;

import hu.project.innovation.analyse.model.AnalyseService;
import hu.project.innovation.analyse.view.jframe.JFrameAnalyse;
import hu.project.innovation.configuration.model.ConfigurationService;
import hu.project.innovation.utils.Log;
import hu.project.innovation.utils.UiDialogs;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.File;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JFileChooser;

public class AnalyseController implements Observer, ActionListener, KeyListener {

	private JFrameAnalyse analyseJFrame = null;

	public AnalyseController() {
		Log.i(this, "constructor()");
	}

	public void initUi() {
		Log.i(this, "initUi()");

		if (analyseJFrame == null) {
			analyseJFrame = new JFrameAnalyse();

			analyseJFrame.jTextFieldProjectPath.addKeyListener(this);
			analyseJFrame.jTextFieldJarPath.addKeyListener(this);
			analyseJFrame.jTextFieldOutputPath.addKeyListener(this);
			analyseJFrame.jButtonProjectBrowse.addActionListener(this);
			analyseJFrame.jButtonJarpathBrowse.addActionListener(this);
			analyseJFrame.jButtonOutputBrowse.addActionListener(this);
			analyseJFrame.jButtonClose.addActionListener(this);
			analyseJFrame.jButtonStartAnalyse.addActionListener(this);
			analyseJFrame.jComboBoxOutputType.addActionListener(this);
			analyseJFrame.addKeyListener(this);
		}

		// Set the latest project/output path settings
		analyseJFrame.jTextFieldProjectPath.setText(ConfigurationService.getInstance().getProjectPath());
		analyseJFrame.jTextFieldOutputPath.setText(ConfigurationService.getInstance().getOutputPath());
		analyseJFrame.jComboBoxOutputType.setSelectedItem(ConfigurationService.getInstance().getOutputType());

		// Set the visibility of the jframe to true so the jframe is now visible
		UiDialogs.showOnScreen(0, analyseJFrame);

		analyseJFrame.setVisible(true);
	}

	private String browseForPath(String preferedpath) {
		if (preferedpath.trim().equals("")) {
			preferedpath = System.getProperty("user.dir");
		}

		File defaultPath = new File(preferedpath);

		// Create a file chooser
		JFileChooser fc = new JFileChooser(defaultPath);

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
		return preferedpath;
	}
	
	private File[] browseForJarpath(String preferedpath) {
		if (preferedpath.trim().equals("")) {
			preferedpath = System.getProperty("user.dir");
		}

		File defaultPath = new File(preferedpath);

		// Create a file chooser
		JFileChooser fc = new JFileChooser(defaultPath);

		fc.setFileSelectionMode(JFileChooser.FILES_ONLY);
		fc.setAcceptAllFileFilterUsed(false);
		fc.setMultiSelectionEnabled(true);

		// In response to a button click:
		int returnVal = fc.showOpenDialog(analyseJFrame);

		// The user did click on Open
		if (returnVal == JFileChooser.APPROVE_OPTION) {
			try {
				// Getting selected file from dialog
				File files[] = fc.getSelectedFiles();
				Log.i(this, "openConfiguration() - opening files: " + files);

				return files;
			} catch (Exception e) {
				Log.e(this, "openConfiguration() - exeption: " + e.getMessage());
			}
		}
		return null;
	}

	private void updateAnalyseButton(boolean running) {
		if (running) {
			analyseJFrame.jButtonStartAnalyse.setEnabled(false);
		} else {
			analyseJFrame.jButtonStartAnalyse.setEnabled(true);
		}

	}

	public void actionPerformed(ActionEvent action) {
		Log.i(this, "actionPerformed()");
		if (action.getSource() == analyseJFrame.jButtonProjectBrowse) {
			Log.i(this, "actionPerformed() - project browse");
			String path = browseForPath(analyseJFrame.jTextFieldProjectPath.getText());
			ConfigurationService.getInstance().setProjectPath(path);
			analyseJFrame.jTextFieldProjectPath.setText(path);
		} else if (action.getSource() == analyseJFrame.jButtonJarpathBrowse) {
			Log.i(this, "actionPerformed() - project browse");			
			File files[] = browseForJarpath(analyseJFrame.jTextFieldJarPath.getText());
			if(files != null){
				String path = "";
				for(int i = 0; i < files.length; i++){
					if(i != 0){
						path += ";";
					}
					path += files[i].getAbsolutePath();
				}
				ConfigurationService.getInstance().setJarPath(path);
				analyseJFrame.jTextFieldJarPath.setText(path);
			}
		} else if (action.getSource() == analyseJFrame.jButtonOutputBrowse) {
			Log.i(this, "actionPerformed() - output browse");
			String path = browseForPath(analyseJFrame.jTextFieldOutputPath.getText());
			ConfigurationService.getInstance().setOutputPath(path);
			analyseJFrame.jTextFieldOutputPath.setText(path);
		} else if (action.getSource() == analyseJFrame.jComboBoxOutputType) {
			Log.i(this, "actionPerformed() - output type");
			Object selectedItem = analyseJFrame.jComboBoxOutputType.getSelectedItem();
			if(selectedItem != null){
				String selected = analyseJFrame.jComboBoxOutputType.getSelectedItem().toString();
				ConfigurationService.getInstance().setOutputType(selected);
			}
		} else if (action.getSource() == analyseJFrame.jButtonClose) {
			Log.i(this, "actionPerformed() - close");
			analyseJFrame.dispose();
		} else if (action.getSource() == analyseJFrame.jButtonStartAnalyse) {
			Log.i(this, "actionPerformed() - start analyse");

			AnalyseService.getInstance().startAnalyse(this);
			updateAnalyseButton(true);

		}
	}

	public void keyPressed(KeyEvent arg0) {
		// Ignore
	}

	public void keyReleased(KeyEvent arg0) {
		if (arg0.getSource() == analyseJFrame.jTextFieldOutputPath) {
			String text = analyseJFrame.jTextFieldOutputPath.getText();
			ConfigurationService.getInstance().setOutputPath(text);
		} else if (arg0.getSource() == analyseJFrame.jTextFieldProjectPath) {
			String text = analyseJFrame.jTextFieldProjectPath.getText();
			ConfigurationService.getInstance().setProjectPath(text);
		}
		if (arg0.getKeyCode() == KeyEvent.VK_ESCAPE) {
			analyseJFrame.dispose();
		}
	}

	public void keyTyped(KeyEvent arg0) {
		// Ignore
	}

	public void update(Observable arg0, Object arg1) {
		UiDialogs.messageDialog(analyseJFrame, "PMD finished scanning the project", "Done!");
		updateAnalyseButton(false);
	}
}
