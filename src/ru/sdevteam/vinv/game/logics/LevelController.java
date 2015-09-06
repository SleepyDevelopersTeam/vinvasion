package ru.sdevteam.vinv.game.logics;

import java.awt.Graphics;
import java.awt.Rectangle;

import ru.sdevteam.vinv.game.Bug;
import ru.sdevteam.vinv.game.Bullet;
import ru.sdevteam.vinv.game.Decoration;
import ru.sdevteam.vinv.game.Explosion;
import ru.sdevteam.vinv.game.Explosion.Type;
import ru.sdevteam.vinv.game.GameObject;
import ru.sdevteam.vinv.game.Level;
import ru.sdevteam.vinv.game.Tower;
import ru.sdevteam.vinv.ui.IUpdatable;
import ru.sdevteam.vinv.ui.IDrawable;
import ru.sdevteam.vinv.ui.Sprite;
import ru.sdevteam.vinv.ui.TiledLayer;
import ru.sdevteam.vinv.utils.DebugInfo;
import ru.sdevteam.vinv.utils.Vector2F;
import ru.sdevteam.vinv.ui.GameScreen;
import ru.sdevteam.vinv.game.Player;
import ru.sdevteam.vinv.game.Level.Iterator;
public class LevelController implements IUpdatable, IDrawable 
{
	private Level modelOfLevel;
	private GameScreen screen;
	private BugsMover mover;
	
	private Player player;
	public Player getPlayer()
	{
		return player;
	}
	
	private boolean paused;
	public boolean isPaused() { return paused; }
	public void pause() { paused=true; }
	public void unpause() { paused=false; }

	private Iterator UpdateBugsIterator;
	private Iterator UpdateTowersIterator;
	private Iterator UpdateBulletsIterator;
	private Iterator UpdateDecosIterator;
	private Iterator UpdateExplosionsIterator;

	private Iterator PaintBugsIterator;
	private Iterator PaintTowersIterator;
	private Iterator PaintBulletsIterator;
	private Iterator PaintDecosIterator;
	private Iterator PaintExplosionsIterator;

	
	public LevelController(GameScreen a, Level l) 
	{  
		modelOfLevel = l;
		screen=a;
		player=l.createPlayer();

		UpdateBugsIterator = modelOfLevel.createBugsIterator();
		UpdateTowersIterator = modelOfLevel.createTowersIterator();
		UpdateBulletsIterator = modelOfLevel.createBulletsIterator();
		UpdateDecosIterator = modelOfLevel.createDecosIterator();
		UpdateExplosionsIterator = modelOfLevel.createExplosionIterator();

		PaintBugsIterator = modelOfLevel.createBugsIterator();
		PaintTowersIterator = modelOfLevel.createTowersIterator();
		PaintBulletsIterator = modelOfLevel.createBulletsIterator();
		PaintDecosIterator = modelOfLevel.createDecosIterator();
		PaintExplosionsIterator = modelOfLevel.createExplosionIterator();

		UpdateBugsIterator.reset();
		UpdateBulletsIterator.reset();
		UpdateTowersIterator.reset();
		UpdateDecosIterator.reset();
		UpdateExplosionsIterator.reset();

		PaintBugsIterator.reset();
		PaintBulletsIterator.reset();
		PaintTowersIterator.reset();
		PaintDecosIterator.reset();
		PaintExplosionsIterator.reset();

		mover=new BugsMover(this);
		mover.setPath(l.getLevelPath());
		while (UpdateBugsIterator.hasMoreObjects())
		{
			mover.addBug( ((Bug)UpdateBugsIterator.next()) );
		}
	}

	
	//
	// дл€ взаимодействи€ с игроком
	//
	private int getColumn(int x)
	{
		return x/modelOfLevel.getFone().getTileWidth();
	}
	private int getRow(int y)
	{
		return y/modelOfLevel.getFone().getTileHeight();
	}
	
