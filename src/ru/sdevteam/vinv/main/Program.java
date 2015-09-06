package ru.sdevteam.vinv.main;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowFocusListener;

public class Program
{
	
	public static void main(String[] args)
	{
		MainFrame frame = new MainFrame();
		
		frame.addWindowListener(new WindowAdapter()
		{
			public void windowClosing(WindowEvent e) 
			{
				System.exit(0);
	        }
		});
				
		frame.setAutoRequestFocus(true);
		frame.setVisible(true);
	}
}
