package ru.sdevteam.vinv.game;
import ru.sdevteam.vinv.ui.Sprite;

public abstract class GameObject implements ru.sdevteam.vinv.ui.IUpdatable,
                                            ru.sdevteam.vinv.game.IMoveable
{
    protected Sprite sprite;
    public Sprite getSprite(){ return sprite;}

	protected boolean active=true;
	public boolean isActive(){return active;}
	public void setActive(boolean b){active=b;}
	
    private boolean dynamic;
    public boolean isDynamic()
    {   
        return dynamic;
    }

	private 	
	
    private float angle;
    public void rotate(float angle){this.angle=angle;}
    public float getRotation(){return angle;}

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