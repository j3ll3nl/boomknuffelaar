package hu.project.innovation.analyse.controller;

import hu.project.innovation.configuration.model.ConfigurationService;
import hu.project.innovation.configuration.view.dependencies.JFrameDependencies;
import hu.project.innovation.utils.UiDialogs;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.swing.table.DefaultTableModel;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

public class DependencyController {
	private ConfigurationService configurationService;
	private JFrameDependencies dependenciesJFrame = null;
	
	/** format for version number */
	private static String _VERSION = "[0-9]{1,2}(.[0-9])*?";
	
	/**
	 * 
	 */
	public DependencyController() {
		configurationService = ConfigurationService.getInstance();
		configurationService.setProjectPath(System.getProperty("user.dir"));
		
		this.addAllowedDependencies();
		this.addDependencies();
	}
	
	/**
	 * 
	 */
	public void initUI() {
		if(dependenciesJFrame == null) {
			dependenciesJFrame = new JFrameDependencies();
			
			DefaultTableModel atm = (DefaultTableModel) dependenciesJFrame.getJTableFoundComponents().getModel();
			dependenciesJFrame.setResizable(false);
			
			//Disable buttons (voorlopig)
			dependenciesJFrame.getJButton1().setEnabled(false);
			dependenciesJFrame.getJButton2().setEnabled(false);
			dependenciesJFrame.getJButton3().setEnabled(false);
			
			//Add columns
			atm.addColumn("Number");
			atm.addColumn("Dependency");
			atm.addColumn("Type");
			
			// Id's
			int i = 1;
			
			// Parse the (jar) components from the classpath string
			ArrayList<String> extDependencies = getExtDependensies(System.getProperty("java.class.path"));
			if(extDependencies != null) {
				for(String dependency : extDependencies) {
					//Kijken of de dependency een - heeft, indien het geval het stuk van de string ervoor pakken (tijdelijke oplossing)
					if(!configurationService.searchDepSoftwareComponent((dependency.contains("-"))? dependency.split("-")[0] : dependency)) {
						//check if the dependency is added in the project object model (pom.xml)
						boolean tmp = false;
						// if an dependency ends with .jar, remove ".jar" and add it to the table
						Object rowdata[] = { i++, ((tmp = dependency.endsWith(".jar")) ? dependency.substring(0, dependency.length()-4) : dependency), (tmp) ? "Jar" : "not supported" };
						atm.addRow(rowdata);
					}
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
								configurationService.addDependency(_groupId, _artifactId, _version, _type, _scope);
							else
								configurationService.addAllowedDependency(_groupId, _artifactId, _version, _type, _scope);
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
		File file = new File(configurationService.getProjectPath() + "/" + fileName);
		parseXML(file, true);
	}
	
	/**
	 * 
	 */
	private void addDependencies() {
		File file = new File(configurationService.getProjectPath()+"/pom.xml");
		parseXML(file, false);
	}
	
	public boolean isVersionValid(String version) {
		return java.util.regex.Pattern.matches(_VERSION, version);
	}
}
