package ru.sdevteam.vinv.ui;

import java.awt.Graphics;

import ru.sdevteam.vinv.game.IMoveable;
import ru.sdevteam.vinv.main.KeyEvent;
import ru.sdevteam.vinv.main.MouseEvent;

public abstract class Menu implements IDrawable, IUpdatable, IMoveable
{
	public void addMenuItem(MenuItem m)
	{
		
	}
	
	public abstract void addMenuItem(String text);
	// добавл€ет "разделитель" (отступ) после последнего пункта меню
	public abstract void insertSeparator();
	
	public void removeMenuItem(MenuItem m)
	{
		
	}

	// перечисление, определ€ющее стиль меню
	public enum Style { MAIN_MENU, INGAME_MENU }
	

	// обработка событий
	public void processEvent(MouseEvent ev)
	{
		
	}
	
	public void processEvent(KeyEvent ev)
	{
	
	}

	// вызываетс€ при выборе пользователем элемента меню item
	protected void onItemActivated(MenuItem item)
	{
		
	}
	// вызываетс€ некоторым элементом меню, когда он выбран
	final void itemActivated(MenuItem invoker)
	{
		
	}

	// поддержка стека меню
	public Menu getPrevious()
	{
		return null;
	}
	
	protected void setPrevious(Menu m)
	{
		
	}
	
	@Override
	public void paint(Graphics g) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void update() {
		// TODO Auto-generated method stub
		
	}
}
