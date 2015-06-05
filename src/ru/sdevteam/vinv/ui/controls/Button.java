package ru.sdevteam.vinv.ui.controls;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Vector;

import ru.sdevteam.vinv.main.ControlKey;
import ru.sdevteam.vinv.main.KeyEvent;
import ru.sdevteam.vinv.main.MouseEvent;

public class Button extends FocusableControl
{
	private String text;
	public String getText() { return text; }
	public void setText(String ntext) { text=ntext; }
	
	private Vector<ActionListener> pressedListeners;
	public void addButtonPressedListener(ActionListener l)
	{
		pressedListeners.add(l);
	}
	public void removeButtonPressedListener(ActionListener l)
	{
		pressedListeners.remove(l);
	}
	
	
	public Button()
	{
		super();
	}
	public Button(String text)
	{
		super();
		this.text=text;
	}
	public Button(String text, int x, int y, int width, int height)
	{
		super(x, y, width, height);
		this.text=text;
	}
	
	
	@Override
	public void update()
	{
		
	}
	
	@Override
	public void paint(Graphics g)
	{
		// TODO: set design and drawing
		g.setColor(Color.red);
		g.fillRect(getX(), getY(), getWidth(), getHeight());
	}
	
	public final void press()
	{
		if(!isFocused()) focus();
		onPressed();
		for(ActionListener l: pressedListeners)
		{
			l.actionPerformed(null);
		}
	}
	
	protected void onPressed(){}
	
	@Override
	protected void onKeyUp(KeyEvent ev)
	{
		if(ControlKey.ACCEPT.checkCode(ev.getKey()))
		{
			press();
		}
	}
	
	@Override
	protected void onMouseReleased(MouseEvent ev)
	{
		press();
	}
}
