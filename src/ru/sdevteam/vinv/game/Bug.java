package ru.sdevteam.vinv.game;

public class Bug extends Destructable
{
    public enum Type {NULL,NORMAL};

    private Bug.Type type;
    public Bug.Type getType(){return type;}

    private String name;
    public String getName(){return name;}

    private int speed;
    public int getSpeed(){return speed;}


    public Bug()
    {
        setType(Type.NULL);
        objSprite= new BugSprite(this);
    }


    public  void onDestroyed(){

    }
    public void update()
    {

    }

    public void setType(Type type)
    {
        this.type=type;
        switch(this.type)
        {
            case NULL:   speed=0;
                         setMaxHp(0);
                         setHp(0);
                         break;
            case NORMAL: speed=20;
                         setMaxHp(100);
                         setHp(100);
                         break;
        }
    }
    //String getDescription();
    //(Buffered)Image getImage(); // возвращает изображение жука


}
