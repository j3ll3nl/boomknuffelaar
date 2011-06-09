package hu.project.innovation.analyse.view.tables;

import hu.project.innovation.configuration.view.tables.AbstractJTable;

import javax.swing.table.TableColumn;

public class JTableDependencie extends AbstractJTable {

	private static final long serialVersionUID = -7969643318117573036L;

	public JTableDependencie() {
		super();

		tablemodel.addColumn("Number");
		tablemodel.addColumn("Dependency");
		tablemodel.addColumn("Version");
		tablemodel.addColumn("Type");

		TableColumn column = null;
		for (int i = 0; i < getColumnCount(); i++) {
			column = getColumnModel().getColumn(i);
			if (i == 0) {
				column.setPreferredWidth(75); // Component name
			} else if (i == 1) {
				column.setPreferredWidth(75); // To layer
			} else if (i == 2) {
				column.setPreferredWidth(25); // Enabled
			} else if (i == 3) {
				column.setPreferredWidth(50); // Exceptions
			}
		}
	}
}
