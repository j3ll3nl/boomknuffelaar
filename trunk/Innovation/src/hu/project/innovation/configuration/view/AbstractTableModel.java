package hu.project.innovation.configuration.view;

import javax.swing.table.DefaultTableModel;

public class AbstractTableModel extends DefaultTableModel {

	private static final long serialVersionUID = 3535559394466714205L;

	public AbstractTableModel() {
		super();
	}

	@Override
	public boolean isCellEditable(int row, int column) {
		return false;
	}
}
