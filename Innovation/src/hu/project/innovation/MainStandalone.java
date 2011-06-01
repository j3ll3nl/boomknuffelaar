package hu.project.innovation;

import hu.project.innovation.configuration.controller.xml.ImportController;
import hu.project.innovation.configuration.model.ConfigurationService;

import java.io.File;

import net.sourceforge.pmd.PMD;

public class MainStandalone {

	public static void main(String[] args) throws Exception {
		ImportController importc = new ImportController();
		importc.importXML(new File("./xml/architecture.xml"));
		
		String[] pmdArgs = new String[] { 
				ConfigurationService.getInstance().getProjectPath(), 
				"text",
				new File("./xml/ruleset.xml").getAbsolutePath(),
				"-targetjdk", "1.6"};
		
		// Start PMD
		PMD.main(pmdArgs);
	}
}