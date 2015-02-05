package ru.sdevteam.vinv.game;

public class Tower extends Destructable
{
    private int lastShoot;
    void setLastShoot(int newLastShoot){lastShoot=newLastShoot;}

    private int reloadTimeMillis;
    void setReloadTime(int newTime){reloadTimeMillis=newTime;}

    private boolean repairing;

    private String name;
    String getName(){return name;}
    void setName(String newName){name=newName;}

    Bullet objBullet= new Bullet();

    // возвращает true, если прошло достаточно времени с последнего выстрела
    boolean canShoot()
    {
        if((lastShoot+reloadTimeMillis)<System.currentTimeMillis()){
            return true;
        }
        return false;
    }


    Tower()
    {
        objBullet.convertTo(Bullet.Type.NORMAL);
    }


    // тип выпускаемых снарядов
    Bullet.Type getBulletType()
    {
        return objBullet.getType();
    }
    
    boolean isRepairing()
    {
        if(repairing==true){
            return true;
        }
        return false;
    }
    public void onDestroyed()
    {
        
    };
}


