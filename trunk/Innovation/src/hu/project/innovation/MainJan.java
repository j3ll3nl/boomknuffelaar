package hu.project.innovation;

import hu.project.innovation.configuration.model.ConfigurationService;
import net.sourceforge.pmd.PMD;

public class MainJan {

	public static void main(String[] args) {
		new TestConfiguration();

		String[] pmdArgs = new String[]{
			"C:\\Users\\Jan\\workspace\\Innovation\\src",
			ConfigurationService.getInstance().getOutputFormat(),
		    "..\\bin\\hu\\project\\innovation\\configuration\\model\\ruleset.xml"
		};
		// Start PMD
		PMD.main(pmdArgs);

	}
}