package ru.sdevteam.vinv.game;

import java.util.Vector;
import ru.sdevteam.vinv.ui.DecorationSprite;
import ru.sdevteam.vinv.ui.Sprite;

public class WireHolder extends Decoration implements IWireConnectable
{
	private Vector<IWireConnectable> vector;
	
	public void connectTo(IWireConnectable item)
	{
		vector.add(item);
	}
	
	public void disconnect(IWireConnectable item)
	{
		vector.remove(item);
	}
	
	
	public WireHolder()
	{
		super(null,false,true,false,null,Explosion.Type.NONE);
		this.sprite=DecorationSprite.getBaseSprite(this);
		vector = new Vector<IWireConnectable>();
	}
	
	
	public boolean isCharged()
	{
		Base b = null;
		b = findBase(0);
		if(b != null)
			return true;
		return false;
	}

	public boolean isConsumer()
	{
		return false;
	}

	public boolean isConductor()
	{
		return true;
	}

	public boolean isGenerator()
	{
		return false;
	}
	
	public boolean requirePower(int power)
	{		
		Base b = null;
		b = findBase(0);
		if(b == null)
			return false;
		return b.requirePower(power);
	}
	
	public void freePower(int power)
	{
		Base b = null;
		b = findBase(0);
		if(b != null);
		b.freePower(power);
	}
	
	private Base findBase(int index)
	{
		Base b = null,b1 = null;
		index++;
		if( index> 20) 
			return null;
		for( int i=0;i<vector.size();i++)
		{
			if(vector.get(i).isGenerator())
				return (Base)vector.get(i);
			if(vector.get(i).isConductor() && vector.get(i) != this )
				b1 = findBase(index);
			if (b1 != null)
				b = b1;
		}
		return b;
			
	}
}
