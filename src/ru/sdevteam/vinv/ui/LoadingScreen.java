package ru.sdevteam.vinv.ui;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;

import ru.sdevteam.vinv.main.ResourceManager;
import ru.sdevteam.vinv.main.GameCanvas;


public class LoadingScreen extends Screen
{
	private GameCanvas loadingCanvas;
<<<<<<< HEAD
	private long t;
	private Image splash;
	public int hightCanvas;
	public int widthCanvas;
	public int hightSplash;
	public int widthSplash;
=======
	long t;
	Image splash;
>>>>>>> ad8062d94ca437c7a1ec9816a45319dc2aa5df79
	
	public LoadingScreen(GameCanvas owner)
	{
		loadingCanvas=owner;
		t=System.currentTimeMillis();
		splash=null;
<<<<<<< HEAD
		hightCanvas = loadingCanvas.getHeight();
		widthCanvas = loadingCanvas.getWidth();
		System.out.println(hightCanvas+" "+widthCanvas);
=======
>>>>>>> ad8062d94ca437c7a1ec9816a45319dc2aa5df79
	}

	@Override
	public void paint(Graphics g) 
	{
		if(splash==null)
		{
			splash=ResourceManager.getSplash();
		}
<<<<<<< HEAD
		else
		{
			hightSplash=splash.getHeight(null);
			widthSplash=splash.getWidth(null);
			g.drawImage(splash, ((widthCanvas/2)-(widthSplash/2)), ((hightCanvas/2)-(hightSplash/2)), null);
		}
=======
		else g.drawImage(splash, 0, 0, null);
>>>>>>> ad8062d94ca437c7a1ec9816a45319dc2aa5df79
	}
	private int levelNum;

	@Override
	public void update() 
	{
		if (ResourceManager.getProgress()>=1F && (System.currentTimeMillis()-t)>2000)
		{
			loadingCanvas.setActiveScreen(new GameScreen(levelNum));
		}
	}
}
