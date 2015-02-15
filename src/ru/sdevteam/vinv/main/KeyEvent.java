package ru.sdevteam.vinv.main;

public class KeyEvent
{
	public enum Type { PRESSED, RELEASED, TYPED }
	
	private int key;
	private char keyChar;
	private Type event;
	
	private InputState actualState;
	
	public KeyEvent(int keyCode, char keychar, Type eventType)
	{
		key=keyCode;
		keyChar=keychar;
		event=eventType;
	}
	
	public char keyChar() { return keyChar; }
	public int getKey() { return key; }
	public Type getType() { return event; }
	
	public InputState getActualState() { return actualState; }
	void setActualState(InputState s) { actualState=s; }
}
