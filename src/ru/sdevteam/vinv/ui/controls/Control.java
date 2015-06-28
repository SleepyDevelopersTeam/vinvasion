package ru.sdevteam.vinv.ui.controls;

import java.awt.Graphics;

import ru.sdevteam.vinv.main.KeyEvent;
import ru.sdevteam.vinv.main.MouseEvent;
import ru.sdevteam.vinv.ui.IDrawable;
import ru.sdevteam.vinv.ui.IUpdatable;

public abstract class Control implements IUpdatable, IDrawable
{
	//
	// Координаты
	//
	private int x=0, y=0;
	public int getX() { return x; }
	public int getY() { return y; }
	public void setX(int nx) { x=nx; }
	public void setY(int ny) { y=ny; }
	public void moveTo(int nx, int ny) { x=nx; y=ny; }
	//public void moveBy(int dx, int dy) { x+=dx; y+=dy; }
	
	//
	// Размеры
	//
	private int w=50, h=50;
	public int getWidth() { return w; }
	public int getHeight() { return h; }
	public void setWidth(int nw) { w=nw; }
	public void setHeight(int nh) { h=nh; }
	public void setSize(int nw, int nh) { w=nw; h=nh; }
	
	//
	// Состояние
	//
	private boolean hovered, pressed, dragging;
	public boolean isHovered() { return hovered; }
	public boolean isPressed() { return pressed; }
	public boolean isDragging() { return dragging; }
	// mouseDrag start x, y
	private int mdsx, mdsy;
	protected int getMouseDragStartX() { return mdsx; }
	protected int getMouseDragStartY() { return mdsy; }
	// last mouse x, y
	private int lmx, lmy;
	// last mouse hovered?
	private boolean lmh;
	
	private boolean enabled;
	public boolean isEnabled() { return getParent()==null? enabled : enabled && getParent().isEnabled(); }
	public void enable() { enabled=true; }
	public void disable() { enabled=false; }
	
	//
	// Иерархия
	//
	private ContainerControl parent;
	public ContainerControl getParent() { return parent; }
	public void setParent(ContainerControl c) { parent=c; }
	
	//
	// Фокус
	//
	protected abstract void unfocus();
	public abstract boolean isFocused();
	
	
	public Control()
	{
		enabled=true;
	}
	
	public Control(int x, int y, int width, int height)
	{
		this();
		this.x=x; this.y=y; this.w=width; this.h=height;
	}
	
	
	public boolean contains(int x, int y)
	{
		if(x<this.x) return false;
		if(x>this.x+this.w) return false;
		if(y<this.y) return false;
		if(y>this.y+this.h) return false;
		return true;
	}
	
	// TODO: test
	// TODO: Использовать очередь событий в Input
	public void processMouseEvent(MouseEvent ev)
	{
		if(!enabled) return;
		
		if(this.contains(ev.getMouseX(), ev.getMouseY()))
		{
			hovered=true;
		}
		else
		{
			hovered=false;
		}
		
		switch(ev.getType())
		{
		case MOTION:
			if(pressed)
			{
				if(!dragging)
				{
					// !dragging && pressed => драг ещё не начался, но уже может - стоит проверить:
					int dx=mdsx-ev.getMouseX(), dy=mdsy-ev.getMouseY();
					if(dx*dx+dy*dy>25)
					{
						// ага, попался! провёл мышью больше 5px => это уже драг
						dragging=true;
						onMouseDragStart(ev);
					}
					else
					{
						// рано, рано...
						if(hovered) onMouseMoved(ev);
					}
				}
				else // dragging
				{
					onMouseDragging(ev);
					if(hovered)
					{
						if(!lmh) onMouseOver(ev);
					}
					else
					{
						if(lmh) onMouseOut(ev);
					}
				}
			}
			else // !pressed
			{
				if(hovered)
				{
					if(lmh) onMouseMoved(ev);
					else onMouseOver(ev);
				}
				else
				{
					if(lmh) onMouseOut(ev);
				}
			}
			break;
			
		case PRESSED:
			if(hovered)
			{
				pressed=true;
				mdsx=ev.getMouseX();
				mdsy=ev.getMouseY();
				onMousePressed(ev);
			}
			break;
			
		case RELEASED:
			if(dragging)
			{
				dragging=false;
				onMouseDragEnd(ev);
			}
			if(hovered)
			{
				pressed=false;
				onMouseReleased(ev);
			}
			break;
			
		case SCROLL:
			if(hovered)
			{
				onMouseScroll(ev);
			}
			break;
		}
		
		lmx=ev.getMouseX();
		lmy=ev.getMouseY();
		lmh=hovered;
	}
	
	protected void onMouseMoved(MouseEvent ev){}
	
	protected void onMousePressed(MouseEvent ev){}
	protected void onMouseReleased(MouseEvent ev){}
	
	protected void onMouseOver(MouseEvent ev){}
	protected void onMouseOut(MouseEvent ev){}
	
	protected void onMouseDragging(MouseEvent ev){}
	protected void onMouseDragStart(MouseEvent ev){}
	protected void onMouseDragEnd(MouseEvent ev){}
	
	protected void onMouseScroll(MouseEvent ev){}
	
	public void processKeyEvent(KeyEvent ev)
	{
		if(!enabled) return;
		
		switch(ev.getType())
		{
		case PRESSED:
			onKeyDown(ev);
			break;
		case RELEASED:
			onKeyUp(ev);
			break;
		case TYPED:
			onKeyTyped(ev);
			break;
		}
	}
	
	protected void onKeyDown(KeyEvent ev){}
	protected void onKeyUp(KeyEvent ev){}
	protected void onKeyTyped(KeyEvent ev){}
}