	public Rectangle getCellBounds(int cursorx, int cursory)
	{
		int width=modelOfLevel.getFone().getTileWidth();
		int height=modelOfLevel.getFone().getTileHeight();
		int x=(cursorx/width)*width;
		int y=(cursory/height)*height;
		return new Rectangle(x, y, width, height);
	}
	
	public boolean placeTower(Tower item, int x, int y)
	{
		if(placeObject(item, x, y))
		{
			if(player.withdrawResources(item.getPrice()))
			{
				modelOfLevel.addTower(item);
				return true;
			}
			return false;
		}
		return false;
	}
	
	public boolean placeDecoration(Decoration item, int x, int y)
	{
		if(placeObject(item, x, y))
		{
			modelOfLevel.addDeco(item);
			return true;
		}
		return false;
	}
	
	private boolean placeObject(GameObject item, int x, int y)
	{
		int col=getColumn(x);
		int row=getRow(y);
		
		// можно ли в данной €чейке поставить башню?
		if(!TiledLayer.isFreeCell(modelOfLevel.getFone().getTileIndexAt(row, col))) return false;
		
		item.moveTo(col * modelOfLevel.getFone().getTileWidth()  + modelOfLevel.getFone().getTileWidth() /2,
					row * modelOfLevel.getFone().getTileHeight() + modelOfLevel.getFone().getTileHeight()/2);
		
		// теперь проверим, нет ли там уже башни
		Iterator i=modelOfLevel.createTowersIterator();
		while(i.hasMoreObjects())
		{
			if(i.next().getSprite().collidesWith(item.getSprite()))
			{
				DebugInfo.addMessage("Tower-to-tower collision!");
				return false;
			}
		}
		// и каких-нибудь декораций
		i=modelOfLevel.createDecosIterator();
		while(i.hasMoreObjects())
		{
			if(i.next().getSprite().collidesWith(item.getSprite()))
				return false;
		}
		
		// sector clear! roger that!
		return true;
	}
	
	public void removeTower(Tower item)
	{
		modelOfLevel.removeTower(item);
	}
	
	public Tower getTowerUnderCursor(int x, int y)
	{
		Iterator i=modelOfLevel.createTowersIterator();
		while(i.hasMoreObjects())
		{
			Tower t=(Tower)i.next();
			if(t.getSprite().contains(x, y))
				return t;
		}
		return null;
	}
	
	public Decoration getDecoUnderCursor(int x, int y)
	{
		Iterator i=modelOfLevel.createDecosIterator();
		while(i.hasMoreObjects())
		{
			Decoration d=(Decoration)i.next();
			if(d.getSprite().contains(x, y))
				return d;
		}
		return null;
	}
	
	
	public void onPathEndReached(Bug invoker)
	{
		modelOfLevel.removeBug(invoker);
		
		player.eatHumanCount(invoker.getHp()/10);
	}
	
	public void paint(Graphics g) 
	{
		modelOfLevel.getFone().paint(g, screen.getViewportX(), screen.getViewportY(),
										screen.getViewPortWidth(), screen.getViewPortHeight());
		
		PaintBugsIterator.reset();
		PaintBulletsIterator.reset();
		PaintTowersIterator.reset();
		PaintDecosIterator.reset();
		PaintExplosionsIterator.reset();

		while(PaintTowersIterator.hasMoreObjects())
		{
			PaintTowersIterator.next().getSprite().paint(g);
		}
		while(PaintBugsIterator.hasMoreObjects())
		{
			PaintBugsIterator.next().getSprite().paint(g);
		}
		while(PaintBulletsIterator.hasMoreObjects())
		{
			PaintBulletsIterator.next().getSprite().paint(g);
		}
		while(PaintDecosIterator.hasMoreObjects())
		{
			PaintDecosIterator.next().getSprite().paint(g);
		}
		while(PaintExplosionsIterator.hasMoreObjects())
		{
			PaintExplosionsIterator.next().getSprite().paint(g);
		}
	}

