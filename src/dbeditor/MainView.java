package dbeditor;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JTextField;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import javax.swing.JLabel;
import javax.swing.JComboBox;

public class MainView extends JFrame
{

	/**
	 * IDK what it means, but eclipse wants it
	 */
	private static final long serialVersionUID = 6250551778254283186L;

	private JPanel contentPane;
	private JTextField fnameField;
	private JTextField lnameField;
	private JComboBox<DBPerson> reportsField;
	private JLabel lblReportsTo;
	private JLabel lblEmail;
	private JTextField emailField;
	private JButton btnAddToDatabase;

	/**
	 * Create the frame.
	 */
	public MainView()
	{
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 550, 450);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		GridBagLayout gbl_contentPane = new GridBagLayout();
		gbl_contentPane.columnWidths = new int[]{0, 0, 0, 0, 0};
		gbl_contentPane.rowHeights = new int[]{0, 0, 0, 0, 0, 0, 0};
		gbl_contentPane.columnWeights = new double[]{0.0, 1.0, 0.0, 1.0, Double.MIN_VALUE};
		gbl_contentPane.rowWeights = new double[]{0.0, 0.0, 0.0, 1.0, 0.0, 0.0, Double.MIN_VALUE};
		contentPane.setLayout(gbl_contentPane);
		
		JLabel lblFirstName = new JLabel("First Name:");
		GridBagConstraints gbc_lblFirstName = new GridBagConstraints();
		gbc_lblFirstName.insets = new Insets(0, 0, 5, 5);
		gbc_lblFirstName.anchor = GridBagConstraints.EAST;
		gbc_lblFirstName.gridx = 0;
		gbc_lblFirstName.gridy = 0;
		contentPane.add(lblFirstName, gbc_lblFirstName);
		
		fnameField = new JTextField();
		GridBagConstraints gbc_fnameField = new GridBagConstraints();
		gbc_fnameField.insets = new Insets(0, 0, 5, 5);
		gbc_fnameField.fill = GridBagConstraints.HORIZONTAL;
		gbc_fnameField.gridx = 1;
		gbc_fnameField.gridy = 0;
		contentPane.add(fnameField, gbc_fnameField);
		fnameField.setColumns(10);
		
		JLabel lblLastName = new JLabel("Last Name:");
		GridBagConstraints gbc_lblLastName = new GridBagConstraints();
		gbc_lblLastName.insets = new Insets(0, 0, 5, 5);
		gbc_lblLastName.anchor = GridBagConstraints.EAST;
		gbc_lblLastName.gridx = 2;
		gbc_lblLastName.gridy = 0;
		contentPane.add(lblLastName, gbc_lblLastName);
		
		lnameField = new JTextField();
		GridBagConstraints gbc_lnameField = new GridBagConstraints();
		gbc_lnameField.insets = new Insets(0, 0, 5, 0);
		gbc_lnameField.fill = GridBagConstraints.HORIZONTAL;
		gbc_lnameField.gridx = 3;
		gbc_lnameField.gridy = 0;
		contentPane.add(lnameField, gbc_lnameField);
		lnameField.setColumns(10);
		
		lblReportsTo = new JLabel("Reports to:");
		GridBagConstraints gbc_lblReportsTo = new GridBagConstraints();
		gbc_lblReportsTo.insets = new Insets(0, 0, 5, 5);
		gbc_lblReportsTo.anchor = GridBagConstraints.EAST;
		gbc_lblReportsTo.gridx = 0;
		gbc_lblReportsTo.gridy = 1;
		contentPane.add(lblReportsTo, gbc_lblReportsTo);
		
		reportsField = new JComboBox<DBPerson>();
		GridBagConstraints gbc_reportsField = new GridBagConstraints();
		gbc_reportsField.insets = new Insets(0, 0, 5, 5);
		gbc_reportsField.fill = GridBagConstraints.HORIZONTAL;
		gbc_reportsField.gridx = 1;
		gbc_reportsField.gridy = 1;
		contentPane.add(reportsField, gbc_reportsField);
		reportsField.addItem(new DBPerson("Null","",-1));
		reportsField.setSelectedIndex(0);
		
		lblEmail = new JLabel("Email:");
		GridBagConstraints gbc_lblEmail = new GridBagConstraints();
		gbc_lblEmail.anchor = GridBagConstraints.EAST;
		gbc_lblEmail.insets = new Insets(0, 0, 5, 5);
		gbc_lblEmail.gridx = 2;
		gbc_lblEmail.gridy = 1;
		contentPane.add(lblEmail, gbc_lblEmail);
		
		emailField = new JTextField();
		emailField.setToolTipText("If Reportable To");
		GridBagConstraints gbc_emailField = new GridBagConstraints();
		gbc_emailField.insets = new Insets(0, 0, 5, 0);
		gbc_emailField.fill = GridBagConstraints.HORIZONTAL;
		gbc_emailField.gridx = 3;
		gbc_emailField.gridy = 1;
		contentPane.add(emailField, gbc_emailField);
		emailField.setColumns(10);
		
		btnAddToDatabase = new JButton("Add To Database");
		btnAddToDatabase.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent e) 
			{
				DBConnector.instance.AddPerson(
						new DBPerson(fnameField.getText(), lnameField.getText(), 
									emailField.getText(), ((DBPerson)reportsField.getSelectedItem()).getID()));
			}
		});
		GridBagConstraints gbc_btnAddToDatabase = new GridBagConstraints();
		gbc_btnAddToDatabase.insets = new Insets(0, 0, 5, 5);
		gbc_btnAddToDatabase.gridx = 1;
		gbc_btnAddToDatabase.gridy = 2;
		contentPane.add(btnAddToDatabase, gbc_btnAddToDatabase);
		
		new Thread()
		{
			@Override
			public void run()
			{
				DBConnector.instance.FillCombo(reportsField);
			}
		}.start();
	}

}
