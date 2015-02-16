package ru.sdevteam.vinv.game;

public class Player
{
    private Price resources;
    private int humans;
    private int basePower;
    public Price getResources(){return resources;}
    public int getHumansCount(){return humans;}
    public int getBasePower(){return basePower;}


    public Player(Price initialResources, int humans, int basePower)
    {
        this.humans=humans;
        this.basePower=basePower;
        this.resources=initialResources;
    }

    // методы

    // проигрыш игрока (например, не осталось людей на базе)
    public boolean isLost()
    {
        if (humans==0)
        {return true;}
        return false;
    }
}

