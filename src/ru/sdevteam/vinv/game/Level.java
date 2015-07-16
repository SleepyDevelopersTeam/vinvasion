package ru.sdevteam.vinv.game;
import java.awt.image.BufferedImage;
import java.util.Vector;
import ru.sdevteam.vinv.main.ResourceManager;
import ru.sdevteam.vinv.game.logics.Path;
import ru.sdevteam.vinv.ui.TiledLayer;
import ru.sdevteam.vinv.utils.Pool;
import ru.sdevteam.vinv.utils.PoolFactory;

public class Level
{ 
	private Vector<GameObject> massTowers; 
    private Vector<GameObject> massBugs;
	private Vector<GameObject> massDecos;

	private Pool poolBullet;
	private Pool poolExpl;

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
		
        objLevel.poolBullet=new Pool(300,new PoolFactory()
		{
			public Object getObject()
			{
				return new Bullet();
			}
		});
		objLevel.poolExpl=new Pool(300,new PoolFactory()
		{
			public Object getObject()
			{
				return new Explosion();
			}
		});
		
        objLevel.massBugs = new Vector<GameObject>();
        objLevel.massTowers = new Vector<GameObject>();
		objLevel.massDecos = new Vector<GameObject>();
		
		objLevel.createTiledLayer(ResourceManager.getBufferedImage("tiles/test"), 32, 32, 30, 30);

        Tower aTower=new MachineGun();
        aTower.setX(208);
        aTower.setY(208);
		
		Tower bTower=new FlameThrower();
		bTower.setX(336);
        bTower.setY(208);
		
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
		objLevel.addBug(cBug);
        
        objLevel.levelPath=new Path();
        objLevel.levelPath.addPoint(368F, 336F);
        objLevel.levelPath.addPoint(368F, 48F);
        objLevel.levelPath.addPoint(336F, 16F);
        objLevel.levelPath.addPoint(48F, 16F);
        objLevel.levelPath.addPoint(16F, 48F);
        objLevel.levelPath.addPoint(16F, 336F);
        objLevel.levelPath.addPoint(48F, 368F);
        objLevel.levelPath.addPoint(336F, 368F);
        objLevel.levelPath.addPoint(368F, 336F);
        objLevel.levelPath.addPoint(368F, 48F);
        objLevel.levelPath.addPoint(336F, 16F);
        objLevel.levelPath.addPoint(48F, 16F);
        objLevel.levelPath.addPoint(16F, 48F);
        objLevel.levelPath.addPoint(16F, 336F);
        objLevel.levelPath.addPoint(48F, 368F);
        objLevel.levelPath.addPoint(336F, 368F);
        objLevel.levelPath.addPoint(368F, 336F);
        objLevel.levelPath.addPoint(368F, 48F);
        objLevel.levelPath.addPoint(336F, 16F);
        objLevel.levelPath.addPoint(48F, 16F);
        objLevel.levelPath.addPoint(1050F, 1050F);
		
        return objLevel;
    }
 

	private void createTiledLayer(BufferedImage source, int tileWidth, int tileHeight, int tilesWidth, int tilesHeight)
	{
		tLayer = new TiledLayer(source, tileWidth, tileHeight, tilesWidth, tilesHeight);
		int[][] map = new int[tilesHeight][tilesWidth];
		for(int i=0;i<map.length;i++)
		{
			// TODO: Не создавать массив
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
        obj=(Bullet)poolBullet.getNewObject();
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
        obj=(Explosion)poolExpl.getNewObject();
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
			public GameObject next()
			{
				return lvl.poolExpl.next();
			}

			@Override
			public void reset()
			{
				lvl.poolExpl.reset();
			}

			@Override
			public boolean hasMoreObjects()
			{
				return lvl.poolExpl.hasMoreObjects();
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
			public GameObject next()
			{
				return lvl.poolBullet.next();
			}

			@Override
			public void reset()
			{
				lvl.poolBullet.reset();
			}

			@Override
			public boolean hasMoreObjects()
			{
				return lvl.poolBullet.hasMoreObjects();
			}

		};
		bulletsIterator.reset();
		return bulletsIterator;
	}
}