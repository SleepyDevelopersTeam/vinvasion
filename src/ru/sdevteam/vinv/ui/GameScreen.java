package ru.sdevteam.vinv.ui;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;

import ru.sdevteam.vinv.game.Level;
import ru.sdevteam.vinv.game.logics.LevelController;
import ru.sdevteam.vinv.main.GameCanvas;
import ru.sdevteam.vinv.main.Input;
import ru.sdevteam.vinv.main.MouseEvent;
import ru.sdevteam.vinv.main.ResourceManager;
import ru.sdevteam.vinv.utils.Colors;
import ru.sdevteam.vinv.utils.DebugInfo;
import ru.sdevteam.vinv.utils.Fonts;

public class GameScreen extends Screen
{
	private LevelController levelCtrl;
	private float viewportX;
	private float viewportY;
	private float viewportWidth;
	private float viewportHeight;
	private int scaleFactor;
	
	// images
	private static BufferedImage 
	panel=ResourceManager.getBufferedImage("ui/panel"),
	res_r=ResourceManager.getBufferedImage("ui/res_resources"),
	res_h=ResourceManager.getBufferedImage("ui/res_humans"),
	res_p=ResourceManager.getBufferedImage("ui/res_power");
		
	public GameScreen(int levelNum, GameCanvas canvas)
	{
		this.levelCtrl=new LevelController(this, Level.createLevel(levelNum));
		scaleFactor=2;
		viewportX=0;
		viewportY=0;
		viewportHeight=canvas.getHeight()/scaleFactor;
		viewportWidth=canvas.getWidth()/scaleFactor;
		
		setFont(Fonts.main(10));
		
		// временная строчка
		//setSize(canvas.getWidth(), canvas.getHeight());
		setSize((int)viewportWidth, (int)viewportHeight);
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
	synchronized public void paint(Graphics g) 
	{
		//
		// Отрисовка уровня
		//
		((Graphics2D)g).translate(-viewportX, -viewportY);
		
		((Graphics2D)g).scale(scaleFactor, scaleFactor);
		levelCtrl.paint(g);
		((Graphics2D)g).scale(1F/scaleFactor, 1F/scaleFactor);
		
		((Graphics2D)g).translate(viewportX, viewportY);
		
		//
		// Отрисовка интерфейса
		//
		((Graphics2D)g).scale(2F, 2F);
		int penx=0, peny=getHeight();
		g.setFont(getFont());
		
		//панель
		peny-=panel.getHeight();
		while(penx<getWidth())
		{
			g.drawImage(panel, penx, peny, null);
			penx+=panel.getWidth();
		}
		penx=4; peny+=3; g.setColor(Colors.white());
		
		g.drawImage(res_r, penx, peny, null); 
		g.drawString(levelCtrl.getPlayer().getResources()+"", penx+30, peny+10+getFontMetrics(g).getAscent());
		penx+=res_r.getWidth()+10;
		
		g.drawImage(res_p, penx, peny, null);
		g.drawString(levelCtrl.getPlayer().getBasePower()+"", penx+30, peny+10+getFontMetrics(g).getAscent());
		penx+=res_p.getWidth()+10;
		
		g.drawImage(res_h, penx, peny, null);
		g.drawString(levelCtrl.getPlayer().getHumansCount()+"", penx+30, peny+10+getFontMetrics(g).getAscent());
		penx+=res_h.getWidth()+10;
		
		((Graphics2D)g).scale(0.5F, 0.5F);
		// endof Интерфейс
	}

	@Override
	synchronized public void update() 
	{
		super.update();
		
		levelCtrl.update();
		/*
		while (Input.hasMoreMouseEvents())
		{
			MouseEvent ev=Input.getNextMouseEvent();
			posMouseX=ev.getMouseX();
			posMouseY=ev.getMouseY();
			delta=ev.getDelta();
			if(delta<0) 
			{
				if(scaleFactor<8) 
				{
					scaleFactor*=2;
					viewportWidth/=2;
					viewportHeight/=2;
				}
			}
			else 
				if(delta>=1)
				{
					if(scaleFactor>=2) 
					{
						scaleFactor/=2;
						viewportWidth*=2;
						viewportHeight*=2;
					}
				}
			
			if(delta!=0)
			{
				float k;
				if(delta<0) k=1/2;
				else k=2;
				viewportX=viewportX+(1-k)*(posMouseX-viewportX);
				viewportY=viewportY+(1-k)*(posMouseY-viewportY);
			}
	
			if(viewportX<0) viewportX=0;
			if(viewportY<0) viewportY=0;
			//System.out.println(viewportX+" "+viewportY);
		}
		*/
	}
	
	@Override
	protected void onMouseScroll(MouseEvent ev)
	{
		super.onMouseScroll(ev);
		
		if(ev.getDelta()<0) 
		{
			if(scaleFactor<8) 
			{
				scaleFactor*=2;
				viewportWidth/=2;
				viewportHeight/=2;
			}
		}
		else 
			if(ev.getDelta()>0)
			{
				if(scaleFactor>2) 
				{
					scaleFactor/=2;
					viewportWidth*=2;
					viewportHeight*=2;
				}
			}
		
		if(ev.getDelta()!=0)
		{
			float k;
			if(ev.getDelta()<0) k=0.5F;
			else k=2;
			
			// TODO: correct the formula
			viewportX=viewportX+(1-k)*(ev.getMouseX()-viewportX);
			viewportY=viewportY+(1-k)*(ev.getMouseY()-viewportY);
			
			System.out.println("New viewport: ("+viewportX+"; "+viewportY+").(" +
			viewportWidth+"; "+viewportHeight+"), scf="+scaleFactor);
			
			DebugInfo.addMessage("("+viewportX+"; "+viewportY+").(" +
					viewportWidth+"; "+viewportHeight+"), scf="+scaleFactor +
					"mpos: ("+ev.getMouseX()+"; "+ev.getMouseY()+")");
		}

		if(viewportX<0) viewportX=0;
		if(viewportY<0) viewportY=0;
		// TODO: проверять две другие границы уровня
	}
}
