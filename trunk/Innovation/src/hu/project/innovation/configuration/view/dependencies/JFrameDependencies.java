package hu.project.innovation.configuration.view.dependencies;
import java.awt.BorderLayout;
import java.awt.LayoutManager;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;


/**
* This code was edited or generated using CloudGarden's Jigloo
* SWT/Swing GUI Builder, which is free for non-commercial
* use. If Jigloo is being used commercially (ie, by a corporation,
* company or business for any purpose whatever) then you
* should purchase a license for each developer using Jigloo.
* Please visit www.cloudgarden.com for details.
* Use of Jigloo implies acceptance of these licensing terms.
* A COMMERCIAL LICENSE HAS NOT BEEN PURCHASED FOR
* THIS MACHINE, SO JIGLOO OR THIS CODE CANNOT BE USED
* LEGALLY FOR ANY CORPORATE OR COMMERCIAL PURPOSE.
*/
public class JFrameDependencies extends javax.swing.JFrame {
	private JTable jTableFoundComponents;
	private JScrollPane jScrollPane1;
	private JButton jButton1;
	private JPanel jPanelPom;
	private JScrollPane jScrollPane2;
	private JButton jButton4;
	private JButton jButton3;
	private JButton jButtonAllowDepAdd;
	private JButton jButtonSearch;
	private JPanel jPanel2;
	private JTextField jTextField1;
	private JButton jButtonDeletePomD;
	private JButton jButtonAddPomD;
	private JScrollPane jScrollPane3;
	private JTable jTableDepsPom;
	private JTable jTableAllowedDeps;
	private JPanel jPanelAllowed;
	private JTabbedPane jTabbedPane1;
	private JPanel jPanel1;

	/**
	* Auto-generated main method to display this JFrame
	*/
	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				JFrameDependencies inst = new JFrameDependencies();
				inst.setLocationRelativeTo(null);
				inst.setVisible(true);
			}
		});
	}
	
	public JFrameDependencies() {
		super();
		initGUI();
	}
	
	private void initGUI() {
		try {
			setTitle("Dependencies");
			setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
			{
				// Eerste tab (found dependencies)
				jTabbedPane1 = new JTabbedPane();
				getContentPane().add(jTabbedPane1, BorderLayout.NORTH);
				jTabbedPane1.setPreferredSize(new java.awt.Dimension(610, 419));
				{
					jPanel1 = new JPanel();
					jTabbedPane1.addTab("Undefined", null, jPanel1, null);
					jPanel1.setPreferredSize(new java.awt.Dimension(605, 219));
					jPanel1.setBorder(BorderFactory.createTitledBorder("Found project dependencies that are not defined in the pom file"));
					{
						jScrollPane1 = new JScrollPane();
						jPanel1.add(jScrollPane1);
						jScrollPane1.setPreferredSize(new java.awt.Dimension(580, 240));
						jScrollPane1.setFocusable(false);
						{							
							TableModel jTableFoundComponentsModel = 
								new DefaultTableModel() {
								private static final long serialVersionUID = 1L;
								// de velden moeten niet editable zijn.
								public boolean isCellEditable(int row, int col) {
									return false;
								}
							};
							jTableFoundComponents = new JTable();
							
							jScrollPane1.setViewportView(jTableFoundComponents);
							jTableFoundComponents.setModel(jTableFoundComponentsModel);
							
						}
					}
					{
						jButton1 = new JButton();
						jPanel1.add(jButton1);
						jButton1.setText("Add Dependency");
					}
				}
				{
					jPanelPom = new JPanel();
					jTabbedPane1.addTab("Project Object Model", null, jPanelPom, null);
					{
						jScrollPane3 = new JScrollPane();
						jPanelPom.add(jScrollPane3);
						jScrollPane3.setPreferredSize(new java.awt.Dimension(580, 240));
						{
							TableModel jTableFoundComponentsModel3 = 
								new DefaultTableModel() {
								private static final long serialVersionUID = 1L;
								// de velden moeten niet editable zijn.
								public boolean isCellEditable(int row, int col) {
									return false;
								}
							};
							jTableDepsPom = new JTable();
							jScrollPane3.setViewportView(getJTableDepsPom());
							jTableDepsPom.setModel(jTableFoundComponentsModel3);
						}
					}
					{
						jButtonAddPomD = new JButton();
						jPanelPom.add(jButtonAddPomD);
						jButtonAddPomD.setText("Add dependency");
					}
					{
						jButtonDeletePomD = new JButton();
						jPanelPom.add(jButtonDeletePomD);
						jButtonDeletePomD.setText("Remove");
					}
				}
				{
					jPanelAllowed = new JPanel();
					jTabbedPane1.addTab("Allowed", null, jPanelAllowed, null);
					jPanelAllowed.setPreferredSize(new java.awt.Dimension(605, 368));
					{
						jPanel2 = new JPanel();
						jPanelAllowed.add(jPanel2);
						jPanel2.setPreferredSize(new java.awt.Dimension(193, 34));
						{
							jTextField1 = new JTextField();
							jPanel2.add(jTextField1);
							jTextField1.setText("dependency");
						}
						{
							jButtonSearch = new JButton();
							jPanel2.add(jButtonSearch);
							jButtonSearch.setText("Search");
						}
					}
					{
						jScrollPane2 = new JScrollPane();
						jPanelAllowed.add(jScrollPane2);
						jScrollPane2.setPreferredSize(new java.awt.Dimension(580, 296));
						{							
							TableModel jTableFoundComponentsModel2 = 
								new DefaultTableModel() {
								private static final long serialVersionUID = 1L;
								// de velden moeten niet editable zijn.
								public boolean isCellEditable(int row, int col) {
									return false;
								}
							};
							jTableAllowedDeps = new JTable();
							jScrollPane2.setViewportView(jTableAllowedDeps);
							jTableAllowedDeps.setModel(jTableFoundComponentsModel2);
						}
					}
					{
						jButtonAllowDepAdd = new JButton();
						jPanelAllowed.add(jButtonAllowDepAdd);
						jButtonAllowDepAdd.setText("Add dependency");
						jButtonAllowDepAdd.setName("AddDependencyFound");
					}
					{
						jButton3 = new JButton();
						jPanelAllowed.add(jButton3);
						jButton3.setText("Edit");
					}
					{
						jButton4 = new JButton();
						jPanelAllowed.add(jButton4);
						jButton4.setText("Remove");
					}
				}

			}
			pack();
			this.setSize(610, 449);
		} catch (Exception e) {
		    //add your error handling code here
			e.printStackTrace();
		}
	}
	
	public JTable getJTableFoundComponents() {
		return jTableFoundComponents;
	}
	
	/**
	 * 
	 * @return button toevoegen
	 */
	public JButton getJButton1() {
		return jButton1;
	}
	
	public JButton getJButtonDeletePomD() {
		return jButtonDeletePomD;
	}
	
	public JButton getJButtonAllowDepAdd() {
		return jButtonAllowDepAdd;
	}
	
	public JButton getJButtonAllowDepEdit() {
		return jButton3;
	}
	
	public JButton getJButtonAllowDepRemove() {
		return jButton4;
	}
	public JTable getJTableAllowedDeps() {
		return jTableAllowedDeps;
	}
	
	public JTable getJTableDepsPom() {
		return jTableDepsPom;
	}
}