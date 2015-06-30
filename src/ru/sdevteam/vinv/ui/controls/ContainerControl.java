package ru.sdevteam.vinv.ui.controls;

import java.awt.Graphics;
import java.util.Vector;

import ru.sdevteam.vinv.main.KeyEvent;
import ru.sdevteam.vinv.main.MouseEvent;

public class ContainerControl extends Control
{
	protected Vector<Control> controls;
	
	
	private void init()
	{
		controls=new Vector<Control>();
	}
	public ContainerControl()
	{
		super();
		init();
	}
	public ContainerControl(int x, int y, int width, int height)
	{
		super(x, y, width, height);
		init();
	}
	
	
	public void addControl(Control c)
	{
		controls.add(c);
	}
	public void removeControl(Control c)
	{
		controls.remove(c);
	}
	
	@Override
	public void update()
	{
		for(Control c: this.controls)
		{
			c.update();
		}
	}
	
	@Override
	public void paint(Graphics g)
	{
		paintChildren(g);
	}
	
	protected final void paintChildren(Graphics g)
	{
		for(Control c: this.controls)
		{
			c.paint(g);
		}
	}
	
	@Override
	public final void processMouseEvent(MouseEvent ev)
	{
		if(!isEnabled()) return;
		
		super.processMouseEvent(ev);
		for(Control c: this.controls)
		{
			c.processMouseEvent(ev);
		}
	}
	
	@Override
	public final void processKeyEvent(KeyEvent ev)
	{
		if(!isEnabled()) return;
		
		super.processKeyEvent(ev);
		for(Control c: this.controls)
		{
			if(c.isFocused())
				c.processKeyEvent(ev);
		}
	}
	
	@Override
	protected final void unfocus()
	{
		for(Control c: this.controls)
		{
			c.unfocus();
		}
		if(getParent()!=null)
			getParent().unfocus();
	}
	@Override
	public final boolean isFocused()
	{
		for(Control c: this.controls)
		{
			if(c.isFocused()) return true;
		}
		return false;
	}
	
}
