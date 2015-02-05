package ru.sdevteam.vinv.game;


public class Bullet extends GameObject
{
    enum Type {NULL,NORMAL};

    private Bullet.Type type;
    Bullet.Type getType(){return type;}
    
    private int damage;
    int getDamage(){return damage;}

    private boolean unstoppable;
    boolean isUnstoppable()
    {
        if(unstoppable==true){
            return true;
        }
        return false;
    }
    private boolean using;
    void setUsing(boolean using){this.using=using;}
    boolean getUsing(){return using;}


    Bullet()
    {
        convertTo(Type.NULL);
    }


    void convertTo(Bullet.Type type)
    {
        this.type=type;
        switch(this.type)
        {
            case NULL:   damage=0;
                         unstoppable=false;
                         break;
            case NORMAL: damage=15;
                         unstoppable=false;
                         break;
        }
    }
}
