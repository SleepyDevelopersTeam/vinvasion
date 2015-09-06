package ru.sdevteam.vinv.ui;

import java.awt.Graphics;
import java.awt.Image;

import ru.sdevteam.vinv.main.ResourceManager;
import ru.sdevteam.vinv.main.GameCanvas;
import ru.sdevteam.vinv.utils.DebugInfo;


public class LoadingScreen extends Screen
{
	private long t;
	private Image splash;
	public int hightCanvas;
	public int widthCanvas;
	public int hightSplash;
	public int widthSplash;
	
	public LoadingScreen()
	{
		t=System.currentTimeMillis();
		splash=null;
		hightCanvas = GameCanvas.getInstance().getHeight();
		widthCanvas = GameCanvas.getInstance().getWidth();
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
		DebugInfo.addMessage("Loading... "+ResourceManager.getProgress());
		if (ResourceManager.getProgress()>=1F && (System.currentTimeMillis()-t)>500)
		{
			//GameCanvas.getInstance().setActiveScreen(new GameScreen(levelNum));
			GameCanvas.getInstance().setActiveScreen(new MenuScreen());
			Screen t=new TestScreen(); t.setSize(GameCanvas.getInstance().getWidth(), GameCanvas.getInstance().getHeight());
			//GameCanvas.getInstance().setActiveScreen(t);
		}
	}
}
