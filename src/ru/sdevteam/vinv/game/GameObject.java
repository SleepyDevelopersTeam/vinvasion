package ru.sdevteam.vinv.game;
import java.util.Collection;
import java.util.Vector;
import ru.sdevteam.vinv.ui.Sprite;

public abstract class GameObject implements ru.sdevteam.vinv.ui.IUpdatable,
                                            ru.sdevteam.vinv.game.IMoveable
{
	private Vector<Effect> effects=new Vector<Effect>();
	private float angle;
	protected Sprite sprite;
   
    public Sprite getSprite(){ return sprite;}
	
    private boolean dynamic;
    public boolean isDynamic(){return dynamic;}	
	
    public void rotate(float angle){this.angle=angle;}
    public float getRotation(){return angle;}
	
	public java.util.Enumeration getBoundEffects()
	{
		return effects.elements();
	}
	public void bindEffect(Effect eff)
	{
		effects.add(eff);
	}
	public void bindEffectsFrom(GameObject obj)
	{
		//effects.addAll((Collection<? extends Effect>) obj.getBoundEffects());
		while(obj.getBoundEffects().hasMoreElements())
			effects.add(((Effect)obj.getBoundEffects().nextElement()));
	}
	public void clearEffects(){effects.clear();}
	public void unbindEffect(Effect eff)
	{
		effects.remove(eff);
	}
	
    public float getX()
    {
        return (sprite.getX()+sprite.getWidth()/2);
    }
    
    public float getY()
    {
        return (sprite.getY()+sprite.getHeight()/2);
    }

    public void setX(float nx)
    {
        sprite.setX(nx-sprite.getWidth()/2);
    }

    public void setY(float ny)
    {
        sprite.setY(ny-sprite.getHeight()/2);
    }

    public void moveTo(float nx, float ny)
    {
        sprite.moveTo(nx-sprite.getWidth()/2,ny-sprite.getHeight()/2);
    }

    public void moveBy(float dx, float dy)
    {
        sprite.moveBy(dx,dy);
    }
    
    @Override
    public void update()
    {
    	sprite.update();
    }
}