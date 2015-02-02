package ru.sdevteam.vinv.utils;

// Честно говоря, говнокод какой-то, надо переделать
public class MouseEvent
{
	// Кнопка
	public static final int NONE=0, LEFT=1, RIGHT=2, WHEEL=4;
	// Событие
	public static final int MOTION=0, PRESSED=1, RELEASED=2;
	
	private int mx, my, delta;
	// Тип события, нажатые кнопки и вызвавшая событие кнопка
	private int event, btns, invoker;
	
	
	public MouseEvent(int mx, int my, int delta, int type, int buttons, int invoker)
	{
		event=type; btns=buttons; this.invoker=invoker;
		this.mx=mx; this.my=my; this.delta=delta;
	}
	
	
	public boolean isLeftPressed()		{ return (invoker==LEFT)	&&	(event==PRESSED); }
	public boolean isRightPressed()		{ return (invoker==RIGHT)	&&	(event==PRESSED); }
	public boolean isWheelPressed()		{ return (invoker==WHEEL)	&&	(event==PRESSED); }
	
	public boolean isLeftReleased() 	{ return (invoker==LEFT)	&&	(event==RELEASED); }
	public boolean isRightReleased()	{ return (invoker==RIGHT)	&&	(event==RELEASED); }
	public boolean isWheelReleased()	{ return (invoker==WHEEL)	&&	(event==RELEASED); }
	
	public boolean isLeftDown()		{ return ((btns & LEFT)		!=0); }
	public boolean isRightDown()	{ return ((btns & RIGHT)	!=0); }
	public boolean isWheelDown()	{ return ((btns & WHEEL)	!=0); }
	
	// Событие вызвано движением мыши?
	public boolean isMotionEvent() { return invoker==MOTION; }
	
	// Определяет, нажата ли хотя бы одна кнопка
	public boolean isMouseDown() { return btns==NONE; }
	
	public int getMouseX() { return mx; }
	public int getMouseY() { return my; }
	public int getDelta() { return delta; }
}
