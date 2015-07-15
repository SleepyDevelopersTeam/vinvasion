package ru.sdevteam.vinv.ui;

import java.awt.Color;
import java.awt.Graphics;

public class BulletSprite extends Sprite
{	
	public static final Color METAL=Color.gray;
	
	private int size;
	private Color color;
	
	
	public BulletSprite(int size, Color clr)
	{
		super();
		this.size=size; this.color=clr;
		setCollisionRectangle(0, 0, size, size);
	}
	
	
	@Override
	public synchronized void paint(Graphics g)
	{
		g.setColor(Color.black);
		g.fillRect((int)getX()-1, (int)getY(), size+2, size);
		g.fillRect((int)getX(), (int)getY()-1, size, size+2);
		g.setColor(this.color);
		g.fillRect((int)getX(), (int)getY(), size, size);
	}
	
	@Override
	public int getWidth(){ return size; }
	@Override
	public int getHeight(){ return size; }
}
