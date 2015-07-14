package ru.sdevteam.vinv.game.logics;

import java.awt.Graphics;

import ru.sdevteam.vinv.game.Bug;
import ru.sdevteam.vinv.game.Bullet;
import ru.sdevteam.vinv.game.Decoration;
import ru.sdevteam.vinv.game.Explosion;
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
			if (((Decoration)UpdateDecosIterator.current()).getHp()==0)
			{
				 Decoration a  = ( (Decoration)UpdateDecosIterator.current()).getRuins();
				 modelOfLevel.removeDeco((Decoration)UpdateDecosIterator.current());
				 modelOfLevel.addDeco(a);
			}
			UpdateDecosIterator.next().update();
		}
		while(UpdateExplosionsIterator.hasMoreObjects())
		{
			//TODO:Time of Explosion
			UpdateExplosionsIterator.next().update();
		}

		while(UpdateBugsIterator.hasMoreObjects())
		{
			if (((Bug)UpdateBugsIterator.current()).getHp()==0)
			{
				Explosion a = new Explosion(Explosion.Type.SLIME ,(int) ((Bug)UpdateBugsIterator.current()).getX(), 
											(int) ((Bug)UpdateBugsIterator.current()).getY());	
				modelOfLevel.getExplosion(((Bug)UpdateBugsIterator.current()).getX(), ((Bug)UpdateBugsIterator.current()).getY(), a.getType());
				((Bug)UpdateBugsIterator.current()).setActive(false);
			}
			UpdateBugsIterator.next().update();
			
		}

		while(UpdateBulletsIterator.hasMoreObjects())
		{
			UpdateBulletsIterator.next().update();
		}

		UpdateExplosionsIterator.reset();
		UpdateDecosIterator.reset();
		
		while (UpdateExplosionsIterator.hasMoreObjects())
		{
			while(UpdateDecosIterator.hasMoreObjects())
			{
				if ( UpdateExplosionsIterator.current().getSprite().collidesWith(UpdateDecosIterator.current().getSprite()) )
				{
					if (((Decoration) UpdateDecosIterator.current()).isLeavingRuins())
					{
						((Decoration)UpdateDecosIterator.current()).hit((Explosion)UpdateExplosionsIterator.current());
					}
				}
				UpdateDecosIterator.next();
			}
			UpdateDecosIterator.reset();
			UpdateExplosionsIterator.next();
		}
		
		UpdateBulletsIterator.reset();
		UpdateDecosIterator.reset();
		
		while (UpdateBulletsIterator.hasMoreObjects())
		{
			while(UpdateDecosIterator.hasMoreObjects())
			{
				if ( UpdateBulletsIterator.current().getSprite().collidesWith(UpdateDecosIterator.current().getSprite()) )
				{
					if (((Decoration) UpdateDecosIterator.current()).isLeavingRuins())
					{
						((Decoration)UpdateDecosIterator.current()).hit((Explosion)UpdateBulletsIterator.current());
					}
				}
				UpdateDecosIterator.next();
			}
			UpdateDecosIterator.reset();
			UpdateExplosionsIterator.next();
		}
		
		UpdateExplosionsIterator.reset();
		UpdateBugsIterator.reset();
		
		while (UpdateExplosionsIterator.hasMoreObjects())
		{
			while(UpdateBugsIterator.hasMoreObjects())
			{
				if ( UpdateExplosionsIterator.current().getSprite().collidesWith(UpdateBugsIterator.current().getSprite()) )
				{
					((Bug)UpdateBugsIterator.current()).hit((Explosion)UpdateExplosionsIterator.current());
					((Bug)UpdateBugsIterator.current()).bindEffectsFrom((Explosion)UpdateExplosionsIterator.current());
				}
				UpdateBugsIterator.next();
			}
			UpdateBugsIterator.reset();
			UpdateExplosionsIterator.next();
		}
		
		UpdateBulletsIterator.reset();
		UpdateBugsIterator.reset();
		
		//TODO:Explosion from rockets
		while (UpdateBulletsIterator.hasMoreObjects())
		{
			while(UpdateBugsIterator.hasMoreObjects())
			{
				if ( UpdateBulletsIterator.current().getSprite().collidesWith(UpdateBugsIterator.current().getSprite()) )
				{
					((Bug)UpdateBugsIterator.current()).hit((Bullet)UpdateBulletsIterator.current());
					((Bug)UpdateBugsIterator.current()).bindEffectsFrom((Bullet)UpdateBulletsIterator.current());
					
					if ( !((Bullet)UpdateBulletsIterator.current()).isUnstoppable() )
					{
						modelOfLevel.disposeBullet( ((Bullet)UpdateBulletsIterator.current()) );
					}
				}
				UpdateBugsIterator.next();
			}
			UpdateBugsIterator.reset();
			UpdateBulletsIterator.next();
		}

		mover.update();

		UpdateTowersIterator.reset();
		UpdateBugsIterator.reset();

		while (UpdateTowersIterator.hasMoreObjects())
		{
			Tower t=(Tower)UpdateTowersIterator.next();
			
			while (UpdateBugsIterator.hasMoreObjects())
			{
				Bug b=(Bug)UpdateBugsIterator.next();
				Vector2F distanceBugToTower=new Vector2F( (b.getX()-t.getX()),(b.getY()-t.getY()));
				if(distanceBugToTower.getMagnitude()<200F)
					// //bug into radius of Tower )
				{
					if (t.canShoot()) 
					{	
						Bullet b1 = modelOfLevel.getBullet(t.getX(),t.getY(),t.getBulletType());
						System.out.println("Fire");
						b1.bindEffectsFrom(t);
						
						float flightTime=distanceBugToTower.getMagnitude()/b1.getSpeed();

						Vector2F displacement=mover.getBugVelocity(b);
						displacement.multipleBy(flightTime);
						distanceBugToTower.add(displacement);

						Vector2F vectorOfBulletSpeed = new Vector2F(distanceBugToTower, b1.getSpeed());
						b1.setVelocity(vectorOfBulletSpeed);
						t.shoot();

						t.rotate(distanceBugToTower.getDirection());
					}
				}
			}
			UpdateBugsIterator.reset();
		}
		
		UpdateBulletsIterator.reset();

		while (UpdateBulletsIterator.hasMoreObjects())
		{
			if ( (((Bullet)UpdateBulletsIterator.current()).getX()<-30) || (((Bullet)UpdateBulletsIterator.current()).getX()>1030) || 
					(((Bullet)UpdateBulletsIterator.current()).getY()<-30) || (((Bullet)UpdateBulletsIterator.current()).getX()>1030) )
			{
				modelOfLevel.disposeBullet( ((Bullet)UpdateBulletsIterator.current()) );
			}
			UpdateBulletsIterator.next();
		}
		
	}
}
