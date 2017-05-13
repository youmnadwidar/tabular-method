package views;

import javax.swing.JPanel;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.JTextField;
import javax.swing.DropMode;
import javax.swing.SwingConstants;
import javax.swing.Timer;

import models.Minterm;
import models.QMM;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import java.awt.Component;
import javax.swing.JButton;
import javax.swing.AbstractAction;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.LayoutStyle.ComponentPlacement;
import java.awt.SystemColor;
import java.awt.Color;
import java.awt.Canvas;
import javax.swing.JSeparator;
import javax.swing.JProgressBar;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;

public class InputPanel extends JPanel {
	private JTextField mintermInput;
	private JTextField dcInput;
	private JTextField bitsInput;
	OutputPanel output;
	QMM qmm;

	/**
	 * Create the panel.
	 */
	public InputPanel() {
		setBackground(new Color(255, 255, 204));

		JLabel lblMinterms = new JLabel("minterms");
		lblMinterms
				.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblMinterms
				.setForeground(SystemColor.textHighlight);
		lblMinterms.setHorizontalAlignment(
				SwingConstants.CENTER);

		mintermInput = new JTextField();
		mintermInput.setHorizontalAlignment(
				SwingConstants.LEFT);
		mintermInput.setColumns(50);

		JLabel label = new JLabel("Don't cares");
		label.setFont(new Font("Tahoma", Font.BOLD, 11));
		label.setForeground(SystemColor.textHighlight);
		label.setHorizontalAlignment(SwingConstants.CENTER);

		dcInput = new JTextField();
		dcInput.setHorizontalAlignment(SwingConstants.LEFT);
		dcInput.setColumns(50);

		JButton btnMinimize = new JButton("Minimize");
		btnMinimize.addFocusListener(new FocusAdapter() {
			@Override
			public void focusGained(FocusEvent e) {
				Color bg = new Color(204, 255, 153);
				btnMinimize.setBackground(bg);
			}

			@Override
			public void focusLost(FocusEvent e) {
				Color bg = new Color(102, 204, 0);
				btnMinimize.setBackground(bg);
			}
		});
			Timer timer = new Timer(40, new ActionListener() {
		    @Override
		    public void actionPerformed(ActionEvent ae) {
		       output.setState( qmm.getCurrentProccess() );
		       repaint();
		       
		    }
		});
		btnMinimize
				.setFont(new Font("Tahoma", Font.BOLD, 11));
		btnMinimize.setForeground(new Color(255, 255, 204));
		btnMinimize.setBackground(new Color(102, 204, 0));
		btnMinimize.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				qmm = new QMM(mintermInput.getText(),
						dcInput.getText(),
						bitsInput.getText());
				output.showResult(qmm.getReduced());
				output.setSteps(qmm.getSteps());
				timer.start();
			}
		});
		
		JLabel label_1 = new JLabel("number of bits");
		label_1.setFont(new Font("Tahoma", Font.BOLD, 11));
		label_1.setForeground(SystemColor.textHighlight);
		label_1.setHorizontalAlignment(
				SwingConstants.CENTER);

		bitsInput = new JTextField();
		bitsInput.setHorizontalAlignment(
				SwingConstants.LEFT);
		bitsInput.setColumns(50);
		
		JPanel panel = new JPanel();
		panel.setBackground(SystemColor.textHighlight);
		GroupLayout groupLayout = new GroupLayout(this);
		groupLayout.setAutoCreateGaps(true);
		groupLayout.setHorizontalGroup(groupLayout
				.createParallelGroup(Alignment.LEADING)
				.addComponent(panel,
						GroupLayout.DEFAULT_SIZE, 450,
						Short.MAX_VALUE)
				.addGroup(groupLayout
						.createSequentialGroup()
						.addContainerGap()
						.addGroup(groupLayout
								.createParallelGroup(
										Alignment.TRAILING)
								.addGroup(Alignment.LEADING,
										groupLayout
												.createSequentialGroup()
												.addComponent(
														lblMinterms)
												.addPreferredGap(
														ComponentPlacement.RELATED,
														63,
														Short.MAX_VALUE)
												.addComponent(
														mintermInput,
														GroupLayout.PREFERRED_SIZE,
														60,
														GroupLayout.PREFERRED_SIZE))
								.addGroup(Alignment.LEADING,
										groupLayout
												.createSequentialGroup()
												.addComponent(
														label_1,
														GroupLayout.PREFERRED_SIZE,
														77,
														GroupLayout.PREFERRED_SIZE)
												.addPreferredGap(
														ComponentPlacement.RELATED,
														29,
														Short.MAX_VALUE)
												.addComponent(
														bitsInput,
														GroupLayout.PREFERRED_SIZE,
														60,
														GroupLayout.PREFERRED_SIZE))
								.addGroup(Alignment.LEADING,
										groupLayout
												.createSequentialGroup()
												.addComponent(
														label)
												.addPreferredGap(
														ComponentPlacement.RELATED,
														52,
														Short.MAX_VALUE)
												.addComponent(
														dcInput,
														GroupLayout.PREFERRED_SIZE,
														60,
														GroupLayout.PREFERRED_SIZE)))
						.addGap(151)
						.addComponent(btnMinimize)
						.addGap(52)));
		groupLayout.setVerticalGroup(groupLayout
				.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout
						.createSequentialGroup()
						.addComponent(panel,
								GroupLayout.PREFERRED_SIZE,
								51,
								GroupLayout.PREFERRED_SIZE)
						.addGap(18)
						.addGroup(groupLayout
								.createParallelGroup(
										Alignment.LEADING)
								.addGroup(groupLayout
										.createSequentialGroup()
										.addGroup(
												groupLayout
														.createParallelGroup(
																Alignment.BASELINE)
														.addComponent(
																lblMinterms)
														.addComponent(
																mintermInput,
																GroupLayout.PREFERRED_SIZE,
																GroupLayout.DEFAULT_SIZE,
																GroupLayout.PREFERRED_SIZE))
										.addPreferredGap(
												ComponentPlacement.UNRELATED)
										.addGroup(
												groupLayout
														.createParallelGroup(
																Alignment.BASELINE)
														.addComponent(
																label)
														.addComponent(
																dcInput,
																GroupLayout.PREFERRED_SIZE,
																GroupLayout.DEFAULT_SIZE,
																GroupLayout.PREFERRED_SIZE))
										.addGap(18)
										.addGroup(
												groupLayout
														.createParallelGroup(
																Alignment.BASELINE)
														.addComponent(
																label_1)
														.addComponent(
																bitsInput,
																GroupLayout.PREFERRED_SIZE,
																GroupLayout.DEFAULT_SIZE,
																GroupLayout.PREFERRED_SIZE)))
								.addComponent(btnMinimize))
						.addContainerGap(149,
								Short.MAX_VALUE)));

		JLabel lblQuineMcclusqyMinimizer = new JLabel(
				"Quine McClusqy Minimizer");
		lblQuineMcclusqyMinimizer
				.setForeground(new Color(255, 255, 0));
		lblQuineMcclusqyMinimizer
				.setAlignmentX(Component.CENTER_ALIGNMENT);
		lblQuineMcclusqyMinimizer
				.setAlignmentY(Component.TOP_ALIGNMENT);
		lblQuineMcclusqyMinimizer.setHorizontalAlignment(
				SwingConstants.TRAILING);
		lblQuineMcclusqyMinimizer.setFont(new Font(
				"AvantGarde-Demi", Font.PLAIN, 18));
		GroupLayout gl_panel = new GroupLayout(panel);
		gl_panel.setHorizontalGroup(gl_panel
				.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel.createSequentialGroup()
						.addContainerGap()
						.addComponent(
								lblQuineMcclusqyMinimizer)
						.addContainerGap(198,
								Short.MAX_VALUE)));
		gl_panel.setVerticalGroup(gl_panel
				.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel.createSequentialGroup()
						.addContainerGap()
						.addComponent(
								lblQuineMcclusqyMinimizer)
						.addContainerGap(19,
								Short.MAX_VALUE)));
		panel.setLayout(gl_panel);
		setLayout(groupLayout);

	}

	public void setOuputPanel(OutputPanel panel) {
		this.output = panel;
	}
}
