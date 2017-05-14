package views;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JSplitPane;
import javax.swing.GroupLayout;
import javax.swing.JFileChooser;
import javax.swing.GroupLayout.Alignment;

import controllers.QMM;

import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.IOException;
import java.nio.channels.ShutdownChannelGroupException;

import javax.swing.JPopupMenu;
import java.awt.Component;
import java.awt.Button;

public class QM {

	private JFrame frame;
	private InputPanel panel;
	private OutputPanel panel_1;
	private JMenuBar menuBar;
	private JMenu mnFile;
	private JMenuItem mntmSave;
	private JMenuItem mntmHelp;
	private JPopupMenu helpMenu;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					QM window = new QM();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public QM() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(50, 50, 875, 664);
		frame.setDefaultCloseOperation(
				JFrame.EXIT_ON_CLOSE);

		JSplitPane splitPane = new JSplitPane();
		splitPane.setContinuousLayout(true);
		splitPane.setOneTouchExpandable(true);
		splitPane.setOrientation(JSplitPane.VERTICAL_SPLIT);

		panel = new InputPanel();
		splitPane.setLeftComponent(panel);

		GroupLayout groupLayout = new GroupLayout(
				frame.getContentPane());
		groupLayout.setHorizontalGroup(groupLayout
				.createParallelGroup(Alignment.LEADING)
				.addComponent(splitPane,
						GroupLayout.DEFAULT_SIZE, 455,
						Short.MAX_VALUE));
		groupLayout.setVerticalGroup(groupLayout
				.createParallelGroup(Alignment.LEADING)
				.addComponent(splitPane,
						GroupLayout.PREFERRED_SIZE, 391,
						Short.MAX_VALUE));

		panel_1 = new OutputPanel();
		splitPane.setRightComponent(panel_1);
		frame.getContentPane().setLayout(groupLayout);
		panel.setOuputPanel(panel_1);
		
		helpMenu = new JPopupMenu();
		addPopup(panel, helpMenu);

		menuBar = new JMenuBar();
		frame.setJMenuBar(menuBar);

		mnFile = new JMenu("File");
		menuBar.add(mnFile);
		String cwd = System.getProperty("user.dir");
		JFileChooser fileChooser = new JFileChooser(cwd);
		fileChooser
				.setDialogTitle("Specify a file to save");
		mntmSave = new JMenuItem("Save");
		mntmSave.addActionListener(new ActionListener() {


			@Override
			public void actionPerformed(ActionEvent e) {
				if (fileChooser.showSaveDialog(
						frame) == JFileChooser.APPROVE_OPTION) {
					File file = fileChooser
							.getSelectedFile();
					try {
						panel.getObserver().save(file);
					} catch (IOException e1) {
						
						e1.printStackTrace();
					}
				}
			}
		});
		mnFile.add(mntmSave);

		mntmHelp = new JMenuItem("help");
		mntmHelp.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//helpMenu.setVisible(true);
				JOptionPane.showMessageDialog(frame, this.getHelp());
			}

			private Object getHelp() {
				StringBuilder sb = new StringBuilder();
				sb.append("<html><body>"
						+ "<h1> Quine McCluskey </h1>"
						+ "<h2> Instructions: </h2>"
						+ "<ul>"
						+ "<li> <strong>Write down the function minterms in numerical form seperated with commas.</strong></li>"
						+ "<li> Enter the value of the don't cares of the function, if there are any.</li>"
						+ "<li> <strong>Write the number of varaiables of the function.</strong></li>"
						+ "<li> Press minimize and the see the result below accompanied with the steps</li>"
						+ "<li> You may upload a text file containing the minterms, but it should be formatted as follows:"
						+ "<ul>"
						+ "<li> First line should be the number of input variables.</li>"
						+ "<li> Second line should be the minterms seperated with commas</li>"
						+ "<li> Third line should be the don't cares values seperated with commas </li>"
						+ "<li> First and second line are required and the reducer won't operate without them.</li>"
						+ "<li> Third line is optional.</li>"
						+ "</ul>"
						+ "</li>"
						+ "<li> You can save the output function along with the result, from file -> save.</li>"
						+ "</ul>");
				sb.append("</body></html>");
				
				return sb.toString();
			}
		});
		mnFile.add(mntmHelp);

	}

	private static void addPopup(Component component, final JPopupMenu popup) {
		component.addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent e) {
				if (e.isPopupTrigger()) {
					showMenu(e);
				}
			}
			public void mouseReleased(MouseEvent e) {
				if (e.isPopupTrigger()) {
					showMenu(e);
				}
			}
			private void showMenu(MouseEvent e) {
				popup.show(e.getComponent(), e.getX(), e.getY());
			}
		});
	}
}
