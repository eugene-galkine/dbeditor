package dbeditor;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.EmptyBorder;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.ListSelectionModel;
import javax.swing.UIManager;
import javax.swing.UIManager.LookAndFeelInfo;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.JButton;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Font;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;

public class MainView extends JFrame 
{
	private static final long serialVersionUID = 325717971456310977L;
	
	private DefaultTableModel tableMod = new DefaultTableModel(new Object[][] {},new String[] {"ID", "First Name", "Last Name", "Reports To", "Email"}) 
	{
		private static final long serialVersionUID = 4097999218204811939L;

		public boolean isCellEditable(int row, int column) 
		{
			return false;
		}
	};
	private JPanel contentPane;
	private JTable table;
	private JButton btnRemoveSelected;
	private JButton btnEditSelected;
	private JButton btnMakeQR;
	private JButton btnRefresh;
	private String whereStmnt;

	/**
	 * Create the frame.
	 */
	public MainView()
	{
		whereStmnt = "";
		
		try
		{
			UIManager.setLookAndFeel(
			       "com.sun.java.swing.plaf.windows.WindowsClassicLookAndFeel");
		} catch (Exception e)
		{
			e.printStackTrace();
		}

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 843, 618);
		setTitle("Database Editor");
		setResizable(true);
		//setres
		GridBagLayout gbl_contentPane = new GridBagLayout();
		gbl_contentPane.rowHeights = new int[] {200};
		gbl_contentPane.columnWidths = new int[] {0, 0, 0, 0, 0};
		contentPane = new JPanel(gbl_contentPane);
		contentPane.setBorder(new EmptyBorder(3, 3, 3, 3));
		setContentPane(contentPane);
		
		table = new JTable();
		table.setFillsViewportHeight(true);
		table.setFont(new Font("Tahoma", Font.PLAIN, 21));
		table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		table.setModel(tableMod);
		table.getColumnModel().getColumn(0).setResizable(true);
		table.getColumnModel().getColumn(0).setPreferredWidth(35);
		table.getColumnModel().getColumn(1).setResizable(true);
		table.getColumnModel().getColumn(2).setResizable(true);
		table.getColumnModel().getColumn(3).setResizable(true);
		table.getColumnModel().getColumn(3).setPreferredWidth(70);
		table.getColumnModel().getColumn(4).setResizable(true);
		table.getColumnModel().getColumn(4).setPreferredWidth(100);
		table.setRowHeight(25);
		
