package ru.sdevteam.vinv.game;
import ru.sdevteam.vinv.ui.Sprite;

public abstract class GameObject implements ru.sdevteam.vinv.ui.IUpdatable,
                                            ru.sdevteam.vinv.game.IMoveAble
{
    private Sprite objSprite;
    public Sprite getSprite(){ return objSprite;}

    private boolean dynamic;
    public boolean isDynamic()
    {   
        return dynamic;
    }
    
    
    public float getX(){
        return (objSprite.getX()+objSprite.getWidth()/2);
    }
    
    public float getY(){
        return (objSprite.getY()+objSprite.getHeight()/2);
    }

    public void setX(float nx){
        objSprite.setX(nx-objSprite.getWidth()/2);
    }

    public void setY(float ny){
        objSprite.setY(ny-objSprite.getHeight()/2);
    }

    public void moveTo(float nx, float ny){
        objSprite.moveTo(nx-objSprite.getWidth()/2,ny-objSprite.getHeight()/2);
    }

    public void moveBy(float dx, float dy){
        objSprite.moveBy(dx,dy);
    }

}