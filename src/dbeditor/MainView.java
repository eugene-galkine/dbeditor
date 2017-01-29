package dbeditor;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.EmptyBorder;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.ListSelectionModel;
import javax.swing.JButton;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class MainView extends JFrame 
{

	/**
	 * 
	 */
	private static final long serialVersionUID = 325717971456310977L;
	
	private JPanel contentPane;
	private JTable table;
	private DefaultTableModel tableMod;
	private JButton btnRemoveSelected;
	private JButton btnEditSelected;

	/**
	 * Create the frame.
	 */
	public MainView()
	{
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 500, 352);
		setTitle("Database Editor");
		setResizable(false);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		tableMod = new DefaultTableModel(new Object[][] {},new String[] {"ID", "First Name", "Last Name", "Reports To", "Email"}) 
		{
			private static final long serialVersionUID = 4097999218204811939L;

			public boolean isCellEditable(int row, int column) 
			{
				return false;
			}
		};
		
		table = new JTable();
		table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		table.setModel(tableMod);
		table.getColumnModel().getColumn(0).setResizable(false);
		table.getColumnModel().getColumn(0).setPreferredWidth(35);
		table.getColumnModel().getColumn(0).setMinWidth(35);
		table.getColumnModel().getColumn(0).setMaxWidth(35);
		table.getColumnModel().getColumn(1).setResizable(false);
		table.getColumnModel().getColumn(2).setResizable(false);
		table.getColumnModel().getColumn(3).setResizable(false);
		table.getColumnModel().getColumn(3).setPreferredWidth(70);
		table.getColumnModel().getColumn(3).setMinWidth(32);
		table.getColumnModel().getColumn(3).setMaxWidth(72);
		table.getColumnModel().getColumn(4).setResizable(false);
		table.getColumnModel().getColumn(4).setPreferredWidth(100);
		
		
		JScrollPane scrollPane = new JScrollPane(table);
		scrollPane.setBounds(0, 0, 494, 240);
		contentPane.add(scrollPane);
		
		JButton btnNewButton = new JButton("Add New Person");
		btnNewButton.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent e) 
			{
				try
				{
					EditAddView frame = new EditAddView();
					frame.setVisible(true);
				} catch (Exception e1)
				{
					e1.printStackTrace();
				}
			}
		});
		btnNewButton.setBounds(4, 244, 147, 23);
		contentPane.add(btnNewButton);
		
		btnEditSelected = new JButton("Edit Selected");
		btnEditSelected.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent e) 
			{
				try
				{
					DBPerson selected = getFromSelectedRow();
					if (selected != null)
					{
						EditAddView frame = new EditAddView(selected);
						frame.setVisible(true);
					} else
						System.out.println("No Row Selected!");
				} catch (Exception e1)
				{
					e1.printStackTrace();
				}
			}
		});
		btnEditSelected.setBounds(4, 270, 147, 23);
		contentPane.add(btnEditSelected);
		
		btnRemoveSelected = new JButton("Remove Selected");
		btnRemoveSelected.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent arg0) 
			{
				DBPerson selected = getFromSelectedRow();
				if (selected != null)
				{
					DBConnector.instance.RemovePerson(selected);
					refresh();
				} else
					System.out.println("No Row Selected!");
			}
		});
		btnRemoveSelected.setBounds(4, 296, 147, 23);
		contentPane.add(btnRemoveSelected);
		
		refresh();
	}
	
	public void refresh()
	{
		while (tableMod.getRowCount() > 0)
			tableMod.removeRow(0);
		
		new Thread()
		{
			@Override
			public void run()
			{
				DBConnector.instance.FillTable(tableMod);
			}
		}.start();
	}
	
	private DBPerson getFromSelectedRow()
	{
		return table.getSelectedRow() != -1 ? new DBPerson(
				(int)tableMod.getValueAt(table.getSelectedRow(), 0), 
				(String)tableMod.getValueAt(table.getSelectedRow(), 1), 
				(String)tableMod.getValueAt(table.getSelectedRow(), 2), 
				(tableMod.getValueAt(table.getSelectedRow(), 3) instanceof String) ? -1 : (int)tableMod.getValueAt(table.getSelectedRow(), 3),
				(String)tableMod.getValueAt(table.getSelectedRow(), 4)) : 
				null;
	}
}
