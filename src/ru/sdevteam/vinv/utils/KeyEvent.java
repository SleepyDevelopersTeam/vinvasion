package ru.sdevteam.vinv.utils;

public class KeyEvent
{
	public static final int PRESSED=1, RELEASED=2, TYPED=3;
	private ControlKeys key;
	private char keyChar;
	private int event;
	
	public KeyEvent(ControlKeys ckey, char keychar, int type)
	{
		key=ckey;
		keyChar=keychar;
		event=type;
	}
	
	public boolean isKeyPressed() { return event==PRESSED; }
	public boolean isKeyReleased() { return event==RELEASED; }
	public boolean isKeyTyped() { return event==TYPED; }
	
	public char getKeyChar() { return keyChar; }
	public ControlKeys getKey() { return key; }
}
