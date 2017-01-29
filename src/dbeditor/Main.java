package dbeditor;

import java.awt.EventQueue;

public class Main
{
	private static MainView mv;
	
	public static void main(String[] args) 
	{
		//System.out.println(new String(Base64.encode("Dylan,Lehotsky,1".getBytes())));
		
		EventQueue.invokeLater(new Runnable()
		{
			public void run()
			{
				try
				{
					mv = new MainView();
					mv.setVisible(true);
				} catch (Exception e)
				{
					e.printStackTrace();
				}
			}
		});
	}

	public static MainView getMainView()
	{
		return mv;
	}
}
