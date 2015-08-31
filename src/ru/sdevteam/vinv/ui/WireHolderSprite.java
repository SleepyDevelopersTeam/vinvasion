package ru.sdevteam.vinv.ui;

import java.awt.Graphics;
import java.util.Enumeration;

import ru.sdevteam.vinv.game.GameObject;
import ru.sdevteam.vinv.game.IWireConnectable;
import ru.sdevteam.vinv.game.WireHolder;
import ru.sdevteam.vinv.main.ResourceManager;
import ru.sdevteam.vinv.utils.Colors;

public class WireHolderSprite extends DecorationSprite
{
	public WireHolderSprite()
	{
		super(ResourceManager.getBufferedImage("decos/wireholder"), 16, 16);
	}
	
	
	@Override
	public synchronized void paint(Graphics g)
	{
		super.paint(g);
		
		WireHolder wh=(WireHolder)instance;
		g.setColor(Colors.black());
		Enumeration<IWireConnectable> neighbours=wh.getConnectedItems();
		while(neighbours.hasMoreElements())
		{
			GameObject n=(GameObject)neighbours.nextElement();
			
			g.drawLine((int)instance.getX(), (int)instance.getY(), (int)n.getX(), (int)n.getY());
		}
	}
}
