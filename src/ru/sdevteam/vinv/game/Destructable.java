package ru.sdevteam.vinv.game;


public abstract class Destructable extends GameObject
{    
    private int hp;
    public int getHp(){return hp;}
    protected void setHp(int hp)
    {
        this.hp=hp;
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
    { // будет отнимать здоровье, согласуясь с типом пули и собственным состоянием
        hp-=b.getDamage();
        if(hp<=0)
        {
            onDestroyed();
            hp=0;
        }
    }

    protected abstract void onDestroyed(); //??? должен вызываться при достижении показателем hp нуля

}
