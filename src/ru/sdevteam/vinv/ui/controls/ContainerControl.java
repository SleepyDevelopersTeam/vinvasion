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
		c.setParent(this);
	}
	public void removeControl(Control c)
	{
		controls.remove(c);
		c.setParent(null);
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
		// TODO: возможно, более оптимальное решение:
		// всегда возвращать true, чтобы метод выполнялся быстрее и без захламления стека;
		// но тогда дольше будет выполняться processKeyEvent верхнего в иерархии контейнера.
		for(Control c: this.controls)
		{
			if(c.isFocused()) return true;
		}
		return false;
	}
	
}
