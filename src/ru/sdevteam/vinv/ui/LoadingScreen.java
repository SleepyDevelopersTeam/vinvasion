package ru.sdevteam.vinv.ui;

import java.awt.Graphics;
import java.awt.Image;

import ru.sdevteam.vinv.main.ResourceManager;
import ru.sdevteam.vinv.main.GameCanvas;


public class LoadingScreen extends Screen
{
	private GameCanvas loadingCanvas;
	private long t;
	private Image splash;
	public int hightCanvas;
	public int widthCanvas;
	public int hightSplash;
	public int widthSplash;
	
	public LoadingScreen(GameCanvas owner)
	{
		loadingCanvas=owner;
		t=System.currentTimeMillis();
		splash=null;
		hightCanvas = loadingCanvas.getHeight();
		widthCanvas = loadingCanvas.getWidth();
		System.out.println(hightCanvas+" "+widthCanvas);

	}

	@Override
	public void paint(Graphics g) 
	{
		if(splash==null)
		{
			splash=ResourceManager.getSplash();
		}
		else
		{
			hightSplash=splash.getHeight(null);
			widthSplash=splash.getWidth(null);
			g.drawImage(splash, ((widthCanvas/2)-(widthSplash/2)), ((hightCanvas/2)-(hightSplash/2)), null);
		}
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
