package hu.project.innovation.analyse.view.jframe;

import hu.project.innovation.analyse.view.tables.JTableDependencie;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.JTextPane;
import javax.swing.WindowConstants;
import javax.swing.border.TitledBorder;
import javax.swing.text.DefaultEditorKit;

/**
 * This code was edited or generated using CloudGarden's Jigloo SWT/Swing GUI Builder, which is free for non-commercial use. If Jigloo is being used commercially (ie, by a corporation, company or
 * business for any purpose whatever) then you should purchase a license for each developer using Jigloo. Please visit www.cloudgarden.com for details. Use of Jigloo implies acceptance of these
 * licensing terms. A COMMERCIAL LICENSE HAS NOT BEEN PURCHASED FOR THIS MACHINE, SO JIGLOO OR THIS CODE CANNOT BE USED LEGALLY FOR ANY CORPORATE OR COMMERCIAL PURPOSE.
 */
public class JFrameDependencies extends javax.swing.JFrame {

	private static final long serialVersionUID = -4282706188506824166L;
	public JTable jTableFoundComponents;
	private JScrollPane jScrollPane1;
	private JPanel jPanelPom;
	private JScrollPane jScrollPane2;
	private JScrollPane jScrollPane5;

	private JPanel jPanel6;

	private JPanel jPanel9;
	private JPanel jPanel8;

	private JLabel jLabel2;
	private JLabel jLabel1;

	private JPanel jPanel7;
	private JPanel jPanel5;
	private JPanel jPanel4;
	private JScrollPane jScrollPane3;
	public JTable jTableDepsPom;
	public JTable jTableAllowedDeps;
	private JTabbedPane jTabbedPane1;
	private JPanel jPanel1;
	public JTextField jTextFieldDependencies;
	public JButton jButtonDependenciesBrowse;
	public JTextField jTextFieldPom;
	public JButton jButtonPomBrowse;
	private JPanel jPanel11;
	private JPanel jPanel2;
	private JPanel jPanel10;
	private JPanel jPanel3;
	public JTextPane jTextPaneDepLog;
	public JButton jButtonClear;
	public JButton jButtonCheckDeps;

