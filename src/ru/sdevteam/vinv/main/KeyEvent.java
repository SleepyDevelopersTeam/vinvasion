package ru.sdevteam.vinv.main;

public class KeyEvent
{
	public enum Type { PRESSED, RELEASED, TYPED }
	
	private ControlKey key;
	private char keyChar;
	private Type event;
	
	public KeyEvent(ControlKey ckey, char keychar, Type eventType)
	{
		key=ckey;
		keyChar=keychar;
		event=eventType;
	}
	
	public char keyChar() { return keyChar; }
	public ControlKey getKey() { return key; }
	public Type getType() { return event; }
}
