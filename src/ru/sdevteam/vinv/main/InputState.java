package ru.sdevteam.vinv.main;

import java.util.Vector;

public class InputState
{	
	private Vector<Integer> down;
	private int mx, my, delta;
	private boolean leftDown, rightDown, wheelDown;
	
	InputState()
	{
		down=new Vector<Integer>();
		mx=my=delta=0;
		leftDown=rightDown=wheelDown=false;
	}
	
	public boolean isKeyDown(int vk)
	{
		for(int a: down)
		{
			if(a==vk)
				return true;
		}
		return false;
	}
	public boolean isKeyDown(ControlKey ck)
	{
		for(int code: down)
		{
			if(ck.checkCode(code))
				return true;
		}
		return false;
	}
	
	public boolean isLeftDown() { return leftDown; }
	public boolean isRightDown() { return rightDown; }
	public boolean isWheelDown() { return wheelDown; }
	
	public int getWheelDelta() { return delta; }
	public int getMouseX() { return mx; }
	public int getMouseY() { return my; }
	
	InputState copy()
	{
		InputState result=new InputState();
		result.mx=mx;
		result.my=my;
		result.delta=delta;
		
		result.wheelDown=wheelDown;
		result.leftDown=leftDown;
		result.rightDown=rightDown;
		
		for(int vk: down)
		{
			result.down.add(vk);
		}
		
		return result;
	}
	
	void setLeftDown(boolean flag) { leftDown=flag; }
	void setRightDown(boolean flag) { rightDown=flag; }
	void setWheelDown(boolean flag) { wheelDown=flag; }
	
	void setWheelDelta(int d) { delta=d; }
	void setMouseCoords(int x, int y) { mx=x; my=y; }
	
	void markKeyDown(int vk)
	{
		for(int k: down)
		{
			if(k==vk)
				return;
		}
		down.add(vk);
	}
	
	void markKeyUp(int vk)
	{
		down.remove(vk);
	}
}
