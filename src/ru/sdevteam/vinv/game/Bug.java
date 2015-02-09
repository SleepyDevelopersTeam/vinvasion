package ru.sdevteam.vinv.game;

public class Bug extends Destructable
{
    private String name;
    public String getName(){return name;}

    private int speed;
    public int getSpeed(){return speed;}
    public void setSpeed(int speed){this.speed=speed;}

    public Bug()
    {
        speed=5;
    }


    public  void onDestroyed(){

    }
    public void update()
    {

    }
    //String getDescription();
    //(Buffered)Image getImage(); // возвращает изображение жука


}
