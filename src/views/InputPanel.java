package views;

import javax.swing.JPanel;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.Timer;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import java.awt.Component;
import javax.swing.JButton;
import javax.swing.JFileChooser;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;

import javax.swing.LayoutStyle.ComponentPlacement;

import controllers.QMM;

import java.awt.SystemColor;
import java.awt.Color;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;

public class InputPanel extends JPanel {
	/**
	 * 
	 */
	private static final long serialVersionUID = 7945084502171095720L;
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
		Timer timer = new Timer(40, new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent ae) {
				output.setState(qmm.getCurrentProccess());
				repaint();

			}
		});
		JButton btnUpload = new JButton("Upload A File");
		btnUpload.setToolTipText("Upload a file representing a digital function, see help for further instructions.");
		btnUpload.setFont(new Font("Tahoma", Font.BOLD, 11));
		btnUpload.setForeground(new Color(255, 255, 204));
		btnUpload.setBackground(new Color(102, 204, 0));
		String cwd = System.getProperty("user.dir");
		JFileChooser jfc = new JFileChooser(cwd);
		btnUpload.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if(jfc.showOpenDialog(getParent()) == JFileChooser.APPROVE_OPTION){
					File file =  jfc.getSelectedFile();
					qmm = new QMM(file);
					output.showResult(qmm.getReduced());
					output.setSteps(qmm.getSteps());
					timer.start();
				}
			}
		});
		
		add(jfc);
		JLabel lblMinterms = new JLabel("minterms");
		lblMinterms
				.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblMinterms
				.setForeground(SystemColor.textHighlight);
		lblMinterms.setHorizontalAlignment(
				SwingConstants.CENTER);

		mintermInput = new JTextField();
		mintermInput.setToolTipText("Minterms of the function sperated by commas.");
		mintermInput.setHorizontalAlignment(
				SwingConstants.LEFT);
		mintermInput.setColumns(50);

		JLabel label = new JLabel("Don't cares");
		label.setFont(new Font("Tahoma", Font.BOLD, 11));
		label.setForeground(SystemColor.textHighlight);
		label.setHorizontalAlignment(SwingConstants.CENTER);

		dcInput = new JTextField();
		dcInput.setToolTipText("Don't Cares of the functions seperated with commas, leave empty if there isn't any.");
		dcInput.setHorizontalAlignment(SwingConstants.LEFT);
		dcInput.setColumns(50);

		JButton btnMinimize = new JButton("Minimize");
		btnMinimize.setToolTipText("Minimize the function, minterms and number of variables are required.");
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
		
		btnMinimize
				.setFont(new Font("Tahoma", Font.BOLD, 11));
		btnMinimize.setForeground(new Color(255, 255, 204));
		btnMinimize.setBackground(new Color(102, 204, 0));
		btnMinimize.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				timer.start();
				qmm = new QMM(mintermInput.getText(),
						dcInput.getText(),
						bitsInput.getText());
				output.showResult(qmm.getReduced());
				output.setSteps(qmm.getSteps());
				
			}
		});

		JLabel label_1 = new JLabel("number of bits");
		label_1.setFont(new Font("Tahoma", Font.BOLD, 11));
		label_1.setForeground(SystemColor.textHighlight);
		label_1.setHorizontalAlignment(
				SwingConstants.CENTER);

		bitsInput = new JTextField();
		bitsInput.setToolTipText("Number of Input Variables of the functions.");
		bitsInput.setHorizontalAlignment(
				SwingConstants.LEFT);
		bitsInput.setColumns(50);

		JPanel panel = new JPanel();
		panel.setBackground(SystemColor.textHighlight);
		
		
		GroupLayout groupLayout = new GroupLayout(this);
		groupLayout.setHorizontalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addComponent(panel, GroupLayout.DEFAULT_SIZE, 450, Short.MAX_VALUE)
				.addGroup(groupLayout.createSequentialGroup()
					.addContainerGap()
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addGroup(groupLayout.createSequentialGroup()
							.addComponent(lblMinterms)
							.addGap(26)
							.addComponent(mintermInput, GroupLayout.DEFAULT_SIZE, 67, Short.MAX_VALUE))
						.addGroup(groupLayout.createSequentialGroup()
							.addComponent(label_1, GroupLayout.PREFERRED_SIZE, 77, GroupLayout.PREFERRED_SIZE)
							.addGap(4)
							.addComponent(bitsInput, GroupLayout.DEFAULT_SIZE, 67, Short.MAX_VALUE))
						.addGroup(groupLayout.createSequentialGroup()
							.addComponent(label)
							.addGap(17)
							.addComponent(dcInput, GroupLayout.DEFAULT_SIZE, 67, Short.MAX_VALUE)))
					.addGap(151)
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING, false)
						.addComponent(btnUpload, 0, 83, Short.MAX_VALUE)
						.addComponent(btnMinimize, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
					.addGap(68))
		);
		groupLayout.setVerticalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addComponent(panel, GroupLayout.PREFERRED_SIZE, 51, GroupLayout.PREFERRED_SIZE)
					.addGap(18)
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING, false)
						.addGroup(groupLayout.createSequentialGroup()
							.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE, false)
								.addComponent(lblMinterms)
								.addComponent(mintermInput, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
							.addPreferredGap(ComponentPlacement.UNRELATED)
							.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
								.addComponent(label)
								.addComponent(dcInput, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
							.addGap(18)
							.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE, false)
								.addComponent(label_1)
								.addComponent(bitsInput, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
								.addComponent(btnUpload, GroupLayout.PREFERRED_SIZE, 23, GroupLayout.PREFERRED_SIZE)))
						.addComponent(btnMinimize))
					.addGap(139))
		);
		groupLayout.setAutoCreateGaps(true);

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

	public QMM getObserver() {
		return this.qmm;
	}
}
