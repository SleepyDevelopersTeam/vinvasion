package ru.sdevteam.vinv.ui;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import ru.sdevteam.vinv.main.ResourceManager;
import ru.sdevteam.vinv.ui.MessageBox.DialogResult;
import ru.sdevteam.vinv.ui.controls.Button;
import ru.sdevteam.vinv.ui.controls.HorizontalAlignment;
import ru.sdevteam.vinv.ui.controls.VerticalAlignment;
import ru.sdevteam.vinv.utils.Colors;
import ru.sdevteam.vinv.utils.TextRenderer;

public class MessageBoxButton extends Button
{
	private DialogResult dr;
	
	private static BufferedImage 
	reg = ResourceManager.getBufferedImage("ui/button_r"), 
	hov = ResourceManager.getBufferedImage("ui/button_h"), 
	prs = ResourceManager.getBufferedImage("ui/button_p");
	
	public MessageBoxButton(DialogResult result)
	{
		super();
		dr=result;
		switch(dr)
		{
		case OK:
			setText("Славно");
			break;
		case CANCEL:
			setText("Отнюдь");
			break;
		case YES:
			setText("Да-с");
			break;
		case NO:
			setText("Нет-с");
			break;
		}
		setSize(reg.getWidth(), reg.getHeight());
	}
	
	public MessageBoxButton(String text, DialogResult result)
	{
		super(text);
		dr=result;
		setSize(reg.getWidth(), reg.getHeight());
	}
	
	public MessageBoxButton(String text, DialogResult result, int x, int y, int width, int height)
	{
		super(text, x, y, width, height);
		dr=result;
		setSize(reg.getWidth(), reg.getHeight());
	}
	
	
	public DialogResult getDialogResult()
	{
		return dr;
	}
	
	
	@Override
	public void paint(Graphics g)
	{
		if(isPressed())
		{
			g.drawImage(prs, getX(), getY(), null);
		}
		else if(isHovered() || isFocused())
		{
			g.drawImage(hov, getX(), getY(), null);
		}
		else
		{
			g.drawImage(reg, getX(), getY(), null);
		}
		
		g.setColor(getForeground());
		TextRenderer.drawString(g, getX()+getWidth()/2, getY()+getHeight()/2,
				HorizontalAlignment.CENTER, VerticalAlignment.CENTER, getText());
	}
	
	@Override
	public void update()
	{
		super.update();
		
		if(isHovered() || isFocused()) setForeground(Colors.white());
		else setForeground(Colors.get(10));
	}
}
