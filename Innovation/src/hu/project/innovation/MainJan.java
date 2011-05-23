package hu.project.innovation;

import hu.project.innovation.configuration.model.ConfigurationService;
import net.sourceforge.pmd.PMD;

class MainJan {

	private MainJan() {
	};

	public static void main(String[] args) {
		new TestConfiguration();

		String[] pmdArgs = new String[] { ConfigurationService.getInstance().getProjectPath(), ConfigurationService.getInstance().getOutputFormat(), "\\hu\\project\\innovation\\configuration\\model\\rules\\ruleset.xml" };
		// Start PMD
		PMD.main(pmdArgs);

	}
}