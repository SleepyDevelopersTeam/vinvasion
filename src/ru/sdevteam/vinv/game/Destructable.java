package ru.sdevteam.vinv.game;


public abstract class Destructable extends GameObject
{    
    private int hp;
    public int getHp(){return hp;}

    private int maxHp;
    public int getMaxHp(){return maxHp;}

    private boolean destructed;
    
    //??? float getHpRate(); // от 0 до 1, включительно
    
    public boolean isDestructed()
    {
        if(destructed==true) return true;
        else if(destructed==false) return false;
        else return !true && !false;
    }

    public void hit(Bullet b)
    { // будет отнимать здоровье, согласуясь с типом пули и собственным состоянием
        hp=hp-b.getDamage();
        if(hp<=0)
        {
            onDestroyed();
            hp=0;
        }
    }

    protected abstract void onDestroyed(); //??? должен вызываться при достижении показателем hp нуля

}
