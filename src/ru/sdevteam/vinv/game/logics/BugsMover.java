package ru.sdevteam.vinv.game.logics;

import java.util.Vector;
import ru.sdevteam.vinv.game.Bug;
import ru.sdevteam.vinv.utils.Vector2F;
import ru.sdevteam.vinv.game.logics.Path;

public class BugsMover
{
	private Path path;
	private Vector <BugsMoverItem> items;
	
	BugsMover()
	{
		items = new Vector<BugsMoverItem>();
		path = new Path();
	}
	
	private Vector2F getVelocityFor(int part, float speed)
	{
		Vector2F deltaR= new Vector2F((path.getPathX(part+1)-path.getPathX(part)),(path.getPathY(part+1)-path.getPathY(part)));
		deltaR.setMagnitude(speed);
		return deltaR;
	}
	public void addBug(Bug item)
	{
		BugsMoverItem a = new BugsMoverItem(item);
		a.velocity = getVelocityFor(0, item.getSpeed());
		item.setX(path.getPathX(0));
		item.setY(path.getPathY(0));
		items.addElement(a);
	}
	
	public void deleteBug(Bug item)
	{
		items.remove(item);
	}
	
	public int getControlledCount()
	{
		return items.size();
	}
	
	public void setPath(Path a)
	{
		this.path=a;
	}
	
	public Path getCurrentPath()
	{
		return this.path;
	}
	
	public void setPath(float[] coords)
	{
		path.setCoords(coords);
	}
	
	public void setPath(Vector2F[] coords)
	{
		for (int i=0;i<coords.length;i++)
		{
			path.setCoords(coords[i]);
		}
	}
	
	public void addPoint(float x, float y)
	{
		path.addPoint(x, y);
	}

	public void update() 
	{
		for(int i=0;i<items.size();i++)
		{
			BugsMoverItem b = items.get(i);
			b.bug.moveBy((b.velocity.getX()),b.velocity.getY());
			// solving the problem of 2 point and line
			float a = new Vector2F(b.bug.getX()-path.getPathX(b.section),b.bug.getY()-path.getPathY(b.section)).getMagnitude(); // distance from bug to section
			float z = new Vector2F(path.getPathX(b.section)-path.getPathX(b.section+1), path.getPathY(b.section)-path.getPathY(b.section+1)).getMagnitude(); // distance from section+1 to section
			float c = new Vector2F(path.getPathX(b.section+1)-path.getPathX(b.section+2), path.getPathY(b.section+1)-path.getPathY(b.section+2)).getMagnitude(); // distance from section+2 to section+1
			float d = new Vector2F(b.bug.getX()-path.getPathX(b.section+2),b.bug.getY()-path.getPathY(b.section+2)).getMagnitude(); //distance from section +2 to bug 
			if (a/z>d/c)
			{
				b.section++;
				if (path.getPointsCount()-2==b.section)
				{
					items.remove(b);
					i--;
				}
			}
		}
	}
}
