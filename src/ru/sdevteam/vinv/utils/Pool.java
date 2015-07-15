package ru.sdevteam.vinv.utils;

import java.util.Vector;
import ru.sdevteam.vinv.game.Bullet;
import ru.sdevteam.vinv.game.Explosion;
import ru.sdevteam.vinv.game.GameObject;
import ru.sdevteam.vinv.game.Level;


interface PoolFactory
{
	public GameObject getObject();
}

class BulletFactory implements PoolFactory
{
	public GameObject getObject()
	{
		return new Bullet();
	}
}

class ExplosionFactory implements PoolFactory
{
	public GameObject getObject()
	{
		return new Explosion();
	}
}
class Factories
{
	public  GameObject getObject(PoolFactory f)
	{
		return f.getObject();
	}
}
		
public class Pool
{
	private PoolFactory f;
    GameObject[] pool;
    boolean[] used;
    private int created;
	protected int count=-1;
	private Factories factories;
	
	public static BulletFactory getBulletFactory()
	{
		return new BulletFactory();
	}
	
	public static ExplosionFactory getExplosionFactory()
	{
		return new ExplosionFactory();
	}
	
    public int getSize()
    {
        return created;
    }

    public int getMaxSize()
    {
        return pool.length;
    }


    public Pool(int maxSize, PoolFactory f)
	{
		pool = new GameObject[maxSize];
        used = new boolean[maxSize];
        created = 0;
		this.f = f;
		factories = new Factories();
    }
	


    public GameObject getNewObject()
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
        GameObject obj=factories.getObject(f);
        pool[created] = obj;
        //EventBroker.invoke("debugMsgChanged", "created "+created);
        
        return pool[created++];
    }

    public void dispose(GameObject obj)
    {
        for (int i = 0; i < created; i++)
        {
            if (pool[i] == obj)
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

    public GameObject[] getArray()
    {
        int count=0;
        int j=0;
        for(int i=0;i<pool.length;i++)
        {
            if (used[i])
            {
                count++;
            }
        }
        GameObject[] mass=new GameObject[count];
        for(int i=0;i<pool.length;i++)
        {
            if (used[i])
            {
                mass[j]=pool[i];
                j++;
            }
        }
        return mass;
    }
	

	public GameObject next()
	{
		do
		{
			count++;
			if(count>=pool.length)
				return null;
		}
		while(!used[count]);
		return pool[count];
	}

	public void reset()
	{
		count=-1;
	}

	public boolean hasMoreObjects()
	{
		int i=count;
		do
		{
			i++;
			if(i>=pool.length)
				return false;
		}
		while(!used[i]);
		return true;
	}

}
