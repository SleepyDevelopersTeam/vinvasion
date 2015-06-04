package ru.sdevteam.vinv.game;

public class Price 
{
	private int credit;
	
	public void setCredit(int cr)
	{
		credit=cr;
	}
	public void addCredir(int cr)
	{
		credit+=cr;
	}
	public int getCredit()
	{
		return credit;
	}
	
	public Price (int cr)
	{
		credit = cr;
	}
			
}