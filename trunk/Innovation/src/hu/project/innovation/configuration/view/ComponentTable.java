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
	}

}
