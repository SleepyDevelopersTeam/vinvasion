package ru.sdevteam.vinv.game.logics;

import java.util.Vector;
import ru.sdevteam.vinv.game.Bug;
import ru.sdevteam.vinv.utils.Vector2F;
import ru.sdevteam.vinv.game.logics.Path;

public class BugsMover
{
	private Path path;
	private Vector <BugsMoverItem> items;
	private LevelController lvlctrl;
	
	public BugsMover(LevelController a)
	{
		items = new Vector<BugsMoverItem>();
		path = new Path();
		lvlctrl=a;
	}
	
	private Vector2F getVelocityFor(float x, float y, int part, float speed)
	{
		Vector2F deltaR=new Vector2F(	(path.getPathX(part+1)-/*path.getPathX(part)*/x),
										(path.getPathY(part+1)-/*path.getPathY(part)*/y));
		deltaR.setMagnitude(speed);
		return deltaR;
	}
	public void addBug(Bug item)
	{
		BugsMoverItem a = new BugsMoverItem(item);
		item.setX(path.getPathX(0));
		item.setY(path.getPathY(0));
		
		a.velocity = getVelocityFor(item.getX(), item.getY(), 0, item.getSpeed());
		
		item.rotate(a.velocity.getDirection());
		
		items.addElement(a);
	}
	
	public void deleteBug(Bug item)
	{
		for(BugsMoverItem b: items)
		{
			if (b.bug==item)
			{
				items.remove(b);
				return;
			}
		}
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
		path.setCoords(coords);
	}
	
	public void addPoint(float x, float y)
	{
		path.addPoint(x, y);
	}
	public Vector2F getBugVelocity(Bug a)
	{
		for(int i=0;i<items.size();++i)
		{
			if (items.elementAt(i).bug==a)
			{
				return items.elementAt(i).velocity.copy();
			}
		}
		return null;
	}

	public void update() 
	{
		for(int i=0;i<items.size();i++)
		{
			BugsMoverItem b = items.get(i);
			b.bug.moveBy((b.velocity.getX()),b.velocity.getY());
			// solving the problem of 2 point and line
			// names
			// distance from bug to section
			float vecBugTosec = new Vector2F(b.bug.getX()-path.getPathX(b.section),
								   b.bug.getY()-path.getPathY(b.section)).getMagnitude();
			float vecSec1Tosec = new Vector2F(path.getPathX(b.section)-path.getPathX(b.section+1),
								   path.getPathY(b.section)-path.getPathY(b.section+1)).getMagnitude();
			// distance from section+1 to section
			float vecSec2Tosec1 = new Vector2F(path.getPathX(b.section+1)-path.getPathX(b.section+2),
								   path.getPathY(b.section+1)-path.getPathY(b.section+2)).getMagnitude();
			// distance from section+2 to section+1
			float vecBugTosec2 = new Vector2F(b.bug.getX()-path.getPathX(b.section+2),
								   b.bug.getY()-path.getPathY(b.section+2)).getMagnitude();
			//distance from section +2 to bug 
			if (vecBugTosec/vecSec1Tosec>vecBugTosec2/vecSec2Tosec1)
			{
				b.section++;
				b.velocity=getVelocityFor(b.bug.getX(), b.bug.getY(), b.section, b.bug.getSpeed());
				
				b.bug.rotate(b.velocity.getDirection());
				if (path.getPointsCount()-2==b.section)
				{
					lvlctrl.onPathEndReached(b.bug);
					items.remove(b);
					i--;
				}
			}
		}
	}
}
