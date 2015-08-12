package ru.sdevteam.vinv.ui;

import ru.sdevteam.vinv.main.Input;
import ru.sdevteam.vinv.main.ResourceManager;
import ru.sdevteam.vinv.ui.controls.ContainerControl;;

public abstract class Screen extends ContainerControl implements IDrawable, IUpdatable
{
	public void showMessageBox(MessageBox item)
	{
		//this.addControl(item);
		item.show();
	}
	
	@Override
	public void update()
	{
		if(!ResourceManager.isReady()) return;
		super.update();
		while(Input.hasMoreMouseEvents())
		{
			processMouseEvent(Input.getNextMouseEvent());
		}
		while(Input.hasMoreKeyEvents())
		{
			processKeyEvent(Input.getNextKeyEvent());
		}
	}
}
