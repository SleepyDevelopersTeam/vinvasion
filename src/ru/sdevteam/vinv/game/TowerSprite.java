package ru.sdevteam.vinv.game;

import ru.sdevteam.vinv.main.ResourceManager;
import ru.sdevteam.vinv.ui.Sprite;
import ru.sdevteam.vinv.utils.Vector2F;

public class TowerSprite extends Sprite
{
	private Tower instanse;
	
	public TowerSprite(Tower t)
	{
		super(ResourceManager.getBufferedImage("towers/test_tower"), 32, 32);
		instanse=t;
		pause();
	}
	
	@Override
	public void update()
	{
		super.update();
		
		// TODO: ��������� ������ � ������������ � ���������� instanse
		// test tower has 12 rotation images
		
		int frame=Vector2F.getDiscreteRotation(12, instanse.getRotation());
		gotoFrame(frame);
	}
}
