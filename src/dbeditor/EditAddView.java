package dbeditor;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JTextField;
import java.awt.GridBagConstraints;
import javax.swing.JLabel;
import javax.swing.JComboBox;

public class EditAddView extends JFrame
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

	private String fname, lname, email;
	private boolean edit;
	private int id, reportsTo;
	
	/**
	 * Create the frame.
	 */
	public EditAddView()
	{
		fname = "";
		lname = "";
		email = "";
		reportsTo = -1;
		edit = false;
		fillView();
	}
	
	public EditAddView(DBPerson p)
	{
		fname = p.getFName();
		lname = p.getLName();
		email = p.getEmail();
		edit = true;
		id = p.getID();
		reportsTo = p.getReportsTo();
		fillView();
	}
	
	private void fillView()
	{
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(120, 120, 600, 150);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		setTitle("Database Editor");
		setResizable(false);
		contentPane.setLayout(null);
		
		JLabel lblFirstName = new JLabel("First Name:");
		GridBagConstraints gbc_lblFirstName = new GridBagConstraints();
		lblFirstName.setBounds(10, 11, 79, 14);
		contentPane.add(lblFirstName, gbc_lblFirstName);
		
		fnameField = new JTextField(fname);
		GridBagConstraints gbc_fnameField = new GridBagConstraints();
		fnameField.setBounds(85, 7, 200, 23);
		contentPane.add(fnameField, gbc_fnameField);
		fnameField.setColumns(10);
		
		JLabel lblLastName = new JLabel("Last Name:");
		GridBagConstraints gbc_lblLastName = new GridBagConstraints();
		lblLastName.setBounds(295, 11, 79, 14);
		contentPane.add(lblLastName, gbc_lblLastName);
		
		lnameField = new JTextField(lname);
		GridBagConstraints gbc_lnameField = new GridBagConstraints();
		lnameField.setBounds(374, 7, 200, 23);
		contentPane.add(lnameField, gbc_lnameField);
		lnameField.setColumns(10);
		
		lblReportsTo = new JLabel("Reports to:");
		GridBagConstraints gbc_lblReportsTo = new GridBagConstraints();
		lblReportsTo.setBounds(10, 36, 71, 14);
		contentPane.add(lblReportsTo, gbc_lblReportsTo);
		
		reportsField = new JComboBox<DBPerson>();
		GridBagConstraints gbc_reportsField = new GridBagConstraints();
		reportsField.setBounds(85, 32, 200, 23);
		contentPane.add(reportsField, gbc_reportsField);
		reportsField.addItem(new DBPerson("Null","",-1));
		
		lblEmail = new JLabel("Email:");
		GridBagConstraints gbc_lblEmail = new GridBagConstraints();
		lblEmail.setBounds(295, 36, 79, 14);
		contentPane.add(lblEmail, gbc_lblEmail);
		
		emailField = new JTextField(email);
		emailField.setToolTipText("If Reportable To");
		GridBagConstraints gbc_emailField = new GridBagConstraints();
		emailField.setBounds(374, 32, 200, 23);
		contentPane.add(emailField, gbc_emailField);
		emailField.setColumns(10);
		
		btnAddToDatabase = new JButton(edit ? "Commit Edit" : "Add To Database");
		btnAddToDatabase.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent e) 
			{
				new Thread()
				{
					@Override
					public void run()
					{
						if (edit)
							DBConnector.instance.EditPerson(id,
									new DBPerson(fnameField.getText(), lnameField.getText(), 
												emailField.getText(), ((DBPerson)reportsField.getSelectedItem()).getID()));
						else
							DBConnector.instance.AddPerson(
									new DBPerson(fnameField.getText(), lnameField.getText(), 
												emailField.getText(), ((DBPerson)reportsField.getSelectedItem()).getID()));
						//populateDropDown();
						Main.getMainView().refresh();
						dispose();
					}
				}.start();;
			}
		});
		GridBagConstraints gbc_btnAddToDatabase = new GridBagConstraints();
		btnAddToDatabase.setBounds(85, 75, 200, 23);
		contentPane.add(btnAddToDatabase, gbc_btnAddToDatabase);
		
		populateDropDown();
	}
	
	private void populateDropDown()
	{
		reportsField.setSelectedIndex(0);
		
		while (reportsField.getItemCount() > 1)
			reportsField.removeItemAt(1);
		
		new Thread()
		{
			@Override
			public void run()
			{
				DBConnector.instance.FillCombo(reportsField);
				if (reportsTo != -1)
				{
					for (int i = 0; i < reportsField.getItemCount(); i++)
						if (reportsField.getItemAt(i).getID() == reportsTo)
						{
							reportsField.setSelectedIndex(i);
							break;
						}
				}
			}
		}.start();
	}
}
