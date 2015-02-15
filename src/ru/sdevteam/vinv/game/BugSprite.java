package ru.sdevteam.vinv.game;

import ru.sdevteam.vinv.main.ResourceManager;
import ru.sdevteam.vinv.ui.Sprite;

public class BugSprite extends Sprite
{	
	private Bug instanse;
	
	public BugSprite(Bug b)
	{
		super(ResourceManager.getBufferedImage("bugs/test_bug"), 256, 256);
		instanse=b;
	}
	
	@Override
	public void update()
	{
		super.update();
		
		// TODO: обновлять спрайт в соответствии с instanse
	}
}
