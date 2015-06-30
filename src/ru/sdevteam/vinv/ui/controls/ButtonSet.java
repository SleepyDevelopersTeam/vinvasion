package ru.sdevteam.vinv.ui.controls;

import java.util.Vector;

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
		// TODO: Проверка на столкновение с каждой кнопкой и выделение, если да
		super.onMouseMoved(ev);
	}
	
	@Override
	protected void onKeyDown(KeyEvent ev)
	{
		// TODO: При нажатии клавиш влево/вправо перемещаем выделение, Enter - активируем кнопку
		super.onKeyDown(ev);
	}
}
