package ru.sdevteam.vinv.utils;

import java.awt.Color;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.util.StringTokenizer;
import ru.sdevteam.vinv.ui.controls.HorizontalAlignment;
import ru.sdevteam.vinv.ui.controls.VerticalAlignment;

public class TextRenderer
{
	// отрисовывает строку text так, чтобы её левый верхний угол находился в (x;y)
	public static void drawString(Graphics g, int x, int y, String text)
	{
		FontMetrics f = g.getFontMetrics();
		y+=f.getHeight();
		g.drawString(text, x, y);
	}
	// отрисовывает строку text относительно точки (x;y) с заданным выравниванием
	// выравнивание по умолчанию - LEFT, TOP
	public static void drawString(Graphics g, int x, int y, HorizontalAlignment h, VerticalAlignment v, String text)
	{
		FontMetrics f = g.getFontMetrics();
		int width = f.stringWidth(text);
		int height = f.getHeight();
		y+=f.getHeight();
		switch(h)
		{
			case RIGHT:
				x-=width;
				break;
			case CENTER:
				x=x-(int)width/2;
				break;
		}
		switch(v)
		{
			case CENTER:
				y=y-(int)height/2;
				break;
			case BOTTOM:
				y-=height;
				break;
			
		}
		g.drawString(text, x, y);		
	}
	
	public static void drawString(Graphics g, int x, int y, HorizontalAlignment h, String text)
	{
		FontMetrics f = g.getFontMetrics();
		int width = f.stringWidth(text);
		y+=f.getHeight();
		switch(h)
		{
			case RIGHT:
				x-=width;
				break;
			case CENTER:
				x=x-(int)width/2;
				break;
		}
		g.drawString(text, x, y);	
	}
	
	public static void drawString(Graphics g, int x, int y, VerticalAlignment v, String text)
	{
		FontMetrics f = g.getFontMetrics();
		int height = f.getHeight();
		y+=f.getHeight();
		switch(v)
		{
			case CENTER:
				y=y-(int)height/2;
				break;
			case BOTTOM:
				y-=height;
				break;
			
		}
		g.drawString(text, x, y);
	}
	// отрисовывает многострочный текст в заданном прямоугольнике с заданным выравниванием
	public static void drawMultiline(Graphics g, int x, int y, int width, int height, HorizontalAlignment h, VerticalAlignment v, String text)
	{
		int ht;
		String string,helpString;
		FontMetrics f = g.getFontMetrics();
		StringTokenizer st = new StringTokenizer(text);
		y+=f.getHeight();
		switch(h)
		{
			case RIGHT:
				x-=width;
				break;
			case CENTER:
				x=x-(int)width/2;
				break;
		}
		switch(v)
		{
			case CENTER:
				y=y-(int)height/2;
				break;
			case BOTTOM:
				y-=height;
				break;			
		}
		ht = y+height-f.getHeight();
		string = "";
		
		while( st.hasMoreTokens() && y < ht )
		{
			helpString = st.nextToken();
			if( f.stringWidth(string)+f.stringWidth(helpString) <= width)
			{
				string = string+helpString+" ";
			}
			else
			{
				string = string.substring(0, string.length()-1);
				switch(h)
				{
					case NONE:
						g.drawString(string, x, y);
						break;
					case LEFT:
						g.drawString(string, x, y);
						break;
					case RIGHT:
						g.drawString(string, x+width-f.stringWidth(string), y);
						break;
					case CENTER:
						g.drawString(string, x+(int)(width-f.stringWidth(string))/2, y);
						break;
				}
				y = y+f.getHeight();
				if( f.stringWidth(helpString) <= width)
				{
					string = helpString+" ";
				}
				else
				{
					break;
				}
			}
		}
	}
	// выравнивание по умолчанию то же
	public static void drawMultiline(Graphics g, int x, int y, int width, int height, HorizontalAlignment h, String text)
	{
		int wh,ht;
		String string,helpString;
		FontMetrics f = g.getFontMetrics();
		StringTokenizer st = new StringTokenizer(text);
		y+=f.getHeight();
		switch(h)
		{
			case RIGHT:
				x-=width;
				break;
			case CENTER:
				x=x-(int)width/2;
				break;
		}
		ht = y+height-f.getHeight();
		string = "";
		
		while( st.hasMoreTokens() && y < ht )
		{
			helpString = st.nextToken();
			if( f.stringWidth(string)+f.stringWidth(helpString) <= width)
			{
				string = string+helpString+" ";
			}
			else
			{
				string = string.substring(0, string.length()-1);
				switch(h)
				{
					case NONE:
						g.drawString(string, x, y);
						break;
					case LEFT:
						g.drawString(string, x, y);
						break;
					case RIGHT:
						g.drawString(string, x+width-f.stringWidth(string), y);
						break;
					case CENTER:
						g.drawString(string, x+(int)(width-f.stringWidth(string))/2, y);
						break;
				}
				y = y+f.getHeight();
				if( f.stringWidth(helpString) <= width)
				{
					string = helpString+" ";
				}
				else
				{
					break;
				}
			}
		}
	}
	public static void drawMultiline(Graphics g, int x, int y, int width, int height, VerticalAlignment v, String text)
	{
		int ht;
		String string,helpString;
		FontMetrics f = g.getFontMetrics();
		StringTokenizer st = new StringTokenizer(text);
		y+=f.getHeight();
		switch(v)
		{
			case CENTER:
				y=y-(int)height/2;
				break;
			case BOTTOM:
				y-=height;
				break;			
		}
		ht = y+height-f.getHeight();
		string = "";
		
		while( st.hasMoreTokens() && y < ht )
		{
			helpString = st.nextToken();
			if( f.stringWidth(string)+f.stringWidth(helpString) <= width)
			{
				string = string+helpString+" ";
			}
			else
			{
				string = string.substring(0, string.length()-1);
				g.drawString(string, x, y);
				y = y+f.getHeight();
				if( f.stringWidth(helpString) <= width)
				{
					string = helpString+" ";
				}
				else
				{
					break;
				}
			}
		}
	}
	public static void drawMultiline(Graphics g, int x, int y, int width, int height, String text)
	{
		int ht;
		String string,helpString;
		FontMetrics f = g.getFontMetrics();
		StringTokenizer st = new StringTokenizer(text);
		ht = y+height-f.getHeight();
		y+=f.getHeight();
		string = "";
		
		while( st.hasMoreTokens() && y < ht )
		{
			helpString = st.nextToken();
			if( f.stringWidth(string)+f.stringWidth(helpString) <= width)
			{
				string = string+helpString+" ";
			}
			else
			{
				string = string.substring(0, string.length()-1);
				g.drawString(string, x, y);
				y = y+f.getHeight();
				if( f.stringWidth(helpString) <= width)
				{
					string = helpString+" ";
				}
				else
				{
					break;
				}
			}
		}
	}

}
