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
	
	private Iterator UpdateBugsIterator;
	private Iterator UpdateTowersIterator;
	private Iterator UpdateBulletsIterator;
	
	private Iterator PaintBugsIterator;
	private Iterator PaintTowersIterator;
	private Iterator PaintBulletsIterator;
	
	
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
		
		UpdateBugsIterator = modelOfLevel.createBugsIterator();
		UpdateTowersIterator = modelOfLevel.createTowersIterator();
		UpdateBulletsIterator = modelOfLevel.createBulletsIterator();
		
		PaintBugsIterator = modelOfLevel.createBugsIterator();
		PaintTowersIterator = modelOfLevel.createTowersIterator();
		PaintBulletsIterator = modelOfLevel.createBulletsIterator();
		
		UpdateBugsIterator.reset();
		UpdateBulletsIterator.reset();
		UpdateTowersIterator.reset();
		
		PaintBugsIterator.reset();
		PaintBulletsIterator.reset();
		PaintTowersIterator.reset();
		
		//Bug[] arrayOfBugs=this.modelOfLevel.getBugs();
		
		mover=new BugsMover(this);
		mover.setPath(l.getLevelPath());
		while (UpdateBugsIterator.hasMoreObjects())
		{
			mover.addBug( ((Bug)UpdateBugsIterator.next()) );
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
		
		PaintBugsIterator.reset();
		PaintBulletsIterator.reset();
		PaintTowersIterator.reset();
		
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
		
		UpdateBugsIterator.reset();
		UpdateBulletsIterator.reset();
		UpdateTowersIterator.reset();
		
		while(UpdateTowersIterator.hasMoreObjects())
		{
			UpdateTowersIterator.next().update();
		}
		
		while(UpdateBugsIterator.hasMoreObjects())
		{
			UpdateBugsIterator.next().update();
		}
		
		while(UpdateBulletsIterator.hasMoreObjects())
		{
			UpdateBulletsIterator.next().update();
		}
		
		PaintBulletsIterator.reset();
		PaintBugsIterator.reset();
		
		while (PaintBulletsIterator.hasMoreObjects())
		{
			while(PaintBugsIterator.hasMoreObjects())
			{
				if ( PaintBulletsIterator.current().getSprite().collidesWith(PaintBugsIterator.current().getSprite()) )
						{
							((Bug)PaintBugsIterator.current()).hit((Bullet)PaintBulletsIterator.current());
							if ( !((Bullet)PaintBulletsIterator.current()).isUnstoppable() )
							{
								modelOfLevel.disposeBullet( ((Bullet)PaintBulletsIterator.current()) );
							}
						}
				PaintBugsIterator.next();
			}
			PaintBugsIterator.reset();
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
		
		PaintTowersIterator.reset();
		PaintBugsIterator.reset();
		
		while (PaintTowersIterator.hasMoreObjects())
		{
			while (PaintBugsIterator.hasMoreObjects())
			{
				
				Vector2F distanceBugToTower=new Vector2F( ((Bug)PaintBugsIterator.current()).getX()-((Tower)PaintTowersIterator.current()).getX(),
						((Bug)PaintBugsIterator.current()).getY()-((Tower)PaintTowersIterator.current()).getY());
				 if(distanceBugToTower.getMagnitude()<200F)
				// //bug into radius of Tower )
				 {
					if (((Tower)PaintTowersIterator.current()).canShoot()) 
					{	
						Bullet b = modelOfLevel.getBullet(	((Tower)PaintTowersIterator.current()).getX(),
															((Tower)PaintTowersIterator.current()).getY(),
															((Tower)PaintTowersIterator.current()).getBulletType());
						
						
						float flightTime=distanceBugToTower.getMagnitude()/b.getSpeed();
						
						Vector2F displacement=mover.getBugVelocity((Bug)PaintBugsIterator.current());
						displacement.multipleBy(flightTime);
						distanceBugToTower.add(displacement);
						
						Vector2F vectorOfBulletSpeed = new Vector2F(distanceBugToTower, b.getSpeed());
						b.setVelocity(vectorOfBulletSpeed);
						((Tower)PaintTowersIterator.current()).shoot();
						
						((Tower)PaintTowersIterator.current()).rotate(distanceBugToTower.getDirection());
					}
				}
				PaintBugsIterator.next(); 
			}
			PaintBugsIterator.reset();
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
		
		PaintBulletsIterator.reset();
		
		while (PaintBulletsIterator.hasMoreObjects())
		{
			if ( (((Bullet)PaintBulletsIterator.current()).getX()<-30) || (((Bullet)PaintBulletsIterator.current()).getX()>1030) || 
				 (((Bullet)PaintBulletsIterator.current()).getY()<-30) || (((Bullet)PaintBulletsIterator.current()).getX()>1030) )
			{
				modelOfLevel.disposeBullet( ((Bullet)PaintBulletsIterator.current()) );
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
