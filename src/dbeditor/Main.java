package dbeditor;

import java.awt.EventQueue;

public class Main
{
	
	
	public static void main(String[] args) 
	{
		//System.out.println(new String(Base64.encode("Dylan,Lehotsky,1".getBytes())));
		
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
