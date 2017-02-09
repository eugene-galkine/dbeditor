package dbeditor;

import java.awt.GridBagLayout;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import java.awt.GridBagConstraints;
import javax.swing.JButton;
import java.awt.Insets;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class FilterView extends JFrame
{

	private static final long serialVersionUID = 7464525959068578200L;
	private JPanel contentPane;

	/**
	 * Create the frame.
	 */
	public FilterView()
	{
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(120, 120, 700, 130);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		GridBagLayout gbl_contentPane = new GridBagLayout();
		gbl_contentPane.rowHeights = new int[] {0, 0};
		gbl_contentPane.columnWidths = new int[] {0, 0, 0};
		contentPane.setLayout(gbl_contentPane);
		
		JCheckBox chckbxManagers = new JCheckBox("Managers");
		GridBagConstraints gbc_chckbxManagers = new GridBagConstraints();
		gbc_chckbxManagers.insets = new Insets(0, 0, 5, 5);
		gbc_chckbxManagers.weightx = 0.15;
		gbc_chckbxManagers.anchor = GridBagConstraints.WEST;
		gbc_chckbxManagers.fill = GridBagConstraints.HORIZONTAL;
		gbc_chckbxManagers.gridy = 0;
		gbc_chckbxManagers.gridx = 0;
		contentPane.add(chckbxManagers, gbc_chckbxManagers);
		
		JLabel lblReportsTo = new JLabel("Reports To:");
		GridBagConstraints gbc_lblReportsTo = new GridBagConstraints();
		gbc_lblReportsTo.insets = new Insets(0, 0, 5, 5);
		gbc_lblReportsTo.weightx = 0.1;
		gbc_lblReportsTo.anchor = GridBagConstraints.WEST;
		gbc_lblReportsTo.fill = GridBagConstraints.HORIZONTAL;
		gbc_lblReportsTo.gridy = 0;
		gbc_lblReportsTo.gridx = 1;
		contentPane.add(lblReportsTo, gbc_lblReportsTo);
		
		JComboBox<DBPerson> comboBox = new JComboBox<DBPerson>();
		GridBagConstraints gbc_comboBox = new GridBagConstraints();
		gbc_comboBox.insets = new Insets(0, 0, 5, 0);
		gbc_comboBox.weightx = 1.0;
		gbc_comboBox.anchor = GridBagConstraints.WEST;
		gbc_comboBox.fill = GridBagConstraints.HORIZONTAL;
		gbc_comboBox.gridy = 0;
		gbc_comboBox.gridx = 2;
		contentPane.add(comboBox, gbc_comboBox);
		comboBox.addItem(new DBPerson("Null","",-1));
		comboBox.setSelectedIndex(0);
		new Thread()
		{
			@Override
			public void run()
			{
				DBConnector.instance.FillCombo(comboBox);
			}
		}.start();
		
		
		JButton btnConfirm = new JButton("Confirm");
		btnConfirm.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent arg0) 
			{
				Main.getMainView().refresh(chckbxManagers.isSelected() ? " WHERE EMAIL IS NOT NULL" : "" + 
					((comboBox.getSelectedIndex() > 0) ? (chckbxManagers.isSelected() ? " AND REPORTS_TO='" : " WHERE REPORTS_TO='") + ((DBPerson) comboBox.getSelectedItem()).getID() + "'" : ""));
				
			}
		});
		GridBagConstraints gbc_btnConfirm = new GridBagConstraints();
		gbc_btnConfirm.fill = GridBagConstraints.HORIZONTAL;
		gbc_btnConfirm.anchor = GridBagConstraints.NORTH;
		gbc_btnConfirm.insets = new Insets(0, 0, 0, 5);
		gbc_btnConfirm.gridx = 0;
		gbc_btnConfirm.gridy = 1;
		contentPane.add(btnConfirm, gbc_btnConfirm);
	}

	
	
}
