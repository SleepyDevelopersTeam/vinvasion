package ru.sdevteam.vinv.game;

public class AirBug extends Bug
{
	AirBug()
	{
		setType(Bug.Type.AIR);
		sprite= new BugSprite(this);
	}
}
