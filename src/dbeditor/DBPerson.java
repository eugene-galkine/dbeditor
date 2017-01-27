package dbeditor;

public class DBPerson
{
	private String fname, lname, email;
	private int id, reportsTo;

	public DBPerson(String fname, String lname, int ID)
	{
		this.fname = fname;
		this.lname = lname;
		this.id = ID;
	}
	
	public DBPerson(String fname, String lname, String email, int ReportsTo)
	{
		this.fname = fname;
		this.lname = lname;
		this.email = email;
		this.reportsTo = ReportsTo;
	}
	
	@Override
	public String toString()
	{
		return fname + " " + lname;
	}
	
	public int getID()
	{
		return id;
	}
	
	public String getFName()
	{
		return fname;
	}
	
	public String getLName()
	{
		return lname;
	}
	
	public String getEmail()
	{
		return email;
	}
	
	public int getReportsTo()
	{
		return reportsTo;
	}
}
