package dbeditor;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

import javax.swing.JComboBox;
import javax.swing.table.DefaultTableModel;

public class DBConnector
{
	public static final DBConnector instance = new DBConnector();
	
	private String ip = "localhost";
	private String user = "root";
	private String password = "IHiw1aScYopVstpr";
	private String dbname = "addtendance";
	private int port = 3306;
	
	private Connection conn;
	private Properties connectionProps;
	
	private DBConnector()
	{
	    connectionProps = new Properties();
	    connectionProps.put("user", user);
	    connectionProps.put("password", password);
	}
	
	private boolean connect()
	{
		try
		{
			conn = DriverManager.getConnection(
			               "jdbc:mysql://" +
			               ip +
			               ":" + port + "/" + dbname,
			               connectionProps);
		} catch (SQLException e)
		{
			e.printStackTrace();
			return false;
		}
		
		System.out.println("Connection to database established");
		return true;
	}
	
	private void done()
	{
		try
		{
			conn.close();
		} catch (SQLException e)
		{
			e.printStackTrace();
		}
	}
	
	public void FillCombo(JComboBox<DBPerson> combo)
	{
		if (!connect())
			return;
		
		try
		{
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT * FROM PROFILES WHERE EMAIL IS NOT NULL");
			
			while (rs.next())
				combo.addItem(new DBPerson(rs.getString("FIRST_NAME"), rs.getString("LAST_NAME"), rs.getInt("ID")));
				//System.out.println(rs.getString("FIRST_NAME"));
		} catch (SQLException e)
		{
			e.printStackTrace();
		}
		
		System.out.println("Combo box list updated");
		done();
	}
	
	public void AddPerson(DBPerson person)
	{
		if (!connect())
			return;
		
		try
		{
			Statement stmt = conn.createStatement();
			String qry = "INSERT INTO PROFILES (FIRST_NAME,LAST_NAME" + 
					((person.getReportsTo() != -1) ? ",REPORTS_TO" : "") +
					((person.getEmail().length() > 0) ? ",EMAIL" : "") + ")" +
					" VALUES ('" + person.getFName() + "','" + person.getLName() + "'" +
					((person.getReportsTo() != -1) ? "," + person.getReportsTo() + "" : "") +
					((person.getEmail().length() > 0) ? ",'" + person.getEmail() + "'" : "") + ");";
			
			stmt.execute(qry);
		} catch (Exception e)
		{
			e.printStackTrace();
		}
		
		System.out.println("Person added to database");
		done();
	}
	
	public void EditPerson(int idOld, DBPerson personNew)
	{
		if (!connect())
			return;
		
		try
		{
			Statement stmt = conn.createStatement();
			String qry = "UPDATE PROFILES SET " + 
					"FIRST_NAME='" + personNew.getFName() + "'" +
					",LAST_NAME='" + personNew.getLName() + "'" +
					((personNew.getReportsTo() != -1) ? ",REPORTS_TO='" + personNew.getReportsTo() + "'" : ",REPORTS_TO=NULL") +
					((personNew.getEmail().length() > 0) ? ",EMAIL='" + personNew.getEmail() + "'" : ",EMAIL=NULL") +
					" WHERE ID='" + idOld + "';";
			
			stmt.execute(qry);
		} catch (Exception e)
		{
			e.printStackTrace();
		}
		
		System.out.println("Person updated in database");
		done();
	}
	
	public void RemovePerson(DBPerson person)
	{
		if (!connect())
			return;
		
		try
		{
			Statement stmt = conn.createStatement();
			String qry = "DELETE FROM PROFILES WHERE " + 
					"ID='" + person.getID() + "';";
			
			stmt.execute(qry);
		} catch (Exception e)
		{
			e.printStackTrace();
		}
		
		System.out.println("Person removed from database");
		done();
	}
	
	public void FillTable(DefaultTableModel model, String whereStatment)
	{
		if (!connect())
			return;
		
		try
		{
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT * FROM PROFILES " + whereStatment + ";");
			
			while (rs.next())
				model.addRow(new Object[]{rs.getInt("ID"),rs.getString("FIRST_NAME"),rs.getString("LAST_NAME"),rs.getInt("REPORTS_TO") == 0 ? "" : rs.getInt("REPORTS_TO"),rs.getString("EMAIL")});
		} catch (Exception e)
		{
			e.printStackTrace();
		}
		
		System.out.println("Table updated");
		done();
	}
}
