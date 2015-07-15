package ru.sdevteam.vinv.main;

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
	
	private static InputState current=new InputState();
	public static InputState getCurrentState() { return current; }
	
	private static QueueItem keyStart, keyEnd, mouseStart, mouseEnd;
	
	synchronized static void pushKeyEvent(KeyEvent e)
	{
		// Изменяем состояние ввода
		switch(e.getType())
		{
		case PRESSED:
			current.markKeyDown(e.getKey());
			break;
		case RELEASED:
			current.markKeyUp(e.getKey());
			break;
		default:
			break;
		}
		
		// Сохраняем копию состояния в событии
		e.setActualState(current.copy());
		
		// Добавляем в очередь
		if(keyStart==null)
		{
			keyStart=keyEnd=new Input().new QueueItem(e);
			return;
		}
		keyEnd.next=new Input().new QueueItem(e);
		keyEnd=keyEnd.next;
	}
	
	synchronized static void pushMouseEvent(MouseEvent e)
	{
		// Обновляем состояние ввода
		switch(e.getType())
		{
		case PRESSED:
			switch(e.getButton())
			{
			case LEFT:
				current.setLeftDown(true);
				break;
			case RIGHT:
				current.setRightDown(true);
				break;
			case WHEEL:
				current.setWheelDown(true);
				break;
			default: break;
			}
			break;
		case RELEASED:
			switch(e.getButton())
			{
			case LEFT:
				current.setLeftDown(false);
				break;
			case RIGHT:
				current.setRightDown(false);
				break;
			case WHEEL:
				current.setWheelDown(false);
				break;
			default: break;
			}
			break;
		default: break;
		}
		current.setWheelDelta(e.getDelta());
		current.setMouseCoords(e.getMouseX(), e.getMouseY());
		
		// Запихиваем копию текущего состояния в событие
		e.setActualState(current.copy());
		
		// Добавляем событие в очередь
		if(mouseStart==null)
		{
			mouseStart=mouseEnd=new Input().new QueueItem(e);
			return;
		}
		mouseEnd.next=new Input().new QueueItem(e);
		mouseEnd=mouseEnd.next;
	}
	
	public static int getMouseX() { return current.getMouseX(); }
	public static int getMouseY() { return current.getMouseY(); }
	
	public static boolean isKeyDown(ControlKey key)
	{
		return current.isKeyDown(key);
	}

	public synchronized static boolean hasMoreKeyEvents()
	{
		return keyStart!=null;
	}
	public synchronized static KeyEvent getNextKeyEvent()
	{
		KeyEvent result=(KeyEvent)keyStart.event;
		keyStart=keyStart.next;
		if(keyStart==null) keyEnd=null;
		return result;
	}

	public synchronized static boolean hasMoreMouseEvents()
	{
		return mouseStart!=null;
	}
	public synchronized static MouseEvent getNextMouseEvent()
	{
		MouseEvent result=(MouseEvent)mouseStart.event;
		mouseStart=mouseStart.next;
		if(mouseStart==null) mouseEnd=null;
		return result;
	}
}
