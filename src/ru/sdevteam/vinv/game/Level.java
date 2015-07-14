package ru.sdevteam.vinv.game;
import java.awt.image.BufferedImage;
import java.util.*;
import ru.sdevteam.vinv.main.ResourceManager;
import ru.sdevteam.vinv.game.logics.Path;
import ru.sdevteam.vinv.ui.TiledLayer;

public class Level
{ 
	private Vector<GameObject> massTowers; 
    private Vector<GameObject> massBugs;
	private Vector<GameObject> massDecos;
	
	private PoolBullet poolBullet;
	private PoolExpl poolExpl;

	private Player player;
	public Player createPlayer(){return null;}

    private Path levelPath;
    public Path getLevelPath()
    {
    	return levelPath;
    }

	private TiledLayer tLayer;
	public TiledLayer getFone()
	{
		return tLayer;
	}
	
	
    public static Level createLevel(int num)
    {
        Level objLevel=new Level();
		
        objLevel.poolBullet=new PoolBullet(300);
		objLevel.poolExpl=new PoolExpl(300);
		
        objLevel.massBugs = new Vector<GameObject>();
        objLevel.massTowers = new Vector<GameObject>();
		objLevel.massDecos = new Vector<GameObject>();
		
		objLevel.createTiledLayer(ResourceManager.getBufferedImage("tiles/test"), 32, 32, 30, 30);

        Tower aTower=new MachineGun();
        aTower.setX(200);
        aTower.setY(200);
		
		Tower bTower=new FlameThrower();
		aTower.setX(400);
        aTower.setY(200);
		
        Bug aBug=new Bug();
		aBug.setType(Bug.Type.NORMAL);
        aBug.setX(100);
        aBug.setY(100);

        Bug bBug=new Bug();
		bBug.setType(Bug.Type.NORMAL);
        bBug.setX(200);
        bBug.setY(200);
		
		Bug cBug=new Bug();
		cBug.setType(Bug.Type.NORMAL);
        cBug.setX(250);
        cBug.setY(250);
        
        objLevel.addTower(aTower);
        objLevel.addTower(bTower);
		objLevel.addBug(aBug);
        objLevel.addBug(bBug);
		//objLevel.addBug(cBug);
        
        objLevel.levelPath=new Path();
        objLevel.levelPath.addPoint(350F, 325F);
        objLevel.levelPath.addPoint(350F, 50F);
        objLevel.levelPath.addPoint(325F, 25F);
        objLevel.levelPath.addPoint(50F, 25F);
        objLevel.levelPath.addPoint(25F, 50F);
        objLevel.levelPath.addPoint(25F, 325F);
        objLevel.levelPath.addPoint(50F, 350F);
        objLevel.levelPath.addPoint(325F, 350F);
        objLevel.levelPath.addPoint(350F, 325F);
        objLevel.levelPath.addPoint(350F, 50F);
        objLevel.levelPath.addPoint(325F, 25F);
        objLevel.levelPath.addPoint(50F, 25F);
        objLevel.levelPath.addPoint(25F, 50F);
        objLevel.levelPath.addPoint(25F, 325F);
        objLevel.levelPath.addPoint(50F, 350F);
        objLevel.levelPath.addPoint(325F, 350F);
        objLevel.levelPath.addPoint(350F, 325F);
        objLevel.levelPath.addPoint(350F, 50F);
        objLevel.levelPath.addPoint(325F, 25F);
        objLevel.levelPath.addPoint(50F, 25F);
        objLevel.levelPath.addPoint(1050F, 1050F);
		
        return objLevel;
    }
 

	private void createTiledLayer(BufferedImage source, int tileWidth, int tileHeight, int tilesWidth, int tilesHeight)
	{
		tLayer = new TiledLayer(source, tileWidth, tileHeight, tilesWidth, tilesHeight);
		int[][] map = new int[tilesHeight][tilesWidth];
		for(int i=0;i<map.length;i++)
		{
			// TODO: РќРµ СЃРѕР·РґР°РІР°С‚СЊ РјР°СЃСЃРёРІ
			for(int j=0;j<map[i].length;j++)
			{
				if((int)(Math.random()*10)==0)
					map[i][j]=18;
				else
					map[i][j]=0;
			}
		}
		tLayer.setMap(map);
	}
	
    public Bullet getBullet(float x, float y, Bullet.Type type)
    {
        Bullet obj;
        obj=poolBullet.getNewObject();
        obj.setX(x);
        obj.setY(y);
        obj.convertTo(type);
        return (obj);
    }

    public void disposeBullet(Bullet b)
    {
        poolBullet.dispose(b);
    }
	
	public Explosion getExplosion(float x, float y, Explosion.Type type)
    {
        Explosion obj;
        obj=poolExpl.getNewObject();
		obj.convertTo(type);
        obj.setX(x);
        obj.setY(y);
        return (obj);
    }

