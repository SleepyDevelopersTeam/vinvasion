package ru.sdevteam.vinv.ui;

import java.awt.Color;
import java.awt.Graphics;

public class BulletSprite extends Sprite
{	
	public static final Color METAL=new Color(160, 160, 180);
	
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
		// TODO Auto-generated method stub
		//super.paint(g);
		g.setColor(this.color);
		g.fillRect((int)getX(), (int)getY(), size, size);
	}
	
	@Override
	public int getWidth(){ return size; }
	@Override
	public int getHeight(){ return size; }
}
