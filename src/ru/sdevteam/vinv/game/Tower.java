package ru.sdevteam.vinv.game;

public class Tower extends Destructable
{
    private long lastShoot;
    private void setLastShoot(long newLastShoot){lastShoot=newLastShoot;}

    private long reloadTimeMillis;
    private void setReloadTime(long newTime){reloadTimeMillis=newTime;}

    private boolean repairing;

    private String name;
    public String getName(){return name;}
    public void setName(String newName){name=newName;}

    private Bullet.Type type;
    public Bullet.Type getBulletType()
    {
        return type;
    }


    public Tower()
    {
     type=Bullet.Type.NORMAL;
     objSprite= new TowerSprite(this);
    }


     public void shoot()
    {
        setLastShoot(System.currentTimeMillis());
    }

    public boolean isRepairing()
    {
        return repairing;
    }

    // возвращает true, если прошло достаточно времени с последнего выстрела
    public boolean canShoot()
    {
        if((lastShoot+reloadTimeMillis)<System.currentTimeMillis()){
            return true;
        }
        return false;
    }
    
    public void onDestroyed()
    {
        
    };
    public void update()
    {
        
    }
}


