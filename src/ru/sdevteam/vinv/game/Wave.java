package ru.sdevteam.vinv.game;

import java.util.Vector;
import ru.sdevteam.vinv.ui.IUpdatable;


public class Wave implements IUpdatable
{
	private Vector<Bug> vector;
	private int time,index;
	
	
	public Wave()
	{
		vector = new Vector<Bug>();
		time = 0;
		index = 0;
	}
	
	
	public void addBug(Bug b)
	{
		vector.add(b);
	}
	
	public void addTimeInterval()
	{
		time+=15;
	}
	
	public void addLongTimeInterval()
	{
		time+=45;
	}
	
	public void clearWave()
	{
		vector.clear();
	}
	
	public Bug getNextBug()
	{
		if( index >= time )
		{
			if(vector.size()>0)
			{
				Bug b = vector.firstElement();
				vector.remove(b);
				index = 0;
				return b;
			}
	    }
		return null;
	}
	public Bug showNextBug()
	{
		if(vector.size()>0)
			return vector.get(1);
		return null;
	}
	public boolean isEmpty()
	{
		if( vector.size()!= 0 )
			return false;
		return true;
	}
	public void update()
	{
		index++;
	}
	
}
