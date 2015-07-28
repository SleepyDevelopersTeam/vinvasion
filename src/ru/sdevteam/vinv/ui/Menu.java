package ru.sdevteam.vinv.ui;

import java.awt.Graphics;

import ru.sdevteam.vinv.game.IMoveable;
import ru.sdevteam.vinv.main.KeyEvent;
import ru.sdevteam.vinv.main.MouseEvent;
import ru.sdevteam.vinv.ui.controls.ButtonSet;

import java.util.Vector;

public abstract class Menu extends ButtonSet
{
	public Menu(int x, int y, int width, int height, LayoutType layout) {
		super(x, y, width, height, layout);
		// TODO Auto-generated constructor stub
	}

	public Menu getPrevious()
	{
		return null;
	}
	
	protected void setPrevious(Menu m)
	{
		
	}
	
}
