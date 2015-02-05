package ru.sdevteam.vinv.game;
import java.util.*;

public class Level
{
    private Vector massObjects = new Vector();  //массив объектов

    GameObject[] getGameObjects()
    {
        GameObject[] mass=new GameObject[massObjects.capacity()];
        massObjects.toArray(mass);
        return mass;
    }

    void addObject(GameObject item)
    {
        massObjects.add(item);
    }

    void removeObject(GameObject item)
    {
        massObjects.remove(item);
    }

    //создает пробный уровень
    static Level getLevel()
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

    Bullet getBullet(float x, float y, Bullet.Type type)
    {
        Bullet objBullet=new Bullet();
        objBullet.setX(x);
        objBullet.setY(y);
        objBullet.convertTo(type);
        objBullet.setUsing(true);
        return objBullet;
    }

    void disposeBullet(Bullet b)
    {
        b.setUsing(false);
    }
}
