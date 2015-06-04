package ru.sdevteam.vinv.game;

import ru.sdevteam.vinv.main.ResourceManager;
import ru.sdevteam.vinv.ui.Sprite;
import ru.sdevteam.vinv.utils.Vector2F;

public class BugSprite extends Sprite
{	
	private Bug instanse;
	protected int animationFrame;
	
	public BugSprite(Bug b)
	{
		super(ResourceManager.getBufferedImage("bugs/test_bug"), 16, 16);
		instanse=b;
		setCollisionRectangle(2, 2, 14, 14);
		animationFrame=0;
		pause();
	}
	
	@Override
	public void update()
	{
		super.update();
		
		animationFrame++;
		if(animationFrame>=16) animationFrame=0;
		
		// TODO: обновлять спрайт в соответствии с instanse
		// test bug has 4 rotation images
		
		// y
		int rotationFrame=Vector2F.getDiscreteRotation(4, instanse.getRotation());
		
		gotoFrame(animationFrame/4+rotationFrame*4);
	}
}
