package hu.project.innovation;

import hu.project.innovation.configuration.view.DefinitionJPanel;
import hu.project.innovation.configuration.view.StatusTask;

import java.awt.BorderLayout;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JToolBar;
import javax.swing.WindowConstants;

/**
 * This code was edited or generated using CloudGarden's Jigloo SWT/Swing GUI Builder, which is free for non-commercial use. If Jigloo is being used commercially (ie, by a corporation, company or
 * business for any purpose whatever) then you should purchase a license for each developer using Jigloo. Please visit www.cloudgarden.com for details. Use of Jigloo implies acceptance of these
 * licensing terms. A COMMERCIAL LICENSE HAS NOT BEEN PURCHASED FOR THIS MACHINE, SO JIGLOO OR THIS CODE CANNOT BE USED LEGALLY FOR ANY CORPORATE OR COMMERCIAL PURPOSE.
 */
public class ApplicationJFrame extends javax.swing.JFrame {

	private static final long serialVersionUID = 6858870868564931134L;
	private JMenuBar jMenuBar;
	public JLabel jLabelStatus;
	private JToolBar jToolBar;
	public JPanel jPanelContentView;
	public JMenuItem jMenuItemAbout;
	public JMenuItem jMenuItemOnlineHelp;
	public JMenuItem jMenuItemStartAnalyse;
	public JMenuItem jMenuItemSaveArchitecture;
	public JMenuItem jMenuItemOpenArchitecture;
	public JMenuItem jMenuItemNewArchitecture;
	private JMenu jMenu3;
	private JMenu jMenu2;
	private JMenu jMenu1;

	{
		// Set Look & Feel
		try {
			javax.swing.UIManager.setLookAndFeel(javax.swing.UIManager.getSystemLookAndFeelClassName());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public ApplicationJFrame() {
		super();
		initUi();
	}

	private void initUi() {
		try {
			setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
			setTitle("Belastingdienst Architectuur");
			setIconImage(new ImageIcon(getClass().getClassLoader().getResource("belastingdienst.jpg")).getImage());
			{
				jPanelContentView = new JPanel();
				BorderLayout jPanel1Layout = new BorderLayout();
				jPanelContentView.setLayout(jPanel1Layout);
				jPanelContentView.add(new DefinitionJPanel());
				getContentPane().add(jPanelContentView, BorderLayout.CENTER);
			}
			{
				jToolBar = new JToolBar();
				getContentPane().add(jToolBar, BorderLayout.SOUTH);
				jToolBar.setEnabled(false);
				jToolBar.setBorderPainted(false);
				jToolBar.add(StatusTask.getInstance(""));				
			}
			{
				jMenuBar = new JMenuBar();
				setJMenuBar(jMenuBar);
				{
					jMenu1 = new JMenu();
					jMenuBar.add(jMenu1);
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
					jMenuBar.add(jMenu2);
					jMenu2.setText("Analyse");
					{
						jMenuItemStartAnalyse = new JMenuItem();
						jMenu2.add(jMenuItemStartAnalyse);
						jMenuItemStartAnalyse.setText("Start analyse");
					}
				}
				{
					jMenu3 = new JMenu();
					jMenuBar.add(jMenu3);
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
			setSize(1000, 600);
		} catch (Exception e) {
			// add your error handling code here
			e.printStackTrace();
		}
	}

	public void setContentView(JPanel jp) {
		jPanelContentView.removeAll();
		jPanelContentView.add(jp);
	}

}
