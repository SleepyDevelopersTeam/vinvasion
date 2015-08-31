package ru.sdevteam.vinv.game;


public class MachineGun extends Tower
{
	public MachineGun()
	{
		super();
		shootGround=false;
		bulletType=Bullet.Type.NORMAL;
		towerType= Tower.Type.MGUN;
		price=10;
		shootingRadius=100;
		sprite= new TowerSprite(this);
		reloadTimeMillis=100;
		
		requiredPower=0;
		
		active=true;
	}
}
