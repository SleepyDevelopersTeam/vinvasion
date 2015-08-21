package ru.sdevteam.vinv.ui;

import java.awt.Color;
import java.awt.Graphics;

import ru.sdevteam.vinv.game.Bullet;
import ru.sdevteam.vinv.main.ResourceManager;
import ru.sdevteam.vinv.utils.Colors;

public class BulletSprite extends Sprite
{
	private Bullet instance;
	private int size;
	
	private void configure()
	{
		switch(instance.getType())
		{
		case NORMAL:
			setCollisionRectangle(2, 2, 1, 1);
			size=1;
			break;
		case FLAME:
			setCollisionRectangle(0, 0, 5, 5);
			size=5;
			break;
		}
	}
	
	
	public BulletSprite(Bullet b)
	{
		super(ResourceManager.getBufferedImage("bullets/bullets"), 5, 5);
		instance=b;
		configure();
		//play();
	}
	
	
	@Override
	public synchronized void paint(Graphics g)
	{
		switch(instance.getType())
		{
		case NORMAL:
			g.setColor(Colors.black());
			g.fillRect((int)getX()-1, (int)getY(), 3, 1);
			g.fillRect((int)getX(), (int)getY()-1, 1, 3);
			g.setColor(Colors.gray());
			g.fillRect((int)getX(), (int)getY(), 1, 1);
			break;
		case FLAME:
			super.paint(g);
			break;
		}
	}
	
	/*@Override
	public int getWidth(){ return size; }
	@Override
	public int getHeight(){ return size; }*/
	
	public void onTypeChanged()
	{
		configure();
	}
}
