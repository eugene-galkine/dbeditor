package dbeditor;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

import javax.swing.JComboBox;

public class DBConnector
{
	public static final DBConnector instance = new DBConnector();
	
	private String ip = "localhost";
	private String user = "root";
	private String password = "";
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
		
		done();
	}
}
