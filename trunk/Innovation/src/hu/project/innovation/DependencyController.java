package hu.project.innovation;

import hu.project.innovation.configuration.model.ConfigurationService;

import java.io.File;
import java.io.IOException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

public class DependencyController {
	private String xmlFile = "pom.xml";
	private ConfigurationService configurationService;
	
	public static void main(String[] args) {
		new DependencyController();
	}
	
	public DependencyController() {
		configurationService = ConfigurationService.getInstance();
		configurationService.setProjectPath(System.getProperty("user.dir"));
		
		File pomFile = new File(configurationService.getProjectPath()+"/"+xmlFile);
				
		parseXML(pomFile);
	}
	
	private void parseXML(File pomFile) {
		try {
			SAXParser saxParser = SAXParserFactory.newInstance().newSAXParser();
			try {
				saxParser.parse(pomFile, new DefaultHandler() {
					private boolean dependency, groupId, artifactId, version, scope = false;
					private String _groupId, _artifactId, _version, _scope = null;
					public void startElement(String uri, String qName, String localName, Attributes attrs) {
						setBoolVal("startElement", localName);
						
						// reset all strings (needed for declaring a new dependency)
						if(localName.equals("dependency")) {
							this._groupId = null;
							this._artifactId = null;
							this._version = null;
							this._scope = null;
						}
					}
					public void endElement(String uri, String qName, String localName) {
						setBoolVal("endElement", localName);

						// add a new dependency
						if(localName.equals("dependency"))
							configurationService.addDependency(_groupId, _artifactId, _version, _scope);
					}
					
					public void characters(char[] ch, int start, int length) {
						if(dependency) {
							if(groupId) this._groupId = new String(ch, start, length).trim();
							else if(artifactId) this._artifactId = new String(ch, start, length).trim();
							else if(version) this._version = new String(ch, start, length).trim();
							else if(scope) this._scope = new String(ch, start, length).trim();
						}	
					}
					
					private void setBoolVal(String type, String localName) {
						if(!this.dependency)
							this.dependency = ((type.equals("startElement") && localName.equals("dependency")) ? true : false);
						this.groupId = ((type.equals("startElement") && localName.equals("groupId")) ? true : false);
						this.artifactId = ((type.equals("startElement") && localName.equals("artifactId")) ? true : false);
						this.version = ((type.equals("startElement") && localName.equals("version")) ? true : false);
						this.scope = ((type.equals("startElement") && localName.equals("scope")) ? true : false);
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
		System.out.println(configurationService.getDependency("hibernate").getVersion());
	}
}