		JScrollPane scrollPane = new JScrollPane(table);
		GridBagConstraints gbc_scrollPane = new GridBagConstraints();
		gbc_scrollPane.insets = new Insets(0, 0, 5, 0);
		gbc_scrollPane.gridwidth = 5;
		gbc_scrollPane.weighty = 0.85;
		gbc_scrollPane.weightx = 1.0;
		gbc_scrollPane.fill = GridBagConstraints.BOTH;
		gbc_scrollPane.anchor = GridBagConstraints.NORTHWEST;
		gbc_scrollPane.gridx = 0;
		gbc_scrollPane.gridy = 0;
		contentPane.add(scrollPane, gbc_scrollPane);
		
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
						noRowAlert();
						//System.out.println("No Row Selected!");
				} catch (Exception e1)
				{
					e1.printStackTrace();
				}
			}
		});
		GridBagConstraints gbc_btnEditSelected = new GridBagConstraints();
		gbc_btnEditSelected.insets = new Insets(0, 0, 0, 5);
		gbc_btnEditSelected.weightx = 0.2;
		gbc_btnEditSelected.fill = GridBagConstraints.BOTH;
		gbc_btnEditSelected.anchor = GridBagConstraints.WEST;
		gbc_btnEditSelected.weighty = 0.15;
		//gbc_btnEditSelected.insets = new Insets(0, 0, 5, 5);
		gbc_btnEditSelected.gridx = 0;
		gbc_btnEditSelected.gridy = 1;
		contentPane.add(btnEditSelected, gbc_btnEditSelected);
		
		btnRemoveSelected = new JButton("Remove Selected");
		btnRemoveSelected.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent arg0) 
			{
				DBPerson selected = getFromSelectedRow();
				if (selected != null)
				{
					DBConnector.instance.RemovePerson(selected);
					refresh(whereStmnt);
				} else
					noRowAlert();
					//System.out.println("No Row Selected!");
			}
		});
		GridBagConstraints gbc_btnRemoveSelected = new GridBagConstraints();
		gbc_btnRemoveSelected.insets = new Insets(0, 0, 0, 5);
		gbc_btnRemoveSelected.anchor = GridBagConstraints.WEST;
		gbc_btnRemoveSelected.weightx = 0.2;
		gbc_btnRemoveSelected.fill = GridBagConstraints.BOTH;
		gbc_btnRemoveSelected.weighty = 0.15;
		//gbc_btnRemoveSelected.insets = new Insets(0, 0, 5, 5);
		gbc_btnRemoveSelected.gridx = 1;
		gbc_btnRemoveSelected.gridy = 1;
		contentPane.add(btnRemoveSelected, gbc_btnRemoveSelected);
		//contentPane.setLayout(gbl_contentPane);
		
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
		GridBagConstraints gbc_btnNewButton = new GridBagConstraints();
		gbc_btnNewButton.insets = new Insets(0, 0, 0, 5);
		gbc_btnNewButton.anchor = GridBagConstraints.WEST;
		gbc_btnNewButton.weightx = 0.2;
		gbc_btnNewButton.fill = GridBagConstraints.BOTH;
		gbc_btnNewButton.weighty = 0.15;
		//gbc_btnNewButton.insets = new Insets(0, 0, 5, 0);
		gbc_btnNewButton.gridx = 2;
		gbc_btnNewButton.gridy = 1;
		contentPane.add(btnNewButton, gbc_btnNewButton);
		
		btnMakeQR = new JButton("Generate QR");
		btnMakeQR.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e) 
			{
				DBPerson selected = getFromSelectedRow();
				if (selected != null)
					QRCodeHandler.createQR(selected);
				else
					noRowAlert();
					//System.out.println("No Row Selected!");
			}
		});
		GridBagConstraints gbc_btnMakeQR = new GridBagConstraints();
		gbc_btnMakeQR.insets = new Insets(0, 0, 0, 5);
		gbc_btnMakeQR.anchor = GridBagConstraints.WEST;
		gbc_btnMakeQR.fill = GridBagConstraints.BOTH;
		gbc_btnMakeQR.weightx = 0.2;
		gbc_btnMakeQR.weighty = 0.15;
		gbc_btnMakeQR.gridx = 3;
		gbc_btnMakeQR.gridy = 1;
		contentPane.add(btnMakeQR, gbc_btnMakeQR);
		
		btnRefresh = new JButton("Filter Table");
		btnRefresh.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent arg0) 
			{
				FilterView frame = new FilterView();
				frame.setVisible(true);
			}
		});
		GridBagConstraints gbc_btnRefresh = new GridBagConstraints();
		gbc_btnRefresh.weighty = 0.15;
		gbc_btnRefresh.weightx = 0.2;
		gbc_btnRefresh.anchor = GridBagConstraints.WEST;
		gbc_btnRefresh.fill = GridBagConstraints.BOTH;
		gbc_btnRefresh.gridx = 4;
		gbc_btnRefresh.gridy = 1;
		contentPane.add(btnRefresh, gbc_btnRefresh);
		
		refresh(whereStmnt);
	}
	
	public void refresh(String where)
	{
		while (tableMod.getRowCount() > 0)
			tableMod.removeRow(0);
		
		whereStmnt = where;
		
		new Thread()
		{
			@Override
			public void run()
			{
				DBConnector.instance.FillTable(tableMod, where);
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
	
	private void noRowAlert()
	{
		JOptionPane.showMessageDialog(this, "Please Select A Row From The Table", "Error", JOptionPane.ERROR_MESSAGE);
	}

	public void refreshAndQR()
	{
		while (tableMod.getRowCount() > 0)
			tableMod.removeRow(0);
		
		new Thread()
		{
			@Override
			public void run()
			{
				DBConnector.instance.FillTable(tableMod, "");
				QRCodeHandler.createQR(new DBPerson(
						(int)tableMod.getValueAt(table.getRowCount() - 1, 0), 
						(String)tableMod.getValueAt(table.getRowCount() - 1, 1), 
						(String)tableMod.getValueAt(table.getRowCount() - 1, 2), 
						(tableMod.getValueAt(table.getRowCount() - 1, 3) instanceof String) ? -1 : (int)tableMod.getValueAt(table.getRowCount() - 1, 3),
						(String)tableMod.getValueAt(table.getRowCount() - 1, 4)));
			}
		}.start();
	}
}
