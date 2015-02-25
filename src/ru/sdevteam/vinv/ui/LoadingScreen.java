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
	long t;
	Image splash;
	
	public LoadingScreen(GameCanvas owner)
	{
		loadingCanvas=owner;
		t=System.currentTimeMillis();
		splash=null;
	}

	@Override
	public void paint(Graphics g) 
	{
		if(splash==null)
		{
			splash=ResourceManager.getSplash();
		}
		else g.drawImage(splash, 0, 0, null);
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
