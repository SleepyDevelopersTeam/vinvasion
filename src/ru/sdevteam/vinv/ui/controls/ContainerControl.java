package ru.sdevteam.vinv.ui.controls;

import java.awt.Graphics;
import java.util.Vector;

import ru.sdevteam.vinv.main.KeyEvent;
import ru.sdevteam.vinv.main.MouseEvent;

public class ContainerControl extends Control
{
	protected Vector<Control> controls;
	
	private int lmx=-1, lmy=-1; // last mouse coords
	private int cmx=-1, cmy=-1; // current click coords
	// элемент, над которым курсор, и элемент, над которым была нажата кнопка мыши
	private Control hovered=null, clicked=null;
	private boolean draggable, dragging;
	
	
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
		for(int i=0; i<this.controls.size(); i++)
		{
			if(this.controls.get(i).isVisible()) this.controls.get(i).paint(g);
		}
	}
	
	@Override
	public final void processMouseEvent(MouseEvent ev)
	{
		if(!isEnabled()) return;
		
		int x=ev.getMouseX(), y=ev.getMouseY();
		switch(ev.getType())
		{
		case MOTION:
			Control newHovered=null;
			for(int i=this.controls.size()-1; i>=0; i--)
			{
				Control c=this.controls.get(i);
				if(c.contains(x, y) && c.isVisible())
				{
					newHovered=c;
					break;
				}
			}
			
			if(draggable)
			{
				// мышь нажата, возможен драг
				if(cmx*cmx+cmy*cmy>25)
				{
					// начало драга
					dragging=true;
					draggable=false;
					
					if(newHovered==hovered)
					{
						// драг начался на этом же компоненте
						if(hovered!=null) hovered.onMouseDragStart(ev);
					}
					else
					{
						// чёрт возьми, это почти невозможно!
						// но драг начался как раз тогда, когда мышь ушла к другому компоненту
						if(hovered!=null)
						{
							hovered.onMouseDragStart(ev);
							hovered.onMouseDraggedOut(ev);
						}
						if(newHovered!=null)
						{
							newHovered.onMouseDraggedInto(ev);
						}
					}
					
				}
				else
				{
					// нет, драга (пока) нет
					if(newHovered==hovered)
					{
						// курсор над тем же
						if(hovered!=null) hovered.onMouseMoved(ev);
					}
					else
					{
						// курсор перешёл
						if(hovered!=null) hovered.onMouseOut(ev);
						if(newHovered!=null) newHovered.onMouseOver(ev);
					}
				}
			}
			else
			{
				// драг невозможен => он уже идёт, либо мышь не нажата
				if(dragging)
				{
					// драг
					if(newHovered==hovered)
					{
						// курсор над тем же элементом
						if(hovered!=null) hovered.onMouseDragging(ev);
						if(clicked!=null && clicked!=hovered) clicked.onMouseDraggingOutside(ev, hovered);
					}
					else
					{
						// курсор сместился со старого элемента на новый
						if(hovered!=null) hovered.onMouseDraggedOut(ev);
						if(newHovered!=null) newHovered.onMouseDraggedInto(ev);
					}
				}
				else
				{
					// не драг
					if(newHovered==hovered)
					{
						// курсор над тем же
						if(hovered!=null) hovered.onMouseMoved(ev);
					}
					else
					{
						// курсор перешёл
						if(hovered!=null) hovered.onMouseOut(ev);
						if(newHovered!=null) newHovered.onMouseOver(ev);
					}
				}
			}
			
			hovered=newHovered;
			
			break;
		case PRESSED:
			draggable=true;
			cmx=x; cmy=y;
			clicked=hovered;
			
			if(hovered!=null)
			{
				hovered.onMousePressed(ev);
			}
			
			break;
		case RELEASED:
			//draggable=false;
			
			if(dragging)
			{
				// был драг, пора его завершить
				dragging=false;
				if(clicked!=null) clicked.onMouseDragDroppedOutside(ev, hovered);
				if(hovered!=clicked && hovered!=null) hovered.onMouseDragEnd(ev, clicked);
			}
			else if(hovered!=null) hovered.onMouseReleased(ev);
			
			cmx=cmy=-1;
			break;
		case SCROLL:
			if(hovered!=null)
			{
				hovered.onMouseScroll(ev);
			}
			else
			{
				this.onMouseScroll(ev);
			}
			break;
		}
		
		for(int i=this.controls.size()-1; i>=0; i--)
		{
			if(this.controls.get(i).isVisible()) this.controls.get(i).processMouseEvent(ev);
			//if(ev.isHandled()) return;
		}
		
		lmx=x;
		lmy=y;
		
		super.processMouseEvent(ev);
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
