package ru.sdevteam.vinv.utils;

public class Factories
{
	public  Object getObject(PoolFactory f)
	{
		return f.getObject();
	}
}