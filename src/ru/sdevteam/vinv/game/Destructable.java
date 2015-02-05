package ru.sdevteam.vinv.game;


public abstract class Destructable extends GameObject
{    
    private int hp;
    int getHp(){return hp;}

    private int maxHp;
    int getMaxHp(){return maxHp;}

    private boolean destructed;
    
    //??? float getHpRate(); // от 0 до 1, включительно
    
    boolean isDestructed() 
    {
        if (destructed==true)
        {
            return true;
        }
        return false;
    }

    void hit(Bullet b)
    { // будет отнимать здоровье, согласуясь с типом пули и собственным состоянием
        hp=hp-b.getDamage();
        if(hp<=0)
        {
            onDestroyed();
        }
    }

    protected abstract void onDestroyed(); //??? должен вызываться при достижении показателем hp нуля

}
