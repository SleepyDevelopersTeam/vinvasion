package ru.sdevteam.vinv.game;

import java.util.ArrayList;
import java.util.Vector;
import ru.sdevteam.vinv.ui.IUpdatable;


public class Wave implements IUpdatable
{
	private Vector<Bug> vector;
	private Vector<Integer> timeVector;
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
		timeVector.add(0);
	}
	
	public void addTimeInterval()
	{
		int t = timeVector.lastElement();
		t+=15;
		timeVector.removeElementAt(timeVector.size()-1);
		timeVector.add(t);
		
	}
	
	public void addLongTimeInterval()
	{
		int t = timeVector.lastElement();
		t+=60;
		timeVector.removeElementAt(timeVector.size()-1);
		timeVector.add(t);
	}
	
	public void clearWave()
	{
		vector.clear();
		timeVector.clear();
	}
	
	public Bug getNextBug()
	{
		if( index >= 15 + timeVector.firstElement() )
		{
			if(vector.size()>0)
			{
				Bug b = vector.firstElement();
				vector.remove(b);
				index = 0;
				timeVector.removeElementAt(0);
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
