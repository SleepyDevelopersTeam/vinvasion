package ru.sdevteam.vinv.game;


public abstract class Destructable extends GameObject
{    
    protected int hp;
    public int getHp(){return hp;}
    protected void setHp(int hp)
    {
		if(hp<=this.getMaxHp())
			this.hp=hp;
		else
			hp=this.getMaxHp();
    }

    private int maxHp;
    public int getMaxHp(){return maxHp;}
    protected void setMaxHp(int maxHp)
    {
        this.maxHp=maxHp;
    }
    
    public float getHpRate() // от 0 до 1, включительно
    {
        return (hp/maxHp);
    }

    public boolean isDestructed()
    {
        return hp==0;
    }

    public void hit(Bullet b)
    {  
        hp-=b.getDamage();
        if(hp<=0)
        {
            onDestructed();
            hp=0;
        }
    }

	public void hit(Explosion e)
	{
		hp-=e.getDamage();
        if(hp<=0)
        {
            onDestructed();
            hp=0;
        }
	}
	
	public void hit(int damage)
	{
		hp-=damage;
        if(hp<=0)
        {
            onDestructed();
            hp=0;
        }
	}
    protected abstract void onDestructed(); 

    @Override
    public void update()
    {
    	super.update();
    }
}
