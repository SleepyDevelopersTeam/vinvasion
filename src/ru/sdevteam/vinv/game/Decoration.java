package ru.sdevteam.vinv.game;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Vector;

import ru.sdevteam.vinv.ui.Sprite;

public class Decoration extends Destructable
{
	private Sprite s;
	private boolean unstable;
	private boolean hitable;
	private boolean leavesRuins;
	private Decoration ruins;
	private Explosion.Type expt;
	
	private Vector<ActionListener> destructedListener;
	public void addOnDestructedListener(ActionListener l)
	{
		destructedListener.add(l);
	}
	public void removeOnDestructedListener(ActionListener l)
	{
		destructedListener.remove(l);
	}
	@Override
	protected void onDestroyed() 
	{
		for(ActionListener listener: destructedListener)
		{
			listener.actionPerformed(new ActionEvent(this, 0, ""));
		}
		
	}
	public Decoration(Sprite a)
	{
		s = a;
		destructedListener = new Vector<ActionListener>();
		unstable  = false;
		hitable = true;
		leavesRuins = false;
		ruins = null;
		expt = null;
	}
	public Decoration(Sprite a, boolean isUnstable, boolean isHitable, boolean isleavesRuins, Decoration Ruins, Explosion.Type Expt)
	{
		s = a;
		destructedListener = new Vector<ActionListener>();
		unstable  = isUnstable;
		hitable = isHitable;
		leavesRuins = isleavesRuins;
		ruins = Ruins;
		expt = Expt;
	}
	public static Decoration createTree()
	{
		return null;
		
	}
	public static Decoration createBigTree()
	{
		return null;
		
	}
	public static Decoration createBuilding()
	{
		return null;
		
	}
	public static Decoration createBigBuilding()
	{
		return null;
		
	}
	public static Decoration createCrater()
	{
		return null;
		
	}
	public static Decoration createBurningCrater()
	{
		return null;
		
	}
	public boolean isUnstable()
	{
		return unstable;
	}
	protected void setUnstable(boolean value)
	{
		unstable = value;
	}
	public Decoration getRuins()
	{
		return ruins;
	}
	protected void setRuins(Decoration value)
	{
		ruins = value;
	}
	public boolean isLeavingRuins()
	{
		return leavesRuins;
	}
	public void setLeavingRuins(boolean value)
	{
		leavesRuins = value;
	}
	public boolean isHitable()
	{
		return hitable;
	}
	protected void setHitable(boolean value)
	{
		hitable = value;
	}
}
