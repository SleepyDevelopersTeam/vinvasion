package ru.sdevteam.vinv.ui.controls;

import ru.sdevteam.vinv.main.KeyEvent;
import ru.sdevteam.vinv.main.MouseEvent;

public abstract class FocusableControl extends Control
{
	private boolean focus;
	@Override
	public final boolean isFocused() { return focus; }
	@Override
	protected final void unfocus() { focus=false; }
	public final void focus()
	{
		if(getParent()!=null) getParent().unfocus();
		focus=true;
	}
	
	@Override
	public final void processKeyEvent(KeyEvent ev)
	{
		super.processKeyEvent(ev);
	}
	@Override
	public final void processMouseEvent(MouseEvent ev)
	{
		super.processMouseEvent(ev);
	}
}
