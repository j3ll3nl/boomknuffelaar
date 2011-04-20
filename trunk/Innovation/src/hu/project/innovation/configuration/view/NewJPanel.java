package hu.project.innovation.configuration.view;

import hu.project.innovation.configuration.controller.DefinitionController;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;

import javax.swing.BorderFactory;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ListModel;

/**
 * This code was edited or generated using CloudGarden's Jigloo SWT/Swing GUI Builder, which is free for non-commercial use. If Jigloo is being used commercially (ie, by a corporation, company or
 * business for any purpose whatever) then you should purchase a license for each developer using Jigloo. Please visit www.cloudgarden.com for details. Use of Jigloo implies acceptance of these
 * licensing terms. A COMMERCIAL LICENSE HAS NOT BEEN PURCHASED FOR THIS MACHINE, SO JIGLOO OR THIS CODE CANNOT BE USED LEGALLY FOR ANY CORPORATE OR COMMERCIAL PURPOSE.
 */
public class NewJPanel extends javax.swing.JPanel {

	private static final long serialVersionUID = 7442552399461704491L;
	private DefinitionController definitioncontroller;
	private JSplitPane jSplitPane1;
	private JPanel jPanel1;
	private JList jList1;
	private JScrollPane jScrollPane2;
	private JTextArea jTextArea1;
	private JLabel jLabel2;
	private JTextField jTextField1;
	private JLabel jLabel1;
	private JPanel jPanel8;
	private JPanel jPanel7;
	private JButton jButton4;
	private JButton jButton3;
	private JButton jButton1;
	private JScrollPane jScrollPane1;
	private JButton jButton2;
	private JPanel jPanel6;
	private JPanel jPanel5;
	private JPanel jPanel4;
	private JPanel jPanel3;
	private JPanel jPanel2;

	{
		// Set Look & Feel
		try {
			javax.swing.UIManager.setLookAndFeel(javax.swing.UIManager.getSystemLookAndFeelClassName());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Auto-generated main method to display this JPanel inside a new JFrame.
	 */
	public NewJPanel() {
		super();
		initGUI();
	}

	public NewJPanel(DefinitionController dc) {
		super();
		definitioncontroller = dc;
		initGUI();
	}

	public void initGUI() {
		try {
			{
				BorderLayout thisLayout = new BorderLayout();
				this.setLayout(thisLayout);
				this.setPreferredSize(new java.awt.Dimension(611, 393));
				{
					jSplitPane1 = new JSplitPane();
					jSplitPane1.setDividerLocation(300);
					this.add(jSplitPane1, BorderLayout.CENTER);
					jSplitPane1.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
					{
						jPanel2 = new JPanel();
						BorderLayout jPanel2Layout = new BorderLayout();
						jPanel2.setLayout(jPanel2Layout);
						jSplitPane1.add(jPanel2, JSplitPane.LEFT);
						jPanel2.setBorder(BorderFactory.createEmptyBorder(3, 3, 3, 3));
						{
							jPanel3 = new JPanel();
							BorderLayout jPanel3Layout = new BorderLayout();
							jPanel3.setLayout(jPanel3Layout);
							jPanel2.add(jPanel3, BorderLayout.CENTER);
							jPanel3.setBorder(BorderFactory.createTitledBorder("Layer hierarchy"));
							{
								jPanel5 = new JPanel();
								BorderLayout jPanel5Layout = new BorderLayout();
								jPanel5.setLayout(jPanel5Layout);
								jPanel3.add(jPanel5, BorderLayout.CENTER);
								{
									jScrollPane1 = new JScrollPane();
									jPanel5.add(jScrollPane1, BorderLayout.CENTER);
									jScrollPane1.setPreferredSize(new java.awt.Dimension(383, 213));
									{
										ListModel jList1Model = new DefaultComboBoxModel(new String[] { "Item One", "Item Two" });
										jList1 = new JList();
										jScrollPane1.setViewportView(jList1);
										jList1.setModel(jList1Model);
									}
								}
							}
							{
								jPanel6 = new JPanel();
								GridLayout jPanel6Layout = new GridLayout(2, 2);
								jPanel6.setLayout(jPanel6Layout);
								jPanel3.add(jPanel6, BorderLayout.SOUTH);
								jPanel6Layout.setColumns(2);
								jPanel6Layout.setHgap(5);
								jPanel6Layout.setVgap(5);
								jPanel6Layout.setRows(2);
								{
									jButton1 = new JButton();
									jPanel6.add(jButton1);
									jButton1.setText("New layer");
								}
								{
									jButton2 = new JButton();
									jPanel6.add(jButton2);
									jButton2.setText("Remove layer");
								}
								{
									jButton3 = new JButton();
									jPanel6.add(jButton3);
									jButton3.setText("Move up");
								}
								{
									jButton4 = new JButton();
									jPanel6.add(jButton4);
									jButton4.setText("Move down");
								}
							}
						}
					}
					{
						jPanel1 = new JPanel();
						BorderLayout jPanel1Layout = new BorderLayout();
						jPanel1.setLayout(jPanel1Layout);
						jSplitPane1.add(jPanel1, JSplitPane.RIGHT);
						jPanel1.setBorder(BorderFactory.createEmptyBorder(3, 3, 3, 3));
						{
							jPanel4 = new JPanel();
							GridLayout jPanel4Layout = new GridLayout(2, 2);
							jPanel4Layout.setColumns(2);
							jPanel4Layout.setHgap(5);
							jPanel4Layout.setVgap(5);
							jPanel4Layout.setRows(2);
							jPanel4.setLayout(jPanel4Layout);
							jPanel1.add(jPanel4, BorderLayout.NORTH);
							jPanel4.setBorder(BorderFactory.createTitledBorder("Layer configuration"));
							{
								jLabel1 = new JLabel();
								jPanel4.add(jLabel1);
								jLabel1.setText("Layer name");
							}
							{
								jTextField1 = new JTextField();
								jPanel4.add(jTextField1);
							}
							{
								jLabel2 = new JLabel();
								jPanel4.add(jLabel2);
								jLabel2.setText("Description");
							}
							{
								jScrollPane2 = new JScrollPane();
								jPanel4.add(jScrollPane2);
								jScrollPane2.setPreferredSize(new java.awt.Dimension(142, 26));
								{
									jTextArea1 = new JTextArea();
									jScrollPane2.setViewportView(jTextArea1);
								}
							}
						}
						{
							jPanel7 = new JPanel();
							BorderLayout jPanel7Layout = new BorderLayout();
							jPanel7.setLayout(jPanel7Layout);
							jPanel1.add(jPanel7, BorderLayout.CENTER);
							jPanel7.setBorder(BorderFactory.createTitledBorder("Components which are assigned to this layer"));
						}
						{
							jPanel8 = new JPanel();
							BorderLayout jPanel8Layout = new BorderLayout();
							jPanel8.setLayout(jPanel8Layout);
							jPanel1.add(jPanel8, BorderLayout.SOUTH);
							jPanel8.setBorder(BorderFactory.createTitledBorder("Rules for this layer"));
						}
					}

				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
