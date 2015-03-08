package ru.sdevteam.vinv.game.logics;

import java.util.Vector;
import ru.sdevteam.vinv.utils.Vector2F;

public class Path
{
	private Vector <Vector2F> path;
	
	
	public Path()
	{
		path = new Vector<Vector2F>();
	}
	
	
	public void setCoords(float[] coords)
	{
		path.clear();
		for (int i=0;i<coords.length;i++)
		{
			if (i/2==0)
			{
				path.add(new Vector2F(coords[i],coords[i+1]));
			}
		}
	}
	public void setCoords(Vector2F[] coords)
	{
		path.clear();
		for (int i=0;i<coords.length;i++)
		{
			path.addElement(coords[i]);
		}
	}
	public void addPoint(float x, float y)
	{
		path.add(new Vector2F(x,y));
	}
	public void clear()
	{
		path.clear();
	}
	public Vector2F getCoords(int pointIndex) //!! add to documentation/ about Vector2F
	{
		return path.get(pointIndex);
	}
	public void setCoords(int pointIndex, Vector2F newCoords)
	{
		path.set(pointIndex,newCoords);
	}
	public float getPathX(int pointIndex)
	{
		return path.get(pointIndex).getX();
	}
	public float getPathY(int pointIndex)
	{
		return path.get(pointIndex).getY();
	}
	public int getPointsCount()
	{
		return path.size();
	}
}
