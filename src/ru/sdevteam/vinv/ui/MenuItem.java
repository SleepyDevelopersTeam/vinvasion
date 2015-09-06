package ru.sdevteam.vinv.ui;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import ru.sdevteam.vinv.main.ResourceManager;
import ru.sdevteam.vinv.ui.controls.Button;
import ru.sdevteam.vinv.ui.controls.HorizontalAlignment;
import ru.sdevteam.vinv.ui.controls.VerticalAlignment;
import ru.sdevteam.vinv.utils.Colors;
import ru.sdevteam.vinv.utils.TextRenderer;

public class MenuItem extends Button
{
	private static BufferedImage 
	regular = ResourceManager.getBufferedImage("ui/menuitem_r"),
	hovered = ResourceManager.getBufferedImage("ui/menuitem_h");
	
	public MenuItem()
	{
		setSize(regular.getWidth(),regular.getHeight());
	}
	public MenuItem(String text)
	{
		super(text);
		setSize(regular.getWidth(),regular.getHeight());
	}
	@Override
	public void paint(Graphics g) {
		if( isHovered())
		{
			g.drawImage(hovered, getX(), getY(), null);
		}
		else
		{
			g.drawImage(regular, getX(), getY(), null);
		}
		
		g.setColor(getForeground());
		TextRenderer.drawString(g, getX()+getWidth()/2, getY()+getHeight()/2,
				HorizontalAlignment.CENTER, VerticalAlignment.CENTER, getText());
	}
	
	@Override
	public void update() {
		super.update();
		if (isHovered()) 
			setForeground(Colors.white()); 
		else 
			setForeground(Colors.get(10));
	}

}
