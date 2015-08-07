package ru.sdevteam.vinv.main;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;

import ru.sdevteam.vinv.ui.IDrawable;
import ru.sdevteam.vinv.ui.IUpdatable;
import ru.sdevteam.vinv.ui.Screen;
import ru.sdevteam.vinv.ui.LoadingScreen;
import ru.sdevteam.vinv.ui.TestScreen;

public class GameCanvas extends Canvas implements IDrawable, IUpdatable
{
	Screen screen;
	private Image buffer;
	Graphics buffg;
	private int width, height;
	
	public GameCanvas(int w, int h)
	{
		width = w;
		height = h;
		//buffer = createImage(width, height);
		buffer=new BufferedImage(width, height, BufferedImage.TYPE_3BYTE_BGR);
		buffg=buffer.getGraphics();
	}
	
	public void start()
	{
		this.screen = new LoadingScreen(this);
		//this.screen=new TestScreen();
	}
	
	public void update()
	{
		if(this.screen != null) this.screen.update();
	}
	
	@Override
	public void update(Graphics g) 
	{
		paint(g);
	}
	
	public void paint (Graphics item)
	{
		if(this.screen == null) return;
		buffg.setColor(Color.black);
		buffg.fillRect(0, 0, getWidth(), getHeight());
		this.screen.paint(buffg);
		item.drawImage(buffer, 0, 0, null);
	}

	public void setActiveScreen (Screen item)
	{
		this.screen = item;
	}

}
