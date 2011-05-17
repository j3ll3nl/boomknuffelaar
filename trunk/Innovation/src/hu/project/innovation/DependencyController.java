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
		
		configurationService.addDependency("junit", "junit", "3.8.1", "test");
		System.out.println(configurationService.getDependency("junit").getVersion());
		
		parseXML(pomFile);
//		if(pomFile.exists()) {
//			try {
//				FileInputStream fis = new FileInputStream(pomFile);
//				Scanner scanner = new Scanner(new InputStreamReader(fis));
//				while(scanner.hasNextLine()) {
//					System.out.println(scanner.nextLine());
//				}
//			} catch (FileNotFoundException e) {
//				e.printStackTrace();
//			}
//		}
	}
	
	private void parseXML(File pomFile) {
		try {
			SAXParser saxParser = SAXParserFactory.newInstance().newSAXParser();
			try {
				saxParser.parse(pomFile, new DefaultHandler() {
					public void startElement(String uri, String qName, String localName, Attributes attrs) {
						
					}
					public void characters(char[] ch, int start, int length) {
//						System.out.println(new String(ch, start, length).trim());
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
}
