package ru.sdevteam.vinv.game;

import java.util.Enumeration;
import java.util.Vector;

import ru.sdevteam.vinv.ui.DecorationSprite;
import ru.sdevteam.vinv.ui.Sprite;
import ru.sdevteam.vinv.utils.DebugInfo;

public class WireHolder extends Decoration implements IWireConnectable
{
	private Vector<IWireConnectable> vector;
	
	public void connectTo(IWireConnectable item)
	{
		vector.add(item);
		onCircuitChanged();
	}
	
	public void disconnect(IWireConnectable item)
	{
		vector.remove(item);
		onCircuitChanged();
	}
	
	public Enumeration<IWireConnectable> getConnectedItems()
	{ return vector.elements(); }
	
	
	public WireHolder()
	{
		super(null,false,false,false,null,Explosion.Type.NONE);
		this.sprite=DecorationSprite.getWireHolderSprite(this);
		vector = new Vector<IWireConnectable>();
	}
	
	
	public boolean isCharged()
	{
		Vector v = new Vector();
		Base b = null;
		b = findBase(v);
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
	
	@Override
	public void onCircuitChanged()
	{
		// алгоритм, перерасчитывающий мощности всех генераторов в цепи
		// на всех потребителей в цепи
		Vector<IWireConnectable> gens=new Vector<IWireConnectable>();
		Vector<IWireConnectable> cons=new Vector<IWireConnectable>();
		Vector<IWireConnectable> wires=new Vector<IWireConnectable>();
		fillWithCircuitItems(cons, wires, gens);
		
		DebugInfo.addMessage("onCircuitChanged "+cons.size()+" "+gens.size());
		
		int reqPwr=0, gotPwr=0;
		for(int i=0; i<gens.size(); i++)
		{
			// TODO: добавить поддержку других генераторов
			Base b=(Base)gens.get(i);
			gotPwr+=b.getMaxPower();
			b.freePower(b.getMaxPower());
		}
		for(int i=0; i<cons.size(); i++)
		{
			Tower t=(Tower)cons.get(i);
			if(gotPwr>=t.getRequiredPower())
			{
				// питания хватает
				gotPwr-=t.getRequiredPower();
				t.activate();
				reqPwr+=t.getRequiredPower();
			}
			else
			{
				// питания не хватает на эту башню
				t.deactivate();
			}
		}
		for(int i=0; i<gens.size(); i++)
		{
			Base b=((Base)gens.get(i));
			if(reqPwr>b.getMaxPower())
			{
				b.requirePower(b.getMaxPower());
				reqPwr-=b.getMaxPower();
			}
			else
			{
				b.requirePower(reqPwr);
				break;
			}
		}
	}
	
	public boolean requirePower(int power)
	{		
		Vector v = new Vector();
		Base b = null;
		b = findBase(v);
		if(b == null)
			return false;
		return b.requirePower(power);
	}
	
	public void freePower(int power)
	{
		Vector v = new Vector();
		Base b = null;
		b = findBase(v);
		if(b != null);
		b.freePower(power);
	}
	
	private Base findBase(Vector v)
	{
		Base b = null,b1 = null;

		for( int i=0;i<vector.size();i++)
		{
			if(vector.get(i).isGenerator())
				return (Base)vector.get(i);
			if(!vector.get(i).isConsumer() && vector.get(i) != this && !v.contains(vector.get(i)) )
				b1 = findBase(v);
			if (b1 != null)
				b = b1;
		
		}
		v.add(this);
		return b;
			
	}
	
	// наполняет вектор visited (всеми) элементами цепи, в которую входит данный элемент
	private void fillWithCircuitItems(Vector<IWireConnectable> visited)
	{
		for(int i=0; i<vector.size(); i++)
		{
			IWireConnectable current=vector.get(i);
			if(!visited.contains(current))
			{
				if(current.isConductor())
				{
					((WireHolder)current).fillWithCircuitItems(visited);
				}
				else
				{
					visited.add(current);
				}
			}
		}
		visited.add(this);
	}
	
	private void fillWithCircuitItems(Vector<IWireConnectable> consumers,
			Vector<IWireConnectable> conductors,
			Vector<IWireConnectable> generators)
	{
		for(int i=0; i<vector.size(); i++)
		{
			IWireConnectable current=vector.get(i);
			
			if(current.isConductor())
			{
				if(!conductors.contains(current))
				{
					conductors.add(current);
					((WireHolder)current).fillWithCircuitItems(consumers, conductors, generators);
				}
			}
			if(current.isConsumer())
			{
				if(!consumers.contains(current))
				{
					consumers.add(current);
				}
			}
			if(current.isGenerator())
			{
				if(!generators.contains(current))
				{
					generators.add(current);
				}
			}
		}
		conductors.add(this);
	}
}
