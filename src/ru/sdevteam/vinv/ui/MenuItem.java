package ru.sdevteam.vinv.ui;

import ru.sdevteam.vinv.ui.controls.Button;

public abstract class MenuItem extends Button
{
	//TODO class
	// конструкторы
	private String name;
	private Menu ownMenu;
	private boolean selected;
	
	public MenuItem()
	{
		name="";
		ownMenu=null;
		selected=false;
	}
	
	public MenuItem(String text)
	{
		name=text;
		ownMenu=null;
		selected=false;
	}

	// методы
	public boolean isSelected()
	{
		return selected;
	}
	
	void select()
	{
		selected=true;
	}
	
	void deselect()
	{
		selected=false;
	}

	void setOwner(Menu menu)
	{
		ownMenu=menu;
	}
}
