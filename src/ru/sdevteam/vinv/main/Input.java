package ru.sdevteam.vinv.main;

import ru.sdevteam.vinv.utils.ControlKeys;
import ru.sdevteam.vinv.utils.KeyEvent;
import ru.sdevteam.vinv.utils.MouseEvent;

// TODO: переделать архитектуру класса, слишком говнокодисто
public class Input
{
	private Input() {}
	private class QueueItem
	{
		Object event;
		QueueItem next;
	}
	
	private static QueueItem keyStart, keyEnd, mouseStart, mouseEnd;
	
	//...
	
	private static int mx, my;
	public static int getMouseX() { return mx; }
	public static int getMouseY() { return my; }
	
	public static boolean isKeyDown(ControlKeys key)
	{
		return false;
	}

	public static boolean hasMoreKeyEvents()
	{
		return false;
	}
	public static KeyEvent getNextKeyEvent()
	{
		return null;
	}

	public static boolean hasMoreMouseEvents()
	{
		return false;
	}
	public static MouseEvent getNextMouseEvent()
	{
		return null;
	}
}
