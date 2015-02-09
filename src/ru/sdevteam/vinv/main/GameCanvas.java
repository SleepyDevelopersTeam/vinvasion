package ru.sdevteam.vinv.main;

import java.awt.Canvas;
import java.awt.Graphics;

import ru.sdevteam.vinv.ui.IDrawable;
import ru.sdevteam.vinv.ui.IUpdatable;

public class GameCanvas extends Canvas implements IDrawable, IUpdatable 
{
	Screen screen;
	
	public void update()
	{
		return;
	}
	
	public void paint (Graphics item)
	{
		return;
	}

	public void setActiveScreen (Screen item)
	{
		this.screen = item;
	}
}
