package hu.project.innovation.analyse.controller;

import hu.project.innovation.configuration.model.DependencyService;
import hu.project.innovation.configuration.model.dependencies.DepSoftwareComponent;
import hu.project.innovation.configuration.model.dependencies.DependencyParseHandler;
import hu.project.innovation.configuration.view.jframe.JFrameDependencies;
import hu.project.innovation.utils.UiDialogs;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.JTextPane;
import javax.swing.filechooser.FileFilter;
import javax.swing.table.DefaultTableModel;
import javax.swing.text.DefaultEditorKit;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.SAXException;

public class DependencyController implements ActionListener {
	private DependencyService dependencyService;
		
	private String projectPath = System.getProperty("user.dir");
	private JFrameDependencies dependenciesJFrame = null;
	private ArrayList<JTable> tables = new ArrayList<JTable>();
	private ArrayList<JButton> buttons = new ArrayList<JButton>();
	@SuppressWarnings("unused")
	private JTextField searchField, jTextFieldPomPath, jTextFieldMyDepsPath;
	private JFileChooser selectPom, selectMyDeps;
	private JButton buttonCheckDeps;
	private JTextPane jTextPaneDepLog;
	
	// de pom.xml en mydependencies.xml (toegestane dependencies door de belastingdienst)
	private File pomFile = new File(projectPath + "/pom.xml"),
		allowedDepsFile = new File(projectPath + "/mydependencies.xml");

	/** format for version number */
	@SuppressWarnings("unused")
	private static String _VERSION = "[0-9]{1,2}(.[0-9])*?";
	
	public DependencyController() {
		// TODO Aan daan: Deze code wordt te vroeg uitgevoerd. Bij het opstarten van de app wordt direct iets met pom.xml gedaan. Dat veroorzaakte mij een nullpointerexception waardoor de app niet
		// opstarte. (stefan)
	}
	
	public void check() {
		// Kijken of de dependency in de POM voorkomt (en in dat geval dus is allowed) in mydependencies.xml
		if(dependencyService.getDependencies() != null) {
			if(dependencyService.getAllowedDependencies() != null) {
				for (DepSoftwareComponent _component : dependencyService.getDependencies()) {
					if(dependencyService.searchAllowedDepSoftwareComponent(_component.getArtifactId())) {
						if (!isVersionValidate(dependencyService.getAllowedDependency(_component.getArtifactId()).getVersion(), _component.getVersion())) {
							jTextPaneDepLog.setText(jTextPaneDepLog.getText() + "\n" + "Warning: component " + _component.getArtifactId() + " with version: " + _component.getVersion() + " is illegal");
							jTextPaneDepLog.setText(jTextPaneDepLog.getText() + "\n" + "Allowed version: " + dependencyService.getAllowedDependency(_component.getArtifactId()).getVersion());
						}
					} else{ // de dependency (in de pom) komt niet voor in de allowed file
						jTextPaneDepLog.setText(jTextPaneDepLog.getText() + "\n" + "Warning: component " + _component.getArtifactId() + " (with version: " + _component.getVersion() + ") is illegal");
					}
				}
			}
		}
	}
	
	private boolean doFilesExist() {
		if(pomFile.exists()) {
			if(allowedDepsFile.exists()) {
				dependencyService = new DependencyService();
				this.addAllowedDependencies();
				this.addDependencies();
				return true;
			} else System.err.println("File: " + allowedDepsFile.getAbsolutePath() + " can't be found");
		} else System.err.println("File: " + pomFile.getAbsolutePath() + " can't be found");
		return false;
	}

	private boolean isVersionValidate(String terms, String _version) {
		// Valideer de versie nummer aan de hand van de regular expression
		return java.util.regex.Pattern.matches(getRegExpression(terms), _version);
	}

	/**
	 * 
	 * @param version
	 * @return
	 */
	private String getRegExpression(String version) {
		// converteer de versie nummer naar een reguliere expressie
		String _temp = version;
		if (version.contains(",")) {
			if (_temp.contains(",")) {
				_temp = _temp.contains("x") ? ("(" + _temp.replace(",", ")|(") + ")").replace("x", "([0-9]{1,2})") 
						: "(" + _temp.replace(",", ")|(") + ")";
			}
		} else
			_temp = (!version.contains("x") ? version : version.replace("x", "([0-9]{1,2})"));
		return _temp;
	}

