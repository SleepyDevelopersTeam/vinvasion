package ru.sdevteam.vinv.utils;

import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;

public class Fonts
{	
	private static Font[] main=null;
	private static FontMetrics[] main_m=null;
	
	public static void initialize(Font base)
	{
		main=new Font[] {	base.deriveFont(6F),	base.deriveFont(8F),	base.deriveFont(10F),
							base.deriveFont(12F),	base.deriveFont(14F),	base.deriveFont(16F)	};
		
		main_m=new FontMetrics[6];
	}
	
	public static boolean initialized() { return main_m!=null; }
	
	public static Font main(int size)
	{
		if(size%2==0)
		{
			if(size>=6 && size <=16)
				return main[size/2-3];
		}
		return main[0].deriveFont((float)size);
	}
	
	public static FontMetrics mainMetrics(int size, Graphics g)
	{
		if(size%2==0)
		{
			if(size>=6 && size <=16)
			{
				int i=size/2-3;
				if(main_m[i]==null)
					main_m[i]=g.getFontMetrics(main[i]);
				return main_m[i];
			}
		}
		return g.getFontMetrics(main(size));
	}
}
