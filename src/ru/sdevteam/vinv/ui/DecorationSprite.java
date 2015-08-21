package ru.sdevteam.vinv.ui;

import java.awt.image.BufferedImage;

import ru.sdevteam.vinv.game.Decoration;
import ru.sdevteam.vinv.main.ResourceManager;

public class DecorationSprite extends Sprite
{	
	private Decoration instance;
	
	
	private DecorationSprite(BufferedImage src, int w, int h)
	{
		super(src, w, h);
	}
	
	
	public static DecorationSprite getBaseSprite(Decoration base)
	{
		DecorationSprite r=new DecorationSprite(ResourceManager.getBufferedImage("decos/base"), 96, 96);
		r.instance=base;
		return r;
	}
	
	public static DecorationSprite getWireHolderSprite(Decoration wireholder)
	{
		DecorationSprite r=new DecorationSprite(ResourceManager.getBufferedImage("decos/wireholder"), 16, 16);
		r.instance=wireholder;
		return r;
	}
}
