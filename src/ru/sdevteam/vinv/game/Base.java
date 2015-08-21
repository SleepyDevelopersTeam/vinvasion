package ru.sdevteam.vinv.game;

import java.util.Vector;
import ru.sdevteam.vinv.ui.DecorationSprite;

public class Base extends Decoration implements IWireConnectable
{

	private Vector<IWireConnectable> vector;
	private Player player;
	
	public void connectTo(IWireConnectable item)
	{
		vector.add(item);
	}
	
	public void disconnect(IWireConnectable item)
	{
		vector.remove(item);
	}
	
	
	public Base(Player p)
	{
		super(null,false,false,false,null,null);
		this.sprite=DecorationSprite.getBaseSprite(this);
		player = p;
		hp = 100;
		vector = new Vector<IWireConnectable>();
	}
	
	
	public boolean requirePower(int power)
	{
		return player.reserveBasePower(power);
	}
	
	public void freePower(int power)
	{
		player.freeBasePower(power);
	}
	
	public boolean isCharged()
	{
		return false;
	}

	public boolean isConsumer()
	{
		return false;
	}

	public boolean isConductor()
	{
		return false;
	}

	public boolean isGenerator()
	{
		return true;
	}
}
