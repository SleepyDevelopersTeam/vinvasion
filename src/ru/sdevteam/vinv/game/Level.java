package ru.sdevteam.vinv.game;
import java.util.*;

public class Level
{
    private Vector massObjects = new Vector();  //массив объектов

    public GameObject[] getGameObjects()
    {
        GameObject[] mass=new GameObject[massObjects.capacity()];
        massObjects.toArray(mass);
        return mass;
    }

    public void addObject(GameObject item)
    {
        massObjects.add(item);
    }

    public void removeObject(GameObject item)
    {
        massObjects.remove(item);
    }

    //создает пробный уровень
    public static Level getLevel()
    {
        Level objLevel=new Level();

        Tower aTower=new Tower();
        aTower.setX(200);
        aTower.setY(200);

        Bug aBug=new Bug();
        aBug.setX(100);
        aBug.setY(100);

        Bug bBug=new Bug();
        bBug.setX(300);
        bBug.setY(300);
        
        objLevel.addObject(aTower);
        objLevel.addObject(aBug);
        objLevel.addObject(bBug);

        return objLevel;
    }

    public Bullet getBullet(float x, float y, Bullet.Type type)
    {
        Bullet objBullet=new Bullet();
        objBullet.setX(x);
        objBullet.setY(y);
        objBullet.convertTo(type);
        objBullet.setUsing(true);
        return objBullet;
    }

    public void disposeBullet(Bullet b)
    {
        b.setUsing(false);
    }
}