	/**
	 * start de gui
	 */
	public void initUI() {
		doFilesExist();
		selectPom = new JFileChooser(projectPath);
		selectPom.setFileFilter(new FileFilter() {
			@Override
			public boolean accept(File f) {
				return f.getName().endsWith(".xml");
			}
			@Override
			public String getDescription() {
				return "*.xml";
			}
		});
		//copy
		selectMyDeps = selectPom;
		
		if (dependenciesJFrame == null) {
			dependenciesJFrame = new JFrameDependencies();
			// get (the 3) tables
			tables.add(dependenciesJFrame.getJTableFoundComponents());
			tables.add(dependenciesJFrame.getJTableDepsPom());
			tables.add(dependenciesJFrame.getJTableAllowedDeps());

			dependenciesJFrame.setResizable(false);

			JButton buttonBrowsePom = dependenciesJFrame.getJButtonPomBrowse();
			JButton buttonBrowseMyDeps = dependenciesJFrame.getJButtonMyDepsBrowse();
			buttonBrowsePom.setName("buttonBrowsePom");
			buttonBrowseMyDeps.setName("buttonBrowseMyDeps");
			
			// Disable buttons (voorlopig)
			buttons.add(dependenciesJFrame.getJButton1());
			buttons.add(dependenciesJFrame.getJButtonAllowDepAdd());
			buttons.add(dependenciesJFrame.getJButtonAllowDepEdit());
			buttons.add(dependenciesJFrame.getJButtonAllowDepRemove());
			buttons.add(dependenciesJFrame.getJButtonDeletePomD());
			buttons.add(dependenciesJFrame.getJButtonSearch());
			buttons.add(buttonBrowsePom);
			buttons.add(buttonBrowseMyDeps);
			
			buttonCheckDeps = dependenciesJFrame.getJButtonCheckDeps();
			buttonCheckDeps.setName("buttonCheckDeps");
			buttonCheckDeps.addActionListener(this);
			
			//textfields
			searchField = dependenciesJFrame.getSearchDepField();
			jTextFieldPomPath = dependenciesJFrame.getJTextFieldPom();
			jTextFieldMyDepsPath = dependenciesJFrame.getJTextFieldMyDeps();
			
			//jtextpane
			jTextPaneDepLog = dependenciesJFrame.getJTextPaneDepLog();
			jTextPaneDepLog.getDocument().putProperty(DefaultEditorKit.EndOfLineStringProperty, "\n");

//			DependencySelectionHandler dsHandler = new DependencySelectionHandler(dependencyService);
			for (JButton button : buttons)
				button.addActionListener(this);
			
//			searchField.addActionListener(dsHandler);

			// for(JTable table : tables)
			// pomDepsTable.getSelectionModel().addListSelectionListener(new DependencySelectionHandler(jButtonDeletePomD));

			// Add columns
			int counter = 0;
			for (JTable table : tables) {
				DefaultTableModel dtm = (DefaultTableModel) table.getModel();
				dtm.addColumn("Number");
				dtm.addColumn("Dependency");
				dtm.addColumn("Version");
				dtm.addColumn("Type");
				for (JButton button : buttons) {
					System.out.println(button.getName());
//					table.getSelectionModel().addListSelectionListener(new DependencySelectionHandler(button));
				}
				counter++;
			}

			// Id's
			int i = 1;

			counter = 0;
			for (JTable table : tables) {
				DefaultTableModel dtm = (DefaultTableModel) table.getModel();
				DepSoftwareComponent[] pomDSComponents;
				DepSoftwareComponent[] allowedDSComponents;
				if (counter == 0) {
					// Parse the (jar) components from the classpath string
					ArrayList<String> extDependencies = getExtDependensies(System.getProperty("java.class.path"));
					if (extDependencies != null) {
						for (String dependency : extDependencies) {
							// Kijken of de dependency een - heeft, indien het geval het stuk van de string ervoor pakken (tijdelijke oplossing)
							if (!dependencyService.searchDepSoftwareComponent((dependency.contains("-")) ? dependency.split("-")[0] : dependency)) {
								// check if the dependency is added in the project object model (pom.xml)
								boolean tmp = false;
								// if an dependency ends with .jar, remove ".jar" and add it to the table
								Object rowdata[] = { i++, ((tmp = dependency.endsWith(".jar")) ? dependency.substring(0, dependency.length() - 4) : dependency), "?", (tmp) ? "Jar" : "not supported" };
								dtm.addRow(rowdata);
							}
						}
					}
				} else if (counter == 1) {
					// add pom dependencies to the table
					if ((pomDSComponents = dependencyService.getDependencies()) != null) {
						i = 1;
						for (DepSoftwareComponent pomDsc : pomDSComponents) {
							Object rowdata[] = { i++, pomDsc.getArtifactId(), pomDsc.getVersion(), pomDsc.getType() };
							dtm.addRow(rowdata);
						}
					}
				} else {
					// add allowed components to the table
					if ((allowedDSComponents = dependencyService.getAllowedDependencies()) != null) {
						i = 1;
						for (DepSoftwareComponent allowedDsc : allowedDSComponents) {
							Object rowdata[] = { i++, allowedDsc.getArtifactId(), allowedDsc.getVersion(), allowedDsc.getType() };
							dtm.addRow(rowdata);
						}
					}
				}
				counter++;
			}
		}

		UiDialogs.showOnScreen(0, dependenciesJFrame);
		dependenciesJFrame.setVisible(true);
	}

	/**
	 * 
	 * @param classPath
	 * @return the unit names (jars)
	 */
	private ArrayList<String> getExtDependensies(String classPath) {
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

	/**
	 * 
	 */
	private void addAllowedDependencies() {
		parseXML(allowedDepsFile, true);
	}

	/**
	 * 
	 */
	private void addDependencies() {
		parseXML(pomFile, false);
	}

	public void actionPerformed(ActionEvent event) {
		if(event.getSource() instanceof JButton) {
			JButton button = (JButton) event.getSource();
			if(button.getName().equals("buttonBrowsePom")) {
				// Als de pom (of een xml bestand) is geselecteerd
				if(selectPom.showOpenDialog(button) == JFileChooser.APPROVE_OPTION) {
					pomFile = new File(projectPath + "/" + selectPom.getSelectedFile().getName());
					jTextFieldPomPath.setText(pomFile.getAbsolutePath());
				}
			} else if(button.getName().equals("buttonBrowseMyDeps")) {
				if(selectMyDeps.showOpenDialog(button) == JFileChooser.APPROVE_OPTION) {
					allowedDepsFile = new File(projectPath + "/" + selectPom.getSelectedFile().getName());
					jTextFieldMyDepsPath.setText(allowedDepsFile.getAbsolutePath());
				}
			} else if(button.getName().equals("buttonCheckDeps")) {
				check();
			}
		}
		
	}
}
