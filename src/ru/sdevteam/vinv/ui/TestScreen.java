package ru.sdevteam.vinv.ui;

import java.awt.Color;
import java.awt.Graphics;

import ru.sdevteam.vinv.main.Input;
import ru.sdevteam.vinv.ui.controls.Control;
import ru.sdevteam.vinv.ui.controls.TestControl;

public class TestScreen extends Screen
{	
	public TestScreen()
	{
		// создаём контрол
		Control test=new TestControl();
		// перемещаем в нужную точку
		test.moveTo(100, 100);
		// добавляем его на экран
		this.addControl(test);
		
		// и-и, снова
		test=new TestControl();
		test.moveTo(100, 350);
		this.addControl(test);
	}
	
	@Override
	public void update()
	{
		// стандартный способ для экранов, то же должно быть для событий клавиатуры, если нужно
		super.update();
		while(Input.hasMoreMouseEvents())
		{
			processMouseEvent(Input.getNextMouseEvent());
		}
	}
	
	@Override
	public void paint(Graphics g)
	{
		// рисуем фон
		g.setColor(new Color(50, 20, 20));
		g.fillRect(0, 0, getWidth(), getHeight());
		// и детей на нём
		super.paintChildren(g);
	}
}
