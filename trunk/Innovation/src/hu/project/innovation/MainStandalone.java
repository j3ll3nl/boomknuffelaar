package hu.project.innovation;

import hu.project.innovation.configuration.controller.xml.ImportController;
import hu.project.innovation.configuration.model.ConfigurationService;

import java.io.File;

import net.sourceforge.pmd.PMD;

public class MainStandalone {

	public static void main(String[] args) throws Exception {
		ImportController importc = new ImportController();
		importc.importXML(new File("./defaultConfiguratie.xml"));

		String[] pmdArgs = new String[] { ConfigurationService.getInstance().getProjectPath(), ConfigurationService.getInstance().getOutputType(), "\\hu\\project\\innovation\\configuration\\model\\rules\\ruleset.xml" };
		
		// Start PMD
		PMD.main(pmdArgs);
	}
}