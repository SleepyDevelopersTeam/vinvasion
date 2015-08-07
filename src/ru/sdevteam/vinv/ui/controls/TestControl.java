package ru.sdevteam.vinv.ui.controls;

import java.awt.Color;
import java.awt.Graphics;

import ru.sdevteam.vinv.main.MouseEvent;
import ru.sdevteam.vinv.main.ResourceManager;

public class TestControl extends FocusableControl
{
	Color fill, blink;
	int frame;
	
	
	public TestControl()
	{
		fill=Color.GRAY;
		blink=Color.blue;
		setSize(200, 100);
		frame=0;
	}
	
	
	@Override
	public void update()
	{
		
	}
	
	@Override
	public void paint(Graphics g)
	{
		if(frame>100) frame=0;
		if(frame<0) frame=100;
		
		g.setColor(fill);
		g.fillRect(getX(), getY(), getWidth(), getHeight());
		
		g.setColor(blink);
		g.fillRect(getX()+5, getY()+getHeight()-15, 10+frame, 10);
		
		g.setFont(ResourceManager.getMainFont().deriveFont(12F));
		g.drawString("Frame "+frame, getX()+5, getY()+20);
	}
	
	@Override
	protected void onMouseOver(MouseEvent ev)
	{
		super.onMouseOver(ev);
		fill=Color.LIGHT_GRAY;
	}
	@Override
	protected void onMouseOut(MouseEvent ev)
	{
		super.onMouseOut(ev);
		fill=Color.GRAY;
	}
	@Override
	protected void onMousePressed(MouseEvent ev)
	{
		super.onMousePressed(ev);
		fill=Color.DARK_GRAY;
	}
	@Override
	protected void onMouseReleased(MouseEvent ev)
	{
		super.onMouseReleased(ev);
		fill=Color.LIGHT_GRAY;
	}
	@Override
	protected void onMouseDragStart(MouseEvent ev)
	{
		super.onMouseDragStart(ev);
		fill=Color.red;
	}
	@Override
	protected void onMouseDraggedOut(MouseEvent ev)
	{
		super.onMouseDraggedOut(ev);
		fill=Color.yellow;
	}
	@Override
	protected void onMouseDraggedInto(MouseEvent ev)
	{
		super.onMouseDraggedInto(ev);
		fill=Color.red;
	}
	@Override
	protected void onMouseDragEnd(MouseEvent ev, Control dragStarter)
	{
		super.onMouseDragEnd(ev, dragStarter);
		fill=dragStarter==null?Color.LIGHT_GRAY:Color.green;
		if(dragStarter!=null) frame+=50;
	}
	@Override
	protected void onMouseDragDroppedOutside(MouseEvent ev, Control dropTarget)
	{
		super.onMouseDragDroppedOutside(ev, dropTarget);
		fill=dropTarget==null?Color.GRAY:Color.green;
	}
	
	@Override
	protected void onMouseMoved(MouseEvent ev)
	{
		super.onMouseMoved(ev);
		frame++;
		blink=Color.blue;
	}
	@Override
	protected void onMouseDragging(MouseEvent ev)
	{
		super.onMouseDragging(ev);
		frame++;
		blink=Color.cyan;
	}
	@Override
	protected void onMouseDraggingOutside(MouseEvent ev, Control dropTarget)
	{
		super.onMouseDraggingOutside(ev, dropTarget);
		frame++;
		blink=Color.magenta;
	}
	
	@Override
	protected void onMouseScroll(MouseEvent ev)
	{
		super.onMouseScroll(ev);
		if(ev.getDelta()>0)
		{
			frame+=10;
		}
		if(ev.getDelta()<0)
		{
			frame-=10;
		}
	}
}