	private boolean hasBugs=false; // есть ли жуки на уровне
	private int ticksForNextWave=-1;
	public void update() 
	{
		//
		//  од обновлений, происход€щих и в паузе, и в игре
		//
		
		if(paused) return;
		
		//
		//  од обновлений, происход€щих только вне паузы
		//
		
		// проверка на проигрыш
		if(player.isLost())
		{
			screen.onDefeat();
		}
		
		UpdateBugsIterator.reset();
		UpdateBulletsIterator.reset();
		UpdateTowersIterator.reset();
		UpdateDecosIterator.reset();
		UpdateExplosionsIterator.reset();

		while(UpdateTowersIterator.hasMoreObjects())
		{
			
			UpdateTowersIterator.next().update();
			
		}
		
		while(UpdateDecosIterator.hasMoreObjects())
		{
			GameObject dec =  UpdateDecosIterator.next();
			dec.update();
			if ( ((Decoration)dec).getHp()==0)
			{
				 Decoration b  = ( (Decoration)dec).getRuins();
				 modelOfLevel.removeDeco((Decoration)dec);
				 modelOfLevel.addDeco(b);
			}
			
			
		}
		
		while(UpdateExplosionsIterator.hasMoreObjects())
		{
			GameObject expl = UpdateExplosionsIterator.next();
			expl.update();
			if ( !((Explosion)expl).isActive() )
			{
				modelOfLevel.disposeExplosion( (Explosion)expl);
			}
		}

		while(UpdateBulletsIterator.hasMoreObjects())
		{
			
			UpdateBulletsIterator.next().update();
		}

		UpdateExplosionsIterator.reset();
		UpdateDecosIterator.reset();
		
		while (UpdateExplosionsIterator.hasMoreObjects())
		{
			GameObject expl = UpdateExplosionsIterator.next();
			while(UpdateDecosIterator.hasMoreObjects())
			{
				GameObject dec = UpdateDecosIterator.next();
				if ( expl.getSprite().collidesWith(dec.getSprite()) )
				{
					if (((Decoration) dec).isLeavingRuins())
					{
						((Decoration)dec).hit((Explosion)expl);
					}
				}
				
			}
			UpdateDecosIterator.reset();
			
			
		}
		
		UpdateBulletsIterator.reset();
		UpdateDecosIterator.reset();
		
		while (UpdateBulletsIterator.hasMoreObjects())
		{
			GameObject bul = UpdateBulletsIterator.next();
			while(UpdateDecosIterator.hasMoreObjects())
			{
				GameObject dec = UpdateDecosIterator.next();
				if ( bul.getSprite().collidesWith(dec.getSprite()) )
				{
					if (((Decoration) dec).isHitable())
					{
						((Decoration)dec).hit((Bullet)bul);
					}
				}
				
			}
			UpdateDecosIterator.reset();
			

		}
		
		UpdateExplosionsIterator.reset();
		UpdateBugsIterator.reset();
		
		while (UpdateExplosionsIterator.hasMoreObjects())
		{
			GameObject expl = UpdateExplosionsIterator.next();
			while(UpdateBugsIterator.hasMoreObjects())
			{
				GameObject bug = UpdateBugsIterator.next();
				if ( expl.getSprite().collidesWith(bug.getSprite()) )
				{
					((Bug)bug).hit((Explosion)expl);
					((Bug)bug).bindEffectsFrom((Explosion)expl);
				}
				
				
			}
			UpdateBugsIterator.reset();
			
		}
		
		UpdateBulletsIterator.reset();
		UpdateBugsIterator.reset();
		
		//TODO:Explosion from rockets
		while (UpdateBulletsIterator.hasMoreObjects())
		{
			GameObject bul = UpdateBulletsIterator.next();
			while(UpdateBugsIterator.hasMoreObjects())
			{
				GameObject bug = UpdateBugsIterator.next();
				if ( bul.getSprite().collidesWith(bug.getSprite()) )
				{
					((Bug)bug).hit((Bullet)bul);
					((Bug)bug).bindEffectsFrom((Bullet)bul);
					
					if ( !((Bullet)bul).isUnstoppable() )
					{
						modelOfLevel.disposeBullet( ((Bullet)bul) );
					}
				}
				
			}
			UpdateBugsIterator.reset();
			
		}

		mover.update();

		UpdateBugsIterator.reset();
		hasBugs=false;
		while(UpdateBugsIterator.hasMoreObjects())
		{
			hasBugs=true;
			GameObject bug = UpdateBugsIterator.next();
			bug.update();
			if (((Bug)bug).getHp()==0)
			{			
				Explosion expl = modelOfLevel.getExplosion(((Bug)bug).getX(), (bug).getY(), Type.SLIME);
				expl.explode();
				player.addResources(((Bug)bug).getCost());
				modelOfLevel.markInactive( ((Bug)bug) );
			}
		}
		
		UpdateTowersIterator.reset();
		UpdateBugsIterator.reset();

		
		
		while (UpdateTowersIterator.hasMoreObjects())
		{
			Tower tow=(Tower)UpdateTowersIterator.next();
			
			while (UpdateBugsIterator.hasMoreObjects())
			{
				
				Bug bug=(Bug)UpdateBugsIterator.next();
				// TODO: нан€ть трезвого наводчика
				
				//попытка улучшить алгоритм пуль
				float x,y,r,k;
				x = bug.getX()-tow.getX();
				y = bug.getY()-tow.getY();
				r = (float) Math.sqrt(x*x+y*y);
				k = r/8;
				x = x/k;
				y = y/k;
				
				Vector2F distanceBugToTower=new Vector2F( (bug.getX()-tow.getX()-x),(bug.getY()-tow.getY())-y);
				if(distanceBugToTower.getMagnitude()<tow.getShootingRadius())
					// //bug into radius of Tower )
				{
					if (tow.canShoot()) 
					{	
						Bullet newBullet = modelOfLevel.getBullet(tow.getX()+x,tow.getY()+y,tow.getBulletType());
						//System.out.println("Fire");
						newBullet.bindEffectsFrom(tow);
						
						float flightTime=distanceBugToTower.getMagnitude()/newBullet.getSpeed();

						Vector2F displacement=mover.getBugVelocity(bug);
						displacement.multipleBy(flightTime);
						distanceBugToTower.add(displacement);

						Vector2F vectorOfBulletSpeed = new Vector2F(distanceBugToTower, newBullet.getSpeed());
						newBullet.setVelocity(vectorOfBulletSpeed);
						tow.shoot();

						tow.rotate(distanceBugToTower.getDirection());
					}
				}
			}
			UpdateBugsIterator.reset();
		}
		
		
		UpdateBulletsIterator.reset();

		while (UpdateBulletsIterator.hasMoreObjects())
		{
			GameObject a = UpdateBulletsIterator.next();
			if ( (((Bullet)a).getX()<-30) || (((Bullet)a).getX()>1030) || 
					(((Bullet)a).getY()<-30) || (((Bullet)a).getX()>1030) )
			{
				modelOfLevel.disposeBullet( ((Bullet)a) );
			}
			
		}
		
		//
		// ¬олны
		//
		
		if(modelOfLevel.getActiveWave()!=null) 
		{
			modelOfLevel.getActiveWave().update();
			if(!hasBugs && modelOfLevel.isWaveEmpty())
			{
				if(ticksForNextWave==-1)
				{
					// начало ожидани€
					ticksForNextWave=300;
				}
				else if(ticksForNextWave>0)
				{
					// ожидаем...
					--ticksForNextWave;
				}
				else if(ticksForNextWave==0)
				{
					// всЄ, врем€ вышло
					if(modelOfLevel.hasMoreWaves())
					{
						modelOfLevel.activateNextWave();
					}
					else
					{
						screen.onVictory();
					}
					ticksForNextWave=-1; // в начало ожидани€
				}
			}
			if(!modelOfLevel.isWaveEmpty())
			{
				Bug b=modelOfLevel.getNextBug();
				if(b!=null)
				{
					modelOfLevel.addBug(b);
					mover.addBug(b);
				}
			}
		}
	}
}
