package ru.sdevteam.vinv.utils;

import java.awt.Color;

public class Colors
{	
	private static Color[] palette=new Color[]
	{
		new Color(0, 0, 0), 		new Color(128, 0, 0), 		new Color(0, 128, 0), 		new Color(128, 128, 0), 	
		new Color(0, 0, 128), 		new Color(128, 0, 128), 	new Color(0, 128, 128), 	new Color(128, 128, 128), 	
		new Color(192, 220, 192), 	new Color(166, 202, 240), 	new Color(42, 63, 170), 	new Color(42, 63, 255), 	
		new Color(42, 95, 0), 		new Color(42, 95, 85), 		new Color(42, 95, 170), 	new Color(42, 95, 255), 	
		new Color(42, 127, 0), 		new Color(42, 127, 85), 	new Color(42, 127, 170), 	new Color(42, 127, 255), 	
		new Color(42, 159, 0), 		new Color(42, 159, 85), 	new Color(42, 159, 170), 	new Color(42, 159, 255), 	
		new Color(42, 191, 0), 		new Color(42, 191, 85), 	new Color(42, 191, 170), 	new Color(42, 191, 255), 	
		new Color(42, 223, 0), 		new Color(42, 223, 85), 	new Color(42, 223, 170), 	new Color(42, 223, 255), 	
		new Color(42, 255, 0), 		new Color(42, 255, 85), 	new Color(42, 255, 170), 	new Color(42, 255, 255), 	
		new Color(85, 0, 0), 		new Color(85, 0, 85), 		new Color(85, 0, 170), 		new Color(85, 0, 255), 	
		new Color(85, 31, 0), 		new Color(85, 31, 85), 		new Color(85, 31, 170), 	new Color(85, 31, 255), 	
		new Color(85, 63, 0), 		new Color(85, 63, 85), 		new Color(85, 63, 170), 	new Color(85, 63, 255), 	
		new Color(85, 95, 0), 		new Color(85, 95, 85), 		new Color(85, 95, 170), 	new Color(85, 95, 255), 	
		new Color(85, 127, 0), 		new Color(85, 127, 85), 	new Color(85, 127, 170), 	new Color(85, 127, 255), 	
		new Color(85, 159, 0), 		new Color(85, 159, 85), 	new Color(85, 159, 170), 	new Color(85, 159, 255), 	
		new Color(85, 191, 0), 		new Color(85, 191, 85), 	new Color(85, 191, 170), 	new Color(85, 191, 255), 	
		new Color(85, 223, 0), 		new Color(85, 223, 85), 	new Color(85, 223, 170), 	new Color(85, 223, 255), 	
		new Color(85, 255, 0), 		new Color(85, 255, 85), 	new Color(85, 255, 170), 	new Color(85, 255, 255), 	
		new Color(127, 0, 0), 		new Color(127, 0, 85), 		new Color(127, 0, 170), 	new Color(127, 0, 255), 	
		new Color(127, 31, 0), 		new Color(127, 31, 85), 	new Color(127, 31, 170), 	new Color(127, 31, 255), 	
		new Color(127, 63, 0), 		new Color(127, 63, 85), 	new Color(127, 63, 170), 	new Color(127, 63, 255), 	
		new Color(127, 95, 0), 		new Color(127, 95, 85), 	new Color(127, 95, 170), 	new Color(127, 95, 255), 	
		new Color(127, 127, 0), 	new Color(127, 127, 85), 	new Color(127, 127, 170), 	new Color(127, 127, 255), 	
		new Color(127, 159, 0), 	new Color(127, 159, 85), 	new Color(127, 159, 170), 	new Color(127, 159, 255), 	
		new Color(127, 191, 0), 	new Color(127, 191, 85), 	new Color(127, 191, 170), 	new Color(127, 191, 255), 	
		new Color(127, 223, 0), 	new Color(127, 223, 85), 	new Color(127, 223, 170), 	new Color(127, 223, 255), 	
		new Color(127, 255, 0), 	new Color(127, 255, 85), 	new Color(127, 255, 170), 	new Color(127, 255, 255), 	
		new Color(170, 0, 0), 		new Color(170, 0, 85), 		new Color(170, 0, 170), 	new Color(170, 0, 255), 	
		new Color(170, 31, 0), 		new Color(170, 31, 85), 	new Color(170, 31, 170), 	new Color(170, 31, 255), 	
		new Color(170, 63, 0), 		new Color(170, 63, 85), 	new Color(170, 63, 170), 	new Color(170, 63, 255), 	
		new Color(170, 95, 0), 		new Color(170, 95, 85), 	new Color(170, 95, 170), 	new Color(170, 95, 255), 	
		new Color(170, 127, 0), 	new Color(170, 127, 85), 	new Color(170, 127, 170), 	new Color(170, 127, 255), 	
		new Color(170, 159, 0), 	new Color(170, 159, 85), 	new Color(170, 159, 170), 	new Color(170, 159, 255), 	
		new Color(170, 191, 0), 	new Color(170, 191, 85), 	new Color(170, 191, 170), 	new Color(170, 191, 255), 	
		new Color(170, 223, 0), 	new Color(170, 223, 85), 	new Color(170, 223, 170), 	new Color(170, 223, 255), 	
		new Color(170, 255, 0), 	new Color(170, 255, 85), 	new Color(170, 255, 170), 	new Color(170, 255, 255), 	
		new Color(212, 0, 0), 		new Color(212, 0, 85), 		new Color(212, 0, 170), 	new Color(212, 0, 255), 	
		new Color(212, 31, 0), 		new Color(212, 31, 85), 	new Color(212, 31, 170), 	new Color(212, 31, 255), 	
		new Color(212, 63, 0), 		new Color(212, 63, 85), 	new Color(212, 63, 170), 	new Color(212, 63, 255), 	
		new Color(212, 95, 0), 		new Color(212, 95, 85), 	new Color(212, 95, 170), 	new Color(212, 95, 255), 	
		new Color(212, 127, 0), 	new Color(212, 127, 85), 	new Color(212, 127, 170), 	new Color(212, 127, 255), 	
		new Color(212, 159, 0), 	new Color(212, 159, 85), 	new Color(212, 159, 170), 	new Color(212, 159, 255), 	
		new Color(212, 191, 0), 	new Color(212, 191, 85), 	new Color(212, 191, 170), 	new Color(212, 191, 255), 	
		new Color(212, 223, 0), 	new Color(212, 223, 85), 	new Color(212, 223, 170), 	new Color(212, 223, 255), 	
		new Color(212, 255, 0), 	new Color(212, 255, 85), 	new Color(212, 255, 170), 	new Color(212, 255, 255), 	
		new Color(255, 0, 85), 		new Color(255, 0, 170), 	new Color(255, 31, 0), 		new Color(255, 31, 85), 	
		new Color(255, 31, 170), 	new Color(255, 31, 255), 	new Color(255, 63, 0), 		new Color(255, 63, 85), 	
		new Color(255, 63, 170), 	new Color(255, 63, 255), 	new Color(255, 95, 0), 		new Color(255, 95, 85), 	
		new Color(255, 95, 170), 	new Color(255, 95, 255), 	new Color(255, 127, 0), 	new Color(255, 127, 85), 	
		new Color(255, 127, 170), 	new Color(255, 127, 255), 	new Color(255, 159, 0), 	new Color(255, 159, 85), 	
		new Color(255, 159, 170), 	new Color(255, 159, 255), 	new Color(255, 191, 0), 	new Color(255, 191, 85), 	
		new Color(255, 191, 170), 	new Color(255, 191, 255), 	new Color(255, 223, 0), 	new Color(255, 223, 85), 	
		new Color(255, 223, 170), 	new Color(255, 223, 255), 	new Color(255, 255, 85), 	new Color(255, 255, 170), 	
		new Color(204, 204, 255), 	new Color(255, 204, 255), 	new Color(51, 255, 255), 	new Color(102, 255, 255), 	
		new Color(153, 255, 255), 	new Color(204, 255, 255), 	new Color(0, 127, 0), 		new Color(0, 127, 85), 	
		new Color(0, 127, 170), 	new Color(0, 127, 255), 	new Color(0, 159, 0), 		new Color(0, 159, 85), 	
		new Color(0, 159, 170), 	new Color(0, 159, 255), 	new Color(0, 191, 0), 		new Color(0, 191, 85), 	
		new Color(0, 191, 170), 	new Color(0, 191, 255), 	new Color(0, 223, 0), 		new Color(0, 223, 85), 	
		new Color(0, 223, 170), 	new Color(0, 223, 255), 	new Color(0, 255, 85), 		new Color(0, 255, 170), 	
		new Color(42, 0, 0), 		new Color(42, 0, 85), 		new Color(42, 0, 170), 		new Color(42, 0, 255), 	
		new Color(42, 31, 0), 		new Color(42, 31, 85), 		new Color(42, 31, 170), 	new Color(42, 31, 255), 	
		new Color(42, 63, 0), 		new Color(42, 63, 85), 		new Color(255, 251, 240), 	new Color(160, 160, 164), 	
		new Color(128, 128, 128), 	new Color(255, 0, 0), 		new Color(0, 255, 0), 		new Color(255, 255, 0), 	
		new Color(0, 0, 255), 		new Color(255, 0, 255), 	new Color(0, 255, 255), 	new Color(255, 255, 255)
	};
	
	public static Color get(int index) { return palette[index]; }
	
	public static Color black() 	{ return palette[0]; }
	public static Color lightGray() { return palette[247]; }
	public static Color gray() 		{ return palette[248]; }
	public static Color white() 	{ return palette[255]; }
	
	public static Color red() 		{ return palette[249]; }
	public static Color lime() 		{ return palette[250]; }
	public static Color blue() 		{ return palette[252]; }
	
	public static Color maroon()	{ return palette[1]; }
	public static Color green() 	{ return palette[2]; }
	public static Color navy() 		{ return palette[4]; }
	
	public static Color yellow() 	{ return palette[251]; }
	public static Color cyan() 		{ return palette[254]; }
	public static Color pink() 		{ return palette[253]; }
	
	public static Color olive() 	{ return palette[3]; }
	public static Color teal() 		{ return palette[6]; }
	public static Color purple()	{ return palette[5]; }
}
