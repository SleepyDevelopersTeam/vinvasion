package ru.sdevteam.vinv.game;

public class Tower extends Destructable
{
    private long lastShoot;

    private long reloadTimeMillis;

    private boolean repairing;

    private String name;
    public String getName(){return name;}
    public void setName(String newName){name=newName;}

    private Bullet.Type type;
    public Bullet.Type getBulletType(){return type;}

    private int price;
    public int getPrice(){return price;}

    private int shootingRadius;
    public float getShootingRadius(){return shootingRadius;}


    public Tower()
    {
     type=Bullet.Type.NORMAL;
     price=10;
     shootingRadius=100;
     sprite= new TowerSprite(this);
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
    
    public void onDestroyed()
    {
        
    }
    
    public void update()
    {
        
    }
}


