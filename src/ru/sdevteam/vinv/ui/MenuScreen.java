package ru.sdevteam.vinv.ui;

import java.awt.Graphics;
import java.util.Stack;

public abstract class MenuScreen extends Screen 
{
	private Menu activeMenu;
	private Menu previosMenu;
	
	
	public void setActiveMenu(Menu m)
	{ 
		this.activeMenu=m;
	}
	
	public Menu getActiveMenu()
	{
		return activeMenu;	
	}
	
	public MenuScreen() {
		// TODO Auto-generated constructor stub
	}
	
	public void setNextMenu(Menu m)
	{
		this.previosMenu=activeMenu;
		this.activeMenu=m;
	}
	
	public void goBack()
	{
		
		activeMenu=previosMenu;
	}

	public void goHead()
	{
		
	}
	
	@Override
	public void paint(Graphics g) {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public void update() {
		// TODO Auto-generated method stub
		
	}
	
}
