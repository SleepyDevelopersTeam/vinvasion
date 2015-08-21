package ru.sdevteam.vinv.game;

public class FlameThrower extends Tower
{
	
	public FlameThrower()
	{
		super();
		shootGround=true;
		bulletType=Bullet.Type.FLAME;
		towerType= Tower.Type.FLAME_THROWER;
		price=10;
		shootingRadius=70;
		sprite= new TowerSprite(this);
		reloadTimeMillis=50;
	}
}
