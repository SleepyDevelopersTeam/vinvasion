package ru.sdevteam.vinv.game;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import ru.sdevteam.vinv.main.ResourceManager;
import ru.sdevteam.vinv.ui.Sprite;
import ru.sdevteam.vinv.utils.Colors;
import ru.sdevteam.vinv.utils.Vector2F;

public class TowerSprite extends Sprite
{
	private Tower instanse;
	int rotationImages=1;
	
	public TowerSprite(Tower t)
	{
		super(getImage(t.getTowerType()), 32, 32);
		instanse=t;
		switch(t.getTowerType())
		{
		case MGUN:
			rotationImages=12;
			break;
		case FLAME_THROWER:
			rotationImages=8;
		}
		pause();
		setCollisionRectangle(2, 2, 28, 28);
	}
	
	@Override
	public void update()
	{
		super.update();
		
		// TODO: обновляем спрайт в соответствии с состоянием instanse
		
		int frame=Vector2F.getDiscreteRotation(rotationImages, instanse.getRotation());
		gotoFrame(frame);
	}
	
	private static BufferedImage getImage(Tower.Type type)
	{
		switch(type)
		{
		case MGUN:
			return ResourceManager.getBufferedImage("towers/machinegun");
		case FLAME_THROWER:
			return ResourceManager.getBufferedImage("towers/flamethrower");
		}
		return ResourceManager.getBufferedImage("towers/test_tower");
	}
}
