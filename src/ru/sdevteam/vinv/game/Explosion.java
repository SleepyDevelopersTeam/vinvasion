package ru.sdevteam.vinv.game;

import ru.sdevteam.vinv.ui.ExplosionSprite;

public class Explosion extends GameObject 
{
	public enum Type { NONE, REGULAR, SLIME, BIG, BIG_SLIME, FROSTY, FIREBALL, POISONOUS };
	private Explosion.Type type;
	private int damage;
	private boolean launched; 

	
	
	public Explosion()
	{
		this.setX(0);
		this.setY(0);
		this.type = Type.NONE;
		launched = false;
	}
	public Explosion(Type type)
	{
		this.setX(0);
		this.setY(0);
		this.type = type;
		launched = false;
	}
	
	public Explosion(Type type, int x, int y)
	{
		this.type =type;
		this.setX(x);
		this.setY(y);
		launched = false;
	}
	public Type getType()
	{
		return type;
	}
	public int getDamage()
	{
		return damage;
	}
	public boolean isLaunched()
	{
		return launched;
	}
	public void setLaunched(boolean a)
	{
		launched = a;
	}
	public Effect applyingEffect()
	{
		 switch(this.type)
         {
             case NONE:
            	 return Effect.NONE;
             case REGULAR:
            	 return Effect.NONE;
             case SLIME:
            	 return Effect.NONE;
             case BIG:
            	 return Effect.DAMAGE;
             case BIG_SLIME:
            	 return Effect.NONE;
             case FROSTY:
            	 return Effect.FROST;
             case FIREBALL:
            	 return Effect.BURNING;
             case POISONOUS:
            	 return Effect.POISON;
         }
		 return null;
	}
	public void convertTo(Explosion.Type type)
	    { 
	        this.type=type;
	        switch(this.type)
	        {
	        
	            case NONE:
	            	this.sprite =new ExplosionSprite(this); 
	            	damage = 0;
	            	break;
	            case REGULAR:
	            	this.sprite =new ExplosionSprite(this);
	            	damage  = 10;
	            	break;
	            case SLIME:
	            	this.sprite =new ExplosionSprite(this);
	            	damage = 0;
	            	break;
	            case BIG:
	            	this.sprite =new ExplosionSprite(this);
	            	damage  = 15;
	            	break;
	            case BIG_SLIME:
	            	this.sprite =new ExplosionSprite(this);
	            	damage = 0;
	            	break;
	            case FROSTY:
	            	this.sprite =new ExplosionSprite(this);
	            	damage = 0;
	            	break;
	            case FIREBALL:
	            	this.sprite =new ExplosionSprite(this);
	            	damage = 5;
	            	break;
	            case POISONOUS:
	            	this.sprite =new ExplosionSprite(this);
	            	damage = 3;
	            	break;
	            
	                         
	        }
	    }
	public void explode()
	{
		launched = true;
	}
	public boolean isActive()
	{
		if ( (type != Type.NONE) && (this.getSprite().isPlaying()) && (launched) )
		{
			return true;
		}
		return false;
	}
	
}
