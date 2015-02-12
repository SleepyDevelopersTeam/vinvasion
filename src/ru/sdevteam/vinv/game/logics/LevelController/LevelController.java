package ru.sdevteam.vinv.game.logics.LevelController;

import java.awt.Graphics;
import java.util.Vector;
import ru.sdevteam.vinv.game.Bug;
import ru.sdevteam.vinv.game.Bullet;
import ru.sdevteam.vinv.game.GameObject;
import ru.sdevteam.vinv.game.Level;
import ru.sdevteam.vinv.game.Tower;
import ru.sdevteam.vinv.ui.*;

public class LevelController implements IUpdatable, IDrawable 
{
	private Level modelOfLevel;

	LevelController(Level l) 
	{
		modelOfLevel = l;
	}

	public void paint(Graphics g) 
		{
			GameObject[] arrayOfGameObjects=modelOfLevel.getBugObjects();
			for (int i=0;i<arrayOfGameObjects.length;i++)
			{
				arrayOfGameObjects[i].getSprite().paint(g);
			}
			Tower[] arrayOfTowers=this.modelOfLevel.getTowerObjects();
			Bug[] arrayOfBugs=this.modelOfLevel.getBugObjects();
			//Bullet[] arrayOfBullets=this.modelOflevel.getBulletObjects();
			for(int i=0;i<arrayOfTowers.length;i++)
			{
				arrayOfTowers[i].getSprite().paint(g);
			}
			for(int i=0;i<arrayOfBugs.length);i++)
			{
				arrayOfBugs[i].getSprite().paint(g);
			}
			for(int i=0;i<arrayOfBullets.length);i++)
			{
				arrayOfBullets[i].getSprite().paint(g);
			}
		}

	public void update() 
	{
		GameObject[] arrayOfGameObjects = modelOfLevel.getBugObjects();
		for (int i = 0; i < arrayOfGameObjects.length; i++) 
		{
			arrayOfGameObjects[i].update();
		}

		Tower[] arrayOfTowers = this.modelOfLevel.getTowerObjects();
		Bug[] arrayOfBugs = this.modelOfLevel.getBugObjects();
		// Bullet[] ArrayOfBullets=this.modelOflevel.getBulletObjects();

		for (int i = 0; i < arrayOfBullets.length; i++) 
		{
			for (int j = 0; j < arrayOfBugs.length; i++) 
			{
				if (arrayOfBullets[i].getSprite().collidesWith(arrayOfBugs[j].getSprite())) 
				{
					arrayOfBugs[j].hit(arrayOfBullets[i]);
					modelOfLevel.disposeBullet(arrayOfBullets[i]); // !!!!!!!
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
				{
					if (arrayOfTowers[i].canShoot()) 
					{
						// ArrayOfTowers[i].
						Bullet bullet = null;
						bullet.convertTo(arrayOfTowers[i].getBulletType());
						bullet.setX(arrayOfTowers[i].getX());
						bullet.setY(arrayOfTowers[i].getY());
					}
				}
			}
		}

	}

}
