package hu.project.innovation.configuration.view;

import hu.project.innovation.utils.Log;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;

public class JPanelStatus extends JPanel {

	private static final long serialVersionUID = -7360960342696885795L;
	private String defaultMessage = "Idle";
	//TODO: change String to a Strack with pop/flop ;)
	private String message = "";
	private JLabel jLabelStatus;
	private JProgressBar jProgressBar1;

	private static JPanelStatus instance;

	private JPanelStatus() {
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

	/**
	 * Because there can be only one instance of this jpanel the singleton pattern is implemented.
	 * 
	 * @return An StatusTask object
	 */
	public static JPanelStatus getInstance() {
		if (instance == null) {
			instance = new JPanelStatus();
		}
		return instance;
	}

	/**
	 * @see <code>getInstance()</code>
	 * @param newMessage
	 * @return
	 */
	public static JPanelStatus getInstance(String newMessage) {
		instance = JPanelStatus.getInstance();

		instance.setMessage(newMessage);
		return instance;
	}

	/**
	 * Sets the message in an local variable
	 * 
	 * @param newMessage The message that needs to show when the user calls <code>start()</code>
	 */
	private void setMessage(String newMessage) {
		if (newMessage.trim().equals("")) {
			message = defaultMessage;
		} else {
			message = newMessage;
		}
	}

	/**
	 * This method shows the given message and starts the progressbar.
	 */
	public void start() {
		Log.i(this, "start() - Message: " + message);
		jLabelStatus.setText(message);
		jProgressBar1.setIndeterminate(true);
		repaint();

	}

	/**
	 * This method will replace the message with an default message and stops the progressbar.
	 */
	public void stop() {
		Log.i(this, "stop() - Message: " + defaultMessage);
		jLabelStatus.setText(defaultMessage);
		jProgressBar1.setIndeterminate(false);

	}
}