package hu.project.innovation.configuration.view.dependencies;
import java.awt.BorderLayout;
import javax.swing.JPanel;

import javax.swing.BorderFactory;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
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
				jPanel1 = new JPanel();
				getContentPane().add(jPanel1, BorderLayout.NORTH);
				jPanel1.setPreferredSize(new java.awt.Dimension(610, 165));
				jPanel1.setBorder(BorderFactory.createTitledBorder("Found project dependencies that are not defined in the pom file"));
				{
					jScrollPane1 = new JScrollPane();
					jPanel1.add(jScrollPane1);
					jScrollPane1.setPreferredSize(new java.awt.Dimension(580, 127));
					jScrollPane1.setFocusable(false);
					{
						DefaultTableModel model = new DefaultTableModel();
						
						TableModel jTableFoundComponentsModel = 
							new DefaultTableModel();
						jTableFoundComponents = new JTable();
						
						jScrollPane1.setViewportView(jTableFoundComponents);
						jTableFoundComponents.setModel(jTableFoundComponentsModel);
						
					}
				}
			}
			pack();
			this.setSize(610, 330);
		} catch (Exception e) {
		    //add your error handling code here
			e.printStackTrace();
		}
	}
	
	public JTable getJTableFoundComponents() {
		return jTableFoundComponents;
	}

}
