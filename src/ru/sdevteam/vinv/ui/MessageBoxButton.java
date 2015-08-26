package ru.sdevteam.vinv.ui;

import ru.sdevteam.vinv.ui.MessageBox.DialogResult;
import ru.sdevteam.vinv.ui.controls.Button;

public class MessageBoxButton extends Button
{
	private DialogResult dr;
	
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
	}
	
	public MessageBoxButton(String text, DialogResult result)
	{
		super(text);
		dr=result;
	}
	
	public MessageBoxButton(String text, DialogResult result, int x, int y, int width, int height)
	{
		super(text, x, y, width, height);
		dr=result;
	}
	
	
	public DialogResult getDialogResult()
	{
		return dr;
	}
}
