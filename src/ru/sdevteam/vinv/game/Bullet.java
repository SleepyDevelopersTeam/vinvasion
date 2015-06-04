package ru.sdevteam.vinv.game;
import java.awt.image.BufferedImage;

import ru.sdevteam.vinv.main.ResourceManager;
import ru.sdevteam.vinv.ui.BulletSprite;
import ru.sdevteam.vinv.ui.Sprite;
import ru.sdevteam.vinv.utils.PhysObject;
import ru.sdevteam.vinv.utils.Vector2F;


public class Bullet extends GameObject
{
    public enum Type {NULL,NORMAL};

    private Bullet.Type type;
    public Bullet.Type getType(){return type;}

    public PhysObject phys;
    public void setVelocity(float x,float y)
    {
        phys.setVelocity(new Vector2F(x,y));
    }

    public void setVelocity(Vector2F val)
    {
        phys.setVelocity(val);
    }

    
    private int damage;
    public int getDamage(){return damage;}

    private boolean unstoppable;
    public boolean isUnstoppable()
    {
        return unstoppable;
    }

    private int speed;
    public int getSpeed(){return speed;}


    public Bullet()
    {
        phys=new PhysObject();
        convertTo(Type.NULL);
        //this.sprite=new Sprite(ResourceManager.getBufferedImage("bullets/test_bullet"));
        this.sprite=new BulletSprite(1, BulletSprite.METAL);
    }


    public void convertTo(Bullet.Type type)
    {
        this.type=type;
        switch(this.type)
        {
            case NULL:   damage=0;
                         unstoppable=false;
                         speed=0;
                         break;
            case NORMAL: damage=15;
                         unstoppable=false;
                         speed=10;
                         break;
        }
    }
    public void update()
    {
        phys.update();
        moveTo(phys.location().getX(),phys.location().getY());
    }

    @Override
    public void setX(float nx)
    {
        super.setX(nx);
        phys.location().setX(nx);
    }

    @Override
    public void setY(float ny)
    {
        super.setY(ny);
        phys.location().setY(ny);
    }

    @Override
    public void moveTo(float nx, float ny)
    {
        super.moveTo(nx, ny);
        phys.location().moveTo(nx, ny);
    }

    @Override
    public void moveBy(float dx, float dy)
    {
        super.moveBy(dx, dy);
        phys.location().moveBy(dx, dy);
    }


}



