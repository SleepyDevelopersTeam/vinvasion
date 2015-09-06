package ru.sdevteam.vinv.ui;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import ru.sdevteam.vinv.main.GameCanvas;
import ru.sdevteam.vinv.main.ResourceManager;
import ru.sdevteam.vinv.ui.controls.Button;
import ru.sdevteam.vinv.ui.controls.IButtonPressedListener;
import ru.sdevteam.vinv.utils.Colors;
import sun.java2d.loops.DrawRect;

public class MenuScreen extends Screen 
{
	private Menu activeMenu;
	private static BufferedImage bg = ResourceManager.getBufferedImage("ui/mainmenu");
	
	
	public void setActiveMenu(Menu m)
	{ 
		if(m==activeMenu) return;
		if (activeMenu != null) this.removeControl(activeMenu);
		this.activeMenu=m;
		this.addControl(m);
	}
	
	public Menu getActiveMenu()
	{
		return activeMenu;	
	}
	
	public MenuScreen() 
	{
		Menu MainMenu = new Menu(getX(),getY(), getWidth(), getHeight());
		MenuItem m = new MenuItem("Продолжить");
		m.addButtonPressedListener(new IButtonPressedListener() {
			
			@Override
			public void buttonPressed(Button sender) {
				//TODO Костыль с lvlnum
				GameCanvas.getInstance().setActiveScreen(new GameScreen(1));
			}
		});
		MainMenu.addButton(m);
		m = new MenuItem("Выход");
		m.addButtonPressedListener(new IButtonPressedListener() {
			
			@Override
			public void buttonPressed(Button sender) {
				System.exit(0);
			}
			
		});
		MainMenu.addButton(m);
		this.setActiveMenu(MainMenu);
	}
	
	public void setNextMenu(Menu m)
	{
		activeMenu.setPrevious(activeMenu);
		setActiveMenu(m);
	}
	
	public void goBack()
	{
		setActiveMenu(activeMenu.getPrevious());
	}

	public void goHead()
	{
		while (activeMenu.getPrevious() != null)
		{
			setActiveMenu(activeMenu.getPrevious());
		}
	}
	
	@Override
	public void paint(Graphics g) {
		g.drawImage(bg, 0, 0, null);
		super.paint(g);
	}
	
	@Override
	public void update() {
		super.update();
	}
	
}
