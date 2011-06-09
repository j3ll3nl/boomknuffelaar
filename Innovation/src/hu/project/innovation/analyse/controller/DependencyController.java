package hu.project.innovation.analyse.controller;

import hu.project.innovation.analyse.view.jframe.JFrameDependencies;
import hu.project.innovation.configuration.model.ConfigurationService;
import hu.project.innovation.configuration.model.DependencyService;
import hu.project.innovation.configuration.model.dependencies.DepSoftwareComponent;
import hu.project.innovation.configuration.model.dependencies.DependencyParseHandler;
import hu.project.innovation.utils.Log;
import hu.project.innovation.utils.UiDialogs;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.swing.JFileChooser;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.SAXException;

public class DependencyController implements ActionListener {
	private DependencyService dependencyService;
	private JFrameDependencies dependenciesJFrame = null;

	/** format for version number */
	// private static String _VERSION = "[0-9]{1,2}(.[0-9])*?";

	public DependencyController() {
		Log.i(this, "constructor()");

		dependencyService = new DependencyService();
	}

	/**
	 * start de gui
	 */
	public void initUI() {
		Log.i(this, "initUI");

		if (dependenciesJFrame == null) {
			dependenciesJFrame = new JFrameDependencies();

			dependenciesJFrame.jButtonDependenciesBrowse.addActionListener(this);
			dependenciesJFrame.jButtonPomBrowse.addActionListener(this);
			dependenciesJFrame.jButtonClear.addActionListener(this);
			dependenciesJFrame.jButtonCheckDeps.addActionListener(this);
		}

		dependenciesJFrame.jTextFieldDependencies.setText(ConfigurationService.getInstance().getProjectPath());

		UiDialogs.showOnScreen(0, dependenciesJFrame);
		dependenciesJFrame.setVisible(true);
	}

	public void loadTables() throws Exception {
		Log.i(this, "loadTables()");

		Log.i(this, "loadTables() - loading undenfined");
		JTable table1 = dependenciesJFrame.jTableFoundComponents;
		DefaultTableModel table1dtm = (DefaultTableModel) table1.getModel();

		// Parse the (jar) components from the classpath string
		ArrayList<String> extDependencies = getExtDependensies(System.getProperty("java.class.path"));
		if (extDependencies != null) {
			int counter = 0;
			for (String dependency : extDependencies) {
				// Kijken of de dependency een - heeft, indien het geval het stuk van de string ervoor pakken (tijdelijke oplossing)

				if (!dependencyService.searchDepSoftwareComponent((dependency.contains("-")) ? dependency.split("-")[0] : dependency)) {
					// check if the dependency is added in the project object model (pom.xml)
					boolean tmp = false;
					// if an dependency ends with .jar, remove ".jar" and add it to the table
					Object rowdata[] = { counter++, ((tmp = dependency.endsWith(".jar")) ? dependency.substring(0, dependency.length() - 4) : dependency), "?", (tmp) ? "Jar" : "not supported" };
					table1dtm.addRow(rowdata);
				}
			}
		}

		Log.i(this, "loadTables() - loading pom");
		JTable table2 = dependenciesJFrame.jTableDepsPom;
		DefaultTableModel table2dtm = (DefaultTableModel) table2.getModel();
		DepSoftwareComponent[] pomDSComponents;

		// add pom dependencies to the table
		if ((pomDSComponents = dependencyService.getDependencies()) != null) {
			int counter = 0;
			for (DepSoftwareComponent pomDsc : pomDSComponents) {
				Object rowdata[] = { counter++, pomDsc.getArtifactId(), pomDsc.getVersion(), pomDsc.getType() };
				table2dtm.addRow(rowdata);
			}
		}

		Log.i(this, "loadTables() - loading dependencies.xml");
		JTable table3 = dependenciesJFrame.jTableAllowedDeps;
		DefaultTableModel table3dtm = (DefaultTableModel) table3.getModel();
		DepSoftwareComponent[] allowedDSComponents;

		// add allowed components to the table
		if ((allowedDSComponents = dependencyService.getAllowedDependencies()) != null) {
			int counter = 0;
			for (DepSoftwareComponent allowedDsc : allowedDSComponents) {
				Object rowdata[] = { counter++, allowedDsc.getArtifactId(), allowedDsc.getVersion(), allowedDsc.getType() };
				table3dtm.addRow(rowdata);
			}
		}

	}

	private String browseForPath(String preferedpath) {
		Log.i(this, "browseForPath(" + preferedpath + ")");

		if (preferedpath.trim().equals("")) {
			preferedpath = System.getProperty("user.dir");
		}

		File defaultPath = new File(preferedpath);

		// Create a file chooser
		JFileChooser fc = new JFileChooser(defaultPath);

		fc.setFileSelectionMode(JFileChooser.FILES_ONLY);
		fc.setAcceptAllFileFilterUsed(false);

		// In response to a button click:
		int returnVal = fc.showOpenDialog(dependenciesJFrame);

		// The user did click on Open
		if (returnVal == JFileChooser.APPROVE_OPTION) {
			try {
				// Getting selected file from dialog
				File file = fc.getSelectedFile();
				Log.i(this, "browseForPath() - opening file: " + file.getAbsolutePath());

				return file.getAbsolutePath();
			} catch (Exception e) {
				Log.e(this, "browseForPath() - exeption: " + e.getMessage());
			}
		}
		return preferedpath;
	}

