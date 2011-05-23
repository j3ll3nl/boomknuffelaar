package hu.project.innovation.analyse.model;

import hu.project.innovation.configuration.view.JPanelStatus;
import hu.project.innovation.utils.Log;
import hu.project.innovation.utils.UiDialogs;

import java.util.Observable;

import net.sourceforge.pmd.PMD;

public class PmdThread extends Observable implements Runnable {

	private String[] pmdargs;

	public void run() {
		try {
			JPanelStatus.getInstance("Running PMD").start();
			Log.i(this, "Starting PMD with settings: " + pmdargs.toString());

			// Start PMD
			PMD.main(pmdargs);

			setChanged();
			notifyObservers();

			Log.i(this, "PMD is done, report is saved");

			JPanelStatus.getInstance().stop();
		} catch (Exception e) {
			Log.e(this, "actionPerformed() - exception " + e.getMessage());
			UiDialogs.errorDialog(null, e.getMessage(), "Error");
		}
	}

	public void setArguments(String[] pmdArgs) {
		pmdargs = pmdArgs;
	}

}
