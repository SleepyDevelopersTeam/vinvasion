package ru.sdevteam.vinv.game;

import ru.sdevteam.vinv.main.ResourseManager;
import ru.sdevteam.vinv.ui.Sprite;

public class TowerSprite extends Sprite
{
	private Tower instanse;
	
	public TowerSprite(Tower t)
	{
		super(ResourseManager.getBufferedImage("towers/test_tower"), 256, 256);
		instanse=t;
	}
	
	@Override
	public void update()
	{
		super.update();
		
		// TODO: обновляем спрайт в соответствии с состоянием instanse
	}
}
