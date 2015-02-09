package ru.sdevteam.vinv.utils;

import ru.sdevteam.vinv.ui.IUpdatable;

public class PhysObject implements IUpdatable
{	
	protected Vector2F r, v, a;
	protected float vmax;
	float friction;
	
	public Vector2F getLocation() { return r.copy(); }
	public Vector2F getVelocity() { return v.copy(); }
	public Vector2F getAccel() { return a.copy(); }
	
	public void setLocation(Vector2F val) { r=val; }
	public void setVelocity(Vector2F val) { v=val; }
	public void setAccel(Vector2F val) { a=val; }
	
	public Vector2F location() { return r; }
	public Vector2F velocity() { return v; }
	public Vector2F accel() { return a; }
	
	public float getMaxSpeed() { return vmax; }
	public float getFriction() { return friction; }
	
	public void setMaxSpeed(float val)
	{
		vmax=val;
	}
	
	public void setFriction(float val)
	{
		assert val>0F && val<=1F: "Коэффициэнт трения должен лежать в (0; 1]";
		friction=val;
	}
	
	
	public PhysObject()
	{
		r=Vector2F.O.copy();
		v=Vector2F.O.copy();
		vmax=-1;
		a=Vector2F.O.copy();
		friction=1F;
	}
	
	
	public void update()
	{	
		r.add(v);
		v.add(a);
		
		if(friction!=1F) v.multipleBy(friction);
		
		if(vmax>=0)
		{
			if(v.getMagnitude()>vmax)
			{
				v.setMagnitude(vmax);;
			}
		}
		
		// HACK: increase this value to reduce effect of 'jumping' pix by pix (due to int draw coords) 
		if(v.getMagnitude()<0.5F) v=Vector2F.O.copy();
	}
}
