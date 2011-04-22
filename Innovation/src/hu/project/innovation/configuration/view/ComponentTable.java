package hu.project.innovation.configuration.view;

import javax.swing.JTable;
import javax.swing.table.TableColumn;

public class ComponentTable extends JTable {

	private static final long serialVersionUID = 3535559394466714205L;
	private AbstractTableModel tablemodel;

	public ComponentTable() {
		super();
		setAutoCreateRowSorter(true);
		setFillsViewportHeight(true);

		tablemodel = new AbstractTableModel();
		tablemodel.addColumn("Component name");
		tablemodel.addColumn("Type");
		tablemodel.addColumn("Exceptions");

		setModel(tablemodel);

		TableColumn column = null;
		for (int i = 0; i < getColumnCount(); i++) {
			column = getColumnModel().getColumn(i);
			if (i == 0) {
				column.setPreferredWidth(275); // Component name
			} else if (i == 2) {
				column.setPreferredWidth(25);
			} else {
				column.setPreferredWidth(50);
			}
		}

		Object[] data1 = { "Test", "Class", "0" };
		Object[] data2 = { "Test2", "Class", "0" };
		Object[] data3 = { "Test3", "Class", "0" };
		Object[] data4 = { "Test4", "Class", "0" };
		Object[] data5 = { "Test5", "Class", "0" };
		Object[] data6 = { "Test6", "Class", "0" };
		Object[] data7 = { "Test7", "Class", "0" };
		Object[] data8 = { "Test8", "Class", "0" };

		tablemodel.addRow(data1);
		tablemodel.addRow(data2);
		tablemodel.addRow(data3);
		tablemodel.addRow(data4);
		tablemodel.addRow(data5);
		tablemodel.addRow(data6);
		tablemodel.addRow(data7);
		tablemodel.addRow(data8);
		

	}

}
