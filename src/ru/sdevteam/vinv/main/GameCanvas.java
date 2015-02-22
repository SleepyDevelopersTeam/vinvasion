package ru.sdevteam.vinv.main;

import java.awt.Canvas;
import java.awt.Graphics;
import java.awt.Image;

import ru.sdevteam.vinv.ui.IDrawable;
import ru.sdevteam.vinv.ui.IUpdatable;
import ru.sdevteam.vinv.ui.Screen;
import ru.sdevteam.vinv.ui.LoadingScreen;

public class GameCanvas extends Canvas implements IDrawable, IUpdatable
{
	Screen screen;
	private Image buffer;
	private int width, height;
	
	public GameCanvas()
	{
		width = getWidth();
		height = getHeight();
		buffer = createImage(width, height);
		this.screen = new LoadingScreen();
	}
	
	public void update()
	{
		this.screen.update();
	}
	
	public void paint (Graphics item)
	{
		this.screen.paint(buffer.getGraphics());
		item.drawImage(buffer, 0, 0, null);
	}

	public void setActiveScreen (Screen item)
	{
		this.screen = item;
	}

}