	{
		// Set Look & Feel
		try {
			javax.swing.UIManager.setLookAndFeel(javax.swing.UIManager.getSystemLookAndFeelClassName());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public JFrameDependencies() {
		super();
		initGUI();
	}

	private void initGUI() {
		try {
			setTitle("Dependencies");
			setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
			setIconImage(new ImageIcon(getClass().getClassLoader().getResource("hu/project/innovation/resources/jframeicon.jpg")).getImage());
			{
				// Eerste tab (found dependencies)
				jTabbedPane1 = new JTabbedPane();
				getContentPane().add(jTabbedPane1, BorderLayout.NORTH);
				jTabbedPane1.setBorder(BorderFactory.createEmptyBorder(3, 3, 3, 3));
				{
					jPanel4 = new JPanel();
					BorderLayout jPanel4Layout = new BorderLayout();
					jPanel4.setLayout(jPanel4Layout);
					jTabbedPane1.addTab("Check", null, jPanel4, null);
					jPanel4.setBorder(BorderFactory.createEmptyBorder(3, 3, 3, 3));
					{
						jPanel5 = new JPanel();
						GridBagLayout jPanel5Layout = new GridBagLayout();
						jPanel4.add(jPanel5, BorderLayout.NORTH);
						jPanel5.setBorder(BorderFactory.createTitledBorder("Settings"));
						jPanel5Layout.rowWeights = new double[] { 0.0, 0.1 };
						jPanel5Layout.rowHeights = new int[] { 18, 7 };
						jPanel5Layout.columnWeights = new double[] { 0.0, 0.1 };
						jPanel5Layout.columnWidths = new int[] { 83, 7 };
						jPanel5.setLayout(jPanel5Layout);
						{
							jLabel1 = new JLabel();
							jPanel5.add(jLabel1, new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0, GridBagConstraints.FIRST_LINE_START, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0));
							jLabel1.setText("Dependencies");
						}
						{
							jLabel2 = new JLabel();
							jPanel5.add(jLabel2, new GridBagConstraints(0, 1, 1, 1, 0.0, 0.0, GridBagConstraints.FIRST_LINE_START, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0));
							jLabel2.setText("POM file");
						}
						{
							jPanel8 = new JPanel();
							FlowLayout jPanel8Layout = new FlowLayout();
							jPanel8Layout.setAlignment(FlowLayout.LEFT);
							jPanel8Layout.setHgap(0);
							jPanel8Layout.setVgap(0);
							jPanel8.setLayout(jPanel8Layout);
							jPanel5.add(jPanel8, new GridBagConstraints(1, 0, 1, 1, 0.0, 0.0, GridBagConstraints.FIRST_LINE_START, GridBagConstraints.BOTH, new Insets(0, 0, 0, 0), 0, 0));
							{
								jTextFieldDependencies = new JTextField(50);
								jPanel8.add(jTextFieldDependencies);
							}
							{
								jButtonDependenciesBrowse = new JButton();
								jPanel8.add(jButtonDependenciesBrowse);
								jButtonDependenciesBrowse.setText("Browse");
							}
						}
						{
							jPanel9 = new JPanel();
							FlowLayout jPanel9Layout = new FlowLayout();
							jPanel9Layout.setAlignment(FlowLayout.LEFT);
							jPanel9Layout.setHgap(0);
							jPanel9Layout.setVgap(0);
							jPanel9.setLayout(jPanel9Layout);
							jPanel5.add(jPanel9, new GridBagConstraints(1, 1, 1, 1, 0.0, 0.0, GridBagConstraints.FIRST_LINE_START, GridBagConstraints.BOTH, new Insets(0, 0, 0, 0), 0, 0));
							{
								jTextFieldPom = new JTextField(50);
								jPanel9.add(jTextFieldPom);
							}
							{
								jButtonPomBrowse = new JButton();
								jPanel9.add(jButtonPomBrowse);
								jButtonPomBrowse.setText("Browse");
							}
						}
					}
					{
						jPanel6 = new JPanel();
						BorderLayout jPanel6Layout = new BorderLayout();
						jPanel6.setLayout(jPanel6Layout);
						jPanel4.add(jPanel6, BorderLayout.CENTER);
						jPanel6.setBorder(BorderFactory.createTitledBorder(null, "Output", TitledBorder.LEADING, TitledBorder.DEFAULT_POSITION));
						{
							jScrollPane5 = new JScrollPane();
							jPanel6.add(jScrollPane5, BorderLayout.CENTER);

							{
								jTextPaneDepLog = new JTextPane();
								jScrollPane5.setViewportView(jTextPaneDepLog);
								jTextPaneDepLog.setBackground(new java.awt.Color(0, 0, 0));
								jTextPaneDepLog.setForeground(new java.awt.Color(255, 255, 255));
								jTextPaneDepLog.setEditable(false);
								jTextPaneDepLog.getDocument().putProperty(DefaultEditorKit.EndOfLineStringProperty, "\n");

							}
						}
					}
					{
						jPanel7 = new JPanel();
						jPanel4.add(jPanel7, BorderLayout.SOUTH);
						{
							jButtonClear = new JButton();
							jPanel7.add(jButtonClear);
							jButtonClear.setText("Clear log");
						}
						{
							jButtonCheckDeps = new JButton();
							jPanel7.add(jButtonCheckDeps);
							jButtonCheckDeps.setText("Check dependencies");
						}
					}
				}
				{
					jPanel1 = new JPanel();
					BorderLayout jPanel1Layout = new BorderLayout();
					jPanel1.setLayout(jPanel1Layout);
					jTabbedPane1.addTab("Undefined", null, jPanel1, null);
					jPanel1.setBorder(BorderFactory.createEmptyBorder(3, 3, 3, 3));
					{
						jPanel11 = new JPanel();
						BorderLayout jPanel11Layout = new BorderLayout();
						jPanel11.setLayout(jPanel11Layout);
						jPanel1.add(jPanel11, BorderLayout.CENTER);
						jPanel11.setBorder(BorderFactory.createTitledBorder("Found project dependencies that are not defined in the pom file"));
						{
							jScrollPane1 = new JScrollPane();
							jPanel11.add(jScrollPane1, BorderLayout.CENTER);
							jScrollPane1.setFocusable(false);
							{
								jTableFoundComponents = new JTableDependencie();
								jScrollPane1.setViewportView(jTableFoundComponents);

							}
						}
					}
				}
				{
					jPanelPom = new JPanel();
					BorderLayout jPanelPomLayout = new BorderLayout();
					jPanelPom.setLayout(jPanelPomLayout);
					jTabbedPane1.addTab("Project Object Model", null, jPanelPom, null);
					jPanelPom.setBorder(BorderFactory.createEmptyBorder(3, 3, 3, 3));
					{
						jPanel2 = new JPanel();
						BorderLayout jPanel2Layout = new BorderLayout();
						jPanel2.setLayout(jPanel2Layout);
						jPanelPom.add(jPanel2, BorderLayout.CENTER);

						jPanel2.setBorder(BorderFactory.createTitledBorder("Loaded project object model"));
						{
							jScrollPane3 = new JScrollPane();
							jPanel2.add(jScrollPane3, BorderLayout.CENTER);

							{

								jTableDepsPom = new JTableDependencie();
								jScrollPane3.setViewportView(getJTableDepsPom());

							}
						}
					}
				}
				{
					jPanel3 = new JPanel();
					BorderLayout jPanel3Layout = new BorderLayout();
					jPanel3.setLayout(jPanel3Layout);
					jTabbedPane1.addTab("Dependencies", null, jPanel3, null);
					jPanel3.setBorder(BorderFactory.createEmptyBorder(3, 3, 3, 3));
					{
						jPanel10 = new JPanel();
						BorderLayout jPanel10Layout = new BorderLayout();
						jPanel10.setLayout(jPanel10Layout);
						jPanel3.add(jPanel10, BorderLayout.CENTER);
						jPanel10.setBorder(BorderFactory.createTitledBorder("Loaded dependencies"));
						{
							jScrollPane2 = new JScrollPane();
							jPanel10.add(jScrollPane2, BorderLayout.CENTER);
							{

								jTableAllowedDeps = new JTableDependencie();
								jScrollPane2.setViewportView(jTableAllowedDeps);
							}
						}
					}
				}

			}
			pack();
			setSize(601, 520);
			setResizable(false);
		} catch (Exception e) {
			// add your error handling code here
			e.printStackTrace();
		}
	}

	public JTable getJTableFoundComponents() {
		return jTableFoundComponents;
	}

	public JTable getJTableAllowedDeps() {
		return jTableAllowedDeps;
	}

	public JTable getJTableDepsPom() {
		return jTableDepsPom;
	}

	public JTextPane getJTextPaneDepLog() {
		return jTextPaneDepLog;
	}

	public JButton getJButtonCheckDeps() {
		return jButtonCheckDeps;
	}

	public JButton getJButtonPomBrowse() {
		return jButtonDependenciesBrowse;
	}

	public JButton getJButtonMyDepsBrowse() {
		return jButtonPomBrowse;
	}

	public JButton getJButtonClear() {
		return jButtonClear;
	}

	public JTextField getJTextFieldPom() {
		return jTextFieldDependencies;
	}

	public JTextField getJTextFieldMyDeps() {
		return jTextFieldPom;
	}
}
