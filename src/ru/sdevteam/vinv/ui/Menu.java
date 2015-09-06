package ru.sdevteam.vinv.ui;

import java.awt.Graphics;

import ru.sdevteam.vinv.game.IMoveable;
import ru.sdevteam.vinv.main.KeyEvent;
import ru.sdevteam.vinv.main.MouseEvent;
import ru.sdevteam.vinv.ui.controls.Button;
import ru.sdevteam.vinv.ui.controls.ButtonSet;
import ru.sdevteam.vinv.ui.controls.ContainerControl;
import ru.sdevteam.vinv.ui.controls.FlowLayout.LayoutType;


public class Menu extends ContainerControl
{
	private ButtonSet buttons;
	private Menu previous;
	
	public Menu(int x, int y, int width, int height) 
	{
		super(x,y,width,height);
		buttons = new ButtonSet(LayoutType.VERTICAL);
		buttons.setStartPoint(15, 150);
		addControl(buttons);
	}

	public Menu getPrevious()
	{
		return previous;
	}
	
	protected void setPrevious(Menu m)
	{
		previous = m;
	}
	public void addButton(Button b)
	{
		buttons.addButton(b);
	}
	@Override
	public void paint(Graphics g) {
		super.paint(g);
	}
	@Override
	public void update() {
		super.update();
	}
}
