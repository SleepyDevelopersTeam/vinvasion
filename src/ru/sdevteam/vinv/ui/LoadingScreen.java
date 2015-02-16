package ru.sdevteam.vinv.ui;

import java.awt.Graphics;
import ru.sdevteam.vinv.main.ResourceManager;
import ru.sdevteam.vinv.main.GameCanvas;


public class LoadingScreen extends Screen
{
	private GameCanvas loadingCanvas;

	@Override
	public void paint(Graphics g) 
	{
		g.drawImage(ResourceManager.getImage("splash"), 0, 0, null);
	}
	private int levelNum;

	@Override
	public void update() 
	{
		if (ResourceManager.isReady()) loadingCanvas.setActiveScreen(new GameScreen(levelNum));		
	}
}