	private boolean isVersionValidate(String terms, String _version) {
		Log.i(this, "isVersionValidate(" + terms + ", " + _version + ")");

		// Valideer de versie nummer aan de hand van de regular expression
		return java.util.regex.Pattern.matches(getRegExpression(terms), _version);
	}

	/**
	 * 
	 * @param version
	 * @return
	 */
	private String getRegExpression(String version) {
		Log.i(this, "getRegExcpression(" + version + ")");

		// converteer de versie nummer naar een reguliere expressie
		String _temp = version;
		if (version.contains(",")) {
			if (_temp.contains(",")) {
				_temp = _temp.contains("x") ? ("(" + _temp.replace(",", ")|(") + ")").replace("x", "([0-9]{1,2})") : "(" + _temp.replace(",", ")|(") + ")";
			}
		} else
			_temp = (!version.contains("x") ? version : version.replace("x", "([0-9]{1,2})"));
		return _temp;
	}

	/**
	 * 
	 * @param classPath
	 * @return the unit names (jars)
	 */
	private ArrayList<String> getExtDependensies(String classPath) {
		Log.i(this, "getExtDependensies(" + classPath + ")");

		ArrayList<String> unitNames = new ArrayList<String>();
		for (String path : classPath.split(":")) {
			String[] unitName = path.split("/");
			String unit = unitName[(unitName.length - 1)];
			if (unit.contains("."))
				unitNames.add(unit);
		}
		return unitNames;
	}

	/**
	 * 
	 * @param pomFile
	 * @param isAllowedByDefault
	 */
	private void parseXML(File pomFile, final boolean isAllowedByDefault) {
		Log.i(this, "parseXML(" + pomFile + ", " + isAllowedByDefault + ")");
		try {
			SAXParser saxParser = SAXParserFactory.newInstance().newSAXParser();
			try {
				saxParser.parse(pomFile, new DependencyParseHandler(dependencyService, isAllowedByDefault));
			} catch (IOException e) {
				e.printStackTrace();
			}
		} catch (ParserConfigurationException e) {
			e.printStackTrace();
		} catch (SAXException e) {
			e.printStackTrace();
		}
	}

	private void check() {
		Log.i(this, "check()");

		try {
			addAllowedDependencies();
			addDependencies();

			loadTables();

			// Kijken of de dependency in de POM voorkomt (en in dat geval dus is allowed) in mydependencies.xml
			if (dependencyService.getDependencies() != null) {
				if (dependencyService.getAllowedDependencies() != null) {
					for (DepSoftwareComponent _component : dependencyService.getDependencies()) {
						if (dependencyService.searchAllowedDepSoftwareComponent(_component.getArtifactId())) {
							if (!isVersionValidate(dependencyService.getAllowedDependency(_component.getArtifactId()).getVersion(), _component.getVersion())) {
								dependenciesJFrame.jTextPaneDepLog.setText(dependenciesJFrame.jTextPaneDepLog.getText() + "\n" + "Warning: component " + _component.getArtifactId() + " with version: " + _component.getVersion() + " is illegal");
								dependenciesJFrame.jTextPaneDepLog.setText(dependenciesJFrame.jTextPaneDepLog.getText() + "\n" + "Allowed version: " + dependencyService.getAllowedDependency(_component.getArtifactId()).getVersion());
							}
						} else { // de dependency (in de pom) komt niet voor in de allowed file
							dependenciesJFrame.jTextPaneDepLog.setText(dependenciesJFrame.jTextPaneDepLog.getText() + "\n" + "Warning: component " + _component.getArtifactId() + " (with version: " + _component.getVersion() + ") is illegal");
						}
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			Log.e(this, "loadTables() - exception: " + e.getMessage());
			UiDialogs.errorDialog(dependenciesJFrame, e.getMessage(), "Error!");
		}
	}

	/**
	 * 
	 */
	private void addAllowedDependencies() {
		Log.i(this, "addAllowedDependencies()");

		File file = new File(dependenciesJFrame.jTextFieldDependencies.getText());
		parseXML(file, true);
	}

	/**
	 * 
	 */
	private void addDependencies() {
		Log.i(this, "addDependencies()");

		File file = new File(dependenciesJFrame.jTextFieldPom.getText());
		parseXML(file, false);
	}

	public void actionPerformed(ActionEvent event) {
		if (event.getSource() == dependenciesJFrame.jButtonDependenciesBrowse) {
			Log.i(this, "actionPerformed() - dependencie browse");

			String path = browseForPath(dependenciesJFrame.jTextFieldDependencies.getText());
			dependenciesJFrame.jTextFieldDependencies.setText(path);
		} else if (event.getSource() == dependenciesJFrame.jButtonPomBrowse) {
			Log.i(this, "actionPerformed() - pom browse");

			String path = browseForPath(dependenciesJFrame.jTextFieldPom.getText());
			dependenciesJFrame.jTextFieldPom.setText(path);
		} else if (event.getSource() == dependenciesJFrame.jButtonClear) {
			Log.i(this, "actionPerformed() - clear log");

			dependenciesJFrame.jTextPaneDepLog.setText("");
		} else if (event.getSource() == dependenciesJFrame.jButtonCheckDeps) {
			Log.i(this, "actionPerformed() - check dependencies");

			dependencyService.removeAllDependencies();
			dependencyService.removeAllAllowedDependencies();

			check();
		} else {
			Log.e(this, "actionPerformed() - unkown command: " + event);
		}
	}
}
