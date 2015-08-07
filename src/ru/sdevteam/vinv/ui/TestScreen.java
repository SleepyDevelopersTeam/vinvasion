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
		Control test=new TestControl();
		test.moveTo(100, 100);
		this.addControl(test);
		test=new TestControl();
		test.moveTo(100, 350);
		this.addControl(test);
	}
	
	@Override
	public void update()
	{
		super.update();
		while(Input.hasMoreMouseEvents())
		{
			processMouseEvent(Input.getNextMouseEvent());
		}
	}
	
	@Override
	public void paint(Graphics g)
	{
		g.setColor(new Color(50, 20, 20));
		super.paint(g);
	}
}
