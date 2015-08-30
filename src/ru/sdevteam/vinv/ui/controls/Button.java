package ru.sdevteam.vinv.ui.controls;

import java.awt.Graphics;
import java.util.Vector;

import ru.sdevteam.vinv.main.ControlKey;
import ru.sdevteam.vinv.main.KeyEvent;
import ru.sdevteam.vinv.main.MouseEvent;
import ru.sdevteam.vinv.utils.Colors;

public class Button extends FocusableControl
{
	private String text;
	public String getText() { return text; }
	public void setText(String ntext) { text=ntext; }
	
	private Vector<IButtonPressedListener> pressedListeners;
	public void addButtonPressedListener(IButtonPressedListener l)
	{
		pressedListeners.add(l);
	}
	public void removeButtonPressedListener(IButtonPressedListener l)
	{
		pressedListeners.remove(l);
	}
	
	
	public Button()
	{
		this("");
	}
	public Button(String text)
	{
		this(text, 0, 0, 80, 20);
	}
	public Button(String text, int x, int y, int width, int height)
	{
		super(x, y, width, height);
		this.text=text;
		pressedListeners=new Vector<IButtonPressedListener>();
	}
	
	
	@Override
	public void update()
	{
		
	}
	
	@Override
	public void paint(Graphics g)
	{
		// TODO: set design and drawing
		if(isFocused()) g.setColor(getBackground());
		else g.setColor(Colors.lime());
		g.fillRect(getX(), getY(), getWidth(), getHeight());
		
		g.setFont(getFont());
		g.setColor(getForeground());
		g.drawString(this.getText(), 5+getX(), 5+getY()+g.getFontMetrics().getAscent());
	}
	
	public final void press()
	{
		if(!isFocused()) focus();
		onPressed();
		for(IButtonPressedListener l: pressedListeners)
		{
			l.buttonPressed(this);
		}
	}
	
	protected void onPressed(){}
	
	@Override
	protected void onKeyUp(KeyEvent ev)
	{
		if(ControlKey.ACCEPT.checkCode(ev.getKey()))
		{
			press();
		}
	}
	
	@Override
	protected void onMouseReleased(MouseEvent ev)
	{
		press();
	}
}
