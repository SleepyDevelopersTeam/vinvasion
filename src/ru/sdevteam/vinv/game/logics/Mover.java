package ru.sdevteam.vinv.game.logics;

import java.util.Vector;
import ru.sdevteam.vinv.ui.IUpdatable;
import ru.sdevteam.vinv.game.IMoveAble;

public abstract class Mover implements IUpdatable
{
	private Vector<IMoveAble> items;
	Mover()
	{
		items = new Vector<IMoveAble>();
	}
	public void addMoveable(IMoveAble item)
	{
		items.add(item);
	}
	
	public void deleteMoveable(IMoveAble item)
	{
		items.remove(item);
	}
	
	public int getControlledCount()
	{
		return items.size();
	}
}
