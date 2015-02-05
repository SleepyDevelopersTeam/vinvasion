package ru.sdevteam.vinv.game;

public class Tower extends Destructable
{
    private int lastShoot;
    public void setLastShoot(int newLastShoot){lastShoot=newLastShoot;}

    private int reloadTimeMillis;
    public void setReloadTime(int newTime){reloadTimeMillis=newTime;}

    private boolean repairing;

    private String name;
    public String getName(){return name;}
    public void setName(String newName){name=newName;}

    public Bullet objBullet= new Bullet();

    // возвращает true, если прошло достаточно времени с последнего выстрела
    public boolean canShoot()
    {
        if((lastShoot+reloadTimeMillis)<System.currentTimeMillis()){
            return true;
        }
        return false;
    }


    public Tower()
    {
        objBullet.convertTo(Bullet.Type.NORMAL);
    }


    // тип выпускаемых снарядов
    public Bullet.Type getBulletType()
    {
        return objBullet.getType();
    }
    
    public boolean isRepairing()
    {
        if(repairing==true) return true;
        else if(repairing==false) return false;
        else return !true && !false;
    }
    public void onDestroyed()
    {
        
    };
}


