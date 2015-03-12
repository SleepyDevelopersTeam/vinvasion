package ru.sdevteam.vinv.main;

import java.awt.event.KeyEvent;

public enum ControlKey
{
	NONE(KeyEvent.VK_UNDEFINED), 
	LEFT(KeyEvent.VK_LEFT, KeyEvent.VK_A), RIGHT(KeyEvent.VK_RIGHT, KeyEvent.VK_D), 
	UP(KeyEvent.VK_UP, KeyEvent.VK_W), DOWN(KeyEvent.VK_DOWN, KeyEvent.VK_W),
	PAUSE(KeyEvent.VK_ESCAPE),
	NEXT_TOWER(KeyEvent.VK_T), BASE(KeyEvent.VK_B), 
	SPEED_UP(KeyEvent.VK_Q), SLOW_DOWN(KeyEvent.VK_E),
	AIR_STRIKE(KeyEvent.VK_R);
	
	private int vk, vk2;
	public int getVK() { return vk; }
	public int getSecondaryVK() { return vk2; }
	public boolean checkCode(int vkCode)
	{
		if(vk==vkCode) return true;
		if(vk2==vkCode) return true;
		return false;
	}
	
	public void rebind(int newVK)
	{
		vk=newVK;
	}
	public void rebindSecondary(int newVK)
	{
		vk2=newVK;
	}
	
	public static ControlKey getByKeyCode(int vkCode)
	{
		for(ControlKey ck: values())
		{
			if(ck.checkCode(vkCode)) return ck;
		}
		return NONE;
	}
	
	private ControlKey(int k)
	{
		vk=k;
	}
	private ControlKey(int k, int k2)
	{
		vk=k; vk2=k2;
	}
}
