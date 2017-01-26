package dbeditor;

import java.awt.EventQueue;

public class Main
{
	public static void main(String[] args) 
	{
		DBConnector connector = new DBConnector();
		connector.Connect();
		connector.testQuery();
		
		EventQueue.invokeLater(new Runnable()
		{
			public void run()
			{
				try
				{
					MainView frame = new MainView();
					frame.setVisible(true);
				} catch (Exception e)
				{
					e.printStackTrace();
				}
			}
		});
	}

	
}
