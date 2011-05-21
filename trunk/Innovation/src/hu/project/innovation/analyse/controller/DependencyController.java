package hu.project.innovation.analyse.controller;

import hu.project.innovation.configuration.model.DependencyService;
import hu.project.innovation.configuration.model.dependencies.DepSoftwareComponent;
import hu.project.innovation.configuration.model.dependencies.DependencySelectionHandler;
import hu.project.innovation.configuration.view.dependencies.JFrameDependencies;
import hu.project.innovation.utils.UiDialogs;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

public class DependencyController {
	private String projectPath;
	private DependencyService dependencyService;
	private JFrameDependencies dependenciesJFrame = null;
	
	private JTable extDependencyTable, allowedDepsTable;
	private JButton buttonToevoegen;
	
	/** format for version number */
	private static String _VERSION = "[0-9]{1,2}(.[0-9])*?";
	
	/**
	 * 
	 */
	public DependencyController() {
		dependencyService = new DependencyService();
		projectPath = System.getProperty("user.dir");
		this.addAllowedDependencies();
		this.addDependencies();
	}
	
	/**
	 * 
	 */
	public void initUI() {
		if(dependenciesJFrame == null) {
			dependenciesJFrame = new JFrameDependencies();
			
			// get (the 3) tables
			extDependencyTable = dependenciesJFrame.getJTableFoundComponents();
			allowedDepsTable = dependenciesJFrame.getJTableAllowedDeps();
			
			// get the models
			DefaultTableModel atm = (DefaultTableModel) extDependencyTable.getModel();
			DefaultTableModel atm2 = (DefaultTableModel) allowedDepsTable.getModel();
			
			dependenciesJFrame.setResizable(false);
			
			//Disable buttons (voorlopig)
			buttonToevoegen = dependenciesJFrame.getJButton1(); 
			buttonToevoegen.addActionListener(new DependencySelectionHandler(dependencyService));
			
			
			extDependencyTable.getSelectionModel().addListSelectionListener(new DependencySelectionHandler(buttonToevoegen));
			
			//Add columns
			atm.addColumn("Number"); atm2.addColumn("Number");
			atm.addColumn("Dependency"); atm2.addColumn("Dependency");
			atm.addColumn("Type"); atm2.addColumn("Type");
			
			// Id's
			int i = 1;
			
			// Parse the (jar) components from the classpath string
			ArrayList<String> extDependencies = getExtDependensies(System.getProperty("java.class.path"));
			if(extDependencies != null) {
				for(String dependency : extDependencies) {
					//Kijken of de dependency een - heeft, indien het geval het stuk van de string ervoor pakken (tijdelijke oplossing)
					if(!dependencyService.searchDepSoftwareComponent((dependency.contains("-"))? dependency.split("-")[0] : dependency)) {
						//check if the dependency is added in the project object model (pom.xml)
						boolean tmp = false;
						// if an dependency ends with .jar, remove ".jar" and add it to the table
						Object rowdata[] = { i++, ((tmp = dependency.endsWith(".jar")) ? dependency.substring(0, dependency.length()-4) : dependency), (tmp) ? "Jar" : "not supported" };
						atm.addRow(rowdata);
					}
				}
			}
			
			//add allowed components to the table
			DepSoftwareComponent[] allowedDSComponents;
			if((allowedDSComponents = dependencyService.getAllowedDependencies()) != null) {
				i = 1;
				for(DepSoftwareComponent allowedDsc : allowedDSComponents) {
					// if an dependency ends with .jar, remove ".jar" and add it to the table
					Object rowdata[] = { i++, allowedDsc.getArtifactId(), allowedDsc.getType() };
					atm2.addRow(rowdata);
				}	
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
	public ArrayList<String> getExtDependensies(String classPath) {
		ArrayList<String> unitNames = new ArrayList<String>();
		for(String path : classPath.split(":")) {
			String[] unitName = path.split("/");
			String unit = unitName[(unitName.length - 1)];
			if(unit.contains("."))
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
				saxParser.parse(pomFile, new DefaultHandler() {
					boolean dependencies, groupId, artifactId, version, type, scope = false;
					String _groupId, _artifactId, _version, _type, _scope = null;
										
					public void startElement(String uri, String qName, String localName, Attributes attrs) {
						if(localName.equalsIgnoreCase("dependencies"))
							dependencies = true;
						else if(localName.equalsIgnoreCase("groupId"))
							groupId = (dependencies) ? true : false;
						else if(localName.equalsIgnoreCase("artifactId"))
							artifactId = (dependencies) ? true : false;
						else if(localName.equalsIgnoreCase("version"))
							version = (dependencies) ? true : false;
						else if(localName.equalsIgnoreCase("type"))
							type = (dependencies) ? true : false;
						else if(localName.equalsIgnoreCase("scope"))
							scope = (dependencies) ? true : false;
					}
					
					public void endElement(String uri, String qName, String localName) {
						if(localName.equalsIgnoreCase("dependency")) {
							if(!isAllowedByDefault)
								dependencyService.addDependency(_groupId, _artifactId, _version, _type, _scope);
							else
								dependencyService.addAllowedDependency(_groupId, _artifactId, _version, _type, _scope);
						}
					}
					
					public void characters(char[] ch, int start, int length) {
						if(dependencies) {
							if(groupId) {
								_groupId = new String(ch, start, length);
								groupId = false;
							} else if(artifactId) {
								_artifactId = new String(ch, start, length);
								artifactId = false;
							} else if(version) {
								_version = new String(ch, start, length);
								version = false;
							} else if(type) {
								_type = new String(ch, start, length);
								type = false;
							} else if(scope) {
								_scope = new String(ch, start, length);
								scope = false;
							}
						}
					}
				});
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
		String fileName = "mydependencies.xml";
		File file = new File(projectPath + "/" + fileName);
		parseXML(file, true);
	}
	
	/**
	 * 
	 */
	private void addDependencies() {
		File file = new File(projectPath+"/pom.xml");
		parseXML(file, false);
	}
	
	public boolean isVersionValid(String version) {
		return java.util.regex.Pattern.matches(_VERSION, version);
	}
}
