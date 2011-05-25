package hu.project.innovation.analyse.view;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.BorderFactory;
import javax.swing.ComboBoxModel;
import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.WindowConstants;

/**
 * This code was edited or generated using CloudGarden's Jigloo SWT/Swing GUI Builder, which is free for non-commercial use. If Jigloo is being used commercially (ie, by a corporation, company or
 * business for any purpose whatever) then you should purchase a license for each developer using Jigloo. Please visit www.cloudgarden.com for details. Use of Jigloo implies acceptance of these
 * licensing terms. A COMMERCIAL LICENSE HAS NOT BEEN PURCHASED FOR THIS MACHINE, SO JIGLOO OR THIS CODE CANNOT BE USED LEGALLY FOR ANY CORPORATE OR COMMERCIAL PURPOSE.
 */
public class JFrameAnalyse extends javax.swing.JFrame {

	private static final long serialVersionUID = 404609294738834067L;
	private JPanel jPanel1;
	private JPanel jPanel2;
	private JPanel jPanel3;
	private JPanel jPanel4;
	private JPanel jPanel5;
	private JPanel jPanel6;
	private JPanel jPanel7;
	private JLabel jLabel1;
	private JLabel jLabel4;
	private JLabel jLabel5;
	public JButton jButtonProjectBrowse;
	public JButton jButtonOutputBrowse;
	public JButton jButtonClose;
	public JButton jButtonStartAnalyse;
	public JTextField jTextFieldProjectPath;
	public JTextField jTextFieldOutputPath;
	public JComboBox jComboBoxOutputType;

	{
		// Set Look & Feel
		try {
			javax.swing.UIManager.setLookAndFeel(javax.swing.UIManager.getSystemLookAndFeelClassName());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public JFrameAnalyse() {
		super();
		initUi();
	}

	private void initUi() {
		try {
			setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
			pack();
			setTitle("Code analyse");
			setIconImage(new ImageIcon(getClass().getClassLoader().getResource("hu/project/innovation/resources/jframeicon.jpg")).getImage());
			setResizable(false);
			{
				jPanel1 = new JPanel();
				BorderLayout jPanel1Layout = new BorderLayout();
				jPanel1.setLayout(jPanel1Layout);
				getContentPane().add(jPanel1, BorderLayout.CENTER);
				jPanel1.setBorder(BorderFactory.createEmptyBorder(3, 3, 3, 3));
				{
					jPanel2 = new JPanel();
					BorderLayout jPanel2Layout = new BorderLayout();
					jPanel1.add(jPanel2, BorderLayout.CENTER);
					jPanel2.setLayout(jPanel2Layout);
					{
						jPanel4 = new JPanel();
						GridBagLayout jPanel4Layout = new GridBagLayout();
						jPanel2.add(jPanel4, BorderLayout.NORTH);
						jPanel4.setBorder(BorderFactory.createTitledBorder("Project"));
						jPanel4Layout.rowWeights = new double[] { 0.0 };
						jPanel4Layout.rowHeights = new int[] { 27 };
						jPanel4Layout.columnWeights = new double[] { 0.0, 0.1 };
						jPanel4Layout.columnWidths = new int[] { 83, 7 };
						jPanel4.setLayout(jPanel4Layout);
						{
							jLabel1 = new JLabel();
							jPanel4.add(jLabel1, new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0, GridBagConstraints.FIRST_LINE_START, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0));
							jLabel1.setText("Path");
						}
						{
							jPanel6 = new JPanel();
							FlowLayout jPanel6Layout = new FlowLayout();
							jPanel6Layout.setAlignment(FlowLayout.LEFT);
							jPanel6Layout.setHgap(0);
							jPanel6Layout.setVgap(0);
							jPanel6.setLayout(jPanel6Layout);
							jPanel4.add(jPanel6, new GridBagConstraints(1, 0, 1, 1, 0.0, 0.0, GridBagConstraints.FIRST_LINE_START, GridBagConstraints.BOTH, new Insets(0, 0, 0, 0), 0, 0));
							{
								jTextFieldProjectPath = new JTextField(50);
								jPanel6.add(jTextFieldProjectPath);

							}
							{
								jButtonProjectBrowse = new JButton();
								jPanel6.add(jButtonProjectBrowse);
								jButtonProjectBrowse.setText("Browse");
							}
						}
					}
					{
						jPanel5 = new JPanel();
						GridBagLayout jPanel5Layout = new GridBagLayout();
						jPanel2.add(jPanel5, BorderLayout.CENTER);
						jPanel5.setBorder(BorderFactory.createTitledBorder("Output"));

						jPanel5Layout.rowWeights = new double[] { 0.0, 0.0 };
						jPanel5Layout.rowHeights = new int[] { 27, 27 };
						jPanel5Layout.columnWeights = new double[] { 0.0, 0.1 };
						jPanel5Layout.columnWidths = new int[] { 83, 7 };

						jPanel5.setLayout(jPanel5Layout);
						{
							jLabel4 = new JLabel();
							jPanel5.add(jLabel4, new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0, GridBagConstraints.FIRST_LINE_START, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0));
							jLabel4.setText("Path");
						}
						{
							jPanel7 = new JPanel();
							FlowLayout jPanel7Layout = new FlowLayout();
							jPanel7Layout.setAlignment(FlowLayout.LEFT);
							jPanel7Layout.setHgap(0);
							jPanel7Layout.setVgap(0);
							jPanel7.setLayout(jPanel7Layout);
							jPanel5.add(jPanel7, new GridBagConstraints(1, 0, 1, 1, 0.0, 0.0, GridBagConstraints.FIRST_LINE_START, GridBagConstraints.BOTH, new Insets(0, 0, 0, 0), 0, 0));
							{
								jTextFieldOutputPath = new JTextField(50);
								jPanel7.add(jTextFieldOutputPath);
							}
							{
								jButtonOutputBrowse = new JButton();
								jPanel7.add(jButtonOutputBrowse);
								jButtonOutputBrowse.setText("Browse");
							}
						}
						{
							jLabel5 = new JLabel();
							jPanel5.add(jLabel5, new GridBagConstraints(0, 1, 1, 1, 0.0, 0.0, GridBagConstraints.FIRST_LINE_START, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0));
							jLabel5.setText("Type");
						}
						{
							ComboBoxModel jComboBox1Model = new DefaultComboBoxModel(new String[] { "text", "html", "xml" });
							jComboBoxOutputType = new JComboBox();

							jPanel5.add(jComboBoxOutputType, new GridBagConstraints(1, 1, 1, 1, 0.0, 0.0, GridBagConstraints.FIRST_LINE_START, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0));
							jComboBoxOutputType.setModel(jComboBox1Model);
						}
					}
				}
				{
					jPanel3 = new JPanel();
					FlowLayout jPanel3Layout = new FlowLayout();
					jPanel3.setLayout(jPanel3Layout);
					jPanel1.add(jPanel3, BorderLayout.SOUTH);
					{
						jButtonClose = new JButton();
						jPanel3.add(jButtonClose);
						jButtonClose.setText("Close");
					}
					{
						jButtonStartAnalyse = new JButton();
						jPanel3.add(jButtonStartAnalyse);
						jButtonStartAnalyse.setText("Start analyse");
					}
				}
			}
			this.setSize(638, 233);
		} catch (Exception e) {
			// add your error handling code here
			e.printStackTrace();
		}
	}

}
