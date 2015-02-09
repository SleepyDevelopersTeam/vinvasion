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

    private boolean destructed;
    
    public float getHpRate() // от 0 до 1, включительно
    {
        return (hp/maxHp);
    }

    public boolean isDestructed()
    {
        return destructed;
    }

    public void hit(Bullet b)
    { // будет отнимать здоровье, согласуясь с типом пули и собственным состоянием
        hp=hp-b.getDamage();
        if(hp<=0)
        {
            onDestroyed();
            hp=0;
            destructed=true;
        }
    }

    protected abstract void onDestroyed(); //??? должен вызываться при достижении показателем hp нуля

}
