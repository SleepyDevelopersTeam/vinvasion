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
	private Vector<Wave> massWaves;
	
	private Pool poolBullet;
	private Pool poolExpl;

	private Player player;
	public Player createPlayer(){return player;}

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
		objLevel.massWaves = new Vector<Wave>();
		
		objLevel.player = new Player(100, 20, 50);
		
		Base base = new Base(objLevel.player);
		base.setX(496);
		base.setY(400);
		objLevel.massDecos.add(base);
		
		objLevel.createTiledLayer(ResourceManager.getBufferedImage("tiles/test"), 32, 32, 30, 30);

        Tower aTower=new MachineGun();
        aTower.setX(208);
        aTower.setY(208);
		
		Tower bTower=new FlameThrower();
		bTower.setX(336);
        bTower.setY(208);
		
        Bug aBug=new Bug();
		aBug.setType(Bug.Type.NORMAL);
        Bug bBug=new Bug();
		bBug.setType(Bug.Type.NORMAL);
		Bug cBug=new Bug();
		cBug.setType(Bug.Type.AIR);
        
		Wave wave1 = new Wave();
		wave1.addBug(aBug);
		wave1.addLongTimeInterval();
		wave1.addBug(bBug);
		wave1.addLongTimeInterval();
		wave1.addLongTimeInterval();
		wave1.addLongTimeInterval();
		wave1.addBug(cBug);
		
		objLevel.massWaves.add(wave1);
		
        objLevel.addTower(aTower);
        objLevel.addTower(bTower);
		//objLevel.addBug(aBug);
		//objLevel.addBug(bBug);
		//objLevel.addBug(cBug);
        
        objLevel.levelPath=new Path();
        objLevel.levelPath.addPoint(16F, 16F);
        objLevel.levelPath.addPoint(16F, 336F);
        objLevel.levelPath.addPoint(112F, 336F);
        objLevel.levelPath.addPoint(112F, 48F);
        objLevel.levelPath.addPoint(560F, 48F);
        objLevel.levelPath.addPoint(560F, 240F);
        objLevel.levelPath.addPoint(464F, 240F);
        objLevel.levelPath.addPoint(464F, 112F);
        objLevel.levelPath.addPoint(176F, 112F);
        objLevel.levelPath.addPoint(176F, 304F);
        objLevel.levelPath.addPoint(592F, 304F);
        objLevel.levelPath.addPoint(592F, 464F);
        objLevel.levelPath.addPoint(16F, 464F);
        objLevel.levelPath.addPoint(16F, 400F);
        objLevel.levelPath.addPoint(464F, 400F);
		 objLevel.levelPath.addPoint(592F, 464F);
        /*objLevel.levelPath.addPoint(16F, 336F);
        objLevel.levelPath.addPoint(112F, 336F);
        objLevel.levelPath.addPoint(112F, 48F);
        objLevel.levelPath.addPoint(560F, 48F);
        objLevel.levelPath.addPoint(560F, 240F);
        objLevel.levelPath.addPoint(464F, 240F);
        objLevel.levelPath.addPoint(464F, 112F);
        objLevel.levelPath.addPoint(176F, 112F);
        objLevel.levelPath.addPoint(176F, 304F);
        objLevel.levelPath.addPoint(592F, 304F);
        objLevel.levelPath.addPoint(592F, 16F);
        objLevel.levelPath.addPoint(16F, 16F);*/
		
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
					map[i][j]=17;
				else
					map[i][j]=0;
			}
		}
		//road
		map[0][0] = 10;
		for(int i=1;i<10;i++)
		{
			map[i][0] = 9;
		}
		map[10][0] = 13;
		map[10][1] =8;
		map[10][2] =8;
		map[10][3] =12;
		for(int i=1;i<10;i++)
		{
			map[i][3] = 9;
		}
		map[1][3] = 10;
		for(int i=4;i<17;i++)
		{
			map[1][i] = 8;
		}
		map[1][17] = 11;
		for(int i=2;i<7;i++)
		{
			map[i][17] = 9;
		}
		map[7][17] = 12;
		map[7][16] = 8;
		map[7][15] = 8;
		map[7][14] = 13;
		map[6][14] = 9;
		map[5][14] = 9;
		map[4][14] = 9;
		map[3][14] = 11;
		for(int i=6;i<14;i++)
		{
			map[3][i] = 8;
		}
		map[3][5] = 10;
		for(int i=4;i<9;i++)
		{
			map[i][5] = 9;
		}
		map[9][5] = 13;
		for(int i=6;i<18;i++)
		{
			map[9][i] = 8;
		}
		
		for(int i=10;i<14;i++)
		{
			map[i][18] = 9;
		}
		map[9][18] = 11;
		map[14][18] = 12;
		for(int i=1;i<18;i++)
		{
			map[14][i] = 8;
		}
		map[14][0] = 13; 
		map[13][0] = 9; 
		map[12][0] = 10; 
		for(int i=1;i<16;i++)
		{
			map[12][i] = 8;
		}
		/*map[9][18] = 12;
		for(int i=1;i<9;i++)
		{
			map[i][18] = 9;
		}*/
		/*map[0][18] = 11;
		for(int i=1;i<18;i++)
		{
			map[0][i] = 8;
		}*/
		//end of road
		
		//sea
		for(int i=20;i<30;i++)
		{
			for(int j=0;j<12;j++)
				map[j][i] = 23;
		}
		for(int i=0;i<12;i++)
		{
			map[i][19] = 30;
		}
		map[12][19] = 29;
		for(int i=20;i<30;i++)
		{
			map[12][i] = 28;
		}
		//end of sea
		
		//forest
		for(int i=15;i<30;i++)
		{
			for(int j=0;j<30;j++)
				map[i][j] = 17;
		}
		for(int i=13;i<15;i++)
		{
			for(int j=19;j<30;j++)
				map[i][j] = 16;
		}
		//end of forest
		
		//zig hail!
		for(int i=16;i<23;i++)
		{
			map[i][15] = 1;
		}
		for(int i=12;i<19;i++)
		{
			map[19][i] = 1;
		}
		for(int i=16;i<18;i++)
		{
			map[16][i] = 1;
		}
		for(int i=13;i<15;i++)
		{
			map[22][i] = 1;
		}
		for(int i=19;i<22;i++)
		{
			map[i][18] = 1;
		}
		for(int i=17;i<19;i++)
		{
			map[i][12] = 1;
		}
		// end of hail
		
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
	
	public void activateNextWave()
	{
		if(massWaves.firstElement().isEmpty())
			massWaves.remove(0);
	}
	
	public boolean hasMoreWaves()
	{
		if( massWaves.size()>0 )
			return true;
		return false;
	}
	
	public Wave getActiveWave()
	{
		if(massWaves.size()>1)
			return massWaves.firstElement();
		return null;
	}
	
	public Bug getNextBug()
	{
		return massWaves.firstElement().getNextBug();
	}
	
	public boolean isWaveEmpty()
	{
		if (massWaves.firstElement() != null)
			return massWaves.firstElement().isEmpty();
		return true;
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
				return (GameObject)lvl.poolExpl.next();
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
				return (GameObject)lvl.poolBullet.next();
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