package ru.sdevteam.vinv.ui.controls;

import java.awt.Graphics;

import ru.sdevteam.vinv.main.KeyEvent;
import ru.sdevteam.vinv.utils.Colors;
import ru.sdevteam.vinv.utils.DebugInfo;

public class TextBox extends FocusableControl
{
	// Пока только однострочный
	private StringBuilder text;
	public String getText() { return text.toString(); }
	public void setText(String newText)
	{
		if(readOnly) return; 
		text.replace(0, text.length(), newText);
	}
	
	private int selStart, selLength;
	public int getSelectionStart() { return selStart; }
	public int getSelectionLength() { return selLength; }
	public int getSelectionEnd() { return selStart+selLength; }
	public void select(int pos) { selStart=pos; selLength=0; }
	public void select(int pos, int len) { selStart=pos; selLength=len; }
	public void selectAll() { selStart=0; selLength=text.length(); }
	public void cursorToEnd() { selStart=text.length(); selLength=0; }
	
	private boolean readOnly;
	public boolean isReadOnly() { return readOnly; }
	public void setReadOnly(boolean value)
	{
		readOnly=value;
		setBackground(readOnly?Colors.lightGray():Colors.white());
	}
	
	
	public TextBox(String initialText, int x, int y, int width, int height)
	{
		super(x, y, width, height);
		text=new StringBuilder(initialText);
		cursorToEnd();
		readOnly=false;
		
		setForeground(Colors.black());
		setBackground(Colors.white());
	}
	public TextBox(String initialText, int x, int y)
	{
		this(initialText, x, y, 100, 18);
	}
	public TextBox(String initialText)
	{
		this(initialText, 0, 0, 100, 18);
	}
	public TextBox()
	{
		this("", 0, 0, 100, 18);
	}
	
	
	public void replaceSelected(String newText)
	{
		if(readOnly) return;
		text.replace(selStart, selLength+selStart, newText);
	}
	
	@Override
	public void paint(Graphics g)
	{
		g.setFont(getFont());
		
		g.setColor(getBackground());
		g.fillRect(getX(), getY(), getWidth(), getHeight());
		
		g.setColor(Colors.gray());
		g.drawRect(getX(), getY(), getWidth(), getHeight());
		
		int fx=2, fy, fh, fullH=getFontMetrics(g).getHeight(), asc=getFontMetrics(g).getAscent();
		fh=asc+getFontMetrics(g).getDescent();
		fy=1+asc+(getHeight()-fh)/2;
		g.setColor(getForeground());
		// рисуем выделение
		if(selLength!=0)
		{
			String tmp=text.substring(0, selStart);
			g.drawString(tmp, getX()+fx, getY()+fy);
			fx+=getFontMetrics(g).stringWidth(tmp);
			
			// выделенная часть
			tmp=text.substring(selStart, selStart+selLength);
			int w=getFontMetrics(g).stringWidth(tmp);
			// рисуем подсветку
			g.setColor(Colors.gray());
			g.fillRect(getX()+fx, getY()+fy-asc-2, w, fullH+4);
			// рисуем сам текст
			g.setColor(Colors.lightGray());
			g.drawString(tmp, getX()+fx, getY()+fy);
			fx+=w;
			
			g.setColor(getForeground());
			tmp=text.substring(selStart+selLength);
			g.drawString(tmp, getX()+fx, getY()+fy);
			fx+=getFontMetrics(g).stringWidth(tmp);
		}
		else
		{
			g.drawString(text.toString(), getX()+fx, getY()+fy);
			fx+=getFontMetrics(g).stringWidth(text.toString())+2;
			
			fx=2+getFontMetrics(g).stringWidth(text.substring(0, selStart));
			
			// анимация курсора
			if(frame<20) g.fillRect(getX()+fx-fh/8, getY()+fy-getFontMetrics(g).getDescent()*10, fh/4, fh/4);
		}
	}
	
	private int frame=0;
	@Override
	public void update()
	{
		frame++;
		if(frame>40) frame=0;
	}
	
	
	@Override
	protected void onKeyTyped(KeyEvent ev)
	{
		super.onKeyTyped(ev);
		
		if(Character.isISOControl(ev.keyChar()))
			return;
		
		text.replace(selStart, selLength+selStart, ev.keyChar()+"");
		selLength=0;
		selStart++;
	}
	
	@Override
	protected void onKeyDown(KeyEvent ev)
	{
		super.onKeyDown(ev);
		frame=0;
		
		if(ev.getKey()==java.awt.event.KeyEvent.VK_LEFT)
		{
			if(selStart>0) selStart--;
			selLength=0;
			return;
		}
		
		//ev.getActualState().isKeyDown(java.awt.event.KeyEvent.VK_SHIFT);
		// TODO: анализировать, когда зажат шифт
		
		if(ev.getKey()==java.awt.event.KeyEvent.VK_RIGHT)
		{
			if(getSelectionEnd()<text.length()) selStart=getSelectionEnd()+1;
			selLength=0;
			return;
		}
		
		if(ev.getKey()==java.awt.event.KeyEvent.VK_BACK_SPACE)
		{
			if(selLength!=0)
			{
				text.delete(selStart, selLength+selStart);
				selLength=0;
			}
			else
			{
				if(selStart!=0)
				{
					text.delete(selStart-1, selStart);
					--selStart;
				}
			}
			return;
		}
	}
}
