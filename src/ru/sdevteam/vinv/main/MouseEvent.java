package ru.sdevteam.vinv.main;

public class MouseEvent
{
	public enum Type { MOTION, PRESSED, RELEASED, SCROLL }
	public enum Button { NONE, LEFT, RIGHT, WHEEL }
	
	private int mx, my, delta;
	private Type event;
	private Button button;
	
	private InputState actualState;
	
	public MouseEvent(int mx, int my, int delta, Type type, Button button)
	{
		event=type; this.button=button;
		this.mx=mx; this.my=my; this.delta=delta;
		
		handled=false;
	}
	
	public int getMouseX() { return mx; }
	public int getMouseY() { return my; }
	public int getDelta() { return delta; }
	
	public Type getType() { return event; }
	public Button getButton() { return button; }
	
	public InputState getActualState() { return actualState; }
	void setActualState(InputState s) { actualState=s; }
	
	private boolean handled;
	public boolean isHandled() { return handled; }
	public void handle() { handled=true; }
}
