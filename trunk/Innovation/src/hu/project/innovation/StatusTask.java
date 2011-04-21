package hu.project.innovation;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;

public class StatusTask extends JPanel {

	private static final long serialVersionUID = -7360960342696885795L;
	private String defaultMessage = "Idle";
	private String message = "";
	private JLabel jLabelStatus;
	private JProgressBar jProgressBar1;

	private static StatusTask instance;

	public static StatusTask getInstance() {
		if (instance == null) {
			instance = new StatusTask();
		}
		return instance;
	}

	public static StatusTask getInstance(String newMessage) {
		instance = StatusTask.getInstance();

		instance.setMessage(newMessage);
		return instance;
	}

	private void setMessage(String newMessage) {
		if (newMessage.trim().equals("")) {
			message = defaultMessage;
		} else {
			message = newMessage;
		}
	}

	private StatusTask() {
		super();

		GridBagLayout jPanel1Layout = new GridBagLayout();
		jPanel1Layout.rowWeights = new double[] { 0.1 };
		jPanel1Layout.rowHeights = new int[] { 7 };
		jPanel1Layout.columnWeights = new double[] { 0.0, 0.1 };
		jPanel1Layout.columnWidths = new int[] { 787, 7 };
		setLayout(jPanel1Layout);
		setBorder(BorderFactory.createEmptyBorder(0, 5, 5, 5));

		jLabelStatus = new JLabel(defaultMessage);
		add(jLabelStatus, new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0, GridBagConstraints.FIRST_LINE_START, GridBagConstraints.BOTH, new Insets(0, 0, 0, 0), 0, 0));

		jProgressBar1 = new JProgressBar();
		jProgressBar1.setValue(0);

		add(jProgressBar1, new GridBagConstraints(1, 0, 1, 1, 0.0, 0.0, GridBagConstraints.FIRST_LINE_START, GridBagConstraints.BOTH, new Insets(0, 0, 0, 0), 0, 0));
		jProgressBar1.setPreferredSize(new java.awt.Dimension(823, 14));
	}

	public void start() {
		jLabelStatus.setText(message);
		jProgressBar1.setIndeterminate(true);

	}

	public void stop() {
		jLabelStatus.setText(defaultMessage);
		jProgressBar1.setIndeterminate(false);

	}
}
