package ru.sdevteam.vinv.ui;

import java.awt.Graphics;


public class MessageBox implements IDrawable, IUpdatable
{
	public enum DialogResult { NONE, OK, CANCEL, YES, NO, OTHER}

	private String title,message;
	private boolean visible;
	private DialogResult result;

	public boolean isShown()
	{
		return visible;
	}

	public MessageBox(String title, String message)
	{
		this.title=title;
		this.message=message;
		result=DialogResult.NONE;
	}

	public void show()
	{
		visible=true;
	}
	public void close()
	{
		visible=false;
	}
	public void close(DialogResult withResult)
	{
		result=withResult;
		visible=false;
	}


	public String getTitle()
	{
		return title;
	}
	public String getMessage()
	{
		return message;
	}
	public void setTitle(String caption)
	{
		title=caption;
	}
	public void setMessage(String msg)
	{
		message=msg;
	}

	MessageBox.DialogResult getDialogResult()
	{
		return result;
	}


	public void paint(Graphics g)
	{
		g.drawRect(500, 500, 200, 100);
		g.drawString(message, 600, 550);
	}

	public void update()
	{
		
	}



}
