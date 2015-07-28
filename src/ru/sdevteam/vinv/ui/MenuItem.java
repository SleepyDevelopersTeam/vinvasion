package ru.sdevteam.vinv.ui;

import ru.sdevteam.vinv.ui.controls.Button;

public abstract class MenuItem extends Button
{
	//TODO class
	// конструкторы
	private String name;
	public MenuItem()
	{
		name="";
	}
	public MenuItem(String text)
	{
		name=text;
	}

}
