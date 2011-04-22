package hu.project.innovation;

import net.sourceforge.pmd.PMD;

public class MainJan {

	public static void main(String[] args) {
		new TestConfiguration();

		// Start PMD
		PMD.main(args);

	}
}