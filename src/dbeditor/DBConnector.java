package dbeditor;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

public class DBConnector
{
	private String ip = "localhost";
	private String user = "root";
	private String password = "";
	private String dbname = "addtendance";
	private int port = 3306;
	
	private Connection conn;
	private Properties connectionProps;
	
	public DBConnector()
	{
	    connectionProps = new Properties();
	    connectionProps.put("user", user);
	    connectionProps.put("password", password);
	}
	
	public boolean Connect()
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
	
	public void testQuery()
	{
		try
		{
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT * FROM PROFILES WHERE ID='1'");
			if (rs.next())
				System.out.println(rs.getString("FIRST_NAME"));
		} catch (SQLException e)
		{
			e.printStackTrace();
		}
		
	}
}
