package ru.sdevteam.vinv.ui;

import java.awt.image.BufferedImage;

import ru.sdevteam.vinv.game.Decoration;
import ru.sdevteam.vinv.main.ResourceManager;

public class DecorationSprite extends Sprite
{	
	protected Decoration instance;
	
	
	protected DecorationSprite(BufferedImage src, int w, int h)
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
		DecorationSprite r=new WireHolderSprite();
		r.instance=wireholder;
		return r;
	}
}
