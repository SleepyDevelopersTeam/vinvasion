package ru.sdevteam.vinv.game;

public class Player
{
    private int resources;
    private int humans;
    private int basePower;
	private int maxBasePower;
	
    public int getResources(){return resources;}
    public int getHumansCount(){return humans;}
    public int getBaseTotalPower(){return maxBasePower;}
	public void setMaxBasePower(int max){basePower=max;}
	public int getBasePower(){return basePower;}
	

    public Player(int initialResources, int humans, int basePower)
    {
		maxBasePower = 100;
        this.humans=humans;
		if(basePower<maxBasePower)
			this.basePower=basePower;
		else
			this.basePower=maxBasePower;
        this.resources=initialResources;
    }

 
	public  void eatHumanCount(int n)
	{
		humans-=n;
		if(humans<0)
			humans=0;
	}
	
	public void addHuman(int n)
	{
		humans+=n;
	}
	
	public int getBasedReservedPower()
	{
		return maxBasePower-basePower;
	}
	
	public boolean reserveBasePower(int volts)
	{
		if(volts<=basePower)
		{
			basePower-=volts;
			return true;
		}
		return false;
	}
	
	public void freeBasePower(int volts)
	{
		basePower+=volts;
		if(basePower>maxBasePower)
			basePower=maxBasePower;
	}
	
	public boolean withdrawResources(int res)
	{
		if(res<=resources)
		{
			resources-=res;
			return true;
		}
		return false;
	}
	
	public void addResources(int res)
	{
		resources+=res;
	}
	
    public boolean isLost()
    {
        if (humans==0)
        {return true;}
        return false;
    }
}

