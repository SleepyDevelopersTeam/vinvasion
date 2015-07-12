package ru.sdevteam.vinv.game;

public class Explosion extends GameObject 
{
	public enum Type { NONE, REGULAR, SLIME, BIG, BIG_SLIME, FROSTY, FIREBALL, POISONOUS };
	private Explosion.Type type;
	private int damage;
	private int x;
	private int y;
	
	
	public Explosion()
	{
		int x = 0;
		int y = 0;
		this.type = Type.NONE;
	}
	public Explosion(Type type)
	{
		int x = 0;
		int y = 0;
		this.type = type;
	}
	
	public Explosion(Type type, int x, int y)
	{
		this.type =type;
		this.x = x;
		this.y = y;
	}
	public Type getType()
	{
		return type;
	}
	public int getDamage()
	{
		return damage;
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
}
