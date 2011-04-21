package hu.project.innovation.analyse.view;

import java.awt.BorderLayout;

import javax.swing.JPanel;
import javax.swing.WindowConstants;

/**
 * This code was edited or generated using CloudGarden's Jigloo SWT/Swing GUI Builder, which is free for non-commercial use. If Jigloo is being used commercially (ie, by a corporation, company or
 * business for any purpose whatever) then you should purchase a license for each developer using Jigloo. Please visit www.cloudgarden.com for details. Use of Jigloo implies acceptance of these
 * licensing terms. A COMMERCIAL LICENSE HAS NOT BEEN PURCHASED FOR THIS MACHINE, SO JIGLOO OR THIS CODE CANNOT BE USED LEGALLY FOR ANY CORPORATE OR COMMERCIAL PURPOSE.
 */
public class AnalyseJFrame extends javax.swing.JFrame {

	private static final long serialVersionUID = 404609294738834067L;
	private JPanel jPanel1;

	{
		// Set Look & Feel
		try {
			javax.swing.UIManager.setLookAndFeel(javax.swing.UIManager.getSystemLookAndFeelClassName());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public AnalyseJFrame() {
		super();
		initUi();
	}

	private void initUi() {
		try {
			setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
			pack();
			setTitle("Analyse");
			{
				jPanel1 = new JPanel();
				BorderLayout jPanel1Layout = new BorderLayout();
				jPanel1.setLayout(jPanel1Layout);
				getContentPane().add(jPanel1, BorderLayout.CENTER);
			}
			this.setSize(638, 233);
		} catch (Exception e) {
			// add your error handling code here
			e.printStackTrace();
		}
	}

}
