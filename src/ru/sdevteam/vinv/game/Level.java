package ru.sdevteam.vinv.game;
import java.util.*;

public class Level
{
    private Vector<Tower> massTowers = new Vector<Tower>();  //массив объектов
    public Tower[] getTowers()
    {
        Tower[] mass=new Tower[massTowers.capacity()];
        massTowers.copyInto(mass);
        return mass;
    }

    private Vector<Bug> massBugs = new Vector<Bug>();
    public Bug[] getBugs()
    {
        Bug[] mass=new Bug[massBugs.capacity()];
        massBugs.copyInto(mass);
        return mass;
    }


    //создает пробный уровень
    public static Level getLevel(int num)
    {
        Level objLevel=new Level();
        objLevel.poolBullet=new Pool(300);

        Tower aTower=new Tower();
        aTower.setX(200);
        aTower.setY(200);

        Bug aBug=new Bug();
        aBug.setType(Bug.Type.NORMAL);
        aBug.setX(100);
        aBug.setY(100);

        Bug bBug=new Bug();
        bBug.setType(Bug.Type.NORMAL);
        bBug.setX(300);
        bBug.setY(300);
        
        objLevel.addTower(aTower);
        objLevel.addBug(aBug);
        objLevel.addBug(bBug);

        return objLevel;
    }
    
    private Pool poolBullet;

    public Bullet getBullet(float x, float y, Bullet.Type type)
    {

        Bullet obj=new Bullet();
        obj.setX(x);
        obj.setY(y);
        obj.convertTo(type);
        return (poolBullet.getNewObject(obj));
    }

    public Bullet[] getBullets()
    {
        return poolBullet.getArray();
    }
    public void disposeBullet(Bullet b)
    {
        poolBullet.dispose(b);
    }

     public void addTower(Tower item)
    {
        massTowers.add(item);
    }

    public void removeTower(Tower item)
    {
        massTowers.remove(item);
    }

    public void addBug(Bug item)
    {
        massBugs.add(item);
    }

    public void removeBug(Bug item)
    {
        massBugs.remove(item);
    }
}

class Pool
{
    private Bullet[] pool;
    private boolean[] used;
    private int created;

    public int getSize()
    {
        return created;
    }

    public int getMaxSize()
    {
        return pool.length;
    }


    public Pool(int maxSize)
    {
        pool = new Bullet[maxSize];
        used = new boolean[maxSize];
        created = 0;
    }


    public Bullet getNewObject(Bullet obj)
    {
        for (int i = 0; i < created; i++)
        {
            if (!used[i])
            {
                used[i] = true;
                //EventBroker.invoke("debugMsgChanged", "returned "+i);
                return pool[i];
            }
        }
        if (created == pool.length)
        {
            //mark everything as unused, except the first that would be returned
            //that should make all first objects to disappear and to be converted to latest objs
            for (int i = 1; i < pool.length; i++)
            {
                used[i] = false;
            }
            return pool[0];
        }
        used[created] = true;
        pool[created] = obj;
        //EventBroker.invoke("debugMsgChanged", "created "+created);

        return pool[created++];
    }

    public void dispose(Bullet obj)
    {
        for (int i = 0; i < created; i++)
        {
            if ((Bullet) pool[i] == obj)
            {
                used[i] = false;
                return;
            }
        }
    }

    public void dispose()
    {
        for (int i = 0; i < created; i++)
        {
            pool[i] = null;
        }
        pool = null;
        used = null;
    }

    public Bullet[] getArray()
    {
        int count=0;
        int j=0;
        for(int i=0;i<pool.length;i++)
        {
            if (used[i]=true)
            {
                count++;
            }
        }
        Bullet[] mass=new Bullet[count];
        for(int i=0;i<pool.length;i++)
        {
            if (used[i]=true)
            {
                mass[j]=pool[i];
                j++;
            }
        }
        return mass;
    }
}
