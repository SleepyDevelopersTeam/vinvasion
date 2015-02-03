package ru.sdevteam.vinv.main;


// TODO: переделать архитектуру класса, слишком говнокодисто
public class Input
{
	private Input() {}
	private class QueueItem
	{
		Object event;
		QueueItem next;
		
		QueueItem(Object ev)
		{
			event=ev;
		}
	}
	
	private static QueueItem keyStart, keyEnd, mouseStart, mouseEnd;
	
	static void pushKeyEvent(KeyEvent e)
	{
		if(keyStart==null)
		{
			keyStart=keyEnd=new Input().new QueueItem(e);
			return;
		}
		keyEnd.next=new Input().new QueueItem(e);
		keyEnd=keyEnd.next;
	}
	
	static void pushMouseEvent(MouseEvent e)
	{
		if(mouseStart==null)
		{
			mouseStart=mouseEnd=new Input().new QueueItem(e);
			return;
		}
		mouseEnd.next=new Input().new QueueItem(e);
		mouseEnd=mouseEnd.next;
	}
	
	private static int mx, my;
	public static int getMouseX() { return mx; }
	public static int getMouseY() { return my; }
	
	public static boolean isKeyDown(ControlKeys key)
	{
		return false;
	}

	public static boolean hasMoreKeyEvents()
	{
		return keyStart!=null;
	}
	public static KeyEvent getNextKeyEvent()
	{
		KeyEvent result=(KeyEvent)keyStart.event;
		keyStart=keyStart.next;
		if(keyStart==null) keyEnd=null;
		return result;
	}

	public static boolean hasMoreMouseEvents()
	{
		return mouseStart!=null;
	}
	public static MouseEvent getNextMouseEvent()
	{
		MouseEvent result=(MouseEvent)mouseStart.event;
		mouseStart=mouseStart.next;
		if(mouseStart==null) mouseEnd=null;
		return result;
	}
}
