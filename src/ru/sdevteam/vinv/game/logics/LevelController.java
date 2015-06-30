package ru.sdevteam.vinv.game.logics;

import java.awt.Graphics;
import ru.sdevteam.vinv.game.Bug;
import ru.sdevteam.vinv.game.Bullet;
import ru.sdevteam.vinv.game.Level;
import ru.sdevteam.vinv.game.Tower;
import ru.sdevteam.vinv.ui.IUpdatable;
import ru.sdevteam.vinv.ui.IDrawable;
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
	
	public void onPathEndReached(Bug invoker)
	{
		modelOfLevel.removeBug(invoker);
		//decrease people on the mainBase
	}
	
	public Player getPlayer()
	{
		return player;
	}
	public LevelController(GameScreen a, Level l) 
	{  
		modelOfLevel = l;
		screen=a;
		Iterator bugsIterator = modelOfLevel.getBugsIterator();
		//Bug[] arrayOfBugs=this.modelOfLevel.getBugs();
		
		mover=new BugsMover(this);
		mover.setPath(l.getLevelPath());
		while (bugsIterator.hasMoreObjects())
		{
			mover.addBug( ((Bug)bugsIterator.next()) );
		}
		/*for(int i=0;i<arrayOfBugs.length;i++)
		{
			mover.addBug(arrayOfBugs[i]);
		}*/
	}

	public void paint(Graphics g) 
	{
		modelOfLevel.getFone().paint(g, 0, 0, screen.getViewPortWidth(), screen.getViewPortHeight());
		
		// TODO: ÂÀÐÍÈÍÃ: ÀËßÐÌÀ: Èñïîëüçîâàòü èòåðàòîðû
		/*Tower[] arrayOfTowers=this.modelOfLevel.getTowers();
		Bug[] arrayOfBugs=this.modelOfLevel.getBugs();
		Bullet[] arrayOfBullets=this.modelOfLevel.getBullets();
		for(int i=0;i<arrayOfTowers.length;i++)
		{
			arrayOfTowers[i].getSprite().paint(g);
		}
		for(int i=0;i<arrayOfBugs.length;i++)
		{
			arrayOfBugs[i].getSprite().paint(g);
		}
		for(int i=0;i<arrayOfBullets.length;i++)
		{
			arrayOfBullets[i].getSprite().paint(g);
		}*/
		Iterator towerIterator = modelOfLevel.getTowersIterator();
		Iterator bugsIterator = modelOfLevel.getBugsIterator();
		Iterator bulletsIterator = modelOfLevel.getBulletsIterator();
		
		while(towerIterator.hasMoreObjects())
		{
			towerIterator.next().getSprite().paint(g);
		}
		while(bugsIterator.hasMoreObjects())
		{
			bugsIterator.next().getSprite().paint(g);
		}
		while(bulletsIterator.hasMoreObjects())
		{
			bulletsIterator.next().getSprite().paint(g);
		}
	}

	public void update() 
	{
		/*Tower[] arrayOfTowers = this.modelOfLevel.getTowers();
		Bug[] arrayOfBugs = this.modelOfLevel.getBugs();
		Bullet[] arrayOfBullets=this.modelOfLevel.getBullets();
		for (int i = 0; i < arrayOfTowers.length; i++) 
		{
			arrayOfTowers[i].update();
		}
		for (int i = 0; i < arrayOfBugs.length; i++) 
		{
			arrayOfBugs[i].update();
		}
		for (int i = 0; i < arrayOfBullets.length; i++) 
		{
			arrayOfBullets[i].update();
		}*/
		
		Iterator towerIterator = modelOfLevel.getTowersIterator();
		Iterator bugsIterator = modelOfLevel.getBugsIterator();
		Iterator bulletsIterator = modelOfLevel.getBulletsIterator();
	
		while(towerIterator.hasMoreObjects())
		{
			towerIterator.next().update();
		}
		while(bugsIterator.hasMoreObjects())
		{
			bugsIterator.next().update();
		}
		while(bulletsIterator.hasMoreObjects())
		{
			bulletsIterator.next().update();
		}
		
		bugsIterator.reset();
		bulletsIterator.reset();
		
		while (bulletsIterator.hasMoreObjects())
		{
			while(bugsIterator.hasMoreObjects())
			{
				if ( bulletsIterator.current().getSprite().collidesWith(bugsIterator.current().getSprite()) )
						{
							((Bug)bugsIterator.current()).hit((Bullet)bulletsIterator.current());
							if ( !((Bullet)bulletsIterator.current()).isUnstoppable() )
							{
								modelOfLevel.disposeBullet( ((Bullet)bulletsIterator.current()) );
							}
						}
				bugsIterator.next();
			}
			bugsIterator.reset();
		}
		/*for (int i = 0; i < arrayOfBullets.length; i++) 
		{
			for (int j = 0; j < arrayOfBugs.length; j++) 
			{
				if (arrayOfBullets[i].getSprite().collidesWith(arrayOfBugs[j].getSprite())) 
				{
					arrayOfBugs[j].hit(arrayOfBullets[i]);
					if (!arrayOfBullets[i].isUnstoppable())
					{
						modelOfLevel.disposeBullet(arrayOfBullets[i]);
					}
				}
			}
		}*/
		
		mover.update();
		
		towerIterator.reset();
		bugsIterator.reset();
		
		while (towerIterator.hasMoreObjects())
		{
			while (bugsIterator.hasMoreObjects())
			{
				
				Vector2F distanceBugToTower=new Vector2F( ((Bug)bugsIterator.current()).getX()-((Tower)towerIterator.current()).getX(),
						((Bug)bugsIterator.current()).getY()-((Tower)towerIterator.current()).getY());
				 if(distanceBugToTower.getMagnitude()<200F)
				// //bug into radius of Tower )
				 {
					if (((Tower)towerIterator.current()).canShoot()) 
					{	
						Bullet b = modelOfLevel.getBullet(	((Tower)towerIterator.current()).getX(),
															((Tower)towerIterator.current()).getY(),
															((Tower)towerIterator.current()).getBulletType());
						
						
						float flightTime=distanceBugToTower.getMagnitude()/b.getSpeed();
						
						Vector2F displacement=mover.getBugVelocity((Bug)bugsIterator.current());
						displacement.multipleBy(flightTime);
						distanceBugToTower.add(displacement);
						
						Vector2F vectorOfBulletSpeed = new Vector2F(distanceBugToTower, b.getSpeed());
						b.setVelocity(vectorOfBulletSpeed);
						((Tower)towerIterator.current()).shoot();
						
						((Tower)towerIterator.current()).rotate(distanceBugToTower.getDirection());
					}
				}
				bugsIterator.next(); 
			}
			bugsIterator.reset();
		}
		/*for (int i = 0; i < arrayOfTowers.length; i++) 
		{
			for (int j = 0; j < arrayOfBugs.length; j++) 
			{
				Vector2F distanceBugToTower=new Vector2F(arrayOfBugs[j].getX()-arrayOfTowers[i].getX(),
						arrayOfBugs[j].getY()-arrayOfTowers[i].getY());
				 if(distanceBugToTower.getMagnitude()<200F)
				// //bug into radius of Tower )
				 {
					if (arrayOfTowers[i].canShoot()) 
					{	
						Bullet b = modelOfLevel.getBullet(	arrayOfTowers[i].getX(),
															arrayOfTowers[i].getY(),
															arrayOfTowers[i].getBulletType());
						
						
						float flightTime=distanceBugToTower.getMagnitude()/b.getSpeed();
						
						Vector2F displacement=mover.getBugVelocity(arrayOfBugs[j]);
						displacement.multipleBy(flightTime);
						distanceBugToTower.add(displacement);
						
						Vector2F vectorOfBulletSpeed = new Vector2F(distanceBugToTower, b.getSpeed());
						b.setVelocity(vectorOfBulletSpeed);
						arrayOfTowers[i].shoot();
						
						arrayOfTowers[i].rotate(distanceBugToTower.getDirection());
					}
				}
			}
		}*/
		
		bulletsIterator.reset();
		
		while (bulletsIterator.hasMoreObjects())
		{
			if ( (((Bullet)bulletsIterator.current()).getX()<-30) || (((Bullet)bulletsIterator.current()).getX()>1030) || 
				 (((Bullet)bulletsIterator.current()).getY()<-30) || (((Bullet)bulletsIterator.current()).getX()>1030) )
			{
				modelOfLevel.disposeBullet( ((Bullet)bulletsIterator.current()) );
			}
		}
		/*for (int i = 0; i < arrayOfBullets.length; i++)
		{
			if ((arrayOfBullets[i].getX()<-30) || (arrayOfBullets[i].getX()>1030) || (arrayOfBullets[i].getY()<-30) || (arrayOfBullets[i].getY()>1030))
			{
				modelOfLevel.disposeBullet(arrayOfBullets[i]);
			}
		}*/
		
	}
}
