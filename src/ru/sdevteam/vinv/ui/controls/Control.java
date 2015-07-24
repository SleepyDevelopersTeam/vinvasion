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
	private int x=0, y=0, oldx=0, oldy=0;
	public int getX() { return x; }
	public int getY() { return y; }
	
	public void setX(int nx)
	{ oldx=x; x=nx; onLocationChanged(oldx, y); }
	
	public void setY(int ny)
	{ oldy=y; y=ny; onLocationChanged(x, oldy); }
	
	public void moveTo(int nx, int ny)
	{ oldx=x; oldy=y; x=nx; y=ny; onLocationChanged(oldx, oldy); }
	
	protected void onLocationChanged(int oldx, int oldy){}
	
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
	private boolean hovered, pressed, dragging; // hovered || dragging <= pressed
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
	
	public void processMouseEvent(MouseEvent ev)
	{
		
	}
	
	protected void onMouseMoved(MouseEvent ev){}
	
	protected void onMousePressed(MouseEvent ev){}
	protected void onMouseReleased(MouseEvent ev){}
	
	protected void onMouseOver(MouseEvent ev){}
	protected void onMouseOut(MouseEvent ev){}
	
	protected void onMouseDragging(MouseEvent ev){}
	protected void onMouseDraggingOutside(MouseEvent ev){}
	protected void onMouseDragStart(MouseEvent ev){}
	protected void onMouseDragEnd(MouseEvent ev, Control dragStarter){}
	protected void onMouseDragDroppedOutside(MouseEvent ev, Control dropTarget){}
	
	protected void onMouseDraggedInto(MouseEvent ev){}
	protected void onMouseDraggedOut(MouseEvent ev){}
	
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
