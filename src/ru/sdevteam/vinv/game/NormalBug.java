package ru.sdevteam.vinv.game;

public class NormalBug extends Bug
{
	
	
	NormalBug()
	{
		setType(Bug.Type.NORMAL);
		sprite= new BugSprite(this);
	}
	
}