	public void disposeExplosion(Explosion b)
    {
        poolExpl.dispose(b);
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
	
	public void addDeco(Decoration d)
	{
		massDecos.add(d);
	}
	
	public void removeDeco(Decoration d)
	{
		massDecos.remove(d);
	}
	
	public void markInactive(Bug b)
	{
		b.setActive(false);
	}
	
	public class Iterator
	{
		protected Level lvl;
		protected int count=-1;
		protected Vector<GameObject> vector;
		
		
		public Iterator(Level lvl)
		{
			this.lvl=lvl;
		}
		
		
		public Iterator(Level lvl,Vector<GameObject> vector)
		{
			this.lvl=lvl;
			this.vector=vector;
		}
		
		
		public GameObject current()
		{
			if ((count==-1) && (vector.size()>0))
			{
				count=0;
				return vector.elementAt(count);
			}
			if (count<vector.size())
			{
				return vector.elementAt(count);
			}
			return null;
		}

		public GameObject next()
		{
			if(count<vector.size()-1)
			{
				count++;
				return vector.elementAt(count);

			}
			return null;
		}

		public void reset()
		{
			count=-1;
		}

		public boolean hasMoreObjects()
		{
			if(count<vector.size()-1)
			{
				return true;
			}
			return false;
		}
	
	}
		
	public Level.Iterator createTowersIterator()
	{
		Iterator towersIterator=new Iterator(this, massTowers);
		towersIterator.reset();
		return towersIterator;
	}
	
	public Level.Iterator createDecosIterator()
	{
		Iterator decosIterator=new Iterator(this,massDecos);
		decosIterator.reset();
		return decosIterator;
	}
		
	public Level.Iterator createBugsIterator()
	{
		Iterator bugsIterator=new Iterator(this,massBugs)
		{
			@Override
			public GameObject current()
			{
				if ( count == -1 )
				{
					count=0;
					while ((count<vector.size()) && (((Bug)vector.elementAt(count)).isActive() == false))
						count++;
				}
				if ((count<vector.size()) && (((Bug)vector.elementAt(count)).isActive() == true))
				{
					return vector.elementAt(count);
				}
				return null;
			}
			
			@Override
			public GameObject next()
			{
				//if (count == -1)
				//	count=0;
				count++;
				while ((count<vector.size()) && (((Bug)vector.elementAt(count)).isActive() == false))
					count++;
				if( (count<vector.size()) && ((Bug)vector.elementAt(count)).isActive() == true )
				{
					return vector.elementAt(count);

				}
				return null;
			}
			
			@Override
			public boolean hasMoreObjects()
			{
				int i=count;
				//if (i == -1)
				//	i=0;
				i++;
				while((i<vector.size()) && (((Bug)vector.elementAt(i)).isActive()==false))
					i++;
				if(( i<vector.size() && ((Bug)vector.elementAt(i)).isActive()==true) && (i!=count))
					return true;
				return false;
			}
		};
		bugsIterator.reset();
		return bugsIterator;
	}

	public Level.Iterator createExplosionIterator()
	{	
		Iterator explsIterator=new Iterator(this)
		{
			@Override
			public GameObject current()
			{
				
				if(count>=lvl.poolExpl.pool.length) return null;
				return lvl.poolExpl.pool[count];
			}

			@Override
			public Explosion next()
			{
				do
				{
					count++;
					if(count>=lvl.poolExpl.pool.length)
						return null;
				}
				while(!lvl.poolExpl.used[count]);
				return lvl.poolExpl.pool[count];
			}

			@Override
			public void reset()
			{
				count=0;
			}

			@Override
			public boolean hasMoreObjects()
			{
				int i=count;
				do
				{
					i++;
					if(i>=lvl.poolExpl.pool.length)
						return false;
				}
				while(!lvl.poolExpl.used[i]);
				return true;
			}
		};
		explsIterator.reset();
		return explsIterator;
	}
		
	public Level.Iterator createBulletsIterator()
	{
		Iterator bulletsIterator=new Iterator(this)
		{
			@Override
			public GameObject current()
			{
				
				if(count>=lvl.poolBullet.pool.length) return null;
				return lvl.poolBullet.pool[count];
			}

			@Override
			public GameObject next()
			{
				do
				{
					count++;
					if(count>=lvl.poolBullet.pool.length)
						return null;
				}
				while(!lvl.poolBullet.used[count]);
				return lvl.poolBullet.pool[count];
			}

			@Override
			public void reset()
			{
				count=0;
			}

			@Override
			public boolean hasMoreObjects()
			{
				int i=count;
				do
				{
					i++;
					if(i>=lvl.poolBullet.pool.length)
						return false;
				}
				while(!lvl.poolBullet.used[i]);
				return true;
			}

		};
		bulletsIterator.reset();
		return bulletsIterator;
	}

}

class PoolBullet
{
	// TODO: перенести класс Pool внутрь Level и снова заприватить массивы
    Bullet[] pool;
    boolean[] used;
    private int created;

    public int getSize()
    {
        return created;
    }

    public int getMaxSize()
    {
        return pool.length;
    }


    public PoolBullet(int maxSize)
    {
        pool = new Bullet[maxSize];
        used = new boolean[maxSize];
        created = 0;
    }


    public Bullet getNewObject()
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
        	System.out.println("????? ?????");
            for (int i = 1; i < pool.length; i++)
            {
                used[i] = false;
            }
            return pool[0];
        }
        used[created] = true;
        Bullet obj=new Bullet();
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
            if (used[i])
            {
                count++;
            }
        }
        Bullet[] mass=new Bullet[count];
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

}

class PoolExpl
{
	// TODO: перенести класс Pool внутрь Level и снова заприватить массивы
    Explosion[] pool;
    boolean[] used;
    private int created;

    public int getSize()
    {
        return created;
    }

    public int getMaxSize()
    {
        return pool.length;
    }


    public PoolExpl(int maxSize)
    {
        pool = new Explosion[maxSize];
        used = new boolean[maxSize];
        created = 0;
    }


    public Explosion getNewObject()
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
        	System.out.println("????? ?????");
            for (int i = 1; i < pool.length; i++)
            {
                used[i] = false;
            }
            return pool[0];
        }
        used[created] = true;
        Explosion obj=new Explosion();
        pool[created] = obj;
        //EventBroker.invoke("debugMsgChanged", "created "+created);
        
        return pool[created++];
    }

    public void dispose(Explosion obj)
    {
        for (int i = 0; i < created; i++)
        {
            if ((Explosion) pool[i] == obj)
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

    public Explosion[] getArray()
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
        Explosion[] mass=new Explosion[count];
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

}