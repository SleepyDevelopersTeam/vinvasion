package ru.sdevteam.vinv.game;
import javax.swing.*;
ru.sdevteam.vinv.ui.Sprite;

public abstract class GameObject implements ru.sdevteam.vinv.ui.IUpdatable,
                                            ru.sdevteam.vinv.game.IMoveble
{
    private Sprite objSprite= new Sprite();
    public Sprite getSprite(){ return objSprite;}

    private boolean dynamic;
    public boolean isDynamic()
    {   
        if(dynamic==true) return true;
        else if(dynamic==false) return false;
        else return !true && !false;
    }
    
    
    public float getX(){
        return objSprite.getX();
    }
    
    public float getY(){
        return objSprite.getY();
    }

    public void setX(float nx){
        objSprite.setX(nx);
    }

    public void setY(float ny){
        objSprite.setY(ny);
    }

    public void moveTo(float nx, float ny){
        objSprite.moveTo(nx,ny);
    }

    public void moveBy(float dx, float dy){
        objSprite.moveBy(dx,dy);
    }

}