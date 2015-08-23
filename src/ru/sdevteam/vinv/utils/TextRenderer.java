package ru.sdevteam.vinv.utils;

import java.awt.Color;
import java.awt.Graphics;
import ru.sdevteam.vinv.ui.controls.HorizontalAlignment;
import ru.sdevteam.vinv.ui.controls.VerticalAlignment;

public class TextRenderer
{
	// отрисовывает строку text так, чтобы её левый верхний угол находился в (x;y)
	public static void drawString(Graphics g, int x, int y, String text)
	{
		g.setColor(Color.black);
		g.drawString(text, x, y);
	}
	// отрисовывает строку text относительно точки (x;y) с заданным выравниванием
	// выравнивание по умолчанию - LEFT, TOP
	public static void drawString(Graphics g, int x, int y, HorizontalAlignment h, VerticalAlignment v, String text)
	{
		
	}
	public static void drawString(Graphics g, int x, int y, HorizontalAlignment h, String text);
	public static void drawString(Graphics g, int x, int y, VerticalAlignment v, String text);
	// отрисовывает многострочный текст в заданном прямоугольнике с заданным выравниванием
	public static void drawMultiline(Graphics g, int x, int y, int width, int height, HorizontalAlignment h, VerticalAlignment v, String text)
	{
		
	}
	// выравнивание по умолчанию то же
	public static void drawMultiline(Graphics g, int x, int y, int width, int height, HorizontalAlignment h, String text);
	public static void drawMultiline(Graphics g, int x, int y, int width, int height, VerticalAlignment v, String text);
	public static void drawMultiline(Graphics g, int x, int y, int width, int height, String text);

}
