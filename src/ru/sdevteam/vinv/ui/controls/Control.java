package ru.sdevteam.vinv.ui.controls;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.util.Vector;

import ru.sdevteam.vinv.main.KeyEvent;
import ru.sdevteam.vinv.main.MouseEvent;
import ru.sdevteam.vinv.ui.IDrawable;
import ru.sdevteam.vinv.ui.IUpdatable;
import ru.sdevteam.vinv.utils.Colors;
import ru.sdevteam.vinv.utils.DebugInfo;
import ru.sdevteam.vinv.utils.Fonts;

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
	
	private boolean visible;
	public boolean isVisible() { return visible; }
	public void setVisibility(boolean visible) { this.visible=visible; }
	public void show() { visible=true; }
	public void hide() { visible=false; }
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
	
	//
	// События
	//
	private Vector<IControlListener> listeners;
	public void addControlListener(IControlListener item)
	{
		listeners.add(item);
	}
	public void removeControlListener(IControlListener item)
	{
		listeners.remove(item);
	}
	
	//
	// Шрифт и цвета
	//
	private Font font;
	public Font getFont() { return font; }
	public void setFont(Font f) { font=f; }
	
	private FontMetrics fm;
	protected FontMetrics getFontMetrics(Graphics g)
	{
		if(font==null) return null;
		if(fm==null)
			fm=g.getFontMetrics(this.font);
		return fm;
	}
	
	private Color fore, back;
	public Color getForeground() { return fore; }
	public Color getBackground() { return back; }
	public void setForeground(Color c) { fore=c; }
	public void setBackground(Color c) { back=c; }
	
	
	public Control()
	{
		enabled=true;
		visible=true;
		listeners=new Vector<IControlListener>();
		if(Fonts.initialized())
			font=Fonts.main(8);
		fore=Colors.white();
		back=Colors.gray();
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
	
	protected void onMouseMoved(MouseEvent ev)
	{ 
		for(IControlListener l: listeners)
		{
			l.mouseMoved(ev, this);
		}
	}
	
	protected void onMousePressed(MouseEvent ev)
	{
		pressed=true;
		DebugInfo.addMessage("Pressed: "+pressed);
		
		for(IControlListener l: listeners)
		{
			l.mousePressed(ev, this);
		}
	}
	protected void onMouseReleased(MouseEvent ev)
	{
		pressed=false;
		DebugInfo.addMessage("Released: "+pressed);
		
		for(IControlListener l: listeners)
		{
			l.mouseReleased(ev, this);
		}
	}
	
	protected void onMouseOver(MouseEvent ev)
	{
		hovered=true;
		
		for(IControlListener l: listeners)
		{
			l.mouseOver(ev, this);
		}
	}
	protected void onMouseOut(MouseEvent ev)
	{
		hovered=false;
		
		for(IControlListener l: listeners)
		{
			l.mouseOut(ev, this);
		}
	}
	
	protected void onMouseDragging(MouseEvent ev)
	{ 
		for(IControlListener l: listeners)
		{
			l.mouseDragging(ev, this);
		}
	}
	protected void onMouseDraggingOutside(MouseEvent ev, Control dropTarget)
	{ 
		for(IControlListener l: listeners)
		{
			l.mouseDraggingOutside(ev, this, dropTarget);
		}
	}
	protected void onMouseDragStart(MouseEvent ev)
	{
		mdsx=ev.getMouseX(); mdsy=ev.getMouseY();
		
		for(IControlListener l: listeners)
		{
			l.mouseDragStart(ev, this);
		}
	}
	protected void onMouseDragEnd(MouseEvent ev, Control dragStarter)
	{
		mdsx=mdsy=-1;
		pressed=false;
		
		for(IControlListener l: listeners)
		{
			l.mouseDragEnd(ev, this, dragStarter);
		}
	}
	protected void onMouseDragDroppedOutside(MouseEvent ev, Control dropTarget)
	{
		mdsx=mdsy=-1;
		pressed=false;
		
		for(IControlListener l: listeners)
		{
			l.mouseDragDroppedOutside(ev, this, dropTarget);
		}
	}
	
	protected void onMouseDraggedInto(MouseEvent ev)
	{
		pressed=true; hovered=true;
		
		for(IControlListener l: listeners)
		{
			l.mouseDraggedInto(ev, this);
		}
	}
	protected void onMouseDraggedOut(MouseEvent ev)
	{
		hovered=false;
		
		for(IControlListener l: listeners)
		{
			l.mouseDraggedOut(ev, this);
		}
	}
	
	protected void onMouseScroll(MouseEvent ev)
	{ 
		for(IControlListener l: listeners)
		{
			l.mouseScroll(ev, this);
		}
	}
	
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
	
	protected void onKeyDown(KeyEvent ev)
	{ 
		for(IControlListener l: listeners)
		{
			l.keyDown(ev, this);
		}
	}
	protected void onKeyUp(KeyEvent ev)
	{ 
		for(IControlListener l: listeners)
		{
			l.keyUp(ev, this);
		}
	}
	protected void onKeyTyped(KeyEvent ev)
	{ 
		for(IControlListener l: listeners)
		{
			l.keyTyped(ev, this);
		}
	}
}
