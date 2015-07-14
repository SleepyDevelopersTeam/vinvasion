package ru.sdevteam.vinv.game;

public class Explosion extends GameObject 
{
	public enum Type { NONE, REGULAR, SLIME, BIG, BIG_SLIME, FROSTY, FIREBALL, POISONOUS };
	private Explosion.Type type;
	private int damage;
	private int x;
	private int y;
	private boolean launched; 
	
	
	public Explosion()
	{
		int x = 0;
		int y = 0;
		this.type = Type.NONE;
		launched = false;
	}
	public Explosion(Type type)
	{
		int x = 0;
		int y = 0;
		this.type = type;
		launched = false;
	}
	
	public Explosion(Type type, int x, int y)
	{
		this.type =type;
		this.x = x;
		this.y = y;
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
            	 damage = 0;
            	 return Effect.NONE;
             case REGULAR:
            	 damage  = 10;
            	 return Effect.NONE;
             case SLIME:
            	 damage = 0;
            	 return Effect.NONE;
             case BIG:
            	 damage  = 15;
            	 return Effect.DAMAGE;
             case BIG_SLIME:
            	 damage = 0;
            	 return Effect.NONE;
             case FROSTY:
            	 damage = 0;
            	 return Effect.FROST;
             case FIREBALL:
            	 damage = 5;
            	 return Effect.BURNING;
             case POISONOUS:
            	 damage = 2;
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
	            	damage = 0;
	            	break;
	            case REGULAR:
	            	damage  = 10;
	            	break;
	            case SLIME:
	            	damage = 0;
	            	break;
	            case BIG:
	            	damage  = 15;
	            	break;
	            case BIG_SLIME:
	            	damage = 0;
	            	break;
	            case FROSTY:
	            	damage = 0;
	            	break;
	            case FIREBALL:
	            	damage = 5;
	            	break;
	            case POISONOUS:
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
