package ru.sdevteam.vinv.ui;

import java.awt.Graphics;

import ru.sdevteam.vinv.game.Level;
import ru.sdevteam.vinv.game.logics.LevelController;
import ru.sdevteam.vinv.main.GameCanvas;
import ru.sdevteam.vinv.main.Input;
import ru.sdevteam.vinv.main.MouseEvent;

public class GameScreen extends Screen
{
	private LevelController levelCtrl;
	public float viewportX;
	public float viewportY;
	public float viewportWidth;
	public float viewportHeight;
	public int scaleFactor;
	private int posMouseX;
	private int posMouseY;
	private int delta;
		
	public GameScreen(int levelNum, GameCanvas canvas)
	{
		this.levelCtrl=new LevelController(this, Level.createLevel(levelNum));
		scaleFactor=2;
		viewportX=0;
		viewportY=0;
		viewportHeight=canvas.getHeight();
		viewportWidth=canvas.getWidth();
	}
	
	public int getScaleFactor()
	{
		return scaleFactor;
	}
	
	public float getViewportX()
	{
		return viewportX;
	}
	
	public float getViewportY()
	{
		return viewportY;
	}
	
	public float getViewPortWidth()
	{
		return viewportWidth;
	}
	
	public float getViewPortHeight()
	{
		return viewportHeight;
	}
	
	@Override
	public void paint(Graphics g) 
	{
		// TODO: Масштабирование
		levelCtrl.paint(g);
	}

	@Override
	public void update() 
	{

		levelCtrl.update();
		
		while (Input.hasMoreMouseEvents())
		{
			MouseEvent ev=Input.getNextMouseEvent();
			posMouseX=ev.getMouseX();
			posMouseY=ev.getMouseY();
			delta=ev.getDelta();
			
			if(delta>0) 
			{
				if(scaleFactor<8) 
				{
					scaleFactor*=2;
					viewportWidth/=2;
					viewportHeight/=2;
				}
			}
			else 
				if(delta<0)
				{
					if(scaleFactor>2) 
					{
						scaleFactor/=2;
						viewportWidth*=2;
						viewportHeight*=2;
					}
				}
			
			if(delta!=0)
			{
				float k;
				if(delta>0) k=1/2;
				else k=2;
				viewportX=viewportX+(1-k)*(posMouseX-viewportX);
				viewportY=viewportY+(1-k)*(posMouseY-viewportY);
			}
		
			if(viewportX<0) viewportX=0;
			if(viewportY<0) viewportY=0;
		}
	}

}
