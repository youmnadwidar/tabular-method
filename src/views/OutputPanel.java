package views;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextPane;
import java.awt.SystemColor;
import java.awt.Color;
import java.awt.Font;
import javax.swing.JLabel;
import java.awt.GridLayout;
import javax.swing.JSeparator;
import java.awt.BorderLayout;
import javax.swing.UIManager;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import javax.swing.ScrollPaneConstants;

public class OutputPanel extends JPanel {
	/**
	 * 
	 */
	private static final long serialVersionUID = 7783986719186069245L;
	private JTextPane status;
	private JTextPane result;
	private JTextPane steps;
	JScrollPane scrPane_2;
	JScrollPane scrPane_1;

	/**
	 * Create the panel.
	 */
	public OutputPanel() {
		setBackground(new Color(255, 255, 204));
		setLayout(new BorderLayout(0, 0));

		JPanel panel = new JPanel();
		add(panel);
		 		panel.setForeground(SystemColor.textHighlight);
		panel.setBackground(new Color(255, 255, 204));
		GridBagLayout gbl_panel = new GridBagLayout();
		gbl_panel.columnWidths = new int[] {42, 161, 1, 260,
				0};
		gbl_panel.rowHeights = new int[] {23, 101, 14, 129,
				0};
		gbl_panel.columnWeights = new double[] {1.0, 0.0,
				0.0, 0.0, Double.MIN_VALUE};
		gbl_panel.rowWeights = new double[] {0.0, 1.0, 0.0,
				1.0, Double.MIN_VALUE};
		panel.setLayout(gbl_panel);

		JSeparator separator = new JSeparator();
		GridBagConstraints gbc_separator = new GridBagConstraints();
		gbc_separator.anchor = GridBagConstraints.WEST;
		gbc_separator.insets = new Insets(0, 0, 5, 5);
		gbc_separator.gridx = 2;
		gbc_separator.gridy = 0;
		panel.add(separator, gbc_separator);

		status = new JTextPane();
		status.setForeground(SystemColor.activeCaption);
		status.setFont(new Font("Tahoma",
				Font.BOLD | Font.ITALIC, 11));
		status.setEditable(false);
		status.setBackground(new Color(255, 255, 204));
		GridBagConstraints gbc_status = new GridBagConstraints();
		gbc_status.anchor = GridBagConstraints.NORTHWEST;
		gbc_status.insets = new Insets(0, 0, 5, 0);
		gbc_status.gridx = 3;
		gbc_status.gridy = 0;
		panel.add(status, gbc_status);

		JPanel panel_1 = new JPanel();
		panel_1.setLayout(new BorderLayout(0, 0));
		
		JLabel lblOutput = new JLabel("Output:");
		panel_1.add(lblOutput, BorderLayout.NORTH);
		lblOutput.setForeground(SystemColor.textHighlight);
		lblOutput
				.setFont(new Font("Tahoma", Font.BOLD, 11));

		result = new JTextPane();
		//panel_1.add(result, BorderLayout.CENTER);
		result.setForeground(SystemColor.activeCaption);
		result.setFont(new Font("Tahoma",
				Font.BOLD | Font.ITALIC, 11));
		result.setEditable(false);
		result.setBackground(new Color(235, 235, 204));
		GridBagConstraints gbc_panel_1 = new GridBagConstraints();
		gbc_panel_1.fill = GridBagConstraints.BOTH;
		gbc_panel_1.insets = new Insets(0, 0, 5, 0);
		gbc_panel_1.gridwidth = 4;
		gbc_panel_1.gridx = 0;
		gbc_panel_1.gridy = 1;
		panel.add(panel_1, gbc_panel_1);

		JPanel panel_2 = new JPanel();
		panel_2.setBorder(
				UIManager.getBorder("TextPane.border"));
		panel_2.setLayout(new BorderLayout(0, 0));
		JLabel lblsteps = new JLabel("Steps:");
		panel_2.add(lblsteps, BorderLayout.NORTH);
		lblsteps.setForeground(SystemColor.textHighlight);
		lblsteps.setFont(new Font("Tahoma", Font.BOLD, 11));

		

		steps = new JTextPane();
		steps.setForeground(SystemColor.activeCaption);
		steps.setFont(new Font("Tahoma",
				Font.BOLD | Font.ITALIC, 11));
		steps.setEditable(false);
		steps.setBackground(new Color(235, 235, 204));
		
		scrPane_1 = new JScrollPane(result);
		scrPane_1.setEnabled(true);
		scrPane_1.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
	
		panel_1.add(scrPane_1,BorderLayout.CENTER);
		
		scrPane_2 = new JScrollPane(steps);
		scrPane_2.setEnabled(true);
		scrPane_2.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
		panel_2.add(scrPane_2,BorderLayout.CENTER);

		result.setAutoscrolls(true);
		//panel_2.add(steps, BorderLayout.CENTER);
		GridBagConstraints gbc_panel_2 = new GridBagConstraints();
		gbc_panel_2.fill = GridBagConstraints.BOTH;
		gbc_panel_2.gridwidth = 4;
		gbc_panel_2.gridx = 0;
		gbc_panel_2.gridy = 3;
		panel.add(panel_2, gbc_panel_2);

	}

	public void showResult(String text) {
		result.setText(text);
	}

	public void setState(String text) {
		status.setText(text);
	}

	public void setSteps(String text) {
		steps.setText(text);
        scrPane_2.getVerticalScrollBar().setValue(scrPane_2.getVerticalScrollBar().getMaximum());

	}

	public void viewSteps(boolean choice) {

		steps.setVisible(choice);

	}
}
