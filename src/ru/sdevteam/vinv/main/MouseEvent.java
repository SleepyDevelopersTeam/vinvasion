package ru.sdevteam.vinv.main;

public class MouseEvent
{
	public enum Types { MOTION, PRESSED, RELEASED, SCROLL }
	public enum Buttons { NONE, LEFT, RIGHT, WHEEL }
	
	private int mx, my, delta;
	private Types event;
	private Buttons button;
	
	public MouseEvent(int mx, int my, int delta, Types type, Buttons button)
	{
		event=type; this.button=button;
		this.mx=mx; this.my=my; this.delta=delta;
	}
	
	public int getMouseX() { return mx; }
	public int getMouseY() { return my; }
	public int getDelta() { return delta; }
	
	public Types getType() { return event; }
	public Buttons getButton() { return button; }
}
