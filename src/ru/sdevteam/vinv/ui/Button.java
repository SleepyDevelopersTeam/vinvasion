package ru.sdevteam.vinv.ui;

import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import ru.sdevteam.vinv.game.IMoveable;

public abstract class Button implements IDrawable, IUpdatable, IMoveable
{
	private String text;
	private boolean active;
	private int x,y,width,height;

	public String getText()
	{
		return text;
	}
	public void setText(String ntext)
	{
		text=ntext;
	}

	public Button()
	{
		
	}
	public Button(String text)
	{
		this.text=text;
	}
	public Button(String text, int x, int y, int width, int height)
	{
		this.text=text;
		this.x=x;
		this.y=y;
		this.width=width;
		this.height=height;
	}


	void activate()
	{
		active=true;
	}
	void deactivate()
	{
		active=false;
	}

	public abstract void processEvent(MouseEvent ev);
	public abstract void processEvent(KeyEvent ev);

	
	protected abstract void onActivated();
}
