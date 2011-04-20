package hu.project.innovation;

import java.awt.BorderLayout;

import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.WindowConstants;

/**
 * This code was edited or generated using CloudGarden's Jigloo SWT/Swing GUI Builder, which is free for non-commercial use. If Jigloo is being used commercially (ie, by a corporation, company or
 * business for any purpose whatever) then you should purchase a license for each developer using Jigloo. Please visit www.cloudgarden.com for details. Use of Jigloo implies acceptance of these
 * licensing terms. A COMMERCIAL LICENSE HAS NOT BEEN PURCHASED FOR THIS MACHINE, SO JIGLOO OR THIS CODE CANNOT BE USED LEGALLY FOR ANY CORPORATE OR COMMERCIAL PURPOSE.
 */
public class ApplicationJFrame extends javax.swing.JFrame {

	{
		// Set Look & Feel
		try {
			javax.swing.UIManager.setLookAndFeel(javax.swing.UIManager.getSystemLookAndFeelClassName());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private static final long serialVersionUID = 6858870868564931134L;
	private JMenuBar jMenuBar1;
	private JPanel jPanel1;
	private JMenuItem jMenuItemAbout;
	private JMenuItem jMenuItemOnlineHelp;
	private JMenuItem jMenuItemStartAnalyse;
	public JMenuItem jMenuItemSaveArchitecture;
	public JMenuItem jMenuItemOpenArchitecture;
	public JMenuItem jMenuItemNewArchitecture;
	private JMenu jMenu3;
	private JMenu jMenu2;
	private JMenu jMenu1;
	private GuiController guicontroller;

	public ApplicationJFrame(GuiController gc) {
		super();
		guicontroller = gc;
	}

	public void initGUI() {
		try {
			setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
			setTitle("The app");
			setVisible(true);
			{
				jPanel1 = new JPanel();
				BorderLayout jPanel1Layout = new BorderLayout();
				jPanel1.setLayout(jPanel1Layout);
				getContentPane().add(jPanel1, BorderLayout.CENTER);
			}
			{
				jMenuBar1 = new JMenuBar();
				setJMenuBar(jMenuBar1);
				{
					jMenu1 = new JMenu();
					jMenuBar1.add(jMenu1);
					jMenu1.setText("Architecture");
					{
						jMenuItemNewArchitecture = new JMenuItem();
						jMenu1.add(jMenuItemNewArchitecture);
						jMenuItemNewArchitecture.setText("New architecture");
					}
					{
						jMenuItemOpenArchitecture = new JMenuItem();
						jMenu1.add(jMenuItemOpenArchitecture);
						jMenuItemOpenArchitecture.setText("Open architecture");
					}
					{
						jMenuItemSaveArchitecture = new JMenuItem();
						jMenu1.add(jMenuItemSaveArchitecture);
						jMenuItemSaveArchitecture.setText("Save architecture");
					}
				}
				{
					jMenu2 = new JMenu();
					jMenuBar1.add(jMenu2);
					jMenu2.setText("Analyse");
					{
						jMenuItemStartAnalyse = new JMenuItem();
						jMenu2.add(jMenuItemStartAnalyse);
						jMenuItemStartAnalyse.setText("Start analyse");
					}
				}
				{
					jMenu3 = new JMenu();
					jMenuBar1.add(jMenu3);
					jMenu3.setText("Help");
					{
						jMenuItemOnlineHelp = new JMenuItem();
						jMenu3.add(jMenuItemOnlineHelp);
						jMenuItemOnlineHelp.setText("Online help");
					}
					{
						jMenuItemAbout = new JMenuItem();
						jMenu3.add(jMenuItemAbout);
						jMenuItemAbout.setText("About");
					}
				}
			}
			pack();
			setSize(400, 300);
		} catch (Exception e) {
			// add your error handling code here
			e.printStackTrace();
		}
	}
	
	public void setContentView(JPanel jp){
		
	}

}
