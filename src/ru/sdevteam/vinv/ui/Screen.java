package ru.sdevteam.vinv.ui;

import ru.sdevteam.vinv.main.Input;
import ru.sdevteam.vinv.main.ResourceManager;
import ru.sdevteam.vinv.ui.MessageBox.DialogResult;
import ru.sdevteam.vinv.ui.controls.ContainerControl;
import ru.sdevteam.vinv.ui.controls.FocusableControl;
import ru.sdevteam.vinv.utils.DebugInfo;

public abstract class Screen extends ContainerControl implements IDialogResultListener
{
	public void showMessageBox(MessageBox item)
	{
		this.addControl(item);
		item.show();
		item.addDialogResultListener(this);
	}
	@Override
	public void dialogResult(MessageBox sender, DialogResult result)
	{
		DebugInfo.addMessage("closed");
		this.removeControl(sender);
		this.focusLastElement();
	}
	protected void focusLastElement()
	{
		for(int i=controls.size()-1; i>=0; i--)
		{
			try
			{ ((FocusableControl)controls.get(i)).focus(); }
			catch(ClassCastException ex){}
		}
	}
	
	@Override
	public void update()
	{
		if(!ResourceManager.isReady()) return;
		super.update();
		while(Input.hasMoreMouseEvents())
		{
			processMouseEvent(Input.getNextMouseEvent());
			//DebugInfo.addMessage("Event processed"+Math.random());
		}
		while(Input.hasMoreKeyEvents())
		{
			processKeyEvent(Input.getNextKeyEvent());
		}
	}
}
