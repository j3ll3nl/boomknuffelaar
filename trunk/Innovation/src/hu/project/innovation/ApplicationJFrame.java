package hu.project.innovation;

import javax.swing.WindowConstants;

public class ApplicationJFrame extends javax.swing.JFrame {

	private static final long serialVersionUID = 6858870868564931134L;

	public ApplicationJFrame() {
		super();
		initGUI();
	}

	private void initGUI() {
		try {
			setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
			pack();
			setSize(400, 300);
		} catch (Exception e) {
			// add your error handling code here
			e.printStackTrace();
		}
	}

}
