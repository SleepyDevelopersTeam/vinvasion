package ru.sdevteam.vinv.game.logics;

import java.awt.Graphics;
import ru.sdevteam.vinv.game.Bug;
import ru.sdevteam.vinv.game.Bullet;
import ru.sdevteam.vinv.game.Level;
import ru.sdevteam.vinv.game.Tower;
import ru.sdevteam.vinv.ui.IUpdatable;
import ru.sdevteam.vinv.ui.IDrawable;
import ru.sdevteam.vinv.utils.Vector2F;
import ru.sdevteam.vinv.ui.GameScreen;;

public class LevelController implements IUpdatable, IDrawable 
{
	private Level modelOfLevel;
	private GameScreen screen;
	
	public LevelController(GameScreen a, Level l) 
	{
		modelOfLevel = l;
		screen=a;
	}

	public void paint(Graphics g) 
	{
		//TODO: отрисовка в зависимотсти от размеров окна
		Tower[] arrayOfTowers=this.modelOfLevel.getTowers();
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
		}
	}

	public void update() 
	{
		Tower[] arrayOfTowers = this.modelOfLevel.getTowers();
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
		}

		for (int i = 0; i < arrayOfBullets.length; i++) 
		{
			for (int j = 0; j < arrayOfBugs.length; i++) 
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
		}
		
		for (int i = 0; i < arrayOfTowers.length; i++) 
		{
			for (int j = 0; j < arrayOfBugs.length; i++) 
			{
				// if
				// (ArrayOfTowers[i].getShootingRadius()>=((ArrayOfBugs[j].getX()-ArrayOfTowers[i].getX())*(ArrayOfBugs[j].getX()-ArrayOfTowers[i].getX()+(ArrayOfBugs[j].getY()-ArrayOfTowers[i].getY())*(ArrayOfBugs[j].getY()-ArrayOfTowers[i].getY()))))
				// //bug into radius of Tower )
				// new Vector2F(arrayOfBugs[j].getX()-arrayOfTowers[i].getX(),arrayOfBugs[j].getY()-arrayOfTowers[i].getY())
				{
					if (arrayOfTowers[i].canShoot()) 
					{	
						Bullet b = modelOfLevel.getBullet(	arrayOfTowers[i].getX(),
															arrayOfTowers[i].getY(),
															arrayOfTowers[i].getBulletType());
						
						Vector2F vectorOfBulletSpeed = new Vector2F(new Vector2F(arrayOfBugs[j].getX()-arrayOfTowers[i].getX(),arrayOfBugs[j].getY()-arrayOfTowers[i].getY()),b.getSpeed());
						b.setVelocity(vectorOfBulletSpeed);
					}
				}
			}
		}
		
		for (int i = 0; i < arrayOfBullets.length; i++)
		{
			if ((arrayOfBullets[i].getX()<-30) || (arrayOfBullets[i].getX()>130) || (arrayOfBullets[i].getY()<-30) || (arrayOfBullets[i].getX()>130))
			{
				modelOfLevel.disposeBullet(arrayOfBullets[i]);
			}
		}
	}
}
