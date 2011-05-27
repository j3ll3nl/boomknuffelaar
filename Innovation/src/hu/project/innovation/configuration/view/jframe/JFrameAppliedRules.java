package hu.project.innovation.configuration.view.jframe;

import hu.project.innovation.configuration.model.rules.AbstractRuleType;
import hu.project.innovation.configuration.view.tables.JTableException;

import java.awt.BorderLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.BorderFactory;
import javax.swing.ComboBoxModel;
import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.WindowConstants;

/**
 * This code was edited or generated using CloudGarden's Jigloo SWT/Swing GUI Builder, which is free for non-commercial use. If Jigloo is being used commercially (ie, by a corporation, company or
 * business for any purpose whatever) then you should purchase a license for each developer using Jigloo. Please visit www.cloudgarden.com for details. Use of Jigloo implies acceptance of these
 * licensing terms. A COMMERCIAL LICENSE HAS NOT BEEN PURCHASED FOR THIS MACHINE, SO JIGLOO OR THIS CODE CANNOT BE USED LEGALLY FOR ANY CORPORATE OR COMMERCIAL PURPOSE.
 */
public class JFrameAppliedRules extends javax.swing.JFrame {

	private JPanel jPanel1;
	private JLabel jLabel1;
	private JLabel jLabel2;
	private JLabel jLabel3;
	private JPanel jPanel3;
	private JScrollPane jScrollPane1;
	public JCheckBox jCheckBoxEnabled;
	private JLabel jLabel4;
	public JComboBox jComboBoxToLayer;
	public JComboBox jComboBoxAppliedRule;
	public JTableException jTableException;
	private JPanel jPanel4;
	public JButton jButtonAddExceptionRow;
	public JButton jButtonRemoveExceptionRow;
	private JPanel jPanel2;
	public JButton jButtonCancel;
	public JButton jButtonSave;

