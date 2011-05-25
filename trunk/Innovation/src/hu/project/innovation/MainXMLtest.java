package hu.project.innovation;

import hu.project.innovation.configuration.controller.xml.ExportController;
import hu.project.innovation.configuration.controller.xml.ImportController;

import java.io.File;

public class MainXMLtest {

	public static void main(String[] args) throws Exception {
		new TestConfiguration();

		ExportController exportController = new ExportController();
		ImportController importController = new ImportController();

		exportController.exportXML(new File("./export.xml"));
		importController.importXML(new File("./export.xml"));
		
		System.out.println("Done!");
	}
}