package ru.sdevteam.vinv.game;

public abstract class Tower extends Destructable implements IWireConnectable
{
    protected long lastShoot;
    protected long reloadTimeMillis;
    protected boolean repairing;
	protected boolean shootGround;
	protected boolean shootAir;
	protected boolean active;

    protected String name;
    public String getName(){return name;}
    public void setName(String newName){name=newName;}

    protected Bullet.Type bulletType;
    public Bullet.Type getBulletType(){return bulletType;}

    protected int price;
    public int getPrice(){return price;}

    protected int shootingRadius;
    public float getShootingRadius(){return shootingRadius;}
	
	protected Type towerType;
    public Type getTowerType(){return towerType;}
    
    protected int requiredPower;
    public int getRequiredPower() { return requiredPower; }
	
	public enum Type { MGUN, FLAME_THROWER, AIR_GUN }


    public Tower()
    {
		bulletType=Bullet.Type.NORMAL;
		price=10;
		shootingRadius=100;
		//sprite= new TowerSprite(this);
		reloadTimeMillis=100;
		
		active=false;
    }


	public void shoot()
    {
        if(canShoot())
        {
            lastShoot=System.currentTimeMillis();
        }
    }

    public boolean isRepairing()
    {
        return repairing;
    }

    // возвращает true, если прошло достаточно времени с последнего выстрела
    public boolean canShoot()
    {
        if((lastShoot+reloadTimeMillis)<System.currentTimeMillis())
        {
            return true;
        }
        return false;
    }
    
	public boolean canAttack(Bug.Type type)
	{
		if ( shootAir && type==Bug.Type.AIR )
		{
			return true;
		}
		if ( shootGround && type!=Bug.Type.AIR)
		{
			return true;
		}
		return false;
	}
	
    public void onDestructed(){}
	
	public void activate()
	{
		active=true;
	}
	
    public void deactivate()
	{
		active=false;
	}
    
    public boolean isActive() { return active; }
	
	public boolean isCharged()
	{
		return false;
	}

	public boolean isConsumer()
	{
		return true;
	}

	public boolean isConductor()
	{
		return false;
	}

	public boolean isGenerator()
	{
		return false;
	}
	
	@Override
	public void onCircuitChanged()
	{
		
	}
	
    public void update()
    {
        super.update();
    }
}


