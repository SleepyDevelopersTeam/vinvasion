package ru.sdevteam.vinv.game;
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

    private int damage;
    public int getDamage(){return damage;}

    private boolean unstoppable;
    public boolean isUnstoppable()
    {
        return unstoppable;
    }

    private boolean using;
    public void setUsing(boolean using){this.using=using;}
    public boolean getUsing(){return using;}

    private int speed;
    public int getSpeed(){return speed;}


    public Bullet()
    {
        phys=new PhysObject();
        convertTo(Type.NULL);
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
                         speed=20;
                         break;
        }
    }
    public void update()
    {
        
    }
}
