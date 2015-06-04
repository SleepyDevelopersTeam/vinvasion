package ru.sdevteam.vinv.game;


public class MachineGun extends Tower
{
	
	
	MachineGun()
	{
		shootGround=false;
		bulletType=Bullet.Type.NORMAL;
		towerType= Tower.Type.MGUN;
		price=10;
		shootingRadius=100;
		sprite= new TowerSprite(this);
		reloadTimeMillis=100;
	}
}
