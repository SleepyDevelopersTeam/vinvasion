package ru.sdevteam.vinv.game;

public class Bug extends Destructable
{
    private String name;
    String getName(){return name;}

    private int speed;
    int getSpeed(){return speed;}
    void setSpeed(int speed){this.speed=speed;}

    Bug()
    {
        speed=5;
    }


    public  void onDestroyed(){

    }
    //String getDescription();
    //(Buffered)Image getImage(); // возвращает изображение жука

}
