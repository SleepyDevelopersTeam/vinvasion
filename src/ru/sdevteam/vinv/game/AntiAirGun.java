package ru.sdevteam.vinv.game;


public class AntiAirGun extends Tower
{
	AntiAirGun()
	{
		shootGround=true;
		bulletType=Bullet.Type.NORMAL;
		towerType= Tower.Type.AIR_GUN;
		price=10;
		shootingRadius=200;
		sprite= new TowerSprite(this);
		reloadTimeMillis=40;
	}
}
