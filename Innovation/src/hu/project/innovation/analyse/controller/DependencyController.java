package hu.project.innovation.analyse.controller;

import hu.project.innovation.configuration.model.ConfigurationService;
import hu.project.innovation.configuration.model.Dependencies.Dependency.DepSoftwareComponent;

import java.io.File;
import java.io.IOException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

public class DependencyController {
	private ConfigurationService configurationService;
	
	public static void main(String[] args) {
		new DependencyController();
	}
	
	public DependencyController() {
		configurationService = ConfigurationService.getInstance();
		configurationService.setProjectPath(System.getProperty("user.dir"));
				
		this.addAllowedDependencies();
		this.addDependencies();
		
		for(DepSoftwareComponent dsc : configurationService.getDependencies()) {
			if(configurationService.getAllowedDependency(dsc.getArtifactId()) == null) {
				System.err.println("Component " + dsc.getArtifactId() + " isn't allowed");
			}
		}
	}
	
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
	
	private void addAllowedDependencies() {
		String fileName = "mydependencies.xml";
		File file = new File(configurationService.getProjectPath() + "/" + fileName);
		parseXML(file, true);
	}
	
	private void addDependencies() {
		File file = new File(configurationService.getProjectPath()+"/pom.xml");
		parseXML(file, false);
	}
}
