package ru.sdevteam.vinv.ui.controls;

import java.util.Vector;

import ru.sdevteam.vinv.main.ControlKey;
import ru.sdevteam.vinv.main.KeyEvent;
import ru.sdevteam.vinv.main.MouseEvent;

public class ButtonSet extends FlowLayout
{
	protected Vector<Button> buttons;
	private int currentButton=-1;
	
	
	private void init()
	{
		buttons=new Vector<Button>();
	}
	public ButtonSet(LayoutType layout)
	{
		super(layout);
		init();
	}
	public ButtonSet(int x, int y, int width, int height, LayoutType layout)
	{
		super(x, y, width, height, layout);
		init();
	}
	
	
	public void addButton(Button item)
	{
		buttons.add(item);
		addControl(item);
	}
	public void removeButton(Button item)
	{
		buttons.remove(item);
		removeControl(item);
	}
	
	private void focusCurrent()
	{
		buttons.elementAt(currentButton).focus();
	}
	public void focusButton(int index)
	{
		currentButton=index;
		focusCurrent();
	}
	public void focusNext()
	{
		currentButton++;
		if(currentButton>=buttons.size()) currentButton=0;
		focusCurrent();
	}
	public void focusPrevious()
	{
		currentButton--;
		if(currentButton<0) currentButton=buttons.size()-1;
		focusCurrent();
	}
	
	public void pressFocused()
	{
		if(currentButton==-1) return;
		buttons.elementAt(currentButton).press();
	}
	
	@Override
	protected void onMouseMoved(MouseEvent ev)
	{
		super.onMouseMoved(ev);
		for(int i=buttons.size()-1; i>-0; i--)
		{
			if(buttons.get(i).contains(ev.getMouseX(), ev.getMouseY()))
			{
				currentButton=i;
				focusCurrent();
				break;
			}
		}
	}
	
	@Override
	protected void onKeyDown(KeyEvent ev)
	{
		super.onKeyDown(ev);
		if(ControlKey.LEFT.checkCode(ev.getKey()) || ControlKey.UP.checkCode(ev.getKey()))
		{
			focusPrevious();
			return;
		}
		if(ControlKey.RIGHT.checkCode(ev.getKey()) || ControlKey.DOWN.checkCode(ev.getKey()))
		{
			focusNext();
			return;
		}
		if(ControlKey.ACCEPT.checkCode(ev.getKey()))
		{
			pressFocused();
			return;
		}
	}
}
