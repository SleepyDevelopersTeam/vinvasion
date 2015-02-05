package ru.sdevteam.vinv.game;
import javax.swing.*;
ru.sdevteam.vinv.ui.Sprite;

public abstract class GameObject implements ru.sdevteam.vinv.ui.IUpdatable,
                                            ru.sdevteam.vinv.game.IMoveble
{
    private Sprite objSprite= new Sprite();
    Sprite getSprite(){ return objSprite;}

    private boolean Dynamic;
    boolean isDynamic()
    {
        if (Dynamic==true)
        {
            return true;
        }
        return false;
    }
    
    
    public float getX(){
        return objSprite.getX();
    }
    
    public float getY(){
        return objSprite.getY();
    }

    public void setX(float nx){
        objSprite.x=nx;
    }

    public void setY(float ny){
        objSprite.y=ny;
    }

    public void moveTo(float nx, float ny){
        objSprite.moveTo(nx,ny);
    }

    public void moveBy(float dx, float dy){
        objSprite.moveBy(dx,dy);
    }

}