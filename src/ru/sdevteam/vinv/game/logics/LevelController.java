package ru.sdevteam.vinv.game.logics;

import java.awt.Graphics;

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
	private Iterator UpdateDecosIterator;
	private Iterator UpdateExplosionsIterator;

	private Iterator PaintBugsIterator;
	private Iterator PaintTowersIterator;
	private Iterator PaintBulletsIterator;
	private Iterator PaintDecosIterator;
	private Iterator PaintExplosionsIterator;
	


	public void onPathEndReached(Bug invoker)
	{
		modelOfLevel.removeBug(invoker);
		//TODO:decrease people on the mainBase
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

	public void paint(Graphics g) 
	{
		modelOfLevel.getFone().paint(g, 0, 0, screen.getViewPortWidth(), screen.getViewPortHeight());
		
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

	public void update() 
	{
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
			UpdateExplosionsIterator.next().update();
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
					if (((Decoration) dec).isLeavingRuins())
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
		while(UpdateBugsIterator.hasMoreObjects())
		{
			GameObject bug = UpdateBugsIterator.next();
			bug.update();
			if (((Bug)bug).getHp()==0)
			{			
				Explosion expl = modelOfLevel.getExplosion(((Bug)bug).getX(), (bug).getY(), Type.SLIME);
				expl.explode();
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
				
				Vector2F distanceBugToTower=new Vector2F( (bug.getX()-tow.getX()),(bug.getY()-tow.getY()));
				if(distanceBugToTower.getMagnitude()<200F)
					// //bug into radius of Tower )
				{
					if (tow.canShoot()) 
					{	
						Bullet bug1 = modelOfLevel.getBullet(tow.getX(),tow.getY(),tow.getBulletType());
						System.out.println("Fire");
						bug1.bindEffectsFrom(tow);
						
						float flightTime=distanceBugToTower.getMagnitude()/bug1.getSpeed();

						Vector2F displacement=mover.getBugVelocity(bug);
						displacement.multipleBy(flightTime);
						distanceBugToTower.add(displacement);

						Vector2F vectorOfBulletSpeed = new Vector2F(distanceBugToTower, bug1.getSpeed());
						bug1.setVelocity(vectorOfBulletSpeed);
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
	}
}
