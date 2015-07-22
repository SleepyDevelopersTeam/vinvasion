package ru.sdevteam.vinv.ui;

import java.awt.image.BufferedImage;

import ru.sdevteam.vinv.game.Explosion;
import ru.sdevteam.vinv.main.ResourceManager;

public class ExplosionSprite extends Sprite
{
	private Explosion instance;
	
	
	public ExplosionSprite(Explosion exp)
	{
		super(getImage(exp.getType()), getSize(exp.getType()), getSize(exp.getType()));
		if(getImage(exp.getType())==null) System.out.println("NULL!");
		instance=exp;
		pause();
	}
	
	
	@Override
	public synchronized void update()
	{
		super.update();
		
		if(this.currentFrame()==this.getFramesCount()-1)
			this.pause();
	}
	
	private static BufferedImage getImage(Explosion.Type type)
	{
		switch(type)
		{
		case REGULAR:		return ResourceManager.getBufferedImage("explosions/regular");
		case SLIME:			return ResourceManager.getBufferedImage("explosions/slime");
		case BIG_SLIME:		return ResourceManager.getBufferedImage("explosions/big_slime");
		}
		System.out.println("Oops");
		return null;
	}
	private static int getSize(Explosion.Type type)
	{
		switch(type)
		{
		case REGULAR:		return 32;
		case SLIME:			return 16;
		case BIG_SLIME:		return 32;
		}
		return 0;
	}
}
