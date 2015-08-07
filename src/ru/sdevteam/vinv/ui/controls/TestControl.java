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
		// анимация
		if(frame>100) frame=0;
		if(frame<0) frame=100;
		
		// заливаем фон
		g.setColor(fill);
		g.fillRect(getX(), getY(), getWidth(), getHeight());
		
		// рисуем полоску
		g.setColor(blink);
		g.fillRect(getX()+5, getY()+getHeight()-15, 10+frame, 10);
		
		// ну и немного текста
		g.setFont(ResourceManager.getMainFont().deriveFont(12F));
		g.drawString("Frame "+frame, getX()+5, getY()+20);
	}
	
	@Override
	protected void onMouseOver(MouseEvent ev)
	{
		// подсветка при наведении
		super.onMouseOver(ev);
		fill=Color.LIGHT_GRAY;
	}
	@Override
	protected void onMouseOut(MouseEvent ev)
	{
		// и обратно
		super.onMouseOut(ev);
		fill=Color.GRAY;
	}
	@Override
	protected void onMousePressed(MouseEvent ev)
	{
		// при нажатии
		super.onMousePressed(ev);
		fill=Color.DARK_GRAY;
	}
	@Override
	protected void onMouseReleased(MouseEvent ev)
	{
		// и обратно
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
		// подсветка по окончании драга
		super.onMouseDragEnd(ev, dragStarter);
		fill=dragStarter==null?Color.LIGHT_GRAY:Color.green;
		if(dragStarter!=null) frame+=50;
		// если начали перетаскивать не с пустого места, то dragStarter!=null;
		// поскольку сразу после этого идёт событие mouseMove, то подсветка теряется,
		// а вот frame+=50 заметно сразу
	}
	@Override
	protected void onMouseDragDroppedOutside(MouseEvent ev, Control dropTarget)
	{
		super.onMouseDragDroppedOutside(ev, dropTarget);
		fill=dropTarget==null?Color.GRAY:Color.green;
		// если дропнули не на пустое место, то подсветим зелёным
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
