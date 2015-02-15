package ru.sdevteam.vinv.main;

import java.awt.Canvas;
import java.awt.Graphics;

import ru.sdevteam.vinv.ui.IDrawable;
import ru.sdevteam.vinv.ui.IUpdatable;
import ru.sdevteam.vinv.ui.Screen;
import ru.sdevteam.vinv.ui.LoadingScreen;

public class GameCanvas extends Canvas implements IDrawable, IUpdatable 
{
	Screen screen;
	public GameCanvas()
	{
		this.screen = new LoadingScreen();
	}
	public void update()
	{
		this.screen.update();
	}
	
	public void paint (Graphics item)
	{
		this.screen.paint(item);
	}

	public void setActiveScreen (Screen item)
	{
		this.screen = item;
	}
}
