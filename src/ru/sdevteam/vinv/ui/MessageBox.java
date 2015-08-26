package ru.sdevteam.vinv.ui;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.security.InvalidParameterException;
import java.util.Vector;

import ru.sdevteam.vinv.main.ResourceManager;
import ru.sdevteam.vinv.ui.controls.Button;
import ru.sdevteam.vinv.ui.controls.ButtonSet;
import ru.sdevteam.vinv.ui.controls.FocusableControl;
import ru.sdevteam.vinv.ui.controls.IButtonPressedListener;
import ru.sdevteam.vinv.utils.DebugInfo;


public class MessageBox extends ButtonSet implements IButtonPressedListener
{
	public enum DialogResult { NONE, OK, CANCEL, YES, NO }
	
	// images
	private static BufferedImage large=ResourceManager.getBufferedImage("ui/msg_large");

	private String title, message;
	public String getTitle() { return title; }
	public void setTitle(String caption) { title=caption; }
	public String getMessage() { return message; }
	public void setMessage(String msg) { message=msg; }
	
	private boolean visible;
	public boolean isShown()
	{
		return visible;
	}
	
	private DialogResult result;
	public MessageBox.DialogResult getDialogResult() { return result; }
	protected void setDialogResult(DialogResult result)
	{ this.result=result; if(result!=DialogResult.NONE) close(); }
	
	// подложка
	private FocusableControl background;
	

	public MessageBox(String title, String message)
	{
		super(LayoutType.HORIZONTAL);
		this.title=title;
		this.message=message;
		result=DialogResult.NONE;
		
		background=new FocusableControl()
		{
			@Override
			public void paint(Graphics g)
			{
			}
			@Override
			public void update()
			{
			}
		};
		
		listeners=new Vector<IDialogResultListener>();
	}

	
	public void show()
	{
		visible=true;
		
		// разворачиваемся на всю доступную площадь
		moveTo(getParent().getX(), getParent().getY());
		setSize(getParent().getWidth(), getParent().getHeight());
		
		DebugInfo.addMessage(getX()+30+" "+(getY()+getHeight()-30));
		
		setStartPoint(getX()+30, getY()+getHeight()-30);
		
		background.moveTo(getX(), getY());
		background.setSize(getParent().getWidth(), getParent().getHeight());
		background.focus();
	}
	
	public void close()
	{
		visible=false;
		this.unfocus();
		onDialogResult();
	}
	
	private Vector<IDialogResultListener> listeners;
	public void addDialogResultListener(IDialogResultListener item)
	{ listeners.add(item); }
	public void removeDialogResultListener(IDialogResultListener item)
	{ listeners.remove(item); }
	
	protected void onDialogResult()
	{
		for(IDialogResultListener l: listeners)
		{
			l.dialogResult(this, result);
		}
	}
	
	
	public void addButton(DialogResult result)
	{
		addButton(new MessageBoxButton(result));
	}
	public void addButton(DialogResult result, String text)
	{
		addButton(new MessageBoxButton(text, result));
	}
	@Override
	public void addButton(Button item)
	{
		try
		{ 
			MessageBoxButton b=(MessageBoxButton)item;
			b.addButtonPressedListener(this);
			super.addButton(item);
		}
		catch(ClassCastException ex)
		{ throw new InvalidParameterException("Cannot add not a MessageBoxButton instance"); }
	}
	@Override
	public void buttonPressed(Button sender)
	{
		
	}
	
	


	public void paint(Graphics g)
	{
		if(!visible) return;
		
		int x=getX()+(background.getWidth()-large.getWidth())/2;
		int y=getY()+(background.getHeight()-large.getHeight())/2;
		
		g.drawImage(large, x, y, null);
		
		x+=5; y+=5;
		g.setFont(getFont());
		g.setColor(getForeground());
		g.drawString(title, x, y+getFontMetrics(g).getAscent());
		
		paintChildren(g);
	}

	public void update()
	{
		super.update();
	}
}
