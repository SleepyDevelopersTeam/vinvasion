package ru.sdevteam.vinv.ui;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import ru.sdevteam.vinv.main.Input;
import ru.sdevteam.vinv.main.MouseEvent;
import ru.sdevteam.vinv.main.ResourceManager;
import ru.sdevteam.vinv.ui.controls.Button;
import ru.sdevteam.vinv.ui.controls.ButtonSet;
import ru.sdevteam.vinv.ui.controls.Control;
import ru.sdevteam.vinv.ui.controls.ControlAdapter;
import ru.sdevteam.vinv.ui.controls.FlowLayout;
import ru.sdevteam.vinv.ui.controls.FlowLayout.LayoutType;
import ru.sdevteam.vinv.ui.controls.TestControl;
import ru.sdevteam.vinv.ui.controls.TextBox;
import ru.sdevteam.vinv.utils.Colors;
import ru.sdevteam.vinv.utils.Fonts;

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
		
		ButtonSet l=new ButtonSet(300, 300, 400, 400, LayoutType.HORIZONTAL);
		l.setMargin(5);
		l.addButton(new Button("1"));
		l.addButton(new Button("2"));
		l.addButton(new Button("3"));
		l.addButton(new Button("4"));
		this.addControl(l);
		//l.focusButton(0);
		
		TextBox tb=new TextBox("My text", 300, 50);
		tb.select(2, 3);
		tb.setFont(Fonts.main(8));
		addControl(tb);
		
		tb.focus();
	}
	
	@Override
	public void update()
	{
		if(!ResourceManager.isReady()) return;
		super.update();
		while(Input.hasMoreMouseEvents())
		{
			processMouseEvent(Input.getNextMouseEvent());
		}
		while(Input.hasMoreKeyEvents())
		{
			processKeyEvent(Input.getNextKeyEvent());
		}
	}
	
	@Override
	public void paint(Graphics g)
	{
		if(!ResourceManager.isReady()) return;
		// рисуем фон
		g.setColor(new Color(50, 20, 20));
		g.fillRect(0, 0, getWidth(), getHeight());
		// и детей на нём
		super.paintChildren(g);
	}
}