	{
		// Set Look & Feel
		try {
			javax.swing.UIManager.setLookAndFeel(javax.swing.UIManager.getSystemLookAndFeelClassName());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = -3491664038962722000L;

	/**
	 * Auto-generated main method to display this JFrame
	 */

	public JFrameAppliedRules() {
		super();
		initGUI();
	}

	private void initGUI() {
		try {
			setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
			setTitle("New Applied Rule");
			setIconImage(new ImageIcon(getClass().getClassLoader().getResource("hu/project/innovation/resources/jframeicon.jpg")).getImage());
			{
				jPanel1 = new JPanel();
				GridBagLayout jPanel1Layout = new GridBagLayout();
				jPanel1Layout.rowWeights = new double[] { 0.0, 0.0, 0.0, 0.1 };
				jPanel1Layout.rowHeights = new int[] { 23, 23, 29, 7 };
				jPanel1Layout.columnWeights = new double[] { 0.0, 0.1 };
				jPanel1Layout.columnWidths = new int[] { 132, 7 };
				getContentPane().add(jPanel1, BorderLayout.CENTER);
				jPanel1.setLayout(jPanel1Layout);
				jPanel1.setBorder(BorderFactory.createEmptyBorder(3, 3, 3, 3));
				{
					jLabel1 = new JLabel();
					jPanel1.add(jLabel1, new GridBagConstraints(0, 1, 1, 1, 0.0, 0.0, GridBagConstraints.FIRST_LINE_START, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0));
					jLabel1.setText("To layer");
				}
				{
					jLabel2 = new JLabel();
					jPanel1.add(jLabel2, new GridBagConstraints(0, 2, 1, 1, 0.0, 0.0, GridBagConstraints.FIRST_LINE_START, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0));
					jLabel2.setText("Enabled");
				}
				{
					jLabel3 = new JLabel();
					jPanel1.add(jLabel3, new GridBagConstraints(0, 3, 1, 1, 0.0, 0.0, GridBagConstraints.FIRST_LINE_START, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0));
					jLabel3.setText("Exceptions");
				}
				{
					jPanel3 = new JPanel();
					BorderLayout jPanel3Layout = new BorderLayout();
					jPanel3.setLayout(jPanel3Layout);
					jPanel1.add(jPanel3, new GridBagConstraints(1, 3, 1, 1, 0.0, 0.0, GridBagConstraints.FIRST_LINE_START, GridBagConstraints.BOTH, new Insets(0, 0, 0, 0), 0, 0));
					{
						jScrollPane1 = new JScrollPane();
						jPanel3.add(jScrollPane1, BorderLayout.CENTER);
						jScrollPane1.setPreferredSize(new java.awt.Dimension(516, 55));
						{
							jTableException = new JTableException();
							jScrollPane1.setViewportView(jTableException);
						}
					}
					{
						jPanel4 = new JPanel();
						GridBagLayout jPanel4Layout = new GridBagLayout();
						jPanel3.add(jPanel4, BorderLayout.EAST);
						jPanel4Layout.rowWeights = new double[] { 0.0, 0.1 };
						jPanel4Layout.rowHeights = new int[] { 15, 7 };
						jPanel4Layout.columnWeights = new double[] { 0.1 };
						jPanel4Layout.columnWidths = new int[] { 7 };
						jPanel4.setLayout(jPanel4Layout);
						jPanel4.setBorder(BorderFactory.createEmptyBorder(0, 3, 0, 3));
						{
							jButtonAddExceptionRow = new JButton();
							jPanel4.add(jButtonAddExceptionRow, new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0, GridBagConstraints.FIRST_LINE_START, GridBagConstraints.HORIZONTAL, new Insets(0, 0, 0, 0), 0, 0));
							jButtonAddExceptionRow.setText("Add row");
						}
						{
							jButtonRemoveExceptionRow = new JButton();
							jPanel4.add(jButtonRemoveExceptionRow, new GridBagConstraints(0, 1, 1, 1, 0.0, 0.0, GridBagConstraints.FIRST_LINE_START, GridBagConstraints.HORIZONTAL, new Insets(0, 0, 0, 0), 0, 0));
							jButtonRemoveExceptionRow.setText("Remove row");
						}
					}
				}
				{
					jLabel4 = new JLabel();
					jPanel1.add(jLabel4, new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0, GridBagConstraints.FIRST_LINE_START, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0));
					jLabel4.setText("Rule");
				}
				{
					ComboBoxModel jComboBox1Model = new DefaultComboBoxModel(new Object[] { AbstractRuleType.back_call, AbstractRuleType.skip_call });
					jComboBoxAppliedRule = new JComboBox();
					jPanel1.add(jComboBoxAppliedRule, new GridBagConstraints(1, 0, 1, 1, 0.0, 0.0, GridBagConstraints.FIRST_LINE_START, GridBagConstraints.HORIZONTAL, new Insets(0, 0, 0, 0), 0, 0));
					jComboBoxAppliedRule.setModel(jComboBox1Model);
				}
				{
					ComboBoxModel jComboBox2Model = new DefaultComboBoxModel(new String[] { "" });
					jComboBoxToLayer = new JComboBox();
					jPanel1.add(jComboBoxToLayer, new GridBagConstraints(1, 1, 1, 1, 0.0, 0.0, GridBagConstraints.FIRST_LINE_START, GridBagConstraints.HORIZONTAL, new Insets(0, 0, 0, 0), 0, 0));
					jComboBoxToLayer.setModel(jComboBox2Model);
				}
				{
					jCheckBoxEnabled = new JCheckBox();
					jPanel1.add(jCheckBoxEnabled, new GridBagConstraints(1, 2, 1, 1, 0.0, 0.0, GridBagConstraints.FIRST_LINE_START, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0));
				}
			}
			{
				jPanel2 = new JPanel();
				getContentPane().add(jPanel2, BorderLayout.SOUTH);
				{
					jButtonCancel = new JButton();
					jPanel2.add(jButtonCancel);
					jButtonCancel.setText("Cancel");
				}
				{
					jButtonSave = new JButton();
					jPanel2.add(jButtonSave);
					jButtonSave.setText("Add");
				}

			}
			pack();
			this.setSize(677, 300);
		} catch (Exception e) {
			// add your error handling code here
			e.printStackTrace();
		}
	}

}
